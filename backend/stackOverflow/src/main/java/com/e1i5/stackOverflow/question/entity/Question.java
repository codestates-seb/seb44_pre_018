package com.e1i5.stackOverflow.question.entity;

import com.e1i5.stackOverflow.audit.Auditable;
import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.questionVote.entity.QuestionVote;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Question extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "STATUS")
    private QuestionStatus questionStatus = QuestionStatus.QUESTION_EXIST;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String title;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false)
    private int view;

    public Question(String title, String content, int view) {
        this.title = title;
        this.content = content;
        this.view = view;
    }
    @OneToMany(mappedBy = "question",cascade = CascadeType.PERSIST)
    private List<Tag> tagNews = new ArrayList<>();



    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    List<QuestionVote> questionVoteList = new ArrayList<>();

    public enum QuestionStatus {
        QUESTION_NOT_EXIST("존재하지 않는 질문"),
        QUESTION_EXIST("존재하는 질문");

        @Getter
        private String status;

        QuestionStatus(String status) {
            this.status = status;
        }
    }

    }


