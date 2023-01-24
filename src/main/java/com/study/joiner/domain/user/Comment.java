package com.study.joiner.domain.user;

import com.study.joiner.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Comment extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private SocialUser user;

    public void mappingBoardAndUser(Board boardDetail, SocialUser socialUser) {
        this.board = boardDetail;
        this.user = socialUser;

        board.mappingComment(this);
        user.mappingComment(this);
    }

    public void update(String content) {
        this.content = content;
    }
}
