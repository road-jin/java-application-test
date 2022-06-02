package com.example.javaapplicationtest.mockito;

import com.example.javaapplicationtest.domain.Member;
import com.example.javaapplicationtest.domain.Study;
import com.example.javaapplicationtest.member.MemberService;
import com.example.javaapplicationtest.study.StudyRepository;
import com.example.javaapplicationtest.study.StudyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MockVerify {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;


    /*
        Mock 객체가 어떻게 사용이 됐는지 확인할 수 있습니다.
        1. 특정 메소드가 특정 매개변수로 몇번 호출 되었는지
           Mockito.verify(T mock, VerificationMode mode).실행할메소드(인자);
           mock에 Mock 객체를 넣어줍니다.
           mode에 Mockito.times(정수형 인자)를 넣어줍니다.
           실행할메소드에 Mock 객체의 실행할 메소드와 해당 인자까지 작성합니다.
           Mockito.times(정수형 인자)가 몇번 호출 되었는지를 확인하는 코드입니다.

        2. 특정 메소드가 특정 매개변수로 최소 한번은 호출 됐는지
           Mockito.verify(T mock, VerificationMode mode).실행할메소드(인자);
           mode에 Mockito.atLeast(정수형 인자)를 넣어줍니다.
           Mockito.atLeast(정수형 인자)가 최소 몇번 호출 되었는지를 확인하는 코드입니다.

        3. 특정 메소드가 특정 매개변수로 전혀 호출되지 않았는지
           Mockito.verify(T mock, VerificationMode mode).실행할메소드(인자);
           mode에 Mockito.never()를 넣어줍니다.
           Mockito.never()가 전혀 호출되지 않았는지 확인하는 코드입니다.

        특정 시점 이후에 아무 일도 벌어지지 않았는지도 확인할 수 있습니다.
        Mockito.verifyNoMoreInteractions(Object... mocks)
        mocks에 Mock 객체를 넣어줍니다.
        시점이 Mockito.verify() 확인하는 시점 이후로 확인됩니다.
     */
    @Test
    void mockVerify(){
        StudyService studyService = new StudyService(memberService, studyRepository);

        Study study = new Study(10, "테스트");

        Member member = new Member();
        member.setId(1L);
        member.setEmail("jin@email.com");

        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Mockito.when(studyRepository.save(ArgumentMatchers.any(Study.class))).thenReturn(ArgumentMatchers.any(Study.class));

        Study newStudy = studyService.createNewStudy(1L, study);

        Assertions.assertEquals(member.getId(), study.getOwnerId());
        Mockito.verify(memberService, Mockito.times(1)).notify(newStudy);
        Mockito.verify(memberService, Mockito.atLeast(1)).notify(member);
        Mockito.verify(memberService, Mockito.never()).validate(ArgumentMatchers.any());

        Mockito.verifyNoMoreInteractions(memberService);
    }

    /*
        특정 메소드가 어떤 순서대로 호출했는지
        InOrder inOrder = Mockito.inOrder(Object... mocks)
        inOrder.verify(T mock)..실행할메소드(인자); // 첫번쨰
        inOrder.verify(T mock)..실행할메소드(인자); // 두번째
        mocks에 순서를 확인할 Mock 객체를 넣어줍니다.
        n번째 verify()에 순서를 확인할 Mock 객체를 넣어준 후
        실행할메소드에 순서를 확인할 메소드와 해당 인자까지 작성합니다.
     */
    @Test
    void mockInOrderVerify(){
        StudyService studyService = new StudyService(memberService, studyRepository);

        Study study = new Study(10, "테스트");

        Member member = new Member();
        member.setId(1L);
        member.setEmail("jin@email.com");

        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Mockito.when(studyRepository.save(ArgumentMatchers.any(Study.class))).thenReturn(ArgumentMatchers.any(Study.class));

        Study newStudy = studyService.createNewStudy(1L, study);

        Assertions.assertEquals(member.getId(), study.getOwnerId());

        InOrder inOrder = Mockito.inOrder(memberService);
        inOrder.verify(memberService).notify(newStudy);
        inOrder.verify(memberService).notify(member);
    }
}

