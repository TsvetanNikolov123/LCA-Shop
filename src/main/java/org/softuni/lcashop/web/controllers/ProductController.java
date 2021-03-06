package org.softuni.lcashop.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.lcashop.domain.models.binding.ProductAddBindingModel;
import org.softuni.lcashop.domain.models.service.ProductServiceModel;
import org.softuni.lcashop.domain.models.view.ProductAllViewModel;
import org.softuni.lcashop.domain.models.view.ProductDetailViewModel;
import org.softuni.lcashop.error.ProductNotFoundException;
import org.softuni.lcashop.service.CategoryService;
import org.softuni.lcashop.service.CloudinaryService;
import org.softuni.lcashop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final CloudinaryService cloudinaryService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, CloudinaryService cloudinaryService, CategoryService categoryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.cloudinaryService = cloudinaryService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProduct() {
        return super.view("product/add-product");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProductConfirm(@ModelAttribute ProductAddBindingModel model) throws IOException {
        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        productServiceModel.setCategories(
                this.categoryService.findAllCategories()
                        .stream()
                        .filter(categoryServiceModel -> model.getCategories().contains(categoryServiceModel.getId()))
                        .collect(Collectors.toList())
        );
        productServiceModel.setImageUrl(this.cloudinaryService.uploadImage(model.getImage()));
        this.productService.addProduct(productServiceModel);
        return super.redirect("/products/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allProducts(ModelAndView modelAndView) {
        modelAndView.addObject("products", this.productService
                .findAllProducts()
                .stream()
                .map(productServiceModel -> this.modelMapper.map(productServiceModel, ProductAllViewModel.class))
                .collect(Collectors.toList()));

        return super.view("product/all-products", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsProduct(@PathVariable String id, ModelAndView modelAndView) {
        modelAndView.addObject("product",
                this.modelMapper.map(this.productService.findProductById(id), ProductDetailViewModel.class));

        return super.view("product/details", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductAddBindingModel model = this.modelMapper.map(productServiceModel, ProductAddBindingModel.class);
        model.setCategories(productServiceModel.getCategories().stream().map(c -> c.getName()).collect(Collectors.toList()));

        modelAndView.addObject("product", model);
        modelAndView.addObject("productId", id);

        return super.view("product/edit-product", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProductConfirm(@PathVariable String id, @ModelAttribute ProductAddBindingModel model) {
        this.productService.editProduct(id, this.modelMapper.map(model, ProductServiceModel.class));
        // todo fix product categories disappearing
        return super.redirect("/products/details/" + id);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductAddBindingModel model = this.modelMapper.map(productServiceModel, ProductAddBindingModel.class);
        model.setCategories(productServiceModel.getCategories().stream().map(c -> c.getName()).collect(Collectors.toList()));

        modelAndView.addObject("product", model);
        modelAndView.addObject("productId", id);

        return super.view("product/delete-product", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProductConfirm(@PathVariable String id) {
        this.productService.deleteProduct(id);

        return super.redirect("/products/all");
    }

    @GetMapping("/fetch/{category}")
    @ResponseBody
    @PreAuthorize("isAnonymous()")
    public List<ProductAllViewModel> fetchByCategory(@PathVariable String category) {
        if(category.equals("all")) {
            return this.productService.findAllProducts()
                    .stream()
                    .map(product -> this.modelMapper.map(product, ProductAllViewModel.class))
                    .collect(Collectors.toList());
        }

        return this.productService.findAllByCategory(category)
                .stream()
                .map(product -> this.modelMapper.map(product, ProductAllViewModel.class))
                .collect(Collectors.toList());
    }
    // todo to list products by Category

    @ExceptionHandler(ProductNotFoundException.class)
    public ModelAndView handlerProductNotFound(ProductNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());

        return modelAndView;
    }
}
