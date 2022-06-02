package com.example.javaapplicationtest.archtests;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

public class ArchTests {

    @Test
    void packageDependencyTests() {
        JavaClasses javaClasses = new ClassFileImporter().importPackages("com.example.javaapplicationtest");

        /*
            ..domain.. 패키지에 있는 클래스는 ..study.., ..member.., ..domain.., ..mockito.., ..junit5.., ..testcontainers..에서 참조 가능.
         */
        ArchRule domainPackageRule = ArchRuleDefinition.classes().that().resideInAPackage("..domain..")
                .should().onlyBeAccessed().byClassesThat()
                .resideInAnyPackage("..study..", "..domain.." , "..mockito..", "..junit5..", "..testcontainers..");

        domainPackageRule.check(javaClasses);

        /*
            ..domain.. 패키지에 있는 클래스는 ..member.. 패키지의 클래스를 참조하지 못한다.
         */
        ArchRule memberPackageRule = ArchRuleDefinition.noClasses().that().resideInAPackage("..domain..")
                .should().accessClassesThat().resideInAPackage("..member..");

        memberPackageRule.check(javaClasses);

        /*
            ..study.. 패키지에 있는 클래스는 ..study.., ..mockito.., ..testcontainers.. 에서만 참조 가능.
         */
        ArchRule studyPackageRule = ArchRuleDefinition.classes().that().resideInAPackage("..study..")
                .should().onlyBeAccessed().byClassesThat()
                .resideInAnyPackage("..study..", "..mockito..", "..testcontainers..");

        studyPackageRule.check(javaClasses);

        /*
            순환 참조가 없는지 확인.
         */
        ArchRule freeOfCycles = SlicesRuleDefinition.slices().matching("..javaapplicationtest.(*)..")
                .should().beFreeOfCycles();

        freeOfCycles.check(javaClasses);
    }
}
