package com.example.javaapplicationtest.junit5;

import com.example.javaapplicationtest.domain.Study;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

public class JunitIf {

    /*
       패키지명 : org.junit.jupiter.api
       Assumptions 클래스의 자세한 내용은 참조
       https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assumptions.html


       public static void assumeTrue(boolean assumption) throws TestAbortedException
       특정한 조건일 경우에만 테스트가 진행되며,
       조건을 미달성시에는 @Disabled 처럼 됩니다.
     */
    @Test
    @DisplayName("assumeTrue 테스트")
    void assumeTrue_test(){
        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);
        Assumptions.assumeTrue("LOCAL".equalsIgnoreCase(test_env));

        Study study = new Study(10);
        Assertions.assertEquals(10, study.getLimitCount());
    }

    /*
        public static void assumingThat(boolean assumption, Executable executable)
        특정한 조건일 경우에만 Executable 실행됩니다.
        특정한 조건이 만족이 안되어도 테스트케이스는 진행됩니다.
     */
    @Test
    @DisplayName("assumingThat 테스트")
    void assumingThat_test(){
        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);

        Assumptions.assumingThat("LOCAL".equalsIgnoreCase(test_env), () ->{
            Study study = new Study(10);
            Assertions.assertEquals(10, study.getLimitCount());
        });
    }

    /*
        @EnabledOnOs 이란?
        해당 OS enum 값일 경우만 테스트가 동작하게 되며,
        아닐경우는 @Disabled 같은 경우로 됩니다.
        value 여러개 일경우는 OR 연산입니다.

        @DisabledOnOs 위와 반대 개념
     */
    @Test
    @DisplayName("EnabledOnOs 테스트")
    @EnabledOnOs({OS.MAC, OS.LINUX})
    //@EnabledOnOs(OS.MAC)
    void enabledOnOs_test(){
        OS mac = OS.MAC;
        Assertions.assertTrue(mac.isCurrentOs());
    }

    /*
        @EnabledOnJre 이란?
        해당 JRE enum 값일 경우만 테스트가 동작하게 되며,
        아닐경우는 @Disabled 같은 경우로 됩니다.
        value 여러개 일경우는 OR 연산입니다.

        @DisabledOnJre 위와 반대 개념
     */
    @Test
    @DisplayName("EnabledOnJre 테스트")
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_11})
    //@EnabledOnJre(JRE.JAVA_8)
    void enabledOnJre_test(){
        JRE jre = JRE.JAVA_11;
        Assertions.assertTrue(jre.isCurrentVersion());
    }

    /*
       @EnabledIfEnvironmentVariable
       named에 환경변수명을 적고 matches에 기대하는 값을 입력합니다.
       해당 환경변수 값과 matches에 기입한 값이 맞으면 테스트가 진행되고
       조건을 미달성시에는 @Disabled 처럼 됩니다.

       @DisabledIfEnvironmentVariable 위와 반대 개념
     */
    @Test
    @DisplayName("EnabledIfEnvironmentVariable 테스트")
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void enabledIfEnvironmentVariable_test(){
        Study study = new Study(10);
        Assertions.assertEquals(10, study.getLimitCount());
    }

    /*
        @EnabledIfSystemProperty
        named에 시스템프로퍼티명을 적고 matches에 기대하는 값을 입력합니다.
        해당 시스템프로퍼티 값과 matches에 기입한 값이 맞으면 테스트가 진행되고
        조건을 미달성시에는 @Disabled 처럼 됩니다.
        JVM의 System Property 별 테스트를 할 수 있게 해줍니다.
        시스템 프로퍼티 참고 : https://coding-factory.tistory.com/527

        @DisabledIfSystemProperty 위와 반대 개념
     */
    @Test
    @DisplayName("EnabledIfSystemProperties 테스트")
    @EnabledIfSystemProperty(named = "java.version", matches = "11.0.13")
    void enabledIfSystemProperties_test(){
        Study study = new Study(10);
        Assertions.assertEquals(10, study.getLimitCount());
    }

    /*
        @EnabledIf
        value 값에 boolean 리턴타입이 있는 함수명을 적으면
        해당 함수가 실행되어 조건이 맞을 경우 테스트가 진행됩니다.
        조건을 미달성시에는 @Disabled 처럼 됩니다.

        @DisabledIf 위와 반대 개념
     */
    @Test
    @DisplayName("EnabledIf 테스트")
    @EnabledIf("customCondition")
    void enabledIf_test(){
        Study study = new Study(10);
        Assertions.assertEquals(10, study.getLimitCount());
    }

    boolean customCondition() {
        return true;
    }
}
