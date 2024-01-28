package com.helpduk.helpDuk.entity;

import com.helpduk.helpDuk.base.Enum.DetailCategory;
import com.helpduk.helpDuk.base.Enum.LocationCategory;
import com.helpduk.helpDuk.base.Enum.TaskStatus;
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
    private LocalDateTime taskTime;

    @Column
    private Integer requestFee;

    @Column
    private Integer taskFee;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity userId;

    @OneToOne
    @JoinColumn(name = "acceptUserId")
    private UserEntity acceptUserId;
}
