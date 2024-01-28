package com.helpduk.helpDuk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, length=30)
    private String userEmail;

    @Column(nullable = false, length=50)
    private String password;


    @Column(nullable = false, length=10)
    private String nickName;


    @Column(length = 200)
    private String profileImage;


    @Column(nullable = false)
    private Float temperature;
}
