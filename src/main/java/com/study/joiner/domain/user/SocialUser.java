package com.study.joiner.domain.user;

import com.study.joiner.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class SocialUser extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Column
    private String nickName;

    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "socialUser",cascade = CascadeType.REMOVE)
    private List<Board> boardList = new ArrayList<>();

    @Builder
    public SocialUser(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public SocialUser update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public SocialUser userUpdate(String nickName, String content) {
        this.nickName = nickName;
        this.content = content;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

//    public SocialUser changeName(String name) {
//        this.nickName = name;
//        return this;
//    }
}
