package com.twinkles.Norbs_Shopping_Mall.service.appUser;

import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CreateAppUserRequest;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.AppUserDto;
import com.twinkles.Norbs_Shopping_Mall.data.model.AppUser;

import java.util.List;

public interface AppUserService {
    AppUserDto createAppUserAccount(String host, CreateAppUserRequest createAppUserRequest);
    List<AppUserDto> findAllAppUsers();
    void verifyUser(String token);
    AppUser findAppUserByEmail(String email);
}
