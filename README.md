![image](https://github.com/user-attachments/assets/f3f0a52a-dc7f-4445-a43e-53b9b6fd4a6e)
# sparta-express
MSA 기반 물류 관리 및 배송 시스템

<br/>

## 목차
- [개발 기간](#개발-기간)
- [프로젝트 소개](#프로젝트-소개)
- [프로젝트 목적](#프로젝트-목적)
- [팀원 역할 분담](#팀원-및-역할-분담)
- [서비스 구성 및 실행 방법](#서비스-구성-및-실행-방법)
- [ERD 설계](#erd-설계)
- [API 명세서](#API-명세서)

<br/>

## 개발 기간
- 2024.12.05 ~ 2024.12.17

<br/>

## 프로젝트 소개
**Gourmate**는 **Gourmet와 Mate**의 조합으로, 고객이 음식을 쉽게 주문하고 맛있는 음식을 만날 수 있도록 도와주는 동반자 라는 의미를 담은 **주문 관리 플랫폼**입니다.<br> 
전화 주문 및 수작업 기록 방식에서 벗어나 앱을 통해 주문을 받고 처리할 수 있도록 하여 업무의 효율성을 극대화하고 반복적이고 인력이 많이 소요되는 작업을 자동화하는 것을 목표로 합니다.

<br/>

## 프로젝트 목적
- **백엔드 개발 역량 강화**: 기능/비기능 요구사항을 구체화하고 이를 실현
- **협업 경험**: 팀 프로젝트를 통해 팀원들과의 소통 및 협업 역량 강화
- **AI 서비스 연동**: 생성형 인공지능 API를 활용하여 사용자에게 편리성 제공
- **Slack 서비스 연동**: Slack API를 활용하여 배송 담당자에게 배송 관련 메시지 발송
- **MSA 기반 개발**: 서비스간 통신을 통해 실무에 필요한 **MSA 설계 역량**과 **문제 해결 능력**을 강화

<br/>

## 팀원 및 역할 분담
<table border="1" class="table">
  <thead>
    <tr>
        <th scope="col" style="text-align: center;"> 팀장 </th>
        <th scope="col" style="text-align: center;"> 팀원 </th>
        <th scope="col" style="text-align: center;"> 팀원 </th>
        <th scope="col" style="text-align: center;"> 팀원 </th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td align="center"><a href="https://github.com/cylcoder"><img src="https://avatars.githubusercontent.com/u/156181227?v=4" width="100px;" alt=""/></a><br /></td>
      <td align="center"><a href="https://github.com/kwj0605"><img src="https://avatars.githubusercontent.com/u/107970778?v=4" width="100px;" alt=""/></a><br /></td>
      <td align="center"><a href="https://github.com/sohyuneeee"><img src="https://avatars.githubusercontent.com/u/110372498?v=4" width="100px;" alt=""/></a><br /></td>
      <td align="center"><a href="https://github.com/Soeng-dev"><img src="https://avatars.githubusercontent.com/u/69845367?v=4" width="100px;" alt=""/></a><br /></td>
    </tr>
      <tr>
        <th scope="col" style="text-align: center;"><a href="https://github.com/kimsung3113"> 이창연 </a></th>
        <th scope="col" style="text-align: center;"><a href="https://github.com/kwj0605"> 김우진 </a></th>
        <th scope="col" style="text-align: center;"><a href="https://github.com/sohyuneeee"> 이소현 </a></th>
        <th scope="col" style="text-align: center;"><a href="https://github.com/wooseok50"> 김성용 </a></th>
    </tr>
  </tbody>
</table>

<br/>

> **이창현** <br>
>`업체, 상품, 주문 도메인 기능 구현` <br><br>
> **김우진** <br>
> `인증 인가, 사용자, AI(Gemini) 응답, Slack 메시지 기능 구현`<br><br>
> **이소현** <br>
> `주문 생성, 배송, 배송경로 도메인 기능 구현` <br><br>
> **김성용** <br>
> `허브, 허브간 경로, 출발허브 - 배송지 이동 경로 모델링 기능 구현` <br><br>

<br/>

## 서비스 구성 및 실행 방법

- Java 17
- Gradle 
- PostgreSQL
- Redis
- Springboot 3.4.0
- Gemini API
- Slack API
- Github
- NETFLIX EUREKA
- Spring Cloud Gateway

### 실행 방법

- **공용 DB 정보**:
    - 퍼블릭 IP 주소: `43.203.205.156`
    - 기본 포트(Postgresql, Redis): `5432`, `6379`
 
<br/>


## ERD 설계
<img width="1040" alt="스크린샷 2024-11-18 오후 12 46 01" src="https://github.com/user-attachments/assets/39e288c5-66b3-4fdf-9459-ea9835031ef6">

<br/>

## 아키텍처
![image](https://github.com/user-attachments/assets/54c198a9-94d8-4f78-99de-06c235649efa)

</br> 

## API 명세서

<details>
<summary>열기</summary>
https://www.notion.so/teamsparta/API-b9312502c0694283b8ddac908b9e3f12
</details>

<br>
<br>

## 기술 스택
<div align="center">
    <img src="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white">
    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">
    <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens"><br/>
    <img src="https://img.shields.io/badge/Spring Data JPA-gray?style=for-the-badge&logoColor=white"/>
    <img src="https://img.shields.io/badge/Querydsl-blue?style=for-the-badge&logoColor=white"/><br/>
    <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white">
    <img src="https://img.shields.io/badge/Redis-FF4438?style=for-the-badge&logo=Redis&logoColor=white">
    <img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"><br/>
    <img src="https://img.shields.io/badge/googlegemini-8E75B2?style=for-the-badge&logo=googlegemini&logoColor=white">
    <img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"><br/>
</div>

<br/>
<br/>

