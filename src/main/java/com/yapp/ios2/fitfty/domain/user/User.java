package com.yapp.ios2.fitfty.domain.user;

import com.querydsl.core.util.StringUtils;
import com.yapp.ios2.fitfty.domain.AbstractEntity;
import com.yapp.ios2.fitfty.domain.user.Utils.StringListConverter;
import com.yapp.ios2.fitfty.global.exception.InvalidParamException;
import com.yapp.ios2.fitfty.global.util.BooleanToYNConverter;
import com.yapp.ios2.fitfty.global.util.TokenGenerator;
import com.yapp.ios2.fitfty.interfaces.user.UserDto;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.KakaoProfileDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "`user`")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity {
    private static final String USER_PREFIX = "user_";
    private static final String TEMP_PASS = "$2a$10$ujymf7RwzeAvcQavkKez0O0wAuk6oeZT0TCISiKI0.gxBetvi6pfe";
    private static final String FITFTY_DEFAULT_IMAGE_URL = "https://fitfty.s3.ap-northeast-2.amazonaws.com/fitfty_profile_dummy.png";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userToken;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    private String profilePictureUrl;
    private String message;
    private String role;

    @Enumerated(EnumType.STRING)
    private LoginType type;
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isOnBoardingComplete;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String birthday;
    private String ageRange;

    @Convert(converter = StringListConverter.class)
    private List<String> style;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Feed> feedList = new ArrayList<>();

    @Getter
    @RequiredArgsConstructor
    public enum Gender {
        FEMALE("여성"),
        MALE("남성");

        private final String description;
    }

    @Getter
    @RequiredArgsConstructor
    public enum LoginType {
        KAKAO("KAKAO"),
        APPLE("APPLE"),
        CUSTOM("CUSTOM");

        private final String description;
    }

    @Builder
    public User(String email, LoginType type) {
        if (StringUtils.isNullOrEmpty(email)) {
            throw new InvalidParamException("User.email");
        }
        if (type == null) {
            throw new InvalidParamException("User.type");
        }

        this.email = email;
        this.userToken = TokenGenerator.randomCharacterWithPrefix(USER_PREFIX);
        this.password = TEMP_PASS;
        this.nickname = userToken;
        this.profilePictureUrl = FITFTY_DEFAULT_IMAGE_URL;
        this.message = null;
        this.role = "ROLE_USER";
        this.type = type;
        this.isOnBoardingComplete = false;
        this.style = new ArrayList<>();
    }

    public void updateCustomOption(UserCommand.CustomOption command) {
        this.nickname = command.getNickname();
        this.gender = command.getGender();
        this.style = command.getStyle();
        this.isOnBoardingComplete = true;
    }

    public void updatePrivacyOption(UserCommand.CustomPrivacy command) {
        if (StringUtils.isNullOrEmpty(command.getNickname()) && StringUtils.isNullOrEmpty(command.getBirthday()) && command.getGender() == null) {
            throw new InvalidParamException("모든 파라미터가 null 입니다.");
        }

        if (!StringUtils.isNullOrEmpty(command.getNickname())) {
            this.nickname = command.getNickname();
        }

        if (!StringUtils.isNullOrEmpty(command.getBirthday())) {
            this.birthday = command.getBirthday();
        }

        if (command.getGender() != null) {
            this.gender = command.getGender();
        }
    }

    public void updateProfile(UserCommand.Profile command) {
        if (StringUtils.isNullOrEmpty(command.getProfilePictureUrl()) && StringUtils.isNullOrEmpty(command.getMessage())) {
            throw new InvalidParamException("모든 파라미터가 null 입니다.");
        }

        if (!StringUtils.isNullOrEmpty(command.getMessage())) {
            this.message = command.getMessage();
        }

        if (!StringUtils.isNullOrEmpty(command.getProfilePictureUrl())) {
            this.profilePictureUrl = command.getProfilePictureUrl();
        }
    }

    public void updateKakaoLoginInfo(KakaoProfileDto kakaoProfileDto) {
        if (StringUtils.isNullOrEmpty(kakaoProfileDto.getProperties().profileImage)
                || (Objects.equals(kakaoProfileDto.getProperties().profileImage,
                                   UserDto.defaultProfileImageUrl))) {
            this.profilePictureUrl = FITFTY_DEFAULT_IMAGE_URL;
        } else {
            this.profilePictureUrl = kakaoProfileDto.getProperties().profileImage;
        }

        if (!StringUtils.isNullOrEmpty(kakaoProfileDto.getKakaoAccount().gender)) {
            this.gender = (kakaoProfileDto.getKakaoAccount().gender.equals("male")) ? Gender.MALE : Gender.FEMALE;
        }

        this.birthday = kakaoProfileDto.getKakaoAccount().birthday;
        this.ageRange = kakaoProfileDto.getKakaoAccount().ageRange;
    }
}