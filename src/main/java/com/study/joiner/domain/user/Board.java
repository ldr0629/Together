package com.study.joiner.domain.user;

import com.study.joiner.domain.BaseTimeEntity;
import com.study.joiner.web.dto.BoardDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 10, nullable = false)
    private String postRecruit;

    @Column(length = 10, nullable = false)
    private String recruitNum;

    @Column(length = 10, nullable = false)
    private String progressWay;

    @Column(length = 10, nullable = false)
    private String duration;

    @Column(length = 10, nullable = false)
    private String skill;

    @Column(length = 10, nullable = false)
    private String date;

    @Column(length = 100, nullable = false)
    private String contactWay;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private SocialUser socialUser;

    @OneToMany(mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();
    public void mappingComment(Comment comment) {
        this.commentList.add(comment);
    }

    @Builder
    public Board(Long id, String postRecruit, String recruitNum, String progressWay, String duration,
                 String skill, String date, String contactWay, String title, String content) {
        this.id = id;
        this.postRecruit = postRecruit;
        this.recruitNum = recruitNum;
        this.progressWay = progressWay;
        this.duration = duration;
        this.skill = skill;
        this.date = date;
        this.contactWay = contactWay;
        this.title = title;
        this.content = content;
    }

    // 게시글별 회원 설정시 사용
    public void setUser(SocialUser socialUser) {
        socialUser.getBoardList().add(this);
        this.socialUser = socialUser;
    }

    // 게시글 수정
    public void edit(BoardDto boardDto) {
        this.postRecruit = boardDto.getPostRecruit();
        this.recruitNum = boardDto.getRecruitNum();
        this.progressWay = boardDto.getProgressWay();
        this.duration = boardDto.getDuration();
        this.skill = boardDto.getSkill();
        this.date = boardDto.getDate();
        this.contactWay = boardDto.getContactWay();
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
    }
}
