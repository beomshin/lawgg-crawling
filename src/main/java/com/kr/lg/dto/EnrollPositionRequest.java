package com.kr.lg.dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnrollPositionRequest {

    private String id;
    private String password;
    private String title;
    private String content;
    private Integer lineType;

}
