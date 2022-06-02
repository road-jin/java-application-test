# 자바 애플레케이션 테스트
- - -
테스트 코드를 작성하는데 사용할 수 있는 여러 방법과 도구를 설명합니다.<br/>
먼저, 테스트 작성하는 자바 개발자 90%가 넘게 사용하는 JUnit 최신 버전을 학습하여 자바로 테스트를 작성하고 실행하는 방법을 소개합니다.<br/>
다음으로 Mockito를 사용하여 테스트 하려는 코드의 의존성을 가짜로 만들어 테스트 하는 방법을 학습합니다.<br/>
그리고 도커(Docker)를 사용하는 테스트에서 유용하게 사용할 수 있는 Testcontainers를 학습합니다.<br/>
다음으로는 관점을 조금 바꿔서 JMeter를 사용해서 성능 테스트하는 방법을 살펴보고 카오스 멍키 (Chaos Monkey)를<br/>
사용해서 운영 이슈를 로컬에서 재현하는 방법을 살펴보고,<br/>
마지막으로 ArchUnit으로 애플리케이션의 아키텍처를 테스트하는 방법에 대해 학습합니다.<br/>


[더 자바, 애플리케이션을 테스트하는 다양한방법](https://www.inflearn.com/course/the-java-application-test)을 참고하여 정리하였습니다.
- - - 

## 1. JUnit5
JUnit이란?<br/>
>단위테스트를 수행해주는 대표적인 Testing Framework로써 [자바 개발자가 가장 많이 사용하는 테스팅 프레임워크](https://www.jetbrains.com/lp/devecosystem-2021/java/) 입니다.<br/>
단정(assert) 메서드로 테스트 케이스의 수행 결과를 판별을 합니다.<br/>
그리고 테스트 결과는 Test클래스로 개발자에게 테스트 방법 및 클래스의 History를 공유 가능합니다.<br/>
<br/>
<img src="https://user-images.githubusercontent.com/46990595/168981840-c91f411e-a3de-4362-b3ae-fb081dfc9b7f.png" >

JUnit5 = JUnit Platform + JUnit Jupiter + JUnit Vintage<br/><br/>
JUnit Platform<br/> 
테스트를 발견하고 테스트 계획을 생성하는 TestEngine 인터페이스를 가지고 있습니다.<br/>
Platform은 TestEngine을 통해서 테스트를 발견하고 실행하고 결과를 보고합니다.<br/><br/> 
JUnit Juptier<br/>
TestEngine의 실제 구현체는 별도 모듈인데 그중 하나입니다.<br/>
JUnit5에 새롭게 추가된 Jupiter API를 사용하여 테스트 코드를 발견하고 실행합니다.<br/><br/> 
JUnit Vintage<br/>
기존에 JUnit4 버전으로 작성한 테스트 코드를 실행할때 vintage-engine 모듈을 사용합니다.<br/><br/> 

### 목차
1) [기본 애노테이션 : @Test, @BeforeAll, @AfterAll, @BeforeEach, @AfterEach <br/>@Disabled, @DisplayName, @DisplayNameGeneration](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/junit5/JunitDefaultAnnotations.java)
<br/><br/>
2) [Assertion 클래스의 주요 메소드 : assertEqulas(), assertNotNull()<br/> assertTrue(), assertAll(), assertThrows(), assertTimeout()](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/junit5/JunitAssertions.java)
<br/><br/>
3) [조건에 따라 실행하는 방법 : assumeTrue(), assumingThat(), @EnabledOnOs<br/> @EnabledOnJre, @EnabledIfEnvironmentVariable, @EnabledIfSystemProperty, @EnabledIf](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/junit5/JunitIf.java)
<br/><br/>
4) [태그와 필터링 : @Tag, CustomTag](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/junit5/JunitTag.java)
<br/><br/>
5) [테스트 반복하기 : @RepeatedTest, @ParameterizedTest](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/junit5/JunitRepeat.java)
<br/><br/>
6) [테스트 인스턴스 : @TestInstance](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/junit5/JunitTestInstance.java)
<br/><br/>
7) [테스트 순서 : @TestMethodOrder](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/junit5/JunitScenario.java)
<br/><br/>
8) [JUnit5 디폴트설정 : junit-platform.properties](https://github.com/road-jin/java-application-test/blob/main/src/test/resources/junit-platform.properties)
<br/><br/>
9) [JUnit5 확장모델 : BeforeTestExecutionCallback, AfterTestExecutionCallback](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/junit5/FindSlowTestExtension.java)
<br/>[JUnit5 확장모델 : @ExtendWith, @RegisterExtension](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/junit5/JunitExtend.java)
<br/><br/>
- - -
## 2. Mockito
Mockito이란?<br/>
Mock 객체를 쉽게 만들고 관리하고 검증할 수 있는 방법을 제공합니다.<br/>
테스트를 작성하는 자바 개발자 50%+ 사용하는 Mock 프레임워크<br/>
<br/>
Mock이란?<br/>
진짜 객체와 비슷하게 동작하지만 프로그래머가 직접 그 객체의 행동을 관리하는 객체입니다.<br/>
<br/>
다음 세 가지만 알면 Mock을 활용한 테스트를 쉽게 작성할 수 있습니다.
- Mock을 만드는 방법
- Mock이 어떻게 동작해야 하는지 관리하는 방법
- Mock의 행동을 검증하는 방법
<br/>

