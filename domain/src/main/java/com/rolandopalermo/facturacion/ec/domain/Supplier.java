package com.rolandopalermo.facturacion.ec.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id", updatable = false, nullable = false)
    private long id;

    @Column
    private String idNumber;

    @Column
    private String businessName;

    @Column
    private byte[] logo;

    @Column
    private boolean isDeleted;

}