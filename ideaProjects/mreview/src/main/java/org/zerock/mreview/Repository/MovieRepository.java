package org.zerock.mreview.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m, mi, avg(coalesce(r.grade, 0)), count(r) " +
            " FROM Movie m " +
            " LEFT JOIN MovieImage mi ON mi.movie = m " +
            " LEFT JOIN Review r ON r.movie = m " +
            " GROUP BY m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("SELECT m, mi, avg(coalesce(r.grade,0)) ,count(distinct(r))" +
            " FROM Movie m " +
            " LEFT OUTER JOIN MovieImage mi on mi.movie = m " +
            " LEFT OUTER JOIN Review r on r.movie = m " +
            " WHERE m.mno = :mno" +
            " GROUP BY mi")
    List<Object[]> getMovieWithAll(Long mno);   // 특정영화 조회

}
