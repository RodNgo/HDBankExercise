package com.rodngo.exercise.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationRequest {
    private String username;
    private String password;

}
