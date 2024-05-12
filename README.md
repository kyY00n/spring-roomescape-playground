# Spring MVC

> 예시입니다. 클클이화 파이팅 :D

## 1단계 요구사항

- [x] **`localhost:8080`** 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답할 수 있도록 구현하세요.
- [x] 어드민 메인 페이지는**`templates/home.html`**파일을 이용하세요.

## 2단계 요구사항

- [x] /reservation 요청 시 아래 화면과 같이 어드민 페이지가 응답할 수 있도록 구현하세요.
- [x] 어드민 메인 페이지는 templates/reservation.html 파일을 이용하세요.
- [x] 아래의 API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현하세요.

#### Request

```http request
GET /reservations HTTP/1.1
```

```http
HTTP/1.1 200 
Content-Type: application/json

[
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-01-01",
        "time": "10:00"
    },
    {
        "id": 2,
        "name": "브라운",
        "date": "2023-01-02",
        "time": "11:00"
    }
]

```

## 3단계 요구사항

API 명세를 따라 예약 추가 API 와 삭제 API를 구현하세요.
- [x] 예약 추가 API
- [x] 예약 삭제 API

### 예약 추가 API

#### Request
```http request
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}

```

#### Response
```http
HTTP/1.1 201 
Location: /reservations/1
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}

```

### 예약 삭제 API

#### Request

```http request
DELETE /reservations/1 HTTP/1.1
```

#### Response

```http
HTTP/1.1 204 No Content

```

## 4단계 요구사항

- 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400으로 응답하세요.
  - [x] 예약 추가 시 필요한 인자값이 비어있는 경우
  - [x] 삭제 할 예약의 식별자로 저장된 예약을 찾을 수 없는 경우


## 5단계 요구사항
- [ ] h2 데이터베이스를 활용하여 데이터를 저장하도록 수정하세요.

### gradle 의존성 추가
- build.gradle 파일을 이용하여 다음 두 의존성을 추가하세요.
 - [ ] spring-boot-stater-jdbc
 - [ ] h2

### 테이블 스키마 정의

- [ ] 데이터베이스 테이블 생성을 위해 schema.sql 파일을 생성하고 테이블을 생성하는 쿼리를 작성하세요.

### 데이터베이스 설정
- [ ] h2 데이터베이스의 console 기능을 활성화하세요.
- [ ] datasource url을 다음과 같이 지정하세요: jdbc:h2:mem:database
