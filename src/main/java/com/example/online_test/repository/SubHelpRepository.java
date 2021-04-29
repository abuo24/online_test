package com.example.online_test.repository;

import com.example.online_test.entity.SubHelp;
import com.example.online_test.entity.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubHelpRepository extends JpaRepository<SubHelp, String> {
}
