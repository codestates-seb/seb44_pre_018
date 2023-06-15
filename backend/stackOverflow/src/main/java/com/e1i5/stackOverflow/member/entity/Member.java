package com.e1i5.stackOverflow.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Member {
    private Long memberId;
    private String name;
    private String phone;
    private String email;
    private String password;
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String profileImageName;

    public Member(String name, String phone, String email, String password) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public Member(String profileImageName) {
        this.profileImageName = profileImageName;
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
}
