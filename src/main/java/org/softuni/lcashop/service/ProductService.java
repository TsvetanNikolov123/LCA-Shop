package org.softuni.lcashop.service;

import org.softuni.lcashop.domain.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {

    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel findProductById(String id);
}
