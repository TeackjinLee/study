package org.zerock.mreview.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.mreview.Repository.MemberRepository;
import org.zerock.mreview.Repository.ReviewRepository;
import org.zerock.mreview.entity.Member;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMembers() {
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder()
                    .email("r" + i + "@zerock.org")
                    .pw("1111")
                    .nickname("reviewer" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteMember() {
        Long mid = 44L;
        Member member = Member.builder().mid(mid).build();
//        memberRepository.deleteById(mid);
//        reviewRepository.deleteByMember(member);

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }
}
