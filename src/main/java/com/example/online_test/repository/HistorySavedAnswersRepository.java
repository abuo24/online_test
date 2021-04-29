package com.example.online_test.repository;

import com.example.online_test.entity.HistorySavedAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorySavedAnswersRepository extends JpaRepository<HistorySavedAnswers, String> {

}
