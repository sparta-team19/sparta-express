package com.sparta_express.hub;


import com.sparta_express.hub.domain.Position;
import com.sparta_express.hub.domain.model.BaseEntity;
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
    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;

    public Position geometryPosition() {

        return Position.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Hub)) return false;

        if (this == o || id == ((Hub) o).id) return true;

        return status == ((Hub) o).status
                && address.equals(((Hub) o).address)
                && latitude == ((Hub) o).latitude
                && longitude == ((Hub) o).longitude;
    }
}
