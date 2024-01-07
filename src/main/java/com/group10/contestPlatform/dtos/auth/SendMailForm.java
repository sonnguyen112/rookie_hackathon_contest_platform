package com.group10.contestPlatform.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendMailForm {
	@JsonProperty("email")
	private String email;

	@JsonProperty("content")
	private String content;

		@JsonProperty("imageArray")
	private List<String> imageArray;




}
