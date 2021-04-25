package com.example.online_test.repository;

import com.example.online_test.entity.Admin;
import com.example.online_test.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByName(String role_user);

    List<Role> findAllByName(String roleName);

}
