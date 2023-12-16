package com.cydeo.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true,updatable = false)
    public LocalDateTime insertDateTime;
    @Column(nullable = true,updatable = false)
    public Long insertUserId;
    @Column(nullable = true)
    public LocalDateTime lastUpdateDateTime;
    @Column(nullable = true)
    public Long lastUpdateUserId;

    private Boolean isDeleted=false;

}
