package com.example.javaapplicationtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;


public class JunitRepeat {

    /*
        @RepeatedTest
        value 값에 Int 타입의 정수형 숫자를 받아
        해당 값만큼 반복하는 테스트입니다.

        name 값에 {displayName} , {currentRepetition} , {totalRepetitions} 추가하여
        TestResults 화면에 어떻게 나올지 정의 할수도 있습니다.

        해당 테스트 메소드 파라미터에 RepetitionInfo 객체를 받을수도 있습니다.
        RepetitionInfo 객체는 getCurrentRepetition()로 현재 반복횟수와
        getTotalRepetitions()로 총 반복횟수를 알아낼 수 있습니다.
     */
    @DisplayName("RepeatedTest 반복테스트")
    @RepeatedTest(2)
    void repeat_test(){
        Study study = new Study(10);
        Assertions.assertEquals(10, study.getLimit());
    }

    @DisplayName("RepeatedTest 반복테스트 파라미터 추가")
    @RepeatedTest(2)
    void repeat_test2(RepetitionInfo repetitionInfo){
        Study study = new Study(10);
        Assertions.assertEquals(10, study.getLimit());
        System.out.println(repetitionInfo);
    }

    @DisplayName("RepeatedTest 반복테스트 애노테이션 name 추가")
    @RepeatedTest(value = 3, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeat_test3(RepetitionInfo repetitionInfo){
        Study study = new Study(10);
        Assertions.assertEquals(10, study.getLimit());
        System.out.println(repetitionInfo);
    }

    /*
       @ParameterizedTest
       테스트에 여러 다른 매개변수를 대입해가며 반복 실행합니다.

       @ValueSource
       ParameterizedTest에 다양한 매개변수를 넣어줍니다.
       short[], byte[], int[], long[], float[], double[], char[], boolean[], String[], Class<?>[]
       타입의 매개변수를 넣어줍니다.

       @NullSource
       ParameterizedTest에 Null 인자를 받습니다.

       @EmptySource
       ParameterizedTest에 비어있는 인자를 받습니다.

       @NullAndEmptySource
       @NullSource @EmptySource 합친 애노테이션입니다.

     */
    @DisplayName("ParameterizedTest ValueSource 테스트")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요"})
    @NullAndEmptySource
    void parameterized_test(String message){
        System.out.println(message);
    }

    /*
        @ValueSource 값을 이용하여, 다른 객체로 파라미터를 받고 싶을경우
        SimpleArgumentConverter를 상속받은 클래스를 생성하여
        convert 메소드를 재정의합니다.
        @ConvertWith 파라미터로 SimpleArgumentConverter 상속받은 클래스의 class 타입을 기입하면 됩니다.
     */
    @DisplayName("ParameterizedTest ValueSource SimpleArgumentConverter 테스트")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(ints = {10, 20, 30})
    void parameterized_test2(@ConvertWith(StudyConverter.class) Study study){
        System.out.println(study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            Assertions.assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    /*
        @CsvSource
        여러타입의 인자들을 넘겨줍니다.
        여러타입의 인자를 넘겨줄때 무조건 String[] 형식으로 작성합니다.
        String 형식 여러타입의 인자를 넣을때, 암묵적인 타입변환으로 넣기 때문에
        아래 링크를 참조하여 작성하도록 합니다.

        암묵적인 타입 변환
        https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-argument-conversion-implicit
     */
    @DisplayName("ParameterizedTest CsvSource 테스트")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterized_test3(Integer limit, String name){
        Study study = new Study(limit, name);
        System.out.println(study);
    }

    @DisplayName("ParameterizedTest CsvSource argumentsAccessor 테스트")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterized_test4(ArgumentsAccessor argumentsAccessor){
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println(study);
    }

    /*
        @CsvSource 값을 이용하여, 다른 객체로 파라미터를 받고 싶을경우
        ArgumentsAggregator Interface 상속받은 클래스를 생성하여
        aggregateArguments 메소드를 재정의합니다.
        @AggregateWith 파라미터로 ArgumentsAggregator 상속받은 클래스의 class 타입을 기입하면 됩니다.
     */
    @DisplayName("ParameterizedTest CsvSource ArgumentsAggregator 테스트")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterized_test5(@AggregateWith(StudyAggregator.class) Study study){
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }


}
