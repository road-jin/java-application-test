package com.example.javaapplicationtest.study;

import com.example.javaapplicationtest.domain.Member;
import com.example.javaapplicationtest.domain.Study;
import com.example.javaapplicationtest.domain.StudyStatus;
import com.example.javaapplicationtest.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MockBDD {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;


    /*
        BDD: 애플리케이션이 어떻게 “행동”해야 하는지에 대한 공통된 이해를 구성하는 방법으로,
        TDD에서 창안했습니다.
        Mockito는 BddMockito라는 클래스를 통해 BDD 스타일의 API를 제공합니다.

        기존 : Mockito.verify(memberService, Mockito.times(1)).notify(newStudy);
        변경 : BDDMockito.given(memberService.findById(1L)).willReturn(Optional.of(member));

        기존 : Mockito.verify(memberService, Mockito.times(1)).notify(newStudy);
        변경 : BDDMockito.then(memberService).should(Mockito.times(1)).notify(newStudy);

        기존 : Mockito.verifyNoMoreInteractions(memberService);
        변경 : BDDMockito.then(memberService).shouldHaveNoMoreInteractions();
     */
    @Test
    @DisplayName("BDD 스타일 변경")
    void mockBDD_test(){
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);

        Study study = new Study(10, "테스트");

        Member member = new Member();
        member.setId(1L);
        member.setEmail("jin@email.com");

        BDDMockito.given(memberService.findById(1L)).willReturn(Optional.of(member));
        BDDMockito.given(studyRepository.save(ArgumentMatchers.any(Study.class))).willReturn(ArgumentMatchers.any(Study.class));

        // WHen
        Study newStudy = studyService.createNewStudy(1L, study);

        // Then
        Assertions.assertEquals(member, study.getOwner());
        BDDMockito.then(memberService).should(Mockito.times(1)).notify(newStudy);
        BDDMockito.then(memberService).should(Mockito.times(1)).notify(member);
        BDDMockito.then(memberService).shouldHaveNoMoreInteractions();
    }

    @Test
    void test() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "더 자바, 테스트");
        // TODO studyRepository Mock 객체의 save 메소드를호출 시 study를 리턴하도록 만들기.
        BDDMockito.given(studyRepository.save(study)).willReturn(study);

        // When
        studyService.openStudy(study);

        // Then
        // TODO study의 status가 OPENED로 변경됐는지 확인
        Assertions.assertEquals(StudyStatus.OPENED, study.getStatus());
        // TODO study의 openedDataTime이 null이 아닌지 확인
        Assertions.assertNotNull(study.getOpenedDateTime());
        // TODO memberService의 notify(study)가 호출 됐는지 확인.
        BDDMockito.then(memberService).should(Mockito.times(1)).notify(study);
    }
}
