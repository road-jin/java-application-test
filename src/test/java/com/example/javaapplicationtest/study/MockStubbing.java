package com.example.javaapplicationtest.study;

import com.example.javaapplicationtest.domain.Member;
import com.example.javaapplicationtest.domain.Study;
import com.example.javaapplicationtest.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MockStubbing {

    @Mock MemberService memberService;
    @Mock StudyRepository studyRepository;

    /*
        모든 Mock 객체의 메소드 리턴 값
        리턴 타입 종류
        1. Collection 타입 : 비어있는 Collection
        2. Primitive 타입 : 기본 Primitive 값
        3. void 타입 : 예외를 던지지 않고 아무런 일도 발생하지 않습니다.
        4. Optional 타입 : Optional.empty
        5. 이 외의 객체 타입 : Null
     */
    @Test
    @DisplayName("Mock 객체의 메소드 리턴 값")
    void mockMethodReturn(){
        Optional<Member> member = memberService.findById(1L);
        memberService.validate(1L);
        Assertions.assertEquals(Optional.empty(), member);
    }

    /*
        Mock 객체의 메소드를 실행 시 특정한 인자를 받은 경우
        사용자가 정의한 값을 리턴하도록 만들 수 있습니다.
        Mockito.when(T methodCall).thenReturn(T value)
        methodCall에 실행 할 메소드와 인자를 넣어 줍니다.
        실행할 메소드에 인자를 넣어줄 경우 ArgumentMatchers를 사용할 수도 있습니다.
        ArgumentMatchers.any()를 할경우 모든 값을 허용
        ArgumentMatchers 참고 : https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#3
        value에 리턴할 사용자가 정의한 값을 넣어 주면 됩니다.

        Mock 객체의 메소드를 실행 시 특정한 인자를 받은 경우
        사용자가 정의한 예외를 던질 수도 있습니다.
        Mockito.when(T methodCall).thenThrow(Class<? extends Throwable> throwableType)
        throwableType에 예외객체의 class 타입을 넣어 주면 됩니다.
     */
    @Test
    @DisplayName("Mock Stubbing 특정한 값 리턴 및 예외 던지기")
    void mockStubbing(){
        StudyService studyService = new StudyService(memberService, studyRepository);
        Assertions.assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("jin@email.com");

        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));
        //Mockito.when(memberService.findById(ArgumentMatchers.any())).thenReturn(Optional.of(member));

        Optional<Member> findById = memberService.findById(1L);
        Assertions.assertEquals("jin@email.com", findById.orElseThrow().getEmail());

        Mockito.when(memberService.findById(2L)).thenThrow(NoSuchElementException.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> memberService.findById(2L));
    }

    /*
        Mock 객체의 void 메소드를 실행 시 특정한 인자를 받은 경우
        사용자가 정의한 예외를 리턴하도록 만들 수 있습니다.
        Mockito.doThrow(Throwable... toBeThrown).when(T mock).실행할메소드(인자);
        toBeThrown에 리턴할 예외의 인스턴스를 넣어줍니다.
        mock에 Mock 객체를 넣어줍니다.
        실행할메소드에 Mock 객체의 실행할 메소드와 해당 인자까지 작성합니다.
    */
    @Test
    @DisplayName("Mock Stubbing void 메소드 실행시 예외 던지기")
    void mockStubbing2() {
        Mockito.doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> memberService.validate(1L));

        memberService.validate(2L);
    }

    /*
        Mock 객체의 메소드가 동일한 매개변수로 여러번 호출될 때
        각기 다르게 행동하게 할 수 있습니다.
        Mockito.when(T methodCall)
            .thenReturn(T value)    // 첫번째 실행시 리턴할 값
            .thenThrow(Class<? extends Throwable> throwableType)    // 두번째 실행시 던질 예외
            .thenReturn(T value)    // 세번째 실행시 리턴할 값
            ....
     */
    @Test
    @DisplayName("Mock Stubbing 특정한 값 리턴")
    void mockStubbing3() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        Assertions.assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("jin@email.com");

        Mockito.when(memberService.findById(1L))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> findById = memberService.findById(1L);
        Assertions.assertEquals("jin@email.com", findById.orElseThrow().getEmail());

        Assertions.assertThrows(RuntimeException.class, () -> memberService.findById(1L));

        Assertions.assertEquals(Optional.empty(), memberService.findById(1L));
    }

    @Test
    void test(){
        Study study = new Study(10, "테스트");

        Member member = new Member();
        member.setId(1L);
        member.setEmail("jin@email.com");

        // TODO memberService 객체에 findById 메소드를 1L 값으로 호출하면 Optional.of(member) 객체를 리턴하도록 Stubbing
        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));

        // TODO studyRepository 객체에 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 Stubbing
        Mockito.when(studyRepository.save(ArgumentMatchers.any(Study.class))).thenReturn(ArgumentMatchers.any(Study.class));

        StudyService studyService = new StudyService(memberService, studyRepository);
        studyService.createNewStudy(1L, study);
        Assertions.assertNotNull(study.getOwner());
        Assertions.assertEquals(member, study.getOwner());
    }

}
