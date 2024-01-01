package com.group10.contestPlatform.dtos.auth;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {  @JsonProperty("message")
private String message;

    @JsonProperty("token")
    private String token;

    private Long id;
    private String username;

    private List<String> roles;
    private String first_name;
    private String last_name;
}
