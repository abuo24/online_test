package com.example.online_test.repository;

import com.example.online_test.entity.Course;
import com.example.online_test.entity.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, String> {


}
