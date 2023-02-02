package com.study.joiner.config.auth.dto;

import com.study.joiner.domain.user.SocialUser;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String picture;

    public SessionUser(SocialUser socialUser) {
        this.id = socialUser.getId();
        this.name = socialUser.getName();
        this.email = socialUser.getEmail();
        this.picture = socialUser.getPicture();
    }
}
