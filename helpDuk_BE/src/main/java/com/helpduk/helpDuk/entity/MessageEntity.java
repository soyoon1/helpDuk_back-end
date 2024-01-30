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
@Table(name="message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private UserEntity senderId;

//    @ManyToOne
//    @JoinColumn(name = "roomId")
//    private ChatRoomEntity roomId;

    @Column(nullable = false)
    private String roomId;

    @ManyToOne
    @JoinColumn(name = "roomId", referencedColumnName = "roomId", insertable = false, updatable = false)
    private ChatRoomEntity chatRoomEntity;


    @Column(nullable = false)
    private String content;

    @CreatedDate
    private LocalDateTime sendTime;


    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;

}
