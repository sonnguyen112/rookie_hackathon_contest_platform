package com.group10.contestPlatform.dtos.quiz;

import lombok.Data;

import java.util.List;

@Data
public class GetQuestionResponse {
	private Long id;

	private String image;

	private String text;

	private List<GetOneAnswerResponse> answers;
}
