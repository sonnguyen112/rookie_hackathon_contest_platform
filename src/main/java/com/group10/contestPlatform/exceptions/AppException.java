package com.group10.contestPlatform.exceptions;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Getter @Setter
public class AppException extends RuntimeException{
    private int codeStatus;
    private int codeError;
}
