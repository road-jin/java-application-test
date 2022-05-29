package com.example.javaapplicationtest.junit5;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/*
    @DisplayNameGeneration 이란?
    @DisplayNameGeneration 붙은 클래스 및 메소드 이름을 변형하여 Test Results 에 나오는 Test 이름을 표시합니다.
    value 로 DisplayNameGenerator 인터페이스를 상속받은 클래스여야 한다.

    DisplayNameGenerator.Standard.class : 기존 클래스, 메소드 명을 사용합니다. (기본값)
    DisplayNameGenerator.Simple.class : 괄호를 제외시킵니다.
    DisplayNameGenerator.ReplaceUnderscores.class : _(underscore) 를 공백으로 바꿉니다.
    DisplayNameGenerator.IndicativeSentences.class	: 클래스명 + 구분자(", ") + 메소드명으로 바꿉니다.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JunitDefaultAnnotations {

    /*
        JUnit5는 Java8 이상을 필요로 합니다.
        JUnit5부터 public(접근제어자)를 생략하여도 됩니다.
        Java Reflection API를 활용하여 어떤 접근제어자여도 접근이 가능하고 실행이 가능하기 때문입니다.
        JUnit 테스트할 메소드의 접근제어자가 private 일 경우는 실행되지 않습니다.

        @Test 이란?
        @Test 붙은 메소드는 테스트 대상으로 지정되어 테스트를 실행합니다.
     */
    @Test
    void create_new_string(){
        String string = new String();
        assertNotNull(string);
        System.out.println("create_new_string");
    }

    /*
        @Disabled 이란?
        @Disabled 붙은 메소드는 테스트 대상으로 지정되지 않게 합니다.
     */
    @Test
    @Disabled
    void create(){
        System.out.println("create");
    }

    /*
        @DisplayName 이란?
        @DisplayName 붙은 메소드는 어떤 테스트인지 테스트 이름을 보다 쉽게 표현할 수 있는 방법을 제공하는 애노테이션입니다.
        @DisplayNameGeneration 보다 우선 순위가 높습니다.
     */
    @Test
    @DisplayName("호호잇~ ╯°□°）╯ ☺️")
    void create2(){
        System.out.println("create2");
    }

    /*
        @BeforeAll 이란?
        @BeforeAll 붙은 메소드는 @Test 애노테이션이 붙은 메소드들을 실행하기전 딱 한번 실행됩니다.
        또한 static 항시 붙여야 하고, 리턴타입이 있으면 안됩니다.
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("before ALl");
    }

    /*
        @AfterAll 이란?
        @AfterAll 붙은 메소드는 @Test 애노테이션이 붙은 메소드들을 다 실행하고나서 딱 한번 실행됩니다.
        또한 static 항시 붙여야 하고, 리턴타입이 있으면 안됩니다.
     */
    @AfterAll
    static void afterAll(){
        System.out.println("after ALl");
    }

    /*
        @BeforeEach 이란?
        @BeforeEach 붙은 메소드는 @Test 애노테이션이 붙은 메소드가 실행하기 전에 실행됩니다.
        리턴타입이 있으면 안됩니다.
     */
    @BeforeEach
    void beforeEach(){
        System.out.println("before Each");
    }

    /*
        @BeforeEach 이란?
        @BeforeEach 붙은 메소드는 @Test 애노테이션이 붙은 메소드가 실행된 후에 실행됩니다.
        리턴타입이 있으면 안됩니다.
     */
    @AfterEach
    void afterEach(){
        System.out.println("after Each");
    }
}