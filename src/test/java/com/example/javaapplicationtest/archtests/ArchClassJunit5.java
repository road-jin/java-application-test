package com.example.javaapplicationtest.archtests;

import com.example.javaapplicationtest.App;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import javax.persistence.Entity;

@AnalyzeClasses(packagesOf = App.class)
public class ArchClassJunit5 {

    /*
        Controller는 Service와 Repository를 사용할 수 있다.
     */
    @ArchTest
    ArchRule controllerClassRule = ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Controller")
            .should().accessClassesThat().haveSimpleNameEndingWith("Service")
            .orShould().accessClassesThat().haveSimpleNameEndingWith("Repository");

    /*
        Repository는 Service와 Controller를 사용할 수 없다.
     */
    @ArchTest
    ArchRule repositoryClassRule = ArchRuleDefinition.noClasses().that().haveSimpleNameEndingWith("Repository")
            .should().accessClassesThat().haveSimpleNameEndingWith("Service")
            .orShould().accessClassesThat().haveSimpleNameEndingWith("Controller");


    /*
        Study* 로 시작하는 클래스는 ..study.. 패키지에 있어야 한다.
        예외로 Enum이나, @Entity 붙은 클래스들은 제외
     */
    @ArchTest
    ArchRule studyClassesRule = ArchRuleDefinition.classes().that().haveSimpleNameStartingWith("Study")
            .and().areNotEnums()
            .and().areNotAnnotatedWith(Entity.class)
            .should().resideInAnyPackage("..study..");
}
