package com.twinkles.Norbs_Shopping_Mall.service.appUser;

import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CreateAppUserRequest;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.AppUserDto;

public interface AppUserService {
    AppUserDto createAppUserAccount(CreateAppUserRequest createAppUserRequest);
}
