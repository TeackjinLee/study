package org.zerock.guestbook.dto.common;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResultDTO<DTO, EN> {

    private List<DTO> dtoList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {   // Function<EN, DTO> => Entity를 DTO로 변환

        dtoList = result.stream().map(fn).collect(Collectors.toList());

    }

}
