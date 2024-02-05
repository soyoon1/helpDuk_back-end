package com.helpduk.helpDuk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="likeUser")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likeId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userId;

    @ManyToOne
    @JoinColumn(name = "likeUserId")
    private UserEntity likeUserId;

    @CreatedDate
    private LocalDateTime uploadDate;
}
