package com.rodngo.exercise.dto.response;

import java.util.List;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyPermissionResponse {
    private List<PermissionResponse> permissions;
}
