package com.twinkles.Norbs_Shopping_Mall.data.repository;

import com.twinkles.Norbs_Shopping_Mall.data.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
