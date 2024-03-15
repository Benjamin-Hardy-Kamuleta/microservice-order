package com.hkbusiness.microserviceorder.service;

import com.hkbusiness.microserviceorder.config.feign.InventoryClient;
import com.hkbusiness.microserviceorder.dao.OrderRepository;
import com.hkbusiness.microserviceorder.exception.OrderItemOutOfStockException;
import com.hkbusiness.microserviceorder.exception.OrderNotFoundException;
import com.hkbusiness.microserviceorder.exception.ProductCodeNotFoundException;
import com.hkbusiness.microserviceorder.model.Order;
import com.hkbusiness.microserviceorder.model.OrderItem;
import com.hkbusiness.microserviceorder.model.dto.OrderRequest;
import com.hkbusiness.microserviceorder.model.dto.OrderRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepo;
    private final InventoryClient inventoryClient;
    @Override
    public String placeOrder(OrderRequest orderRequest) throws OrderItemOutOfStockException, ProductCodeNotFoundException {
        Order order = new Order();
        List<OrderItem> cart = checkOrderItems(orderRequest);
        order.setOrderItems(cart);
        orderRepo.save(order);
        return "Order Placed Successfully";
    }
    @Override
    public Order updateOrder(OrderRequestDto orderRequestDto) throws OrderNotFoundException, OrderItemOutOfStockException, ProductCodeNotFoundException {
        Order orderToUpdate = orderRepo.findByOrderNumber(orderRequestDto.getOrderNumber());
        if (Objects.isNull(orderToUpdate)){
            throw new OrderNotFoundException("Order with number "+orderRequestDto.getOrderNumber()+" not found!");
        }
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderItems(orderRequestDto.getOrderItems());

        List<OrderItem> cart = checkOrderItems(orderRequest);
        orderToUpdate.setOrderItems(cart);
        return orderRepo.save(orderToUpdate);
    }
    @Override
    public List<Order> orders() {
        return orderRepo.findAll();
    }
    @Override
    public Order findByOrderNumber(String orderNumber) throws OrderNotFoundException {
        Order order = orderRepo.findByOrderNumber(orderNumber);
        if (Objects.isNull(order)){
            throw new OrderNotFoundException("Order with number "+orderNumber+" not found");
        }
        return order;
    }
    @Override
    public Order findOrderByOrderId(String orderId) throws OrderNotFoundException {
        return orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order with id "+orderId+" not found!"));
    }
    @Override
    public boolean deleteOrderByOrderId(String orderId) {
        Optional<Order> orderToDelete =  orderRepo.findAll().stream()
                .filter(ord -> ord.getId().equals(orderId)).findFirst();
        if (orderToDelete.isPresent()){
            orderRepo.deleteById(orderId);
            return true;
        }
        return false;
    }
    @Override
    public boolean deleteOrderByOrderNumber(String orderNumber) {
        List<Order> orders =  orderRepo.findAll();
        for (Order order: orders){
            if (order.getOrderNumber().equals(orderNumber)){
                orderRepo.delete(order);
                return true;
            }
        }
        return false;
    }

    private List<OrderItem> checkOrderItems(OrderRequest orderRequest) throws OrderItemOutOfStockException, ProductCodeNotFoundException {
        List<OrderItem> orderItems = orderRequest.getOrderItems();
        List<OrderItem> availableProducts = inventoryClient.availableProducts();
        //verify if all order items exist
        verifyOrderProducts(orderItems);
        //Check if all requested products are in stock
        List<OrderItem> cart = getValidOrderItems(orderItems, availableProducts);
        showOutOfStockProducts(orderItems, cart);
        return cart;
    }
    private  void showOutOfStockProducts(List<OrderItem> orderItems, List<OrderItem> cart) throws OrderItemOutOfStockException {
        Map<String,Integer> outOfStockItems = new HashMap<>();
        if(cart.size() != orderItems.size()){
            for (OrderItem item : orderItems){
                if (!cart.contains(item)){
                    outOfStockItems.put("product code "+item.getProductCode()+" with quantity",item.getProductQuantity());
                }
            }
            throw new OrderItemOutOfStockException("Quantity out of stock for items: " +outOfStockItems);
        }
    }
    private  List<OrderItem> getValidOrderItems(List<OrderItem> orderItems, List<OrderItem> availableProducts) {
        List<OrderItem> validOrderItems = new ArrayList<>();
        for(OrderItem item: orderItems){
            for (OrderItem availableProduct : availableProducts) {
                if (item.getProductCode().equals(availableProduct.getProductCode())
                        && item.getProductQuantity() <= availableProduct.getProductQuantity()) {
                    validOrderItems.add(item);
                }
            }
        }
        return validOrderItems;
    }
    private  void verifyOrderProducts(List<OrderItem> orderItems) throws ProductCodeNotFoundException {
        List<OrderItem> allExistingProductsInStock = inventoryClient.inventories();
        List<String> orderItemsProductCodes = orderItems.stream().map(OrderItem::getProductCode).toList();
        List<String> existingProductCodes = allExistingProductsInStock.stream().map(OrderItem::getProductCode).toList();
        //get non-existing products
        List<String> productCodesNotFound = new ArrayList<>();
        for (String productCode: orderItemsProductCodes){
            if (!existingProductCodes.contains(productCode)){
                productCodesNotFound.add(productCode);
            }
        }
        if (!productCodesNotFound.isEmpty()){
            throw new ProductCodeNotFoundException("Product(s) code(s) not found: "+productCodesNotFound);
        }

    }

}
