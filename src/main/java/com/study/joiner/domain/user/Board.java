package com.study.joiner.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;

// 테이블 링크, nullable, 유효성 검사
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) /* JPA에게 해당 Entity는 Auditiong 기능을 사용함을 알립니다. */
public class Board {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 10, nullable = false)
    private String study_project;

    @Column(length = 10, nullable = false)
    private String person_num;

    @Column(length = 10, nullable = false)
    private String online_offline;

    @Column(length = 10, nullable = false)
    private String duration;

    @Column(length = 10, nullable = false)
    private String skill;

    @Column(length = 10, nullable = false)
    private String date;

    @Column(length = 10, nullable = false)
    private String calling;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String input_content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public Board(Long id, String study_project, String person_num, String online_offline, String duration, String skill, String date, String calling, String title, String input_content) {
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
    }

}
