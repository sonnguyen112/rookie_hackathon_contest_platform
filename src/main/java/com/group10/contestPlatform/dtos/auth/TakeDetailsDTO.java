package com.group10.contestPlatform.dtos.auth;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TakeDetailsDTO {
    private Long takeId;
    private String quizTitle;
    private List<String> cheatInfoImgUrls;
}
