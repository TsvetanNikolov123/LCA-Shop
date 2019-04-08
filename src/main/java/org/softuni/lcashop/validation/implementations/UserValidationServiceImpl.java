package org.softuni.lcashop.validation.implementations;

import org.softuni.lcashop.domain.models.service.UserServiceModel;
import org.softuni.lcashop.validation.UserValidationService;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isValid(UserServiceModel user) {
        return user != null;
    }
}
