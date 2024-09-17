package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor  // GuestDTO guestDTO = new GuestDTO();
@AllArgsConstructor // GuestDTO guestDTO = new GuestDTO(gno, title, content, writer, regDate, modDate);
@Data
public class GuestBookDTO {

    private Long gno;

    private String title;

    private String content;

    private String writer;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime regDate, modDate;

}
