package org.softuni.lcashop.service;

import org.modelmapper.ModelMapper;
import org.softuni.lcashop.domain.entities.Order;
import org.softuni.lcashop.domain.entities.Product;
import org.softuni.lcashop.domain.entities.User;
import org.softuni.lcashop.domain.models.service.OrderServiceModel;
import org.softuni.lcashop.domain.models.service.UserServiceModel;
import org.softuni.lcashop.repository.OrderRepository;
import org.softuni.lcashop.repository.ProductRepository;
import org.softuni.lcashop.validation.ProductValidationService;
import org.softuni.lcashop.validation.UserValidationService;
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
    private final UserValidationService userValidationService;
    private final ProductValidationService productValidationService;

    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            UserService userService,
            ProductRepository productRepository,
            ModelMapper modelMapper,
            UserValidationService userValidationService,
            ProductValidationService productValidationService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userValidationService = userValidationService;
        this.productValidationService = productValidationService;
    }

    @Override
    public void createOrder(String productId, String name) throws Exception {
        UserServiceModel userModel = userService.findUserByUserName(name);
        if (!userValidationService.isValid(userModel)) {
            throw new Exception();
        }

        Product product = productRepository.findById(productId)
                .filter(productValidationService::isValid)
                .orElseThrow(Exception::new);

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

    @Override
    public List<OrderServiceModel> findOrdersByCustomer(String username) {
        return orderRepository.findAllByUser_Username(username)
                .stream()
                .map(o -> modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }
}
