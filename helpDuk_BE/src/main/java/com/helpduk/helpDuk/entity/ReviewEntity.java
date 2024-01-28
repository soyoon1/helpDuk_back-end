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
@Table(name="review")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity userId;

    @OneToOne
    @JoinColumn(name = "acceptUserId")
    private UserEntity acceptUserId;

    @OneToOne
    @JoinColumn(name = "taskId")
    private TaskEntity taskId;

    @Column(nullable = false, length = 200)
    private String content;
}
