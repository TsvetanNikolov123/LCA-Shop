package org.softuni.lcashop.web.controllers;

import org.softuni.lcashop.domain.models.rest.ProductOrderRequestModel;
import org.softuni.lcashop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/order")
public class OrdersApiController {
    private final OrderService orderService;

    @Autowired
    public OrdersApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/submit")
    public void submitOrder(@RequestBody ProductOrderRequestModel model, Principal principal) throws Exception {
        String name = principal.getName();
        orderService.createOrder(model.getId(), name);
    }
}
