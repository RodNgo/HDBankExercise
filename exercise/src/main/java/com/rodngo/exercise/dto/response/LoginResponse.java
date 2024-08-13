package com.rodngo.exercise.dto.response;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {
    private Long id;
    private String access_token;
    private String refresh_token;
}
