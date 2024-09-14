package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestBookDTO;
import org.zerock.guestbook.dto.common.PageRequestDTO;
import org.zerock.guestbook.dto.common.PageResultDTO;
import org.zerock.guestbook.entity.GuestBook;
import org.zerock.guestbook.repository.GuestBookRepository;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor    // 의존성 자동 주입
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository repository; // 반드시 final 선언

    @Override
    public Long register(GuestBookDTO dto) {

        log.info("DTO-----------------------");
        log.info(dto);

        GuestBook entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<GuestBook> result = repository.findAll(pageable);

        Function<GuestBook, GuestBookDTO> fn = (this::entityToDto);

        return new PageResultDTO<>(result, fn);
    }

}
