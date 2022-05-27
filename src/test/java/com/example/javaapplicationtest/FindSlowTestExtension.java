package com.example.javaapplicationtest;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/*
    JUnit 4의 확장 모델은 @RunWith(Runner), TestRule, MethodRule.
    JUnit 5의 확장 모델은 단 하나, Extension.
    자동 등록 자바 ServiceLoader 이용 (https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/ServiceLoader.html)
    확장팩 만드는 방법
        테스트 실행 조건
        테스트 인스턴스 팩토리
        테스트 인스턴스 후-처리기
        테스트 매개변수 리졸버
        테스트 라이프사이클 콜백
        예외처리
        ...

    참고
    https://junit.org/junit5/docs/current/user-guide/#extensions


    FindSlowTestExtension Class는 테스트 라이프사이클 콜백을 사용하여 확장팩을 만듭니다.
    BeforeTestExecutionCallback 인터페이스의 beforeTestExecution 메소드를 구현하여
    테스트메소드가 실행하기전에 동작될 작업들을 정의 합니다.
    AfterTestExecutionCallback 인터페이스의 afterTestExecution 메소드를 구현하여
    테스트메소드가 실행 후에 동작될 작업들을 정의 합니다.


    동작 설명 :
    테스트 실행 시간이 1초가 초과할 경우 CustomTag 애노테이션을 붙이라고 메세지를 출력합니다.
    이미 CustomTag 애노테이션을 붙인 경우는 메세지 출력이 되지 않습니다.
 */
public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    /*
        @ExtendWith 애노테이션을 써서 등록할 경우


    private final long THRESHOLD = 1000L;
    */


    // @RegisterExtension 애노테이션을 써서 등록할 경우
    private long THRESHOLD;

    public FindSlowTestExtension(long THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        store.put("START_TIME", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        String testMethodName = context.getRequiredTestMethod().getName();
        CustomTag annotation = context.getRequiredTestMethod().getAnnotation(CustomTag.class);
        ExtensionContext.Store store = getStore(context);
        Long start_time = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - start_time;

        if (duration > THRESHOLD && annotation == null){
            System.out.printf("Please consider mark method [%s] with @SlowTest\n", testMethodName);
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getRequiredTestMethod().getName();
        ExtensionContext.Store store = context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
        return store;
    }
}
