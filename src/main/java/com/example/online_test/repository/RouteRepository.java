package com.example.online_test.repository;

import com.example.online_test.entity.Groups;
import com.example.online_test.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {

    List<Route> findAllBySubjectFirstId(String id);
    List<Route> findAllBySubjectFirstIdAndSubjectSecondId(String id, String id2);
    List<Route> findAllBySubjectFirstIdAndSubjectSecondIdAndSubjectThirdId(String id, String id2, String id3);
}
