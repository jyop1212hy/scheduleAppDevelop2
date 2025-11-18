
---

# 📌 README.md (미니멀 + 깔끔 버전)

````md
# 📅 Schedule App Develop

Spring Boot · JPA · MySQL 기반 일정 & 유저 관리 서비스입니다.  
회원가입 → 로그인(Session) → 일정 CRUD 흐름으로 동작합니다.

---

## 🛠 Tech Stack
- Java 17  
- Spring Boot 3.5.7  
- Spring Web / Spring Data JPA  
- MySQL 8  
- Lombok  
- Validation  
- Cookie / Session  

---

## 📐 ERD  
User (1) — (N) Schedule

**User**
- id, name, email, password  
- createdAt, modifiedAt  

**Schedule**
- id, toDoTitle, toDoContent  
- userId(FK)  
- createdAt, modifiedAt  

---
````
# 📌 API
[API_SCHEDULE.md](src/main/java/com/scheduleappdevelop2/docs/API_SCHEDULE.md)

[API_USER.md](src/main/java/com/scheduleappdevelop2/docs/API_USER.md)
## 🧑‍💻 User API

### ▶ 회원가입  
````
POST /scheduleUsers
```json
{ "name": "홍길동", "email": "test@test.com", "password": "1234" }
````

### ▶ 로그인
````
POST /scheduleUsers/login
(세션 저장: loginUser = userId)

```json
{ "email": "test@test.com", "password": "1234" }
````

### ▶ 유저 조회
````
GET /scheduleUsers
GET /scheduleUsers/{id}
````
### ▶ 유저 수정
````
PATCH /scheduleUsers/{id}
````
### ▶ 유저 삭제
````
DELETE /scheduleUsers/{id}
````
---

## 📂 Schedule API

### ▶ 일정 생성
````
POST /schedules

json
{ "userId": 1, "toDoTitle": "제목", "toDoContent": "내용" }

````
### ▶ 전체 조회
````
GET /schedules
````
### ▶ 단건 조회
````
GET /schedules/{id}
````
### ▶ 수정
````
PATCH /schedules/{id}
````
json
{ "toDoTitle": "수정제목", "toDoContent": "수정내용" }
````

### ▶ 삭제
````
DELETE /schedules/{id}
````
````
---

## ⚠ Error Handling

* Custom ServerException
* 전역 GlobalExceptionHandler 적용

---

## 📁 Project Structure

```
controller / service / repository / entity / dto / exception
```

---

## 🚀 실행 방법

### 1) DB 생성

```sql
CREATE DATABASE schedules;
```

### 2) application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/schedules
spring.datasource.username=root
spring.datasource.password=12345678
spring.jpa.hibernate.ddl-auto=create
```

### 3) 실행

```
./gradlew bootRun
````



### 4) 실행


```
src/main/java/com.scheduleappdevelop2
 └── schedule
      ├── controller
      ├── service
      ├── repository
      ├── dto
      │    ├── request
      │    └── response
      └── entity

 └── user
      ├── controller
      ├── service
      ├── repository
      ├── dto
      │    ├── request
      │    └── response
      └── entity

 └── global
      ├── config      (예: JPAAuditing 설정)
      ├── exception   (전역 예외핸들러)
      ├── common      (BaseTimeEntity 같은 공용객체)
````

---
