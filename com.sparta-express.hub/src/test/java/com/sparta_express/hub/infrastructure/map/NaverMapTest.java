package com.sparta_express.hub.infrastructure.map;

import com.sparta_express.hub.domain.Position;
import com.sparta_express.hub.infrastructure.map.NaverMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class NaverMapTest {

    @Autowired
    private NaverMap naverMap;

    @Test
    void searchGeometryPosition() {
        String address = "포항시 송도동 418-14";
        Position position = naverMap.searchPosition(address);

        assertThat(position).isEqualTo(Position.builder().longitude(129.345).latitude(36.345).build());
    }
}