package com.yapp.ios2.fitfty.interfaces.user;

import com.yapp.ios2.fitfty.domain.user.UserCommand;
import com.yapp.ios2.fitfty.domain.user.UserCommand.CustomPrivacy;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.CustomOption;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserDtoMapper {
    UserCommand.CustomOption of(CustomOption customOption);
    UserCommand.CustomPrivacy of(CustomPrivacy customPrivacy);
}
