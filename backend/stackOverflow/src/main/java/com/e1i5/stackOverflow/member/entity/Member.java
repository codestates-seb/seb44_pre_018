package com.e1i5.stackOverflow.member.entity;

import com.e1i5.stackOverflow.audit.Auditable;
import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.member.dto.MemberDto;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.questionVote.entity.QuestionVote;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 100, nullable = false, unique = true)
    private String name;
    @Column(length = 13, nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length=100, nullable = false, unique = true)
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    private String profileImageName;

    private String profileImagePath;

    // 사용자 등록시 사용자 권한 등록 테이블 생성
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public Member(String name, String phone, String email, String password) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> commentList= new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Question> questionList= new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<QuestionVote> questionVoteList = new ArrayList<>();
}
