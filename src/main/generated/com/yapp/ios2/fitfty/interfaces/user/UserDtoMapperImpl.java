package com.yapp.ios2.fitfty.interfaces.user;

import com.yapp.ios2.fitfty.domain.user.UserCommand.CustomOption.CustomOptionBuilder;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.CustomOption;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-16T23:12:27+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.17 (Oracle Corporation)"
)
@Component
public class UserDtoMapperImpl implements UserDtoMapper {

    @Override
    public com.yapp.ios2.fitfty.domain.user.UserCommand.CustomOption of(CustomOption customOption) {
        if ( customOption == null ) {
            return null;
        }

        CustomOptionBuilder customOption1 = com.yapp.ios2.fitfty.domain.user.UserCommand.CustomOption.builder();

        customOption1.nickname( customOption.getNickname() );
        customOption1.gender( customOption.getGender() );
        List<String> list = customOption.getStyle();
        if ( list != null ) {
            customOption1.style( new ArrayList<String>( list ) );
        }

        return customOption1.build();
    }
}