[Mockito 레퍼런스](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

### 목차
1) [Mock 객체 만들기 : @Mock, Mock()](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/mockito/MockMake.java)
<br/><br/>
2) [Mock Stubbing : when(), thenReturn(), thenTrow(), doThrow()](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/mockito/MockStubbing.java)
<br/><br/>
3) [Mock 객체 확인하기 : Mockito.verify(), inOrder.verify(), Mockito.verifyNoMoreInteractions()](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/mockito/MockVerify.java)
<br/><br/>
4) [Mock BDD : BDDMockito.given(), BDDMockito.then()](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/mockito/MockBDD.java)
<br/><br/>

## 3. 도커와 테스트
Testcontainers이란?<br/>
테스트에서 도커 컨테이너를 실행할 수 있는 라이브러리입니다.<br/>
실제 환경에 가까운 테스트를 만들 수 있으며,<br/>
테스트 실행시 DB를 설정하거나 별도의 프로그램 또는 스크립트를 실행할 필요 없습니다.<br/>
단점으로는 도커 컨터이너를 띄워서 테스트 하기 때문에 테스트가 느려집니다.<br/>
[testcontainers 레퍼런스](https://www.testcontainers.org/)
<br/>

### 목차
1) [Testcontainers : @Testcontainers, @Container 등](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/testcontainers/Testcontainers.java)
<br/><br/>
2) [Testcontainers docker-compose : DockerComposeContainer](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/testcontainers/DockerCompose.java)
## 4. 성능 테스트
JMeter란?<br/>
성능 측정 및 부하 (load) 테스트 기능을 제공하는 오픈 소스 자바 애플리케이션입니다.<br/>
[공식 홈페이지](https://jmeter.apache.org)
다양한 형태의 애플리케이션 테스트 지원합니다.
- 웹 
- HTTP, HTTPS
- SOAP / REST 웹 서비스
- FTP
- 데이터베이스 (JDBC 사용)
- Mail (SMTP, POP3, IMAP)
- ...

CLI 지원
- CI 또는 CD툴과 연동할 때 편리함.
- UI 사용하는 것보다 메모리등 시스템 리소스를 적게사용.

주요 개념
- Thread Group: 한 쓰레드 당 유저 한명
- Sampler: 어떤 유저가 해야 하는 액션
- Listener: 응답을 받았을 할 일 (리포팅, 검증, 그래프 그리기 등)
- Configuration: Sampler 또는 Listener가 사용할 설정 값 (쿠키, JDBC 커넥션 등)
- Assertion: 응답이 성공적인지 확인하는 방법 (응답 코드, 본문 내용 등)

<br/><br/>
JMeter 설치 및 실행
[JMeter 다운로드 주소](https://jmeter.apache.org/download_jmeter.cgi)
Binaries에 .zip 파일 다운받고서 압축파일을 풀면 되겠습니다.
터미널에서 압축파일 푼 폴더/bin 폴더로 이동후 ./jmeter 명령어 실행합니다.
<br/><br/>
Thread Group 만들기<br/>
- Name: 테스트 이름
- Comments: 테스트 설명
- Action to be taken after a Sampler error :
<br/>Sampler가 에러가 날 경우 조치 액션(계속, 다음 Thread loop 시작, Thread 중단, 테스트 중지, 즉시 테스트 중지)
- Number of Threads: 쓰레드 개수, 동시에 접속하는 User 수
- Ramp-up period: 쓰레드 개수를 만드는데 소요할 시간
- Loop Count: infinite 체크 하면 위에서 정한 쓰레드 개수로 계속 요청 보내기. 
<br/>값을 입력하면 해당 쓰레드 개수 X 루프 개수 만큼 요청 보냄.

Sampler 만들기
- 여러 종류의 샘플러가 있지만 그 중에 우리가 사용할 샘플러는 HTTP Request 샘플러.
- HTTP Sampler
- 요청을보낼호스트,포트,URI,요청본문등을설정
- 여러 샘플러를 순차적으로 등록하는 것도 가능하다.

Listener 만들기
- View Results Tree
- View Resulrts in Table
- Summary Report
- Aggregate Report
- Response Time Graph
- Graph Results
- ...

Assertion 만들기
- 응답 코드확인
- 응답 본문확인

CLI 사용하기 
- jmeter -n -t 설정파일 
<br/> -n : ui를 쓰지 않겠다.
<br/> -t : 설정 파일
<br/> ex) ~/Downloads/apache-jmeter-5.4.3/bin/jmeter -n -t ./study\ Perf\ Test.jmx
<br/><br/>
## 5. 운영 이슈 테스트
ChaosMonkey?<br/>
프로덕션 환경, 특히 분산 시스템 환경에서 불확실성을 파악하고 해결 방안을 모색하는데 사용하는 툴<br/>
운영 환경 불확실성의 예
- 네트워크 지연
- 서버장애
- 디스크 오작동
- 메모리누수
- ...

[카오스 멍키 스프링 부트](https://codecentric.github.io/chaos-monkey-spring-boot/)
- 스프링 부트 애플리케이션에 카오스 멍키를 손쉽게 적용해 볼 수 있는 툴
- 즉, 스프링 부트 애플리케이션을 망가트릴 수 있는 툴

### 카오스 멍키 스프링 부트 주요 개념
공격 대상 (Watcher)
- @RestController 
- @Controller
- @Service
- @Repository
- @Component

공격 유형 (Assaults)
- 응답 지연 (Latency Assault)
- 예외 발생 (Exception Assault)
- 애플리케이션 종료 (AppKiller Assault)
- 메모리 누수 (Memory Assault)


### 카오스 멍키 시작해보기
터미널에서 httpie 이용하여 카오스 멍키 활용하기
- 터미널에서 brew install httpie 
- httpie는 터미널에서 http 요청을 보내는 명령어를 지원해줍니다.
<br/> ex) http get localhost:8080/study/3
<br/> http post localhost:8080/study limitCount=20 name="스프링"

카오스 멍키 활성화
- http post localhost:8080/actuator/chaosmonkey/enable 

카오스 멍키 활성화 확인
- http localhost:8080/actuator/chaosmonkey/status 

카오스 멍키 와처 확인
- http localhost:8080/actuator/chaosmonkey/watchers

카오스 멍키 지연공격설정
- http POST localhost:8080/actuator/chaosmonkey/assaults level=3 latencyRangeStart=2000 
<br/> latencyRangeEnd=5000 latencyActive=true
<br/> lebel=3 : 3번 요청중 1번쯤은 공격해라 
<br/> latencyRangeStart=2000 : 2초에서 
<br/> latencyRangeEnd=5000 : 5초 내외로 지연시켜라
<br/> latencyActive=true : 레이턴시공격을 활성화 시켜라
- 특정만 메소드에만 적용하고 싶을경우
<br/>[참고](https://codecentric.github.io/chaos-monkey-spring-boot/latest/#_customize_watcher)

카오스 멍키 에러공격설정
- http POST localhost:8080/actuator/chaosmonkey/assaults level=3 latencyActive=false exceptionsActive=true 
<br/> exception.type=java.lang.RuntimeException
<br/> exceptionsActive=true : 에러공격을 활성화 시켜라
<br/> exception.type=java.lang.RuntimeException : RuntimeException 에러가 발생하게 하라. 

application.properties에서 적용해보기
- chaos.monkey.enabled=true : 카오스몽키 활성화
- chaos.monkey.assaults.level=3 : 3번 요청중 1번쯤은 공격해라
- chaos.monkey.assaults.latency-range-start=2000 : 2초에서
- chaos.monkey.assaults.latency-range-end=5000 : 5초 내외로 지연시켜라
- chaos.monkey.assaults.latency-active=true : 레이턴시공격을 활성화 시켜라
- chaos.monkey.assaults.watchedCustomServices=com.example.chaos.monkey.chaosdemo.controller.HelloController.sayHello,
<br/> com.example.chaos.monkey.chaosdemo.service.HelloService : 특정메소드에만 적용

카오스 멍키 공격 확인
- http GET localhost:8080/actuator/chaosmonkey/assaults



## 6. 아키텍쳐 테스트
ArchUnit?<br/>
애플리케이션의 아키텍처를 테스트 할 수 있는 오픈 소스 라이브러리로,<br/> 
패키지, 클래스, 레이어, 슬라이스 간의 의존성을 확인할 수 있는 기능을 제공한다.<br/>
[공식](https://www.archunit.org/)

아키텍처 테스트 유즈 케이스
- A라는패키지가B(또는C,D)패키지에서만사용되고있는지확인가능.
- *Serivce라는 이름의 클래스들이 *Controller 또는 *Service라는 이름의 클래스에서만 참조하고 있는지 확인.
- *Service라는 이름의 클래스들이 ..service.. 라는 패키지에 들어있는지 확인.
- A라는 애노테이션을 선언한 메소드만 특정 패키지 또는 특정 애노테이션을 가진 클래스를 호출하고 있는지 확인.
- 특정한 스타일의 아키텍처를 따르고 있는지 확인.


### 목차
1) [ArchUnit 테스트](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/archtests/ArchTests.java)


