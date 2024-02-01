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
@Table(name="chatroom")
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private TaskEntity taskId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userId;

    @ManyToOne
    @JoinColumn(name = "helpUserId")
    private UserEntity helperUserId;

}
