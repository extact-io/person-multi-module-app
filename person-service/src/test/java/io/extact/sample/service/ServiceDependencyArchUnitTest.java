package io.extact.sample.service;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
        packages = "io.extact.sample",
        importOptions = ImportOption.DoNotIncludeTests.class)
public class ServiceDependencyArchUnitTest {
    /**
     * serviceパッケージ内のコードから依存OKなライブラリの定義。
     * 依存してよいのは以下のライブラリのみ
     * <pre>
     * ・アプリ自身のクラス(io.extact.sample..)
     * ・SLF4J(org.slf4j..)
     * ・CDI
     * ・JTA
     * ・JavaSE API
     * </pre>
     */
    @ArchTest
    static final ArchRule test_serviceパッケージで依存してOKなライブラリの定義 =
            classes()
                .that()
                    .resideInAPackage("io.extact.sample.person.service..")
                .should()
                    .onlyDependOnClassesThat(
                            resideInAnyPackage(
                                "io.extact.sample..",
                                "org.slf4j..",
                                "javax.annotation..",   // CDI
                                "javax.inject..",       // CDI
                                "javax.enterprise.inject..",  // CDI
                                "javax.enterprise.context..", // CDI
                                "javax.transaction..",  // JTA
                                "java.."                // JavaSE
                            )
                    );
    /**
     * persistenceパッケージ直下のコードから依存OKなライブラリの定義。
     * 依存してよいのは以下のライブラリのみ
     * <pre>
     * ・アプリ自身のクラス(io.extact.sample..)
     * ・SLF4J(org.slf4j..)
     * ・CDI(javax..)
     * ・JavaSE API(java..)
     * </pre>
     */
    @ArchTest
    static final ArchRule test_persistenceパッケージで依存してOKなライブラリの定義 =
            classes()
                .that()
                    .resideInAPackage("io.extact.sample.person.persistence")
                .should()
                    .onlyDependOnClassesThat(
                            resideInAnyPackage(
                                "io.extact.sample..",
                                "org.slf4j..",
                                "javax.annotation..",   // CDI
                                "javax.inject..",       // CDI
                                "javax.enterprise.inject..",  // CDI
                                "javax.enterprise.context..", // CDI
                                "java.."                // JavaSE
                            )
                    );
    /**
     * persistenceの実装パッケージへの依存がないことの定義
     * <pre>
     * ・persistenceパッケージはjpa or fileパッケージに依存してないこと
     * </pre>
     */
    @ArchTest
    static final ArchRule test_persistenceの実装パッケージへの依存がないことの定義 =
            noClasses()
                .that()
                    .resideInAPackage("io.extact.sample.person.persistence") // persistence直下
                .should()
                    .dependOnClassesThat()
                        .resideInAnyPackage(
                                "io.extact.sample.person.persistence.jpa..",
                                "io.extact.sample.person.persistence.file.."
                                );
}
