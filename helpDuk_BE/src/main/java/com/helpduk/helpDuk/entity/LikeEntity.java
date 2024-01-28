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
@Table(name="likeUser")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likeId;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity userId;

    @ManyToOne
    @JoinColumn(name = "likeUserId")
    private UserEntity likeUserId;
}
