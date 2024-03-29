package com.example.javaapplicationtest.member;

import com.example.javaapplicationtest.domain.Member;
import com.example.javaapplicationtest.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member member);
}
