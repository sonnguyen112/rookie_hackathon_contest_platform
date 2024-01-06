package com.group10.contestPlatform.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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




}
