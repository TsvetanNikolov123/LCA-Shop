package org.softuni.lcashop.service;

import org.softuni.lcashop.domain.models.service.UserServiceModel;

public interface RoleService {

    void seedRolesInDb();

    void assignUserRoles(UserServiceModel userServiceModel);
}
