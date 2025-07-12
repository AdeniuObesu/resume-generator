package org.adeniuobesu.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

public class ArchitectureTest {

    private static final JavaClasses classes = new ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages("org.adeniuobesu");

    // CORE
    @Test
    void coreShouldNotDependOnOtherLayers() {
        ArchRuleDefinition.noClasses()
            .that().resideInAPackage("..core..")
            .should().dependOnClassesThat()
            .resideInAnyPackage("..application..", "..adapters..", "..infrastructure..")
            .because("core should be independent");
    }

    // APPLICATION
    @Test
    void applicationShouldOnlyDependOnCore() {
        ArchRuleDefinition.noClasses()
            .that().resideInAPackage("..application..")
            .should().dependOnClassesThat()
            .resideInAnyPackage("..adapters..", "..infrastructure..")
            .because("application should not depend on outer layers");
    }

    // ADAPTERS
    @Test
    void adaptersShouldNotDependOnInfrastructure() {
        ArchRuleDefinition.noClasses()
            .that().resideInAPackage("..adapters..")
            .should().dependOnClassesThat()
            .resideInAnyPackage("..infrastructure..")
            .because("adapters should not depend on infrastructure");
    }

    // DTOs only in application
    @Test
    void dtosShouldOnlyResideInApplicationLayer() {
        ArchRuleDefinition.classes()
            .that().haveSimpleNameEndingWith("Dto")
            .should().resideInAPackage("..application.dtos..")
            .because("DTOs should be in application layer only");
    }

    // Mappers only in application
    @Test
    void mappersShouldResideInApplicationLayer() {
        ArchRuleDefinition.classes()
            .that().haveSimpleNameEndingWith("Mapper")
            .should().resideInAPackage("..application.mappers..")
            .because("Mappers should only live in application layer");
    }

    // Infrastructure is allowed to depend on anything (optional)
    @Test
    void infrastructureCanAccessAllLayers() {
        ArchRuleDefinition.noClasses()
            .that().resideInAPackage("..infrastructure..")
            .should().onlyHaveDependentClassesThat()
            .resideInAnyPackage("..core..", "..application..", "..adapters..", "..infrastructure..")
            .because("infrastructure is the outermost layer and can wire everything");
    }
}
