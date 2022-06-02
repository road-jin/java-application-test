package com.example.javaapplicationtest.archtests;

import com.example.javaapplicationtest.App;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;

/*
    @AnalyzeClasses
    packagesOf 값에 클래스를 주입하면 주입한 클래스 패키지에서 ArchTest 진행합니다.
 */
@AnalyzeClasses(packagesOf = App.class)
public class ArchPackageJunit5 {

    /*
        ..domain.. 패키지에 있는 클래스는 ..study.., ..member.., ..domain.., ..mockito.., ..junit5.., ..testcontainers..에서 참조 가능.
    */
    @ArchTest
    ArchRule domainPackageRule = ArchRuleDefinition.classes().that().resideInAPackage("..domain..")
            .should().onlyBeAccessed().byClassesThat()
            .resideInAnyPackage("..study..", "..domain.." , "..mockito..", "..junit5..", "..testcontainers..");

    /*
        ..domain.. 패키지에 있는 클래스는 ..member.. 패키지의 클래스를 참조하지 못한다.
    */
    @ArchTest
    ArchRule memberPackageRule = ArchRuleDefinition.noClasses().that().resideInAPackage("..domain..")
            .should().accessClassesThat().resideInAPackage("..member..");

    /*
        ..study.. 패키지에 있는 클래스는 ..study.., ..mockito.., ..testcontainers.. 에서만 참조 가능.
     */
    @ArchTest
    ArchRule studyPackageRule = ArchRuleDefinition.classes().that().resideInAPackage("..study..")
            .should().onlyBeAccessed().byClassesThat()
            .resideInAnyPackage("..study..", "..mockito..", "..testcontainers..");

    /*
        순환 참조가 없는지 확인.
     */
    @ArchTest
    ArchRule freeOfCycles = SlicesRuleDefinition.slices().matching("..javaapplicationtest.(*)..")
            .should().beFreeOfCycles();
}
