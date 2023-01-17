package com.study.joiner.web.dto;

import com.study.joiner.domain.user.Board;
import lombok.*;

import java.time.LocalDateTime;

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
    }

    public Board toEntity() {
        Board board = Board.builder()
                .id(id)
                .postRecruit(postRecruit)
                .recruitNum(recruitNum)
                .progressWay(progressWay)
                .duration(duration)
                .skill(skill)
                .date(date)
                .contactWay(contactWay)
                .title(title)
                .content(content)
                .build();
        return board;
    }
}
