package com.e1i5.stackOverflow.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class MemberDto {
    @AllArgsConstructor
    @Getter
    public static class SignupPost{
        @Email
        private String email;

        @NotBlank(message = "이름은 공백이 아니어야 합니다.")
        private String name;

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
        private String phone;

        @Pattern(regexp = "^(?=.*[!@#$%^&*])(?=\\S+$).{8,16}$",
                message = "비밀번호는 8~16자이어야 하며, 특수기호를 포함해야 합니다.")
        @NotBlank
        private String password;
    }

    @AllArgsConstructor
    @Getter
    public static class LoginPost{
        @Email
        private String email;

        @Pattern(regexp = "^(?=.*[!@#$%^&*])(?=\\S+$).{8,16}$",
                message = "비밀번호는 8~16자이어야 하며, 특수기호를 포함해야 합니다.")
        @NotBlank
        private String password;
    }

    @AllArgsConstructor
    @Getter
    public static class Patch{
        private long memberId;

        @NotBlank(message = "이름은 공백이 아니어야 합니다.")
        private String name;

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
        private String phone;

        public void setMemberId(long memberId) {
            this.memberId = memberId;
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Response {
        private String name;
        private String phone;
        private String email;
        private String profileImageName;
        private String profileImagePath;
    }
}
