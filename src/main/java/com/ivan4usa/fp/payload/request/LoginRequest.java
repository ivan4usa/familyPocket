package com.ivan4usa.fp.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "Username cannot be empty.")
    private String email;

    @NotEmpty(message = "Password cannot be empty.")
    private String password;

    private boolean remember;
}
