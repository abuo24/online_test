package com.example.online_test.repository;

import com.example.online_test.entity.Groups;
import com.example.online_test.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {


}
