# 👤 User API 명세서

---

## 1️⃣ 유저 생성 (POST)

### 🔍 개요

| 항목 | 내용 |
|------|------|
| 요청 유형 | POST |
| 엔드포인트 | `/scheduleUsers` |
| 설명 | 새로운 유저 생성 |

---

### 📑 Request Body

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| name | String | O | 유저 이름 |
| email | String | O | 유저 이메일 |
| password | String | O | 유저 비밀번호 |

---

### 📥 요청 예시

```json
{
  "name": "하륜",
  "email": "haryoon@example.com",
  "password": "1234"
}
```

---

### 📤 응답 예시

```json
{
  "id": 1,
  "name": "하륜",
  "email": "haryoon@example.com",
  "createdAt": "2025-11-13T10:11:44",
  "modifiedAt": "2025-11-13T10:11:44"
}
```

---

## 2️⃣ 유저 전체 조회 (GET)

### 🔍 개요

| 항목 | 내용 |
|------|------|
| 요청 유형 | GET |
| 엔드포인트 | `/scheduleUsers` |

---

### 📤 응답 예시

```json
[
  {
    "id": 1,
    "name": "각시탈",
    "email": "gaksital@example.com",
    "createdAt": "2025-11-12T09:33:01",
    "modifiedAt": "2025-11-12T09:33:01"
  }
]
```

---

## 3️⃣ 유저 단건 조회 (GET)

### 🔍 개요

| 항목 | 내용 |
|------|------|
| 요청 유형 | GET |
| 엔드포인트 | `/scheduleUsers/{id}` |

---

### 📤 응답 예시

```json
{
  "id": 3,
  "name": "백엔드천재",
  "email": "genius@example.com",
  "createdAt": "2025-11-13T02:22:11",
  "modifiedAt": "2025-11-13T04:11:22"
}
```

---

## 4️⃣ 유저 수정 (PATCH)

### 📑 Request Body

```json
{
  "name": "하륜짱",
  "email": "superharyoon@example.com"
}
```

---

### 📤 응답 예시

```json
{
  "id": 3,
  "name": "하륜짱",
  "email": "superharyoon@example.com",
  "createdAt": "2025-11-13T01:22:33",
  "modifiedAt": "2025-11-13T05:22:33"
}
```

---

## 5️⃣ 유저 삭제 (DELETE)

### 📤 응답 예시

```json
"선택하신 유저가 삭제 완료되었습니다."
```

