package com.example.javaapplicationtest.testcontainers;

import com.example.javaapplicationtest.domain.Member;
import com.example.javaapplicationtest.domain.Study;
import com.example.javaapplicationtest.member.MemberService;
import com.example.javaapplicationtest.study.StudyRepository;
import com.example.javaapplicationtest.study.StudyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
@ContextConfiguration(initializers = DockerCompose.ContainerPropertyInitializer.class)
public class DockerCompose {
    @Mock
    MemberService memberService;

    @Autowired
    StudyRepository studyRepository;

    @Value("${container.port}") int port;

    /*
        DockerComposeContainer
        docker-compose를 사용하여 할 경우 씁니다.
        생성자에 docker-compose로 작성된 파일 경로를 적어 줍니다.


        withExposedService(String serviceName, int servicePort)
        ApplicationContextInitializer 써는 경우는 필수적으로
        withExposedService() 써서 컨테이너들을 매핑해줘야 합니다.

        withExposedService(String serviceName, int servicePort, @NonNull WaitStrategy waitStrategy)
        Wait 인자를 추가하여 컨테이너가 뜨지도 않았는데, 테스트를 싱행하는 것을 방지하기 위해 씁니다.
        Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(Duration))
     */
    @Container
    private static DockerComposeContainer composeContainer =
            new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
                    .withExposedService("study-testdb", 3306,
                            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
                    .withExposedService("study-test2db", 3306,
                            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));

    @Test
    @DisplayName("testcontainers 테스트")
    void testcontainers_test(){
        System.out.println("port : " + port);


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

    @Test
    @DisplayName("testcontainers 테스트2")
    void testcontainers_test2(){
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

    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of("container.port=" + composeContainer.getServicePort("study-testdb",3306))
                    .applyTo(applicationContext.getEnvironment());
        }
    }
}
