# 👤 User API Document

회원가입 · 로그인(Session) · 조회 · 수정 · 삭제 기능을 제공하는 유저 API입니다.

---

## 📌 Base URL
```
/users
```

---

## 🔐 회원가입
### POST `/users`
**Request Body**
```json
{
  "name": "홍길동",
  "email": "test@test.com",
  "password": "1234"
}
```

**Response**
```json
{
  "id": 1,
  "name": "각시탈",
  "email": "Gaksital4ever@naver.ccm",
  "createdAt": "...",
  "modifiedAt": "..."
}
```

---

## 🔐 로그인 (세션 생성)
### POST `/users/login`
**Request Body**
```json
{
  "email": "test@test.com",
  "password": "1234"
}
```

**Response**
```json
{
  "id": 1,
  "email": "test@test.com",
  "createdAt": "...",
  "modifiedAt": "..."
}
```

---

## 👀 유저 전체 조회
### GET `/users`

---

## 👀 특정 유저 조회 (로그인 + 본인만 가능)
### GET `/users/{id}`

Response Example:
```json
{
  "id": 1,
  "name": "홍길동",
  "email": "test@test.com",
  "createdAt": "...",
  "modifiedAt": "..."
}
```

---

## ✏️ 유저 수정 (본인만 가능)
### PATCH `/users/{id}`
```json
{
  "name": "새 이름",
  "email": "new@mail.com"
}
```

---

## 🗑 유저 삭제 (본인만 가능)
### DELETE `/users/{id}`

Response:
```
해당 유저가 삭제 되었습니다.
```

---
