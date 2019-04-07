package org.softuni.lcashop.service;

import org.modelmapper.ModelMapper;
import org.softuni.lcashop.domain.entities.Order;
import org.softuni.lcashop.domain.entities.Product;
import org.softuni.lcashop.domain.entities.User;
import org.softuni.lcashop.domain.models.service.OrderServiceModel;
import org.softuni.lcashop.domain.models.service.UserServiceModel;
import org.softuni.lcashop.repository.OrderRepository;
import org.softuni.lcashop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            UserService userService,
            ProductRepository productRepository,
            ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
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

    @Override
    public List<OrderServiceModel> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(o -> modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }
}
