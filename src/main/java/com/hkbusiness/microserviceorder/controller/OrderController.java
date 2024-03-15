package com.hkbusiness.microserviceorder.controller;

import com.hkbusiness.microserviceorder.exception.OrderItemOutOfStockException;
import com.hkbusiness.microserviceorder.exception.OrderNotFoundException;
import com.hkbusiness.microserviceorder.exception.ProductCodeNotFoundException;
import com.hkbusiness.microserviceorder.model.Order;
import com.hkbusiness.microserviceorder.model.dto.OrderRequest;
import com.hkbusiness.microserviceorder.model.dto.OrderRequestDto;
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
    public String placeOrder(@RequestBody OrderRequest orderRequest) throws OrderItemOutOfStockException, ProductCodeNotFoundException {
        orderService.placeOrder(orderRequest);
        return "Order successfully placed";
    }
    @GetMapping("/orders")
    public List<Order> orders(){
        return orderService.orders();
    }

    @GetMapping("/orders/{orderNumber}")
    public Order order(@PathVariable String orderNumber) throws OrderNotFoundException {
       return orderService.findByOrderNumber(orderNumber);
    }

    @GetMapping("/order/{orderId}")
    public Order findOrderByOrderId(@PathVariable String orderId) throws OrderNotFoundException {
        return orderService.findOrderByOrderId(orderId);
    }

    @PutMapping("/orders")
    public Order updateOrder(@RequestBody OrderRequestDto orderRequestDto) throws OrderNotFoundException, OrderItemOutOfStockException, ProductCodeNotFoundException {
        return orderService.updateOrder(orderRequestDto);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Boolean> deleteOrderByOrderId(@PathVariable String orderId){
        if (orderService.deleteOrderByOrderId(orderId)){
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
        return ResponseEntity.accepted().body(Boolean.FALSE);
    }

    @DeleteMapping("/orders/{orderNumber}")
    public ResponseEntity<Boolean> deleteOrderByOrderNumber(@PathVariable String orderNumber){
        if (orderService.deleteOrderByOrderNumber(orderNumber)){
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
        return ResponseEntity.accepted().body(Boolean.FALSE);
    }

}
