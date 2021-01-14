# sturdy-lamp

## 프로젝트 소개

요구사항에 맞게 구현을 하였습니다. 주요 문제해결 전략을 간단히 소개하겠습니다.

1. API의 endpoint는 RESTful으로 만들었습니다.
1. 환자등록번호 구현을 위해 별도의 Sequence 테이블을 만들어 추가하였습니다. 동시성 처리는 따로 하지 못했고 한다면 Redis를 통해 진행할 것 같습니다.
2. 동적 쿼리 및 fetch join이 필요한 경우 querydsl을 통하여 조회하도록 하였습니다.
3. 테스트 커버리지는 jacoco로 리포트를 확인 할 수있도록 설정하였습니다.
4. 각 데이터의 삭제는 deleted 컬럼을 추가함으로 소프트 삭제하도록 설정하였습니다.
5. 에러 처리는 HttpErrorControllerAdvice를 통하여 처리하도록 하였습니다. HttErrorException를 상속받아 HttpStatus를 입력하여 상태 코드를 처리하도록 하였습니다.
6. 요청 응답의 빈값의 처리는 null을 사용하지 않고 빈 string("")을 사용합니다.
7. spring-rest-docs 는 처음 사용하여 많은 문서를 작성하지는 못했습니다.