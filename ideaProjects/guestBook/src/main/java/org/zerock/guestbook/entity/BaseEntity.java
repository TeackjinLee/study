package org.zerock.guestbook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@MappedSuperclass   // 테이블이 생성 안됨
@EntityListeners(value={AuditingEntityListener.class})
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDate regDate;

    @LastModifiedDate
    @Column(name="modDate")
    private LocalDate modDate;
}
