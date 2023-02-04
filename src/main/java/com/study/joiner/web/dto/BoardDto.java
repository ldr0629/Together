package com.study.joiner.web.dto;

import com.study.joiner.domain.user.Board;
import com.study.joiner.domain.user.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String postRecruit;
    private String recruitNum;
    private String progressWay;
    private String duration;
    private String skill;
    private String date;
    private String contactWay;
    private String title;
    private String content;
    private List<Comment> commentList;
    private Long writerId;

    public BoardDto(Board entity) {
        this.id = entity.getId();
        this.postRecruit = entity.getPostRecruit();
        this.recruitNum = entity.getRecruitNum();
        this.progressWay = entity.getProgressWay();
        this.duration = entity.getDuration();
        this.skill = entity.getSkill();
        this.date = entity.getDate();
        this.contactWay = entity.getContactWay();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.commentList = entity.getCommentList();
        this.writerId = entity.getSocialUser().getId();
    }

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .postRecruit(postRecruit)
                .recruitNum(recruitNum)
                .progressWay(progressWay)
                .duration(duration)
                .skill(skill)
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .contactWay(contactWay)
                .title(title)
                .content(content)
                .build();
    }
}
