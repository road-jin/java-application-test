package com.example.javaapplicationtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class JunitTag {
    /*
        @Tag
        value 값에 사용할 Tag로 사용할 이름을 기입합니다.

            tasks.named('test') {
                useJUnitPlatform()
            }
        build.gradle에서 기본 task로 위와 같이 되어 있습니다.

            // `gradle test`: slow tag가 안 붙은 테스트만 수행
            tasks.named('test') {
                useJUnitPlatform {
                    excludeTags 'slow'
                }
            }

            // `gradle integrationTest`: slow tag가 붙은 테스트만 수행
            task integrationTest(type: Test, group: 'verification') {
                useJUnitPlatform {
                    includeTags 'slow'
                }
            }
        위와 같이 기본 task 와 integrationTest를 생성하여
        test task는 @Tag("slow")가 안 붙은 테스트만 수행합니다.
        integrationTest task는 @Tag("slow")가 붙은 테스트만 수행합니다.

        터미널 실행
        gradle 테스크명
        ex ) gradle integrationTest

        터미널에서 gradle --version 엔터 후 버전정보가 안나온다면,
        brew install gradle 입력 후 설치해주세요.
     */
    @Test
    @DisplayName("Tag fast 테스트")
    @Tag("fast")
    void tag_fast_test(){
        Study study = new Study(10);
        Assertions.assertEquals(10, study.getLimit());
    }

    @Test
    @DisplayName("Tag slow 테스트")
    @Tag("slow")
    void tag_slow_test(){
        Study study = new Study(10);
        Assertions.assertEquals(10, study.getLimit());
    }

    /*
        package com.example.javaapplicationtest.CustomTag
        JUnit 5 애노테이션을 조합하여 커스텀 태그를 만들 수 있습니다.
     */
    @CustomTag
    @DisplayName("커스텀 태그 테스트")
    void customTag_test(){
        Study study = new Study(10);
        Assertions.assertEquals(10, study.getLimit());
    }
}
