package org.softuni.lcashop.validation.implementations;

import org.softuni.lcashop.domain.entities.Product;
import org.softuni.lcashop.domain.models.service.CategoryServiceModel;
import org.softuni.lcashop.domain.models.service.ProductServiceModel;
import org.softuni.lcashop.validation.ProductValidationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductValidationServiceImpl implements ProductValidationService {
    @Override
    public boolean isValid(Product product) {
        return product != null;
    }

    @Override
    public boolean isValid(ProductServiceModel product) {
        return product != null
                && areCategoriesValid(product.getCategories());
    }

    private boolean areCategoriesValid(List<CategoryServiceModel> categories) {
        return categories != null && !categories.isEmpty();
    }
}
