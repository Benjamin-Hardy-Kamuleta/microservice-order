package com.hkbusiness.microserviceorder.service;

import com.hkbusiness.microserviceorder.config.feign.InventoryClient;
import com.hkbusiness.microserviceorder.dao.OrderRepository;
import com.hkbusiness.microserviceorder.model.Order;
import com.hkbusiness.microserviceorder.model.OrderItem;
import com.hkbusiness.microserviceorder.model.dto.OrderRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepo;
    private final InventoryClient inventoryClient;
    @Override
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        List<OrderItem> orderItems = orderRequest.getOrderItems();
        List<OrderItem> availableProducts = inventoryClient.availableProducts();

        //Check if all requested products are in stock
        List<OrderItem> cart = new ArrayList<>();
        for(OrderItem item: orderItems){
            for (OrderItem availableProduct : availableProducts) {
                if (item.getProductCode().equals(availableProduct.getProductCode())
                        && item.getProductQuantity() <= availableProduct.getProductQuantity()) {
                    cart.add(item);
                }
            }
        }
        if(cart.size() != orderItems.size()){
            throw new RuntimeException();
        }
        order.setOrderItems(cart);
        orderRepo.save(order);
        return "Order Placed Successfully";

    }

    @Override
    public List<Order> orders() {
        return orderRepo.findAll();
    }

    @Override
    public Order findByOrderNumber(String orderNumber) {
        return orderRepo.findByOrderNumber(orderNumber);
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

}
