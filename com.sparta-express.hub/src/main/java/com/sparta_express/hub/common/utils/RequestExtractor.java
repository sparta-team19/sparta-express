package com.sparta_express.hub.common.utils;


import com.sparta_express.hub.common.exception.CustomException;
import com.sparta_express.hub.common.exception.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class RequestExtractor {

    public static String extractHeaderOf(String target) {

        HttpServletRequest request
                = (HttpServletRequest) RequestContextHolder.getRequestAttributes();

        if (!(request instanceof ServletRequestAttributes)) {
            throw new IllegalStateException("""
                        RequestContextHolder.getRequestAttributes() is not ServletRequestAttributes.
                        Maybe thread is not an HTTP request thread.
                    """);
        }


        String extractedHeader
                = request.getHeader(target);

        if (extractedHeader == null) {
            throw new CustomException(ErrorType.INVALID_REQUEST_HEADER);
        }


        return extractedHeader;
    }


    private RequestExtractor() {
    }
}
