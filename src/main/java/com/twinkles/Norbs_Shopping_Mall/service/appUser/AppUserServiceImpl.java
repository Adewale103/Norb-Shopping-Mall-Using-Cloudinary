package com.twinkles.Norbs_Shopping_Mall.service.appUser;

import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CreateAppUserRequest;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.AppUserDto;
import com.twinkles.Norbs_Shopping_Mall.data.repository.AppUserRepository;
import com.twinkles.Norbs_Shopping_Mall.security.jwt.TokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService{
    private AppUserRepository appUserRepository;
    private ApplicationEventPublisher applicationEventPublisher;
    private TokenProvider tokenProvider;
    private ModelMapper modelMapper;

    @Override
    public AppUserDto createAppUserAccount(CreateAppUserRequest createAppUserRequest) {
        return null;
    }
}
