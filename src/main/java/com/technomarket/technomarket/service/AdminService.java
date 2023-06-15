package com.technomarket.technomarket.service;

import com.technomarket.technomarket.dto.admin.AdminUserDto;
import com.technomarket.technomarket.dto.admin.AdminUserSummaryDto;

import java.util.List;

public interface AdminService {

    AdminUserDto getUserDtoById(Long userId);

    List<AdminUserSummaryDto> getAllAdminUsersSummaryDto();

    void deleteUserById(Long userId);

}
