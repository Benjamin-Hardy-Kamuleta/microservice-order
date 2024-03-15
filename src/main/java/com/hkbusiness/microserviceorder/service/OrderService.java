package com.hkbusiness.microserviceorder.service;

import com.hkbusiness.microserviceorder.exception.OrderItemOutOfStockException;
import com.hkbusiness.microserviceorder.exception.OrderNotFoundException;
import com.hkbusiness.microserviceorder.exception.ProductCodeNotFoundException;
import com.hkbusiness.microserviceorder.model.Order;
import com.hkbusiness.microserviceorder.model.dto.OrderRequest;
import com.hkbusiness.microserviceorder.model.dto.OrderRequestDto;

import java.util.List;

public interface OrderService {
    String placeOrder(OrderRequest orderRequest) throws OrderItemOutOfStockException, ProductCodeNotFoundException;
    List<Order> orders();
    Order findByOrderNumber(String orderNumber) throws OrderNotFoundException;
    Order findOrderByOrderId(String orderId) throws OrderNotFoundException;
    Order updateOrder(OrderRequestDto orderRequestDto) throws OrderNotFoundException, OrderItemOutOfStockException, ProductCodeNotFoundException;

    boolean deleteOrderByOrderId(String orderI);

    boolean deleteOrderByOrderNumber(String orderNumber);

}
