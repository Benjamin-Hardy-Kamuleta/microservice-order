package com.hkbusiness.microserviceorder.controller;

import com.hkbusiness.microserviceorder.model.Order;
import com.hkbusiness.microserviceorder.model.dto.OrderRequest;
import com.hkbusiness.microserviceorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order successfully placed";
    }
    @GetMapping("/orders")
    public List<Order> orders(){
        return orderService.orders();
    }
}
