package com.hkbusiness.microserviceorder.model.dto;

import com.hkbusiness.microserviceorder.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<OrderItem> orderItems;

}
