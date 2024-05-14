# 개인 프로젝트 Hello, consultant!

## 프로젝트 기획 배경

외국인들이 본인의 언어로 소통이 가능한 상담사를 찾으려고 노력하는 모습을 보고 서로의 언어로 소통이 가능한 상담 채팅 플랫폼을 만들고자 계획하게 되었습니다.
<br />

## 제공하는 기능
- 외국인 고객의 채팅 시작 전 고객 정보 입력 및 채팅방 비밀 번호 설정 (Yes, continue 클릭시 히스토리 채팅 목록으로 이동)
![image](https://github.com/byeolhaha/hello-counselers/assets/108210958/470eca3f-e0eb-4d53-931a-87f5be5a00b2)
- 서로의 언어로 번역되는 채팅 및 상담원의 상담 완료 버튼(상태가 AVAILABLE로 되어 다른 외국인 상담 매칭 가능), 금칙어 처리
![image](https://github.com/byeolhaha/hello-counselers/assets/108210958/92056e34-5a55-4bf8-ab1f-4e85a152efba)
- 히스토리 저장에 따른 상담원 및 외국인 고객의 채팅 목록
![image](https://github.com/byeolhaha/hello-counselers/assets/108210958/efef140c-3bf6-4eef-b68f-e2abff665482)
- 채팅방 읽지 않은 메세지 수 제공 
- fallback method 및 Open 상태 전환 시 관리자에게 텔레그램 봇 알람
# 운영중인 사이트

- 외국인 고객 : https://hellocounselors.p-e.kr/
- 상담원 : https://hellocounselors.p-e.kr/templates/consultant-login.html

# 프로젝트의 주요 관심사
- 외부 API에 대한 이중화 및 fallback method 호출, open 상태로의 전환 시 관리자 알림
- 금칙어 조회에 성능이 좋은 아호코라식 알고리즘을 사용 및  ApplicationRunner를 통하여 스프링 부트 초기화 시 DB에 있는 금칙어 데이터를 로컬 캐시에 올려두도록 구현 및 새롭게 DB에 추가된 경우 서버를 재가동 시키는 번거로움이 존재하여 API 구현
- 개인 정보 노출을 고려하여 채팅방 비밀번호를 설정 및 암호화, ip Address와 접속 일자를 통한 가변적 salt 처리

## 기술
- 프레임워크 : Java 17,  Spring(Boot 3.2.0, JPA, Hibernate), JUnit5, Javascript
- DB :  MySQL 8.0, MongoDB
- 인프라 :  AWS(EC2, RDS, Code Deploy),  Github Action, Nginx

## 아키텍처

![image](https://github.com/byeolhaha/hello-counselers/assets/108210958/4d69d83c-a5fa-4140-bfb5-d740b4d90975)





