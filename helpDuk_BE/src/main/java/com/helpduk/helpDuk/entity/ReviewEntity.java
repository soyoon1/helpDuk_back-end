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
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    @OneToOne
    private UserEntity userId;

    @OneToOne
    private UserEntity acceptUserId;

    @OneToOne
    private TaskEntity taskId;

    @Column(nullable = false, length = 200)
    private String content;
}
