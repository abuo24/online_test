package com.example.online_test.repository;

import com.example.online_test.entity.Admin;
import com.example.online_test.entity.Blok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BlokRepository extends JpaRepository<Blok, String> {
   List<Blok> findAllByUserId(String id);
   List<Blok> findByUserIdAndFinalDateLessThanAndFinalDateGreaterThan(String id,Date finalDate, Date startDate);
}
