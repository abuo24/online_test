package com.example.online_test.repository;

import com.example.online_test.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, String> {

    Page<History> getAllByOrderByCreateAtDesc(Pageable pageable);

    History findByUserIdAndBlokId(String uId, String bId);
}
