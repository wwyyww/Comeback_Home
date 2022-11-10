# 컴백홈 - 지도 기반 실종자 목격 제보 웹 서비스
2022 제9회 소프트웨어 개발보안 경진대회 장려상(한국정보보호학회장상) 수상


## 핵심 기능
* 등록된 실종자의 위치 정보 시각화
* 지도 검색 기능
  * 검색 장소 주변 10km 내에 존재하는 실종자들의 리스트를 보여줌
* 실종자 제보 작성
* 실종자 제보를 '시간별 보기'와 '장소별 보기'로 볼 수 있음
* 실종자 정보 검색
* 공개/비공개 데이터 구분
  * 공개 데이터 : 실종자 정보는 메인 화면에 제공
  * 비공개 데이터 : 제보 정보는 실종자를 찾는 사람에게만 제공
* 악성글 및 허위 제보 신고 기능 활성화
  * 신고 항목은 관리자의 Slack으로 실시간 전송
  * 유해 이미지 차단 API를 사용해 유해 이미지 필터링 수행


## 기술 스택
- Java 11
- Spring Boot
- Spring Data JPA
- Gradle
- MySQL



## 구현 화면
<img src = "https://user-images.githubusercontent.com/51227226/201108035-d4b96cc3-3904-4de5-b741-dd65d2c65f9c.png" width="70%" height="70%">
<img src = "https://user-images.githubusercontent.com/51227226/201108151-1f8f9a65-7687-4fb7-9a3e-271e8cb1665a.png" width="70%" height="70%">
<img src = "https://user-images.githubusercontent.com/51227226/201108163-0b94afcf-2a08-4f97-996c-75351c421668.png" width="70%" height="70%">
<img src = "https://user-images.githubusercontent.com/51227226/201108176-ea22125a-cb30-48b4-beea-2184949248a9.png" width="70%" height="70%">
<img src = "https://user-images.githubusercontent.com/51227226/201108189-08bf4c0b-b374-4df9-98bd-6d784a4e6fa9.png" width="70%" height="70%">
<img src = "https://user-images.githubusercontent.com/51227226/201108205-76344e2d-d2c9-4b14-b5bd-439abd001855.png" width="70%" height="70%">
<img src = "https://user-images.githubusercontent.com/51227226/201108500-b2b740fc-e42e-47e4-bf2a-acbdfa697d22.png" width="70%" height="70%">




## ERD
![erd](https://user-images.githubusercontent.com/51227226/201107327-9a53dac4-8418-4eff-94df-965c565ea9ee.png)
