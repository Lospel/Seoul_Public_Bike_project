package com.example.course;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Entity
public class course {
    @Id
    private Integer id;

    private String c1_1;

    private String c1_2;

    private String c2_1;

    private String c2_2;

    private String c3_1;

    private String c3_2;

    private String c4_1;

    private String c4_2;

    private String c5_1;

    private String c5_2;
}
