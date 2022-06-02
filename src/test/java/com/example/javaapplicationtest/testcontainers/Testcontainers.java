package com.example.javaapplicationtest.testcontainers;

import com.example.javaapplicationtest.domain.Member;
import com.example.javaapplicationtest.domain.Study;
import com.example.javaapplicationtest.member.MemberService;
import com.example.javaapplicationtest.study.StudyRepository;
import com.example.javaapplicationtest.study.StudyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Optional;

/*
    @ActiveProfiles
    value 값에 있는 문자로 properties 파일을 바꿉니다.
    application-{value}.properties

    @Testcontainers
    JUnit 5 확장팩으로 테스트 클래스에 @Container를 사용한 필드를 찾아서 컨테이너 라이프사이클 관련 메소드를 실행해줍니다.

    @Container
    인스턴스 필드에 사용하면 모든 테스트 마다 컨테이너를 재시작 하고,
    스태틱 필드에 사용하면 클래스 내부 모든 테스트에서 동일한 컨테이너를 재사용합니다.

    @Slf4j
    log 변수에 slf4j.Looger 클래스를 주입시켜 줍니다.

    @ContextConfiguration
    initializers 값에 ApplicationContextInitializer 상속받은 클래스를 입력하여
    스프링 테스트 컨텍스트가 사용할 설정 파일을 지정 할 수 있습니다.
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@org.testcontainers.junit.jupiter.Testcontainers
@Slf4j
@ContextConfiguration(initializers = Testcontainers.ContainerPropertyInitializer.class)
public class Testcontainers {

    @Mock
    MemberService memberService;

    @Autowired
    StudyRepository studyRepository;

    /*
        ApplicationContextInitializer에서 정의한 property 사용방법
        @Autowired
        Environment environment
        environment.getProperty(String key)

        @Value("${변수}) dataType 변수명
     */
    @Autowired
    Environment environment;

    @Value("${container.port}") int port;

    /*
        MySQLContainer
        mysql database의 최적화된 container 클래스를 만듭니다.
        생성자로 도커이미지나 도커이미지 이름을 적어 주시면 됩니다.

        withDatabaseName(final String databaseName)
        database(schema) 이름을 명시하여 생성합니다.

        withExposedPorts(Integer... ports)
         도커 컨테이너에서 매핑할 포트를 명시해줍니다.
         host port는 의도적으로 현재 쓸수 있는 포트에서 랜덤하게 설정됩니다.
     */
    @Container
    private static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:5.7.34")
            .withDatabaseName("studytest")
            .withExposedPorts(3306);

    /*
        GenericContainer
        만일 내가 사용하는 database의 클래스가 없는 경우
        public docker 이미지만 있다면 GenericContainer 써서 testcontainers를 쓸 수 있습니다.
        하지만, .withDatabaseName() 등 database에 최적화된 함수를 쓰지 못하여
        withEnv(), withCommand()로 대체해야 합니다.

        withEnv(String key, String value)
        도커 환경변수 명령어를 쓸수 있습니다.
        ex) -e MYSQL_DATABASE=studytest
     */
    /*@Container
    private static GenericContainer<?> mySQLContainer = new GenericContainer<>("mysql:5.7.34")
            .withEnv("MYSQL_DATABASE", "studytest")
            .withExposedPorts(3306);*/

    /*
        followOutput(Consumer<OutputFrame> consumer)
        실시간으로 도커 컨테이너로그를 출력해줍니다.
     */
    @BeforeAll
    static void beforeAll() {
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        mySQLContainer.followOutput(logConsumer);
    }

    /*
        getMappedPort(int originalPort)
        도커 컨테이너에서 사용자가 입력한 포트와 매핑된 host port를 알아 낼 수 있습니다.

        getLogs()
        도커 컨테이너 log를 출력합니다.
     */
    @BeforeEach
    void beforeEach() {
        System.out.println("mySQLContainer.getMappedPort() : " + mySQLContainer.getMappedPort(3306));
        System.out.println("environment.getProperty() : " + environment.getProperty("container.port"));
        System.out.println("@value : " + port);
        System.out.println(mySQLContainer.getLogs());
        studyRepository.deleteAll();
    }

    @Test
    @DisplayName("testcontainers 테스트")
    void testcontainers_test(){
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);

        Study study = new Study(10, "자바");

        Member member = new Member();
        member.setId(1L);
        member.setEmail("jin@email.com");

        BDDMockito.given(memberService.findById(1L)).willReturn(Optional.of(member));

        // WHen
        Study newStudy = studyService.createNewStudy(1L, study);

        // Then
        Assertions.assertEquals(member.getId(), study.getOwnerId());
        BDDMockito.then(memberService).should(Mockito.times(1)).notify(newStudy);
        BDDMockito.then(memberService).should(Mockito.times(1)).notify(member);
        BDDMockito.then(memberService).shouldHaveNoMoreInteractions();
    }


    /*
       ApplicationContextInitializer
       스프링 ApplicationContext를 프로그래밍으로 초기화 할 때 사용할 수 있는 콜백입니다.
       인터페이스로, 특정 프로파일을 활성화 하거나, 프로퍼티 소스를 추가하는 등의 작업을 할 수 있숩니다.
     */
    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of("container.port=" + mySQLContainer.getMappedPort(3306))
                    .applyTo(applicationContext.getEnvironment());
        }
    }
}
