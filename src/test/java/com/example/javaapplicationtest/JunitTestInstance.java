package com.example.javaapplicationtest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/*
    JUnit 기본 전략은 테스트 메소드 마다 테스트 클래스의 인스턴스를 새로 만듭니다.
    테스트 메소드를 독립적으로 실행하여 예상치 못한 부작용을 방지하기 위합입니다.
    이 전략을 JUnit 5에서 변경할 수 있습니다.

    @TestInstance(Lifecycle.PER_CLASS)
    테스트 클래스당 인스턴스를 하나만 만들어 사용합니다.
    경우에 따라, 테스트 간에 공유하는 모든 상태를 @BeforeEach 또는 @AfterEach에서
    초기화 할 필요가 있습니다.
    @BeforeAll과 @AfterAll을 static 메소드를 안쓰고 정의 할 수도 있습니다.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JunitTestInstance {

    /*
        @TestInstance(TestInstance.Lifecycle.PER_CLASS) 애노테이션을 주석하고
        테스트 실행시 study 변수의 해시코드와 JunitTestInstance 인스턴스가 테스트 메소드마다 서로 다른걸 확인 할수 있습니다.
     */
    Study study = new Study(10);

    @BeforeAll
    static void beforeAll(){
        System.out.println("before All");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("after All");
    }

    @Test
    void studyLimit_test(){
        System.out.println(this);
        System.out.println(study.hashCode());
    }

    @Test
    void studyLimit_test2(){
        System.out.println(this);
        System.out.println(study.hashCode());
    }
}
