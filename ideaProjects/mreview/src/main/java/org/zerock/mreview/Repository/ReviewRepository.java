package org.zerock.mreview.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // LAZY로 could not initialize proxy [org.zerock.mreview.entity.Member#85] - no Session 에러
    // 해결방법 - 1. @Query를 이용해서 조인 처리 2. @EntityGraph를 이용해서 Review 객체를 가져올 때 Member 객체를 로딩하는 방법
    /**
     * EntityGraph
     * attributePaths: 로딩 설정을 변경하고 싶은 속성의 이름을 배열로 명시합니다.
     * type : @EntityGraph를 어떤 방식으로 적용할 것인지를 설정합니다.
     * FATCH 속정값은 attributePaths에 명시한 속성은 EAGER로 처리하고, 나머지 LAZY로 처리합니다.
     * LOAD 속성값은 attributePaths에 명시한 속성은 EAGER로 처리하고, 나머지는 엔티티 클래스에 명시되거나 기본 방정식으로 처리합니다.
    * */
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    @Modifying
    @Query("DELETE FROM Review r WHERE r.member = :member")
    void deleteByMember(Member member);

}
