package org.zerock.mreview.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m, mi, avg(coalesce(r.grade, 0)), count(distinct r) FROM Movie m LEFT JOIN MovieImage mi ON mi.movie = m LEFT JOIN Review r ON r.movie = m GROUP BY m")
    Page<Object[]> getListPage(Pageable pageable);
}
