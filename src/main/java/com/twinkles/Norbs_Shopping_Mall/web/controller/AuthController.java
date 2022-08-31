package com.twinkles.Norbs_Shopping_Mall.web.controller;

import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.AuthToken;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CreateAppUserRequest;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.LoginRequest;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.ApiResponse;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.AppUserDto;
import com.twinkles.Norbs_Shopping_Mall.data.model.AppUser;
import com.twinkles.Norbs_Shopping_Mall.security.jwt.TokenProvider;
import com.twinkles.Norbs_Shopping_Mall.service.appUser.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    public AuthController(AppUserService appUserService, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(HttpServletRequest request, @RequestBody @Valid @NotNull CreateAppUserRequest createAppUserRequest) {
        String host = request.getRequestURL().toString();
        log.info("Old Host --> {}", host);
        int index = host.indexOf("/", host.indexOf("/", host.indexOf("/"))+2);
        host = host.substring(0, index+1);
        log.info("Host --> {}", host);
        AppUserDto userDto = appUserService.createAppUserAccount(host,createAppUserRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status("success")
                .message("user created successfully")
                .data(userDto)
                .build();
        log.info("Returning response");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


    @RequestMapping("/verify/{token}")
    public ModelAndView verify(@PathVariable("token") String token) {
        appUserService.verifyUser(token);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("verification_success");
        return modelAndView;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateJWTToken(authentication);
        AppUser user = appUserService.findAppUserByEmail(loginRequest.getEmail());
        return new ResponseEntity<>(new AuthToken(token, user.getId()), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }));
        return errors;
    }
}
