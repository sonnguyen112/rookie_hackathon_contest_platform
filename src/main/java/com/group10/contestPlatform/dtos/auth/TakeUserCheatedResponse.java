package com.group10.contestPlatform.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class TakeUserCheatedResponse {
    private String fullName;
    private String quizTitle;
    private List<String> cheatInfoImgUrls;
}
