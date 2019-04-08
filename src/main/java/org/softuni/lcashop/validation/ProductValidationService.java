package org.softuni.lcashop.validation;

import org.softuni.lcashop.domain.entities.Product;
import org.softuni.lcashop.domain.models.service.ProductServiceModel;

public interface ProductValidationService {
    boolean isValid(Product product);

    boolean isValid(ProductServiceModel product);
}
