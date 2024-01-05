package com.group10.contestPlatform.dtos.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckCheatResponse {
    private Boolean is_cheat;
    private String type_cheat;
}
