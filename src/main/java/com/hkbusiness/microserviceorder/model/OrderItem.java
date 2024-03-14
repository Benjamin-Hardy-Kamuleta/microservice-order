package com.hkbusiness.microserviceorder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class OrderItem {
   private String productCode;
    private Integer productQuantity;
    private Double productPrice;
}
