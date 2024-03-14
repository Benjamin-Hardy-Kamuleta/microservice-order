package com.hkbusiness.microserviceorder.config.feign;

import com.hkbusiness.microserviceorder.model.OrderItem;
import com.hkbusiness.microserviceorder.model.dto.OrderItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("MICROSERVICE-INVENTORY")
public interface InventoryClient {

    @GetMapping("/api/inventories/products")
    public List<OrderItem> availableProducts();
}
