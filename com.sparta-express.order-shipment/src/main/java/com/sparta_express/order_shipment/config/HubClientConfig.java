package com.sparta_express.order_shipment.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta_express.order_shipment.application.dto.ResponseDataDto;
import com.sparta_express.order_shipment.infrastructure.dto.HubResponseDto;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


public class HubClientConfig {

    @Bean
    public Decoder customFeignDecoder() {
        return new Decoder() {
            private final ObjectMapper objectMapper = new ObjectMapper();

            // 응답 객체가 responseDto 에 매핑되어 올 때 제네릭 타입을 지정하기 위한 설정
            @Override
            public Object decode(Response response, Type type) throws IOException {
                if (type instanceof Class) {
                    return objectMapper.readValue(response.body().asInputStream(), (Class<?>) type);
                } else {
                    return objectMapper.readValue(response.body().asInputStream(),
                            new TypeReference<ResponseDataDto<List<HubResponseDto>>>() {});
                }
            }
        };
    }

}
