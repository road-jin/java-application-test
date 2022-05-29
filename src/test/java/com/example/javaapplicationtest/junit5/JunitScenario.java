package com.example.javaapplicationtest.junit5;

import com.example.javaapplicationtest.domain.Study;
import org.junit.jupiter.api.*;

/*
    실행할 테스트 메소드 특정한 순서에 의해 실행되지만 어떻게 그 순서를 정하는지는 의도적으로 분명히 하지 않습니다.
    테스트 인스턴스를 테스트 마다 새로 만드는 것과 같은 이유입니다.
    경우에 따라, 특정 순서대로 테스트를 실행하고 싶을 때도 있다.
    예를 들어 시나리오 테스트 같은 경우가 있습니다.

    @TestMethodOrder(MethodOrderer.OrderAnnotation.class) 와
    @Order 애노테이션 조합으로 순서를 정할 수 있습니다.
    @Order() Value는 무조건 정수형이어야 합니다.

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)을 사용하여
    테스트 메소드간의 자원을 공유할 수가 있어 아래와 같은 동작도 할수 있습니다.
    자원을 공유할 이유가 없을경우는 굳이 안 쓰셔도 됩니다.

 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JunitScenario {

    private Study study;

    @Order(2)
    @DisplayName("기존 Study limit과 name에 JUnit5를 포함한 새로운 인스턴스 생성")
    @Test
    void studySetName_test(){
        study = new Study(study.getLimitCount(), "JUnit5");
        Assertions.assertEquals(7, study.getLimitCount());
        Assertions.assertEquals("JUnit5", study.getName());
    }

    @Order(1)
    @DisplayName("Study limit 사용하여 인스턴스 생성")
    @Test
    void studyNewInstance_test(){
        study = new Study(7);
        Assertions.assertNotNull(study);
    }

}
