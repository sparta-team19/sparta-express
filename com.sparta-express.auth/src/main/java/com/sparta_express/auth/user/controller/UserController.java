package com.sparta_express.auth.user.controller;

import com.sparta_express.auth.common.ResponseDataDto;
import com.sparta_express.auth.common.ResponseMessageDto;
import com.sparta_express.auth.common.ResponseStatus;
import com.sparta_express.auth.jwt.JwtTokenProvider;
import com.sparta_express.auth.security.UserDetailsImpl;
import com.sparta_express.auth.user.dto.UserRequestDto;
import com.sparta_express.auth.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     *
     * @param requestDto
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<ResponseMessageDto> signup(
        @Valid @RequestBody UserRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.SIGNUP_SUCCESS));
    }

    /**
     * 유저 정보 조회
     *
     * @param pageable
     * @param userDetails
     * @return
     */
    @GetMapping
    public ResponseEntity<ResponseDataDto<PagedModel<Page<?>>>> getUsers(
        Pageable pageable,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Page<?> responseDto = userService.getUsers(pageable, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto(ResponseStatus.GET_USER_SUCCESS,
            new PagedModel(responseDto)));
    }
}
