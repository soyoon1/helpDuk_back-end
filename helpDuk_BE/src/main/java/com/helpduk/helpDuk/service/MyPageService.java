package com.helpduk.helpDuk.service;

import com.helpduk.helpDuk.base.Enum.dto.MyPageDto;
import com.helpduk.helpDuk.base.Enum.dto.MyPageLikedUserDto;
import com.helpduk.helpDuk.base.Enum.dto.MyPageReviewDto;
import com.helpduk.helpDuk.base.Enum.dto.MyPageTaskDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface MyPageService {

    // 마이페이지 화면
    @Transactional
    MyPageDto getMyPage(Integer userId);

    @Transactional
    MyPageLikedUserDto getMyLikePage(Integer userId);

    @Transactional
    MyPageReviewDto getMyReviewPage(Integer userId);

    @Transactional
    MyPageTaskDto getMyTaskPage(Integer userId);

    @Transactional
    MyPageTaskDto getOtherTaskPage(Integer userId);

    @Transactional
    MyPageDto updateProfile(Integer userId, String nickname, String profileImage);
}
