package com.example.javaapplicationtest;

import org.junit.jupiter.api.*;

import java.time.Duration;

public class StudyTest2 {

    private Study study;

    @BeforeEach
    void before_each(){
        study = new Study();
    }

    /*
        패키지명 : org.junit.jupiter.api
        Assertions 클래스의 자세한 내용은 참조
        https://junit.org/junit5/docs/5.8.2/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html

        마지막 매개변수로 Supplier<String> 타입의 인스턴스를 람다 형태로 제공하여,
        복잡한 메시지 생성해야 하는 경우 사용하면 실패한 경우에만 해당 메시지를 만들게 할수 있습니다.

        public static void assertEquals(Object expected, Object actual)
        실제 값이 기대한 값과 같은지 확인합니다.
     */
    @Test
    @DisplayName("Study StudyStatus 기본값 테스트")
    void study_status_defaultValue_test() {
        Assertions.assertEquals(StudyStatus.DRAFT, study.getStatus());
        Assertions.assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT 여야 합니다.");
        Assertions.assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 DRAFT 여야 합니다.");
    }

    /*
        public static void assertNotNull(Object actual)
        값이 null이 아닌지 확인합니다.
     */
    @Test
    @DisplayName("Study Not null 테스트")
    void study_not_null_test() {
        Assertions.assertNotNull(study);
        Assertions.assertNotNull(study, "Study 객체는 Null이 될수 없습니다.");
        Assertions.assertNotNull(study, () -> "Study 객체는 Null이 될수 없습니다.");
    }

    /*
        public static void assertTrue(boolean condition)
        다음 조건이 참(true)인지 확인합니다.
     */
    @Test
    @DisplayName("Study limit True 테스트")
    void study_isLimit_test(){
        Assertions.assertTrue(study.getLimit() > 0);
        Assertions.assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야합니다.");
        Assertions.assertTrue(study.getLimit() > 0, () -> "스터디 최대 참석 가능 인원은 0보다 커야합니다.");
    }

    /*
        public static void assertAll(Executable... executables) throws MultipleFailuresError
        각각의 Executable 실패하거나 성공하여도 전부 실행합니다.
     */
    @Test
    @DisplayName("Study All 테스트")
    void study_all_test(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(StudyStatus.DRAFT, study.getStatus()),
                () -> Assertions.assertNotNull(study),
                () -> Assertions.assertTrue(study.getLimit() > 0)
        );
    }

    /*
        public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable)
        해당 예외가 발생하는지 확인합니다.
     */
    @Test
    @DisplayName("Study limit 오류 테스트")
    void study_limit_Exception(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Study(-10)
                , "IllegalArgumentException 예외가 발생하여야 합니다.");
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Study(-10)
                , () -> "IllegalArgumentException 예외가 발생하여야 합니다.");
    }

    /*
        public static <T> T assertTimeout(Duration timeout, ThrowingSupplier<T> supplier)
        특정 시간 안에 실행이 완료되는지 확인합니다.
        특정 시간을 넘어가도 테스트가 완벽히 종료될때 까지 기다립니다.

        public static <T> T assertTimeoutPreemptively(Duration timeout, ThrowingSupplier<T> supplier)
        특정 시간 안에 실행이 완료되는지 확인합니다.
        특정 시간을 넘어가는순간 테스트 즉시 종료합니다.
     */
    @Test
    @DisplayName("Study 객체 생성시간 테스트")
    void study_time(){
        Assertions.assertTimeout(Duration.ofSeconds(1), () -> new Study(10));

        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> new Study(10));
    }
}