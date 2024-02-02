package com.helpduk.helpDuk.base.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TaskCategoryDto {
    // 카테고리 검색 시 이용됨.
    private boolean onlyYet;
    private boolean onlyMine;
    private boolean school;
    private boolean dormitory;
    private boolean etc;
    private boolean print;
    private boolean food;
    private boolean coverFor;
    private boolean clean;
    private boolean eventAssistant;
    private boolean bug;

}
