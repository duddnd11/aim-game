# aim-game

[<img src="https://img.shields.io/badge/release-v0.0.0-yellow?style=flat&logo=google-chrome&logoColor=white" />](http://3.39.6.214/) <br/>
![](https://img.shields.io/badge/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%20%EA%B8%B0%EA%B0%84-2024.10.01~2024.12.27-green?style=flat&logo=&logoColor=white)
## **프로젝트 소개**
Aiming Game은 생성된 타겟을 마우스를 클릭하여 적중시켜 파괴하는 게임입니다.

사용자별 커스텀 게임, 실시간 PVP 모드 등 이용 가능합니다.

## 주요기능
- 회원가입, 로그인, 아이디 / 비밀번호 찾기
  - Spring Security를 사용하여 로그인 세션 및 권한 관리
  - 비밀번호 찾기(변경) 시 인증번호 이메일 발송 -> 올바른 인증번호 입력 시 인증토큰 발급
  - 인증토큰 확인 후 비밀번호 변경
  - ![비밀번호 변경](https://github.com/user-attachments/assets/2d06463e-cc43-4208-85f2-b14ef1afd7ce)

- 싱글 / 커스텀 / 실시간 PVP 게임
  - ![싱글플레이무빙](https://github.com/user-attachments/assets/3da047fa-1138-4a3c-8a66-a010eb4ce1a1)
  - 타겟 생성 기준, 타겟 격파 기준, 이동속도 등을 개인이 커스텀하여 플레이 가능
  - 큐를 이용한 PVP 상대 매칭 진행
  - 웹소켓을 통해 실시간 PVP 게임 플레이
  - ![pvp플레이](https://github.com/user-attachments/assets/439b01b5-ad9f-4d7d-9405-6493b6fd0b7f)

- 개인별 게임 통계, 게임별 유저 순위
  - 개인별 플레이 통계 차트, 리스트
  - 게임별 유저 순위
  <img width="315" height="895" alt="통계그래프" src="https://github.com/user-attachments/assets/fc357661-9662-42f5-8cca-3c9ac28fc9e4" />
  <img width="315" height="783" alt="통계리스트" src="https://github.com/user-attachments/assets/4be71cf2-a0e0-4e29-a139-63ef54a4620e" />
  <img width="315" height="877" alt="랭킹" src="https://github.com/user-attachments/assets/5a60976b-184d-4dec-8527-ede664573c73" />

- 실시간 채팅, 접속 유저
  - 웹소켓을 활용한 실시간 채팅
  - 접속한 전체 유저 목록
  <img width="250" height="877" alt="랭킹" src="https://github.com/user-attachments/assets/ba50ee41-b65f-4a64-94e0-7c1423732e5a" />



## 기술 스택

### **Back-end**
<div>
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Java.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/SpringBoot.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/SpringSecurity.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/SpringDataJPA.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Mysql.png?raw=true" width="80">
</div>

### **Front-end**
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Thymeleaf.png?raw=true" width="80">

### **Infra**
<div>
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/AWSEC2.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/AWSRDS.png?raw=true" width="80">
</div>

## ERD
<img width="1027" height="830" alt="erd" src="https://github.com/user-attachments/assets/6bb4dcef-63b6-47ef-bf70-d1161da0bcac" />
