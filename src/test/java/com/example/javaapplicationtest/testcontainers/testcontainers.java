package com.example.javaapplicationtest.testcontainers;

import com.example.javaapplicationtest.domain.Member;
import com.example.javaapplicationtest.domain.Study;
import com.example.javaapplicationtest.member.MemberService;
import com.example.javaapplicationtest.mockito.StudyRepository;
import com.example.javaapplicationtest.mockito.StudyService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

/*
    @Testcontainers
    JUnit 5 확장팩으로 테스트 클래스에 @Container를 사용한 필드를 찾아서 컨테이너 라이프사이클 관련 메소드를 실행해줍니다.

    @Container
    인스턴스 필드에 사용하면 모든 테스트 마다 컨테이너를 재시작 하고,
    스태틱 필드에 사용하면 클래스 내부 모든 테스트에서 동일한 컨테이너를 재사용합니다.
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
public class testcontainers {

    @Mock
    MemberService memberService;

    @Autowired
    StudyRepository studyRepository;

    @Container
    private static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:5.7.34")
            .withDatabaseName("studytest")
            .withExposedPorts(33306,3306);

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
}
