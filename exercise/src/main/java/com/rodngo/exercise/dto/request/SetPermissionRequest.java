package com.rodngo.exercise.dto.request;
import java.util.List;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SetPermissionRequest {
    private String userId;
    private List<String> permissionIds;
}

