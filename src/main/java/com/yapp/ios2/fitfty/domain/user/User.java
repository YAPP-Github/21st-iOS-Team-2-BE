package com.yapp.ios2.fitfty.domain.user;

import com.google.common.collect.Lists;
import com.querydsl.core.util.StringUtils;
import com.yapp.ios2.fitfty.global.exception.InvalidParamException;
import com.yapp.ios2.fitfty.global.util.TokenGenerator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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

@Entity
@Table(name = "`user`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private static final String USER_PREFIX = "usr_";
    private static final String TEMP_PASS = "$2a$10$CcRzHQiejRUKVy.P1SjCjOj9FximOzAeIHC69DaiaoTGabQ6Sxfa2";

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

    @Column(name = "role")
    private String role;

    @Column(name = "Type")
    @Enumerated(EnumType.STRING)
    private LoginType type;

    @Column(name = "activated")
    private boolean activated;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Bookmark> bookmarkList = Lists.newArrayList();


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

        this.userToken = TokenGenerator.randomCharacterWithPrefix(USER_PREFIX);
        this.password = TEMP_PASS;
        this.nickname = userToken;
        this.role = "ROLE_USER";
        this.type = type;
        this.activated = true;
    }
}