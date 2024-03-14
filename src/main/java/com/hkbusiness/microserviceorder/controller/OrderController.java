package com.hkbusiness.microserviceorder.controller;

import com.hkbusiness.microserviceorder.model.Order;
import com.hkbusiness.microserviceorder.model.dto.OrderRequest;
import com.hkbusiness.microserviceorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/orders/{orderNumber}")
    public Order order(@PathVariable String orderNumber){
        return orderService.findByOrderNumber(orderNumber);
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Boolean> deleteOrderByOrderId(@PathVariable String orderId){
        if (orderService.deleteOrderByOrderId(orderId)){
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
        return ResponseEntity.accepted().body(Boolean.FALSE);
    }

    @DeleteMapping("/order/{orderNumber}")
    public ResponseEntity<Boolean> deleteOrderByOrderNumber(@PathVariable String orderNumber){
        if (orderService.deleteOrderByOrderNumber(orderNumber)){
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
        return ResponseEntity.accepted().body(Boolean.FALSE);
    }

}
