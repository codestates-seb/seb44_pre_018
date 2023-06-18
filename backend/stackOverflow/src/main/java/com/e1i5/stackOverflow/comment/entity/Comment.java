package com.e1i5.stackOverflow.comment.entity;

import com.e1i5.stackOverflow.audit.Auditable;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.question.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@Entity
@Getter
@Setter
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(nullable = false, columnDefinition = "TEXT CHECK (LENGTH(content) <= 500)")
    private String content;


    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public void addMember(Member member){  // 멤버 변경사항 설정
        this.member = member;
    }

//    public Member getMember() {
//        return this.member;
//    }

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;
    public void addQuestion(Question question){  // 질문 변경사항 설정
        this.question = question;
    }

    private boolean choose; // f = comment t = answercomment

    private int likeCount;
    private int dislikeCount;

    // 댓글 상태 추가 - 채택되지 않은 상태
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private CommentStatus commentStatus = CommentStatus.COMMENT;

    // 댓글 상태 추가 - 수정되지 않은 상태
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private CommentStatus commentStatus2 = CommentStatus.ORIGIN_COMMENT;




    public enum CommentStatus{
        COMMENT_NOT_EXIST("존재하지 않는 답변입니다."),
        COMMENT_EXIST("존재하는 답변입니다."),
        COMMENT("채택되지 않은 댓글입니다."),
        ANSWER_COMMENT("채택된 댓글입니다."),
        ORIGIN_COMMENT("원본 댓글입니다."),
        MODIFIED_COMMENT("수정된 댓글입니다.");
// CommentStatus 를 하나 더 만들면 테이블에서 수정과 채택이 중복되지 않을 것 같다.

        @Getter
        private String status;

        CommentStatus(String status){
            this.status = status;
        }
    }
}
