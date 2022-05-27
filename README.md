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
1) [기본 애노테이션 : @Test, @BeforeAll, @AfterAll, @BeforeEach, @AfterEach <br/>@Disabled, @DisplayName, @DisplayNameGeneration](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/JunitDefaultAnnotations.java)
<br/><br/>
2) [Assertion 클래스의 주요 메소드 : assertEqulas(), assertNotNull()<br/> assertTrue(), assertAll(), assertThrows(), assertTimeout()](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/JunitAssertions.java)
<br/><br/>
3) [조건에 따라 실행하는 방법 : assumeTrue(), assumingThat(), @EnabledOnOs<br/> @EnabledOnJre, @EnabledIfEnvironmentVariable, @EnabledIfSystemProperty, @EnabledIf](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/JunitIf.java)
<br/><br/>
4) [태그와 필터링 : @Tag, CustomTag](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/JunitTag.java)
<br/><br/>
5) [테스트 반복하기 : @RepeatedTest, @ParameterizedTest](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/JunitRepeat.java)
<br/><br/>
6) [테스트 인스턴스 : @TestInstance](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/JunitTestInstance.java)
<br/><br/>
7) [테스트 순서 : @TestMethodOrder](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/JunitScenario.java)
<br/><br/>
8) [JUnit5 디폴트설정 : junit-platform.properties](https://github.com/road-jin/java-application-test/blob/main/src/test/resources/junit-platform.properties)
<br/><br/>
9) [JUnit5 확장모델 : BeforeTestExecutionCallback, AfterTestExecutionCallback](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/FindSlowTestExtension.java)
<br/>[JUnit5 확장모델 : @ExtendWith, @RegisterExtension](https://github.com/road-jin/java-application-test/blob/main/src/test/java/com/example/javaapplicationtest/JunitExtend.java)
<br/><br/>
- - -
## 2. Mockito

## 3. 도커와 테스트

## 4. 성능 테스트

## 5. 운영 이슈 테스트

## 6. 아키텍쳐 테스트
