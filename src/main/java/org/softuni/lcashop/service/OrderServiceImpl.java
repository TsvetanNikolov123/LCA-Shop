package org.softuni.lcashop.service;

import org.softuni.lcashop.domain.entities.Order;
import org.softuni.lcashop.domain.entities.Product;
import org.softuni.lcashop.domain.entities.User;
import org.softuni.lcashop.domain.models.service.UserServiceModel;
import org.softuni.lcashop.repository.OrderRepository;
import org.softuni.lcashop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public void createOrder(String productId, String name) {
        UserServiceModel userModel = userService.findUserByUserName(name);
        Product product = productRepository.findById(productId).orElseThrow();

        User user = new User();
        user.setId(userModel.getId());

        Order order = new Order();
        order.setProduct(product);
        order.setUser(user);

        orderRepository.save(order);
    }
}
