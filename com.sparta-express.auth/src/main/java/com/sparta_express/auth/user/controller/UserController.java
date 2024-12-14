package com.sparta_express.auth.user.controller;

import com.sparta_express.auth.common.ResponseDataDto;
import com.sparta_express.auth.common.ResponseMessageDto;
import com.sparta_express.auth.common.ResponseStatus;
import com.sparta_express.auth.security.UserDetailsImpl;
import com.sparta_express.auth.user.dto.UserRequestDto;
import com.sparta_express.auth.user.dto.UserResponseDto;
import com.sparta_express.auth.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ResponseDataDto<PagedModel<Page<UserResponseDto>>>> getUsers(
        Pageable pageable,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Page<UserResponseDto> responseDto = userService.getUsers(pageable, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto(ResponseStatus.GET_USER_SUCCESS,
            new PagedModel(responseDto)));
    }

    /**
     * 유저 정보 단일 조회
     *
     * @param userId
     * @param userDetails
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDataDto<UserResponseDto>> getUsers(
        @PathVariable Long userId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto responseDto = userService.getUser(userId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto(ResponseStatus.GET_USER_SUCCESS,
            responseDto));
    }
}
