package com.example.javaapplicationtest.junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

/*
    확장팩 등록 방법

    선언적인 등록
    @ExtendWith value 값에 확장팩 클래스의 class 타입으로 기입하여
    테스트 클래스나, 테스트 메소드 등에 애노테이션을 붙여 등록합니다.

    프로그래밍 등록
    @RegisterExtension 필드에 애노테이션을 붙여 등록합니다.
 */
//@ExtendWith(FindSlowTestExtension.class)
public class JunitExtend {

    
    @RegisterExtension
    FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);


    @DisplayName("느린 테스트")
    //@CustomTag
    @Test
    void slow_test() throws InterruptedException {
        Thread.sleep(1001);
        Assertions.assertTrue(true);
    }
}
