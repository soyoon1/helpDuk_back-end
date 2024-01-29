package com.helpduk.helpDuk.entity;

import com.helpduk.helpDuk.base.Enum.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;

    @Column(length = 50)
    private String title;

    @Column(length = 500)
    private String content;

    @CreatedDate
    private LocalDateTime uploadDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationCategory locationCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DetailCategory detailCategory;

    @Column
    private String image;

    @Column
    private String taskTime;

    @Column
    private Integer requestFee;

    @Enumerated(EnumType.STRING)
    private RequestFeeMethod requestFeeMethod;

    @Enumerated(EnumType.STRING)
    private TaskFeeMethod taskFeeMethod;

    @Column
    private Integer taskFee;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userId;

    @ManyToOne
    @JoinColumn(name = "acceptUserId")
    private UserEntity acceptUserId;
}
