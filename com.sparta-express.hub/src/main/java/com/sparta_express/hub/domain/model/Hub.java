package com.sparta_express.hub.domain.model;


import com.sparta_express.hub.domain.model.enumumerate.HubStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;

import java.util.UUID;


//root aggregate
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class Hub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column(length = 100)
    private String address;
    @Column(length = 100)
    private HubStatus status;
    @Column
    private Double latitude;
    @Column
    private Double longitude;

    public Point geometryPoint() {

        return JTSFactoryFinder.getGeometryFactory().createPoint(
                new Coordinate(this.latitude, this.longitude)
        );
    }
}
