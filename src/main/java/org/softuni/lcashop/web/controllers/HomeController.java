package org.softuni.lcashop.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.lcashop.domain.models.view.CategoryViewModel;
import org.softuni.lcashop.domain.models.view.ProductAllViewModel;
import org.softuni.lcashop.service.CategoryService;
import org.softuni.lcashop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private static final String CATEGORIES_ATTRIBUTE_NAME = "categories";
    private static final String PRODUCTS_ATTRIBUTE_NAME = "products";
    private static final String INDEX_VIEW_NAME = "index";
    private static final String INDEX_ROUTE = "/";
    private static final String HOME_ROUTE = "/home";
    private static final String HOME_VIEW_NAME = "home";

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    @Autowired
    public HomeController(
            CategoryService categoryService,
            ModelMapper modelMapper,
            ProductService productService) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @GetMapping(INDEX_ROUTE)
    @PreAuthorize("isAnonymous()")
    public ModelAndView index(ModelAndView modelAndView) {
        List<CategoryViewModel> categoryViewModels = this.categoryService
                .findAllCategories()
                .stream()
                .map(category -> this.modelMapper.map(category, CategoryViewModel.class))
                .collect(Collectors.toList());

        List<ProductAllViewModel> productAllViewModels = this.productService.findAllProducts()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(CATEGORIES_ATTRIBUTE_NAME, categoryViewModels);
        modelAndView.addObject(PRODUCTS_ATTRIBUTE_NAME, productAllViewModels);

        return super.view(INDEX_VIEW_NAME, modelAndView);
    }

    @GetMapping(HOME_ROUTE)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView) {
        List<CategoryViewModel> categoryViewModels = this.categoryService
                .findAllCategories()
                .stream()
                .map(category -> this.modelMapper.map(category, CategoryViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(CATEGORIES_ATTRIBUTE_NAME, categoryViewModels);

        return super.view(HOME_VIEW_NAME, modelAndView);
    }
}
