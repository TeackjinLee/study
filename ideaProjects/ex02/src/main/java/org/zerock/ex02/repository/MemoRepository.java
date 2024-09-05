package org.zerock.ex02.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.ex02.entity.Memo;

import java.util.List;

/** JpaRepository -> PagingAndSortRepository -> CrudRepository -> Repository
    MemoRepository는 특이하게도 인터페이스 자체이고 JpaRepository 인터페이스를 상속하는 것만으로 모든 작업이 끝납니다.
    JpaRepository를 사용할 때는 엔티티의 타입 정보(Memo클래스 타입)와 @Id의 타입을 지정하게 됩니다.
    이처럼 인터페이스 선언만으로 자동으로 스프링 빈(bean)으로 등록됩니다(스프링이 내부적으로 인터페이스 타입에 맞는 객체를 생성해서 빈으로 등록합니다.)
 */
public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    void deleteMemoByMnoLessThan(Long num);

    @Query(value = "SELECT m FROM Memo m WHERE m.mno > :mno",
            countQuery = "SELECT count(m) FROM Memo m WHERE m.mno > :mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);
}
