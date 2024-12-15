package com.sparta_express.company_product.external;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private Long id;

    private String username;

    private String email;

    private String password;

    private String nickname;

    private String slackId;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isPublic = true;

    @Builder
    private User(Long id, String username, String email, String password, String nickname, String slackId, Role role, boolean isPublic) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.slackId = slackId;
        this.role = role;
        this.isPublic = isPublic;
    }

    public static User of(Long id, String username, String email, String password, String nickname, String slackId, Role role, boolean isPublic) {
        return builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .nickname(nickname)
                .slackId(slackId)
                .role(role)
                .isPublic(isPublic)
                .build();
    }

}
