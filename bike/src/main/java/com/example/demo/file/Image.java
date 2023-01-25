package com.example.demo.file;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Image {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int fno;
	
	String filename;
	String fileOriName;
	String fileurl;
	// byte[] data;
}
