package org.zerock.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import org.zerock.guestbook.entity.QGuestBook;
import org.zerock.guestbook.repository.GuestBookRepository;

import java.util.Optional;
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
    public GuestBookDTO read(Long gno) {

        Optional<GuestBook> findByGno = repository.findById(gno);

        GuestBookDTO result =  null;

        if(findByGno.isPresent()) {
            result = entityToDto(findByGno.get());
        }

        return result;
    }

    @Override
    public Boolean remove(Long gno) {

        Optional<GuestBook> findByGno = repository.findById(gno);
        if(findByGno.isPresent()) {
            repository.deleteById(gno);
            return true;
        }

        return false;
    }

    @Override
    public Boolean modify(GuestBookDTO dto) {
        // 제목, 내용 수정
        Optional<GuestBook> findByGno = repository.findById(dto.getGno());

        if(findByGno.isPresent()) {

            GuestBook entity = findByGno.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
            return true;
        }
        return false;
    }

    @Override
    public PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO);  // 검색 조건 처리

        Page<GuestBook> result = repository.findAll(booleanBuilder, pageable);

        Function<GuestBook, GuestBookDTO> fn = (this::entityToDto);

        return new PageResultDTO<>(result, fn);
    }

    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO) {
        String type = pageRequestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestBook qGuestBook = QGuestBook.guestBook;

        String keyword = pageRequestDTO.getKeyword();

        BooleanExpression expression = qGuestBook.gno.gt(0L); // gno > 0

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        // 검색 조건 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")){
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }

        if (type.contains("c")) {
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }

        if (type.contains("w")) {
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }

        // 모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

}
