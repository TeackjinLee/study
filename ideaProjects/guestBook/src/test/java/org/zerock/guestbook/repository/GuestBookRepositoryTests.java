package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.dto.GuestBookDTO;
import org.zerock.guestbook.dto.common.PageRequestDTO;
import org.zerock.guestbook.dto.common.PageResultDTO;
import org.zerock.guestbook.entity.GuestBook;
import org.zerock.guestbook.entity.QGuestBook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestBookRepositoryTests {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    public void insertDummies() {

        IntStream.range(1,300).forEach(i -> {

            GuestBook guestbook = GuestBook.builder()
                    .title("title........"+i)
                    .content("content........."+i)
                    .writer("user" + (i%10))
                    .build();
            System.out.println(guestBookRepository.save(guestbook));
        });
    }

    @Test
    public void update() {
        Optional<GuestBook> result = guestBookRepository.findById(299L);

        if (result.isPresent()) {
            GuestBook guestbook = result.get();

            guestbook.changeTitle("Change Title.....");
            guestbook.changeContent("Change Content....");

            guestBookRepository.save(guestbook);
        }
    }

    @Test
    public void testQuery1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestBook qGuestBook = QGuestBook.guestBook; // 1

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();  // 2

        BooleanExpression expression = qGuestBook.title.contains(keyword);  // 3

        builder.and(expression);    // 4

        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable); // 5

        result.stream().forEach(System.out::println);

    }

    @Test
    public void testQuery2() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestBook qGuestBook = QGuestBook.guestBook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestBook.title.contains(keyword);

        BooleanExpression exContent = qGuestBook.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);

        builder.and(qGuestBook.gno.gt(0L));

        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        result.stream().forEach(System.out::println);

    }


}
