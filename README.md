# Spring Security Template

스프링 시큐리티와 MongoDB를 이용한 JWT 인증을 구현한 템플릿 프로젝트입니다.

## API 명세서

### 로그인
- **Method:** POST
- **Endpoint:** `/api/user`
- **Request Body:**
  ```json  
  {  
    "username": "(username)",  
    "password": "(password)"  
  }  
  ```  
- **Response Body:** 정상적으로 로그인되면 `accessToken`과 `refreshToken`이 응답으로 반환됩니다.

### 회원가입
- **Method:** PUT
- **Endpoint:** `/api/user`
- **Request Body:**
  ```json  
  {  
    "email": "(email)",
    "password": "(password)"
  }  
  ```  
- **Response Body:** 정상적으로 회원가입되면 별도의 응답 바디는 없습니다.

### 토큰 갱신
- **Method:** POST
- **Endpoint:** `/api/refresh-token`
- **Request Body:**
  ```json  
  {  
    "refreshToken": "(refresh token)"
  }
  ```
- **Response Body:** 정상적으로 갱신되면 `accessToken`과 `refreshToken`이 응답으로 반환됩니다.
