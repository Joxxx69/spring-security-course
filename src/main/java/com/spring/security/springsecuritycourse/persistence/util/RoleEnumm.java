package com.spring.security.springsecuritycourse.persistence.util;

import java.util.List;
import java.util.Arrays;



public enum RoleEnumm {


    ADMINISTRATOR(Arrays.asList(
        RolePermissionEnumm.READ_ALL_PRODUCTS,
        RolePermissionEnumm.READ_ONE_PRODUCT,
        RolePermissionEnumm.CREATE_ONE_PRODUCT,
        RolePermissionEnumm.UPDATE_ONE_PRODUCT,
        RolePermissionEnumm.DISABLED_ONE_PRODUCT,

        RolePermissionEnumm.READ_ALL_CATEGORIES,
        RolePermissionEnumm.READ_ONE_CATEGORY,
        RolePermissionEnumm.CREATE_ONE_CATEGORY,
        RolePermissionEnumm.UPDATE_ONE_CATEGORY,
        RolePermissionEnumm.DISABLED_ONE_CATEGORY,

        RolePermissionEnumm.READ_MY_PROFILE
    )),
    ASSISTANT_ADMINISTRATOR(Arrays.asList(
        RolePermissionEnumm.READ_ALL_PRODUCTS,
        RolePermissionEnumm.READ_ONE_PRODUCT,
        RolePermissionEnumm.UPDATE_ONE_PRODUCT,

        RolePermissionEnumm.READ_ALL_CATEGORIES,
        RolePermissionEnumm.READ_ONE_CATEGORY,
        RolePermissionEnumm.UPDATE_ONE_CATEGORY,

        RolePermissionEnumm.READ_MY_PROFILE
    )),
    CUSTOMER(Arrays.asList(
        RolePermissionEnumm.READ_MY_PROFILE
    ));

    private List<RolePermissionEnumm> permissions;

    RoleEnumm(List<RolePermissionEnumm> permissions) {
        this.permissions = permissions;
    }

    public List<RolePermissionEnumm> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermissionEnumm> permissions) {
        this.permissions = permissions;
    }

    

}
