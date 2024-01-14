package com.paymentic.arch;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.lang.conditions.ArchConditions.accessClassesThat;
import static com.tngtech.archunit.lang.conditions.ArchConditions.not;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ArchTests {
  private final JavaClasses importedClasses = new ClassFileImporter().importPackages("com.paymentic");
  @Test
  public void testDomainShouldNotAccessAdapter() {
    var highLevelRule = classes()
        .that().resideInAPackage("..domain..")
        .should(not(accessClassesThat(resideInAPackage("..adapter.."))));
    highLevelRule.check(importedClasses);
  }
  @Test
  public void testInfraShouldNotAccessDomain() {
    var highLevelRule = classes()
        .that().resideInAPackage("..infra..")
        .should(not(accessClassesThat(resideInAPackage("..domain.."))));
    highLevelRule.check(importedClasses);
  }
  @Test
  public void testDomainShouldNotHaveKafkaAsDependency() {
    ArchRule rule = noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat().resideInAPackage("org.apache.kafka..");
    rule.check(importedClasses);
  }

}
