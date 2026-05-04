package com.bookcase.bookcase.member.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginMemberDto {

    private Long memberNo;
    private String email;
    private String nickname;

}
