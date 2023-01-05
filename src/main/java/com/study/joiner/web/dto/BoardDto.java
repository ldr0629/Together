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
    private String study_project;
    private String person_num;
    private String online_offline;
    private String duration;
    private String skill;
    private String date;
    private String calling;
    private String title;
    private String input_content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Board toEntity() {
        Board build = Board.builder()
                .id(id)
                .study_project(study_project)
                .person_num(person_num)
                .online_offline(online_offline)
                .duration(duration)
                .skill(skill)
                .date(date)
                .calling(calling)
                .title(title)
                .input_content(input_content)
                .build();
        return build;
    }

    @Builder
    public BoardDto(Long id, String study_project, String person_num, String online_offline, String duration, String skill, String date, String calling, String title, String input_content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.study_project = study_project;
        this.person_num = person_num;
        this.online_offline = online_offline;
        this.duration = duration;
        this.skill = skill;
        this.date = date;
        this.calling = calling;
        this.title = title;
        this.input_content = input_content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public BoardDto(Board entity) {
        this.id = entity.getId();
        this.study_project = entity.getStudy_project();
        this.person_num = entity.getPerson_num();
        this.online_offline = entity.getOnline_offline();
        this.duration = entity.getDuration();
        this.skill = entity.getSkill();
        this.date = entity.getDate();
        this.calling = entity.getCalling();
        this.title = entity.getTitle();
        this.input_content = entity.getInput_content();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
