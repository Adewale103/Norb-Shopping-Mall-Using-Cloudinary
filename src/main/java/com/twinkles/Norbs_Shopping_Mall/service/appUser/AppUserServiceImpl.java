package com.twinkles.Norbs_Shopping_Mall.service.appUser;

import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CreateAppUserRequest;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.VerificationSmsRequest;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.AppUserDto;
import com.twinkles.Norbs_Shopping_Mall.data.model.AppUser;
import com.twinkles.Norbs_Shopping_Mall.data.model.Role;
import com.twinkles.Norbs_Shopping_Mall.data.repository.AppUserRepository;
import com.twinkles.Norbs_Shopping_Mall.events.SendMessageEvent;
import com.twinkles.Norbs_Shopping_Mall.security.jwt.TokenProvider;
import com.twinkles.Norbs_Shopping_Mall.web.exception.NorbsShoppingMallException;
import com.twinkles.Norbs_Shopping_Mall.web.exception.UserAlreadyExistException;
import com.twinkles.Norbs_Shopping_Mall.web.exception.UserNotFoundException;
import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final TokenProvider tokenProvider;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppUserServiceImpl(AppUserRepository appUserRepository, ApplicationEventPublisher applicationEventPublisher,
                              TokenProvider tokenProvider, ModelMapper modelMapper,
                              BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.tokenProvider = tokenProvider;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public AppUserDto createAppUserAccount(String host, CreateAppUserRequest createAppUserRequest) {
        validate(createAppUserRequest);
        AppUser appUser = new AppUser(createAppUserRequest.getFirstName(),createAppUserRequest.getLastName()
        ,createAppUserRequest.getEmail(), bCryptPasswordEncoder.encode(createAppUserRequest.getPassword()), createAppUserRequest.getPhoneNumber());

        AppUser savedAppUser = appUserRepository.save(appUser);
        String token = tokenProvider.generateTokenForVerification(String.valueOf(savedAppUser.getId()));

        VerificationSmsRequest smsRequest = VerificationSmsRequest.builder()
                .appUserFullName(String.format("%s %s", savedAppUser.getFirstName(), savedAppUser.getLastName()))
                .receiverPhoneNumber(createAppUserRequest.getPhoneNumber())
                .verificationToken(token)
                .domainUrl(host)
                .build();
        SendMessageEvent sendMessageEvent = new SendMessageEvent(smsRequest);
        applicationEventPublisher.publishEvent(sendMessageEvent);
        return modelMapper.map(savedAppUser,AppUserDto.class);
    }

    @Override
    public List<AppUserDto> findAllAppUsers() {
        return appUserRepository.findAll().stream()
                .map(user -> modelMapper.map(user,AppUserDto.class)).toList();
    }

    @Override
    public AppUser findAppUserByEmail(String email) {
        return appUserRepository.findAppUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email: " +email+ " not found!"));
    }

    private void validate(CreateAppUserRequest request) throws NorbsShoppingMallException {
        AppUser foundUser = Optional.ofNullable(appUserRepository.findAppUserByEmail(request.getEmail())).get().orElse(null);
        if (foundUser != null) {
            throw new UserAlreadyExistException("user with" + request.getEmail() + " already exists!", 400);
        }
    }
    @Override
    public void verifyUser(String token) {
        Claims claims = tokenProvider.getAllClaimsFromJWTToken(token);
        Function<Claims, String> getSubjectFromClaim = Claims::getSubject;
        Function<Claims, Date> getExpirationDateFromClaim = Claims::getExpiration;
        Function<Claims, Date> getIssuedAtDateFromClaim= Claims::getIssuedAt;

        String userId = getSubjectFromClaim.apply(claims);
        if (userId == null){
            throw new NorbsShoppingMallException("User id not present in verification token", 404);
        }
        Date expiryDate = getExpirationDateFromClaim.apply(claims);
        if (expiryDate == null){
            throw new NorbsShoppingMallException("Expiry Date not present in verification token", 404);
        }
        Date issuedAtDate = getIssuedAtDateFromClaim.apply(claims);

        if (issuedAtDate == null){
            throw new NorbsShoppingMallException("Issued At date not present in verification token", 404);
        }

        if (expiryDate.compareTo(issuedAtDate) > 14.4 ){
            throw new NorbsShoppingMallException("Verification Token has already expired", 404);
        }

        AppUser user = appUserRepository.findById(Long.valueOf(userId)).orElse(null);
        if(user == null){
            throw new NorbsShoppingMallException("User id does not exist",404);
        }
        user.setVerified(true);
        appUserRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = Optional.ofNullable(appUserRepository.findAppUserByEmail(username)).get().orElse(null);

        if(user != null){
            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),getAuthorities(user.getRoles()));
        }
        return null;
    }
    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleType().name())).collect(Collectors.toSet());
    }
}
