package com.yapp.ios2.fitfty.domain.user;

public interface UserStore {
    User store(User user);
    Bookmark store(Bookmark bookmark);
}
