package org.zerock.guestbook.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.entity.GuestBook;

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
}
