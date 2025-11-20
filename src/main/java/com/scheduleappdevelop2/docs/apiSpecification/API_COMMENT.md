# 💬 Comment API Document

특정 일정(Schedule)에 대한 댓글을 관리하는 API입니다.  
댓글은 작성자(User)와 일정(Schedule)에 종속됩니다.

---

## 📌 Base URL
```
/comments
```

---

# 🆕 댓글 생성
### POST `/comments`

**Request Body**
```json
{
  "scheduleId": 1,
  "commentContent": "댓글 내용입니다."
}
```

**Response**
```json
{
  "id": 3,
  "commentContent": "댓글 내용입니다.",
  "userEmail": "user@test.com",
  "scheduleId": 1,
  "createdAt": "...",
  "modifiedAt": "..."
}
```

> 🔒 로그인 필요

---

# 📚 특정 일정의 댓글 전체 조회
### GET `/comments/schedule/{scheduleId}`

**Response Example**
```json
[
  {
    "id": 1,
    "commentContent": "첫 댓글",
    "userEmail": "user@test.com",
    "createdAt": "...",
    "modifiedAt": "..."
  }
]
```

---

# 🔍 댓글 단건 조회
### GET `/comments/{id}`

---

# ✏️ 댓글 수정 (본인만)
### PATCH `/comments/{id}`
```json
{
  "commentContent": "수정된 댓글 내용"
}
```

---

# 🗑 댓글 삭제 (본인만)
### DELETE `/comments/{id}`

Response:
```
해당 댓글이 삭제 되었습니다.
```

---
