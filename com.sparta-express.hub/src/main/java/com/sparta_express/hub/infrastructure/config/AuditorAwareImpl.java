package com.sparta_express.hub.infrastructure.config;

import com.sparta_express.hub.common.exception.CustomException;
import com.sparta_express.hub.common.exception.ErrorType;
import com.sparta_express.hub.common.utils.RequestExtractor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {


    @Override
    public Optional<Long> getCurrentAuditor() {

        String userIdHeader
                = RequestExtractor.extractHeaderOf("X-User-Id");

        long userId;//todo 멘토님 문의후 id를 pk , loginId중 결정
        try {
            userId = Long.parseLong(userIdHeader);

        } catch (NumberFormatException e) {
            throw new CustomException(ErrorType.USER_ID_INVALID);

        }

        return Optional.of(userId);
    }

}