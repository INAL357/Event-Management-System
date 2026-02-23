package com.app.eventmanagement.dto;

import lombok.*;
import org.jspecify.annotations.Nullable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    private String email;
    private String password;


}
