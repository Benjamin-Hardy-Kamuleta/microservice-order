package com.hkbusiness.microserviceorder.service;

import com.hkbusiness.microserviceorder.exception.OrderItemOutOfStockException;
import com.hkbusiness.microserviceorder.model.Order;
import com.hkbusiness.microserviceorder.model.dto.OrderRequest;

import java.util.List;

public interface OrderService {
    String placeOrder(OrderRequest orderRequest) throws OrderItemOutOfStockException;
    List<Order> orders();
    Order findByOrderNumber(String orderNumber);

    boolean deleteOrderByOrderId(String orderI);

    boolean deleteOrderByOrderNumber(String orderNumber);

}
