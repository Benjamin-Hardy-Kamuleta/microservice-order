package com.hkbusiness.microserviceorder.dao;

import com.hkbusiness.microserviceorder.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Order findByOrderNumber(String orderNumber);
}
