package org.softuni.lcashop.validation;

import org.softuni.lcashop.domain.models.service.UserServiceModel;

public interface UserValidationService {
    boolean isValid(UserServiceModel user);
}
