package com.e1i5.stackOverflow.comment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

//    @ManyToOne
//    @JoinColumn(name = "MEMBER_ID")
//    private Member member;

//    @ManyToOne
//    @JoinColumn(name = "QUESTION_ID")
//    private Question question;

    private boolean choose;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int likeCount;
    private int dislikeCount;

    public enum CommentStatus{
        COMMENT_NOT_EXIST("존재하지 않는 답변입니다."),
        COMMENT_EXIST("존재하는 답변입니다.");

        @Getter
        private String status;

        CommentStatus(String status){
            this.status = status;
        }
    }
}
