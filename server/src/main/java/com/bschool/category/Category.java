package com.bschool.category;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "label", nullable = false)
    private String label;

}
