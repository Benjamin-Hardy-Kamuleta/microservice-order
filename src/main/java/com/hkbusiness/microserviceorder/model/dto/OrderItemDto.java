package com.hkbusiness.microserviceorder.model.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private String productCode;
    @Positive
    private Integer productQuantity;
    private Double productPrice;
}
