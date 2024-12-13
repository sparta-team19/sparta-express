package com.sparta_express.company_product.external;

import com.sparta_express.company_product.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_hubs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hub extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Builder
    public Hub(String name, String address, String status, Double latitude, Double longitude) {
        this.name = name;
        this.address = address;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Hub createHub(String name, String address, String status, Double latitude, Double longitude) {
        return Hub.builder()
                .name(name)
                .address(address)
                .status(status)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

}
