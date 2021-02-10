package com.jskim.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 BaseTimeEntity 를 상속할 경우, 필드도 컬럼으로 인식하게 해줌
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 포함
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdData; // Entity 생성되어 저장될 때 시간이 자동 저장됨

    @LastModifiedDate
    private LocalDateTime modifiedDate; // 조회한 Entity 값 변경 시, 시간이 자동 저장됨

}
