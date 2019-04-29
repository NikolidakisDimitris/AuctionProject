package com.nikolidakis.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.nikolidakis.utils.Utils.md5;

@NoArgsConstructor
@Data
public class AuthenticateUserRequest extends Request {
    @NotNull
    @Size(max = 30)
    private String username;

    @NotNull
    @Size(max = 512)
    private String password;

    public AuthenticateUserRequest(@NotNull @Size(max = 30) String username,
                                   @NotNull @Size(max = 512) String password) {
        this.username = username;
        this.password = md5(password);
    }

    public void setPassword(@NotBlank @Size(max = 512) String password) {
        this.password = md5(password);
    }
}
