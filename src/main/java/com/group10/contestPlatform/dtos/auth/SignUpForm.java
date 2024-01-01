package com.group10.contestPlatform.dtos.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpForm {
	@JsonProperty("email")
	private String email;
	@JsonProperty("username")
	private String username;
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;

	@NotBlank(message = "Password cannot be blank")
	private String password;
	@NotNull(message = "Role ID is required")
	@JsonProperty("role_id")
	private Long roleId;



}
