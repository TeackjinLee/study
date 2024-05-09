package com.springboot.study.datasource.common;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TokenDto {
    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;
}
