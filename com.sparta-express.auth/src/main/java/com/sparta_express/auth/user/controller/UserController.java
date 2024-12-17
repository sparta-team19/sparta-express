package com.sparta_express.auth.user.controller;

import com.querydsl.core.types.Predicate;
import com.sparta_express.auth.common.CustomException;
import com.sparta_express.auth.common.ErrorType;
import com.sparta_express.auth.common.ResponseDataDto;
import com.sparta_express.auth.common.ResponseMessageDto;
import com.sparta_express.auth.common.ResponseStatus;
import com.sparta_express.auth.security.UserDetailsImpl;
import com.sparta_express.auth.user.dto.DeliveryManagerResponseDto;
import com.sparta_express.auth.user.dto.SignUpRequestDto;
import com.sparta_express.auth.user.dto.UserRequestDto;
import com.sparta_express.auth.user.dto.UserResponseDto;
import com.sparta_express.auth.user.entity.User;
import com.sparta_express.auth.user.entity.UserRole;
import com.sparta_express.auth.user.service.UserService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
        @Valid @RequestBody SignUpRequestDto requestDto) {
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
        @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Page<UserResponseDto> responseDto = userService.getUsers(pageable, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto(ResponseStatus.GET_USER_SUCCESS,
            new PagedModel(responseDto)));
    }

//    /**
//     * 유저 정보 단일 조회
//     *
//     * @param userId
//     * @param userDetails
//     * @return
//     */
//    @GetMapping("/{userId}")
//    public ResponseEntity<ResponseDataDto<UserResponseDto>> getUser(
//        @PathVariable Long userId,
//        @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        UserResponseDto responseDto = userService.getUser(userId, userDetails.getUser());
//        return ResponseEntity.ok(new ResponseDataDto(ResponseStatus.GET_USER_SUCCESS,
//            responseDto));
//    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseDataDto<UserResponseDto>> getUserByEmail(
            @PathVariable String email) {
        UserResponseDto responseDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(new ResponseDataDto(ResponseStatus.GET_USER_SUCCESS,
                responseDto));
    }

    /**
     * 유저 정보 수정
     *
     * @param userId
     * @param userDetails
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<ResponseDataDto<UserResponseDto>> updateUser(
        @PathVariable Long userId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.updateUser(userId, userDetails.getUser(),
            requestDto);
        return ResponseEntity.ok(new ResponseDataDto(ResponseStatus.UPDATE_USER_SUCCESS,
            responseDto));
    }

    /**
     * 회원 삭제
     *
     * @param userId
     * @param userDetails
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseMessageDto> deleteUser(
        @PathVariable Long userId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUser(userId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.DELETE_USER_SUCCESS));
    }

    /**
     * 회원 검색
     *
     * @param userId
     * @param role
     * @param pageable
     * @param predicate
     * @return
     */
    @GetMapping("search")
    public ResponseEntity<ResponseDataDto<Page<UserResponseDto>>> searchUser(
        @RequestHeader(value = "X-User-Id", required = true) String userId,
        @RequestHeader(value = "X-Role", required = true) UserRole role,
        @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
        @QuerydslPredicate(root = User.class) Predicate predicate
    ) {
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.SEARCH_USER_SUCCESS,
            userService.serchUser(userId, role, predicate, pageable)));
    }

    /**
     * 배송 담당자 등록
     *
     * @param userId
     * @param role
     * @param requestDto
     * @return
     */
    @PostMapping("/delivery")
    public ResponseEntity<ResponseDataDto<DeliveryManagerResponseDto>> createDeliveryManager(
        @RequestHeader(value = "X-User-Id", required = true) String userId,
        @RequestHeader(value = "X-Role", required = true) UserRole role,
        @RequestBody UserRequestDto requestDto
    ) {
        if (!(role == UserRole.DELIVERY_MANAGER || role == UserRole.MASTER)) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.CREATE_DELIVERY_MANAGER_SUCCESS,
                userService.createDeliveryManager(userId, requestDto)));
    }

    /**
     * 배송 담당자 정보 조회
     *
     * @param userId
     * @param role
     * @param pageable
     * @return
     */
    @GetMapping("/delivery")
    public ResponseEntity<ResponseDataDto<Page<DeliveryManagerResponseDto>>> getDeliveryManagers(
        @RequestHeader(value = "X-User-Id", required = true) String userId,
        @RequestHeader(value = "X-Role", required = true) UserRole role,
        @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable
    ) {
        if (!(role == UserRole.DELIVERY_MANAGER || role == UserRole.MASTER)) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.GET_DELIVERY_MANAGER_SUCCESS,
                userService.getDeliveryManagers(userId, pageable)));
    }

    /**
     * 배송 담당자 정보 단일 조회
     *
     * @param role
     * @param deliveryId
     * @return
     */
    @GetMapping("/delivery/{deliveryId}")
    public ResponseEntity<ResponseDataDto<DeliveryManagerResponseDto>> getDeliveryManager(
        @RequestHeader(value = "X-Role", required = true) UserRole role,
        @PathVariable UUID deliveryId
    ) {
        if (!(role == UserRole.DELIVERY_MANAGER || role == UserRole.MASTER)) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.GET_DELIVERY_MANAGER_SUCCESS,
                userService.getDeliveryManager(deliveryId)));
    }

    /**
     * 배송 담당자 수정
     *
     * @param role
     * @param deliveryId
     * @param requestDto
     * @return
     */
    @PutMapping("/delivery/{deliveryId}")
    public ResponseEntity<ResponseDataDto<DeliveryManagerResponseDto>> updateDeliveryManager(
        @RequestHeader(value = "X-Role", required = true) UserRole role,
        @PathVariable UUID deliveryId,
        @RequestBody UserRequestDto requestDto
    ) {
        if (!(role == UserRole.DELIVERY_MANAGER || role == UserRole.MASTER)) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.PUT_DELIVERY_MANAGER_SUCCESS,
                userService.updateDeliveryManager(deliveryId, requestDto)));
    }

    /**
     * 배송 담당자 삭제
     *
     * @param role
     * @param deliveryId
     * @return
     */
    @DeleteMapping("/delivery/{deliveryId}")
    public ResponseEntity<ResponseMessageDto> deleteDeliveryManager(
        @RequestHeader(value = "X-Role", required = true) UserRole role,
        @PathVariable UUID deliveryId
    ) {
        if (!(role == UserRole.DELIVERY_MANAGER || role == UserRole.MASTER)) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
        userService.deleteDeliveryManager(deliveryId);
        return ResponseEntity.ok(
            new ResponseMessageDto(ResponseStatus.DELETE_DELIVERY_MANAGER_SUCCESS));
    }

    @GetMapping("/delivery/search")
    public ResponseEntity<ResponseDataDto<Page<DeliveryManagerResponseDto>>> searchDeliveryManager(
        @RequestHeader(value = "X-User-Id", required = true) String userId,
        @RequestHeader(value = "X-Role", required = true) UserRole role,
        @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
        @QuerydslPredicate(root = User.class) Predicate predicate
    ) {
        if (!(role == UserRole.DELIVERY_MANAGER || role == UserRole.MASTER)) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.SEARCH_DELIVERY_MANAGER_SUCCESS,
            userService.searchDeliveryManager(predicate, pageable)));
    }
}
