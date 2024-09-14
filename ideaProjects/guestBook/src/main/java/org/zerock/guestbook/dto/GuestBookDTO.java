package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private LocalDateTime regDate, modDate;

}
