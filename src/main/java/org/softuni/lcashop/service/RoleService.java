package org.softuni.lcashop.service;

import org.softuni.lcashop.domain.models.service.RoleServiceModel;
import org.softuni.lcashop.domain.models.service.UserServiceModel;

import java.util.Set;

public interface RoleService {

    void seedRolesInDb();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
