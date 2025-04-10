# sparta-express

## ⭐프로젝트 설명
#### 물류 관리 및 배송 시스템을 위한 MSA 기반 플랫폼 개발

<br/>

## 👨‍👩‍👧‍👦팀원 소개
## 팀원 소개

| 이름   | 역할            | 
|--------|---------------|
| 김우진 | 사용자 도메인 개발, AI 연동 기능 개발, Slack 메시지 개발 | 
| 김성용 | 허브 도메인 개발 | 
| 이창연 | 주문, 상품, 업체 도메인 개발 | 
| 이소현 | 배송 도메인 개발 |

## ✨프로젝트 핵심 목표
'물류 관리 및 배송 시스템'을 개발하며 MSA의 복잡성을 이해하고, 구축할 수 있는 역량을 높힘

- MSA 애플리케이션 구성
  - 유레카 서버로 모든 애플리케이션 관리
  - 게이트웨이로 모든 인입을 관리
  - 게이트웨이에서 인증 및 권한 확인
- 허브간 이동정보 관리
  - P2P 모델을 사용하여 허브 간 이동정보 경로 모델링
- 생성형 AI API 활용 배송 예상 시간 생성 및 알림 처리
  - Gemini API 활용하여 상품 및 주문, 배송 내용을 통해 최종 발송 시한 생성
  - Slack API 활용하여 최종 발생 시한 메시지 알림

 ## ⛏️기술스택
 **Back-end**

- **Java 17 / SpringBoot 3.4.0 / Spring Security / Spring Cloud / JPA**

**DB**

- **PostgreSQL, Redis**

## MSA 구성
<details> <summary><strong>1. Gateway Service</strong></summary> <br>
역할: 로그인 여부 확인 및 API 요청 라우팅

기능:

토큰 검증

각 서비스로 요청을 분산시키는 API Gateway 역할

</details> <details> <summary><strong>2. User Service</strong></summary> <br>
역할: 사용자 인증 및 인가, 사용자 데이터 관리

기능:

인증(Authentication): 사용자 로그인, 토큰 발급

인가(Authorization): 사용자 권한 확인, 토큰 만료 및 재발급 관리

사용자 정보 생성, 수정, 조회 등 관리 기능

</details> <details> <summary><strong>3. Company - Product Service</strong></summary> <br>
역할: 업체와 상품 정보 관리

기능:

업체 등록, 수정, 조회

상품 생성, 수정, 삭제 및 조회

업체와 상품 간의 관계 모델링

</details> <details> <summary><strong>4. Order Shipment Service</strong></summary> <br>
역할: 주문 및 배송, 배송 경로 관리

기능:

주문 생성 프로세스

FeignClient를 통한 다른 서비스와의 동기 통신

플래그 기반 보상 트랜잭션: 재고 감소 후 에러 발생 시 재고 복구로 데이터 일관성 유지

Fallback 메커니즘 구현

Circuit Breaker 적용으로 장애 발생 시 시스템 과부하 방지

</details> <details> <summary><strong>5. AI-Slack Service</strong></summary> <br>
역할: 최종 발송 시한 생성, 메시지 발송

기능:

최종 발송 시한 생성

AI(Gemini API)를 사용한 시한 생성

AI 요청/응답의 생성, 삭제, 조회, 검색

메시지 발송

Slack Bot을 통한 메시지 전송

Slack 메시지 생성, 수정, 삭제 (Slack API 활용)

메시지 조회 및 검색

</details> <details> <summary><strong>6. Hub Service</strong></summary> <br>
역할: 허브, 허브 간 경로, 출발 허브 - 배송지 최적 경로 모델링

기능:

물류 허브/허브 간 경로 등록, 수정, 삭제 및 조회

허브 간 경로 모델링 및 최적화

</details>
 
## ❓고려사항
<details> <summary><strong>허브 간 이동경로 모델링</strong></summary> <br>
Hub 2 Hub 방식 (B2B)
대량 운송을 통해 비용 최적화 가능

다익스트라 알고리즘 채택 배경

물류센터는 오랜 시간과 큰 비용을 들여 구축되며, 이를 위한 계획 수립 필요

매번 경로가 바뀌는 실시간 처리보다, 미리 정해진 경로 기반의 최적화가 더 효율적

장애 상황엔 대체 경로를 이용한 처리 방식이 더 적절

경로의 시간/거리 정보가 고정되어 있는 상황에서는 다익스트라 알고리즘이 적절

</details> <details> <summary><strong>허브 경로 도메인: ConcurrentMap 기반 Spring 기본 캐싱</strong></summary> <br>
작은 데이터 규모

물류센터는 큰 비용과 시간이 소요되며, 허브/경로 정보가 자주 바뀌지 않음

허브 17개, 경로 24개의 정적 데이터 규모는 작고 안정적

서비스 레이어에서 대부분 캐싱 처리

대부분의 API 요청은 변경되지 않는 데이터를 조회

수평 확장 가능성이 낮음

결론

Spring 기본 캐싱(@Cacheable)만으로 충분

네트워크 통신 문제, 속도 저하 걱정 없이 단순하고 유지보수 용이

추후 Redis로 변경 시에도 AOP 기반이므로 수정이 쉬움

</details>

## ERD
![Image](https://github.com/user-attachments/assets/8bbc3a59-55d2-4604-bf54-02cc6539eb5c)

## 아키텍처
![Image](https://github.com/user-attachments/assets/76313107-32f0-46a3-b38e-01238edbbd8e)
