package com.app.eventmanagement.dto;

import com.app.eventmanagement.model.Role;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    private Role role;


}
