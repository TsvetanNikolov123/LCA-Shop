package org.softuni.lcashop.unitTests.validation;

import org.junit.Before;
import org.junit.Test;
import org.softuni.lcashop.domain.entities.Category;
import org.softuni.lcashop.domain.entities.Product;
import org.softuni.lcashop.domain.models.service.CategoryServiceModel;
import org.softuni.lcashop.domain.models.service.ProductServiceModel;
import org.softuni.lcashop.validation.ProductValidationService;
import org.softuni.lcashop.validation.implementations.ProductValidationServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductValidationServiceTests {
    private ProductValidationService service;

    @Before
    public void setupTest() {
        service = new ProductValidationServiceImpl();
    }

    @Test
    public void isValidWithProduct_whenValid_true() {
        Product product = new Product();
        product.setCategories(List.of(new Category()));
        boolean result = service.isValid(product);
        assertTrue(result);
    }

    @Test
    public void isValidWithProduct_whenNull_false() {
        Product product = null;
        boolean result = service.isValid(product);
        assertFalse(result);
    }

    @Test
    public void isValidWithProductServiceModel_whenNull_false() {
        ProductServiceModel product = null;
        boolean result = service.isValid(product);
        assertFalse(result);
    }

    @Test
    public void isValidWithProductServiceModel_whenValid_true() {
        ProductServiceModel product = new ProductServiceModel();
        product.setCategories(List.of(new CategoryServiceModel()));
        boolean result = service.isValid(product);
        assertTrue(result);
    }

    @Test
    public void isValidProductWithCategories_whenNull_false() {
        ProductServiceModel product = new ProductServiceModel();
        product.setCategories(null);

        boolean result = service.isValid(product);
        assertFalse(result);
    }


    @Test
    public void isValidProductWithCategories_whenEmptyList_false() {
        ProductServiceModel product = new ProductServiceModel();
        product.setCategories(new ArrayList<>());

        boolean result = service.isValid(product);
        assertFalse(result);
    }
}
