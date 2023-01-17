package com.study.joiner.config.auth.dto;

import com.study.joiner.domain.user.SocialUser;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class SessionUser implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String picture;

    private LocalDateTime regDate;

    public SessionUser(SocialUser socialUser) {
        this.id = socialUser.getId();
        this.name = socialUser.getName();
        this.email = socialUser.getEmail();
        this.picture = socialUser.getPicture();
        this.regDate = socialUser.getCreatedDate();
    }
}
