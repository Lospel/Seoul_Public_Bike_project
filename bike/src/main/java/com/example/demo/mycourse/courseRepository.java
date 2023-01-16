package com.example.demo.mycourse;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.user.SiteUser;

public interface courseRepository extends JpaRepository<course, Integer>{

    List<course> findByAuthor(SiteUser siteUser);

    @Query(nativeQuery = true, value = "SELECT * FROM course")
    public List<course> selectall();
    
}
