package com.example.online_test.repository;

import com.example.online_test.entity.Groups;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, String> {

    Page<Groups> findAllByOrderByCreateAtDesc(Pageable pageable);

    Page<Groups> findAllByCourseIdOrderByCreateAtDesc(String id,Pageable pageable);

}
