package com.sparta_express.hub.domain.model;


import com.sparta_express.hub.domain.Position;
import com.sparta_express.hub.domain.model.enumumerate.HubStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


//root aggregate
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class Hub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(length = 100)
    private String address;
    @Column(length = 100)
    private HubStatus status;
    @Column
    private Double latitude;
    @Column
    private Double longitude;

    public Position geometryPosition() {

        return Position.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

}
