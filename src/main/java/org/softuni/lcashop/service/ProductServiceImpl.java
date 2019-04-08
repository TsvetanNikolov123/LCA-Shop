package org.softuni.lcashop.service;

import org.modelmapper.ModelMapper;
import org.softuni.lcashop.domain.entities.Category;
import org.softuni.lcashop.domain.entities.Product;
import org.softuni.lcashop.domain.models.service.ProductServiceModel;
import org.softuni.lcashop.repository.ProductRepository;
import org.softuni.lcashop.validation.ProductValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductValidationService productValidationService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            CategoryService categoryService,
            ProductValidationService productValidationService,
            ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productValidationService = productValidationService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel addProduct(ProductServiceModel productServiceModel) {
        if (!productValidationService.isValid(productServiceModel)) {
            throw new IllegalArgumentException();
        }
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        product = this.productRepository.save(product);
        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return this.productRepository
                .findAll()
                .stream()
                .map(product -> this.modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductById(String id) {
        return this.productRepository
                .findById(id)
                .map(product -> this.modelMapper.map(product, ProductServiceModel.class))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel) {
        Product product = this.productRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        productServiceModel.setCategories(
                this.categoryService.findAllCategories()
                        .stream()
                        .filter(c -> productServiceModel.getCategories().contains(c.getId()))
                        .collect(Collectors.toList())
        );

        product.setName(productServiceModel.getName());
        product.setDescription(productServiceModel.getDescription());
        product.setPrice(productServiceModel.getPrice());
        product.setCategories(
                productServiceModel.getCategories()
                        .stream()
                        .map(c -> this.modelMapper.map(c, Category.class))
                        .collect(Collectors.toList()));

        return this.modelMapper
                .map(productRepository.saveAndFlush(product), ProductServiceModel.class);
    }

    @Override
    public ProductServiceModel deleteProduct(String id) {
        Product product = this.productRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);
        this.productRepository.delete(product);
        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    // todo add method findAllByCategory
}
