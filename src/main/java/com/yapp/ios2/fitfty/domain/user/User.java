package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.domain.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity {
    private static final String PREFIX_USER = "user_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userToken;
    private String userName;
    private String email;
    private String statusMessage;
    private List<String> preference;
    private List<Bookmark> bookmarks;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Getter
    @RequiredArgsConstructor
    public enum Gender {
        FEMALE("여성"),
        MALE("남성");

        private final String description;
    }

}
