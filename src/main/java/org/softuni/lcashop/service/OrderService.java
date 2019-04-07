package org.softuni.lcashop.service;

import org.softuni.lcashop.domain.models.service.OrderServiceModel;

import java.util.List;

public interface OrderService {

    void createOrder(String productId, String name);

    List<OrderServiceModel> findAllOrders();
}
