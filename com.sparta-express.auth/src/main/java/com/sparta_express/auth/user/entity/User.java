package com.sparta_express.auth.user.entity;


import com.sparta_express.auth.common.entity.BaseEntity;
import com.sparta_express.auth.user.dto.SignUpRequestDto;
import com.sparta_express.auth.user.dto.UserRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "p_users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 100)
    private String nickname;

    @Column(nullable = false)
    private String slackId;

    @Column(nullable = false)
    private UserRole role;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = Boolean.TRUE;

    @OneToOne
    @JoinColumn(name = "delivery_managers_id")
    @Setter(value = AccessLevel.NONE)
    private DeliveryManager deliveryManager;

    @Builder
    private User(String username, String email, String password
        , String nickname, String slackId, UserRole role, Boolean isPublic) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.slackId = slackId;
        this.role = role;
        this.isPublic = isPublic;
    }

    public static User of(SignUpRequestDto requestDto, String encodedPassword) {
        return builder()
            .username(requestDto.getUsername())
            .email(requestDto.getEmail())
            .password(encodedPassword)
            .nickname(requestDto.getNickname())
            .slackId(requestDto.getSlackId())
            .role(requestDto.getRole())
            .build();
    }

    public void updateUser(UserRequestDto requestDto) {
        builder()
            .username(requestDto.getUsername())
            .email(requestDto.getEmail())
            .nickname(requestDto.getNickname())
            .slackId(requestDto.getSlackId())
            .isPublic(requestDto.getIsPublic())
            .build();
    }

    public void deleteUser() {

    }
}
