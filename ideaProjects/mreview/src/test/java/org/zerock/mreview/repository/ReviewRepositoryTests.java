package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mreview.Repository.ReviewRepository;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMovieReviews() {

        // 200개의 리뷰를 등록
        IntStream.rangeClosed(1,200).forEach(i -> {

            // 영화 번호
            Long mno = (long) (Math.random()*100) + 1;

            // 리뷰어 번호
            Long mid = ((long)(Math.random()*100) + 1);
            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random()*5) + 1)
                    .text("이 영화에 대한 느낌..." + i)
                    .build();

            reviewRepository.save(review);
        });
    }

    @Test
    public void testGetMovieReviews() {

        Movie movie = Movie.builder().mno(92L).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(movieReview -> {
            System.out.println(movieReview.getReviewnum());
            System.out.println("\t" + movieReview.getGrade());
            System.out.println("\t" + movieReview.getText());
            // LAZY로 could not initialize proxy [org.zerock.mreview.entity.Member#85] - no Session 에러
            // 해결방법 - 1. @Query를 이용해서 조인 처리 2. @EntityGraph를 이용해서 Review 객체를 가져올 때 Member 객체를 로딩하는 방법
            System.out.println("\t" + movieReview.getMember().getEmail());
            System.out.println("==============================================");
        });
    }

}
