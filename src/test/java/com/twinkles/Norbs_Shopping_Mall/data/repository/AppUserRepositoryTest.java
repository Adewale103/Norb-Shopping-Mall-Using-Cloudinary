package com.twinkles.Norbs_Shopping_Mall.data.repository;

import com.twinkles.Norbs_Shopping_Mall.data.model.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Slf4j
class AppUserRepositoryTest {
    @Autowired
    AppUserRepository appUserRepository;

    @Test
    void whenUserIsCreated_ThenCreateCartTest(){
        AppUser appUser = new AppUser("firstName", "lastName","email@gmail.com", "12344", "+234906805433");
        appUserRepository.save(appUser);
        assertThat(appUser.getId()).isNotNull();
        assertThat(appUser.getMyCart()).isNotNull();

        log.info("AppUser -> {}",appUser);
    }


}