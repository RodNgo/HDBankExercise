package com.rodngo.exercise.dto.response;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionResponse {
    private String id;
    private String name;
}
