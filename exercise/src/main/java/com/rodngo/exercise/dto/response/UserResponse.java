package com.rodngo.exercise.dto.response;
import java.util.List;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private String id;
    private String username;
    private List<PermissionResponse> permissions;
}
