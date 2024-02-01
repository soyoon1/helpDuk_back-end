package com.helpduk.helpDuk.base.Enum.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MyPageDto {
    // 마이페이지에 반환할 Dto
    private String userEmail;
    private String nickName;
    private String temperature;
    private String profileImage;

    private boolean success;

    private int code;

    private String msg;
}
