package com.example.online_test.repository;

import com.example.online_test.entity.Admin;
import com.example.online_test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByPhoneNumber(String phoneNumber);


}
