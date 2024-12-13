package com.sparta_express.hub.domain.model;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Hub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column(length = 100)
    private String address;
    @Column
    private Double latitude;
    @Column
    private Double longitude;

}
