package com.example.demo.course;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class course {
   @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   
   private String name;
   
   private String longitude;

   private String latitude;
   
   private LocalDateTime createDate;
}
