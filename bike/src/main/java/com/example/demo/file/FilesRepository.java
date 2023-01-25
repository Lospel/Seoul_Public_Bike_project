package com.example.demo.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<Image, Integer> {

	Image findByFno(int fno);
}
