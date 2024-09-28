package org.zerock.board.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReplyDTO {

    private Long rno;

    private String text;

    private String replyer;

    private Long bno;

    private LocalDateTime regDate, modDate;
}
