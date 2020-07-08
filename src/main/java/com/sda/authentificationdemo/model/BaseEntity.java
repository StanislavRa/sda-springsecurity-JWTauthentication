package com.sda.authentificationdemo.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author StanislavR
 */
@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, length = 100)
    private Long id;

    @CreatedDate
    @Column(name = "CREATED")
    private Date created;

    @LastModifiedDate
    @Column(name = "UPDATED")
    private Date updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

}
