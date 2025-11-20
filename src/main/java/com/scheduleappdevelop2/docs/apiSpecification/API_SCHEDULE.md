# 🗓 Schedule API Document

일정 생성 · 조회 · 수정 · 삭제 기능을 제공합니다.  
모든 일정은 로그인된 User와 연결됩니다.

---

## 📌 Base URL
```
/schedules
```

---

## 🆕 일정 생성
### POST `/schedules`

**Request Body**
```json
{
  "title": "일정 제목",
  "content": "일정 내용"
}
```

**Response**
```json
{
  "id": 1,
  "title": "일정 제목",
  "content": "일정 내용",
  "userEmail": "test@test.com",
  "createdAt": "...",
  "modifiedAt": "..."
}
```

> 🔒 로그인 필요

---

## 📚 전체 일정 조회 (로그인 불필요)
### GET `/schedules`

---

## 🔍 단일 일정 조회 (본인 일정만)
### GET `/schedules/{id}`

---

## ✏️ 일정 수정 (본인만)
### PATCH `/schedules/{id}`
```json
{
  "title": "수정 제목",
  "content": "수정 내용"
}
```

---

## 🗑 일정 삭제 (본인만)
### DELETE `/schedules/{id}`
Response:
```
해당 일정이 삭제 되었습니다.
```

---
