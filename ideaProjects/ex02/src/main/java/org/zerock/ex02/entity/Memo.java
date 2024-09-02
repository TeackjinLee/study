package org.zerock.ex02.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // JPA로 관리되는 엔티티 객체라는걸 정의
@Table(name="tbl_memo") // 엔티티 클래스를 어떠한 테이블로 생성할 것인지에 대한 정보
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;

}
