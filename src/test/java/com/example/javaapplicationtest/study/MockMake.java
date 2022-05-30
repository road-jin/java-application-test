package com.example.javaapplicationtest.study;

import com.example.javaapplicationtest.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MockMake {

    @Mock
    MemberService globalMemberService;

    @Mock
    StudyRepository globalStudyRepository;

    /*
        Mock을 만드는 방법
        Mockito.mock(Class<T> classToMock) 메소드를 통하여 생성할 수 있습니다.
     */
    @Test
    @DisplayName("Mockito로 Mock 만드는 방법 1 직접넣기")
    void createStudyService(){
        MemberService memberService = Mockito.mock(MemberService.class);
        StudyRepository studyRepository = Mockito.mock(StudyRepository.class);
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    /*
        Mock을 애노테이션을 활용하여 전역변수를 통해 만드는 방법
        @ExtendWith(MockitoExtension.class) 확장팩 등록을 합니다.
        전역번수에 @Mock 애노테이션을 붙입니다.
     */
    @Test
    @DisplayName("Mockito로 Mock 만드는 방법 2 애노테이션 전역변수")
    void createStudyService2(){
        StudyService studyService = new StudyService(globalMemberService, globalStudyRepository);
        assertNotNull(studyService);
    }

    /*
        Mock을 애노테이션을 활용하여 매개변수를 통해 만드는 방법
        @ExtendWith(MockitoExtension.class) 확장팩 등록을 합니다.
        파라미터변수에 @Mock 애노테이션을 붙입니다.
     */
    @Test
    @DisplayName("Mockito로 Mock 만드는 방법 3 애노테이션 매개변수")
    void createStudyService3(@Mock MemberService memberService, @Mock StudyRepository studyRepository){
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }
}