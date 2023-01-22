package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.interfaces.user.UserDto.SignInDto;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.SignUpDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserMapper {

    UserInfo.CustomOption toCustomOption(User user);
    UserInfo.Login toLogin(User user);

    UserInfo.ProfileInfo toProfileInfo(User user);
    UserInfo.ImageInfo toImageInfo(Bookmark bookmark);
    UserInfo.ImageInfo toImageInfo(Feed feed);

    UserCommand.SignIn toSignIn(SignInDto signInDto);
    UserCommand.SignUp toSignUp(SignUpDto signUpDto);
}
