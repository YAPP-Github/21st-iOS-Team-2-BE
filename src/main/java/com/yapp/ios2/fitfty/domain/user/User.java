package com.yapp.ios2.fitfty.domain.user;

import com.google.common.collect.Lists;
import com.querydsl.core.util.StringUtils;
import com.yapp.ios2.fitfty.domain.AbstractEntity;
import com.yapp.ios2.fitfty.global.exception.InvalidParamException;
import com.yapp.ios2.fitfty.global.util.BooleanToYNConverter;
import com.yapp.ios2.fitfty.global.util.TokenGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "`user`")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity {
    private static final String USER_PREFIX = "user_";
    private static final String TEMP_PASS = "$2a$10$ujymf7RwzeAvcQavkKez0O0wAuk6oeZT0TCISiKI0.gxBetvi6pfe";

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

    @Column(name = "role")
    private String role;

    @Column(name = "Type")
    @Enumerated(EnumType.STRING)
    private LoginType type;

    @Column(name = "activated")
    private boolean activated;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Convert(converter = StringListConverter.class)
    private List<String> style;

    @Convert(converter = BooleanToYNConverter.class)
    private boolean status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.PERSIST)
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
        if (StringUtils.isNullOrEmpty(email)) throw new InvalidParamException("User.partnerId");
        if (type == null) throw new InvalidParamException("User.type");

        this.email = email;
        this.userToken = TokenGenerator.randomCharacterWithPrefix(USER_PREFIX);
        this.password = TEMP_PASS;
        this.nickname = userToken;
        this.profilePictureUrl = null;
        this.message = null;
        this.role = "ROLE_USER";
        this.type = type;
        this.activated = true;
        this.style = new ArrayList<>();
        this.status = true;
    }

    public void updateCustomOption(UserCommand.CustomOption command ) {
        this.nickname = command.getNickname();
        this.gender = command.getGender();
        this.style = command.getStyle();
    }

    public void deleteUser() {
        this.status = false;
    }
}