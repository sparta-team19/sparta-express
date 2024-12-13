package com.sparta_express.auth.user.controller;

import com.sparta_express.auth.common.ResponseMessageDto;
import com.sparta_express.auth.common.ResponseStatus;
import com.sparta_express.auth.jwt.JwtTokenProvider;
import com.sparta_express.auth.user.dto.UserRequestDto;
import com.sparta_express.auth.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     *
     * @param requestDto
     * @return
     */
    @PostMapping("/users/signup")
    public ResponseEntity<ResponseMessageDto> signup(@Valid @RequestBody UserRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.SIGNUP_SUCCESS));
    }

//    @GetMapping("/users/{userId}")
//    public ResponseEntity<ResponseDataDto<UserResponseDto>> getUser(@PathVariable Long userId) {
//
//    }
}
