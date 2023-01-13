package com.yapp.ios2.fitfty.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    // bookmark 관련 mapping 추가로 필요. LisT<Bookmakr> + One to Many
    // 그 외에도 필요한 개인 선호 설정 관련 컬럼 필요
}