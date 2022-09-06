package com.twinkles.Norbs_Shopping_Mall.service.appUser;

import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CreateAppUserRequest;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.AppUserDto;
import com.twinkles.Norbs_Shopping_Mall.data.model.AppUser;
import com.twinkles.Norbs_Shopping_Mall.data.repository.AppUserRepository;
import com.twinkles.Norbs_Shopping_Mall.security.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AppUserServiceImplTest {
    private AppUserService appUserService;
    String host;
    CreateAppUserRequest createAppUserRequest;
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private TokenProvider tokenProvider;

    @BeforeEach
    public void setUp(){
        appUserService = new AppUserServiceImpl(appUserRepository, applicationEventPublisher,tokenProvider,modelMapper,bCryptPasswordEncoder);
        host = "http://localhost:8080/api/v1/auth/signup";
        createAppUserRequest = CreateAppUserRequest.builder()
                .firstName("Dewale")
                .lastName("Adeyinka")
                .email("adeyinkawale11@gmail.com")
                .password("Password123")
                .phoneNumber("09027353832")
                .build();
    }

    @Test
    @DisplayName("Create appUser account test")
    public void createAppUserAccountTest(){
        AppUser appUserToReturn = AppUser.builder()
                .id(1L)
                .firstName(createAppUserRequest.getFirstName())
                .lastName(createAppUserRequest.getLastName())
                .email(createAppUserRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(createAppUserRequest.getPassword()))
                .phoneNumber(createAppUserRequest.getPhoneNumber())
                .build();
        AppUserDto appUserDtoToReturn = AppUserDto.builder()
                .id(1L)
                .firstName(createAppUserRequest.getFirstName())
                .lastName(createAppUserRequest.getLastName())
                .email(createAppUserRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(createAppUserRequest.getPassword()))
                .phoneNumber(createAppUserRequest.getPhoneNumber())
                .build();

        when(appUserRepository.findAppUserByEmail("adeyinkawale11@gmail.com")).thenReturn(Optional.empty());
        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUserToReturn);
        when(modelMapper.map(appUserToReturn, AppUserDto.class)).thenReturn(appUserDtoToReturn);
        AppUserDto appUserDto = appUserService.createAppUserAccount(host,createAppUserRequest);

        assertThat(appUserDto.getEmail()).isEqualTo("adeyinkawale11@gmail.com");
        assertThat(appUserDto.getId()).isEqualTo(1L);
        assertThat(appUserDto.getFirstName()).isEqualTo("Dewale");

    }

}