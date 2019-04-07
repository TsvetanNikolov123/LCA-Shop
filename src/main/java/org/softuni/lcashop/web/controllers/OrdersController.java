package org.softuni.lcashop.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.lcashop.domain.models.rest.ProductOrderRequestModel;
import org.softuni.lcashop.domain.models.service.ProductServiceModel;
import org.softuni.lcashop.domain.models.view.OrderViewModel;
import org.softuni.lcashop.domain.models.view.ProductDetailViewModel;
import org.softuni.lcashop.service.OrderService;
import org.softuni.lcashop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrdersController extends BaseController {

    private final ProductService productService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrdersController(ProductService productService, OrderService orderService, ModelMapper modelMapper) {
        this.productService = productService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView orderProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel serviceModel = productService.findProductById(id);
        ProductDetailViewModel viewModel = modelMapper.map(serviceModel, ProductDetailViewModel.class);
        modelAndView.addObject("product", viewModel);
        return super.view("order/product", modelAndView);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getAllOrders(ModelAndView modelAndView) {
        List<OrderViewModel> viewModels = orderService.findAllOrders()
                .stream()
                .map(o -> modelMapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("orders", viewModels);
        return view("order/list-orders", modelAndView);
    }
}
