package com.yapp.ios2.fitfty.domain.user.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yapp.ios2.fitfty.domain.user.User;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;

    public static UserDto from(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}