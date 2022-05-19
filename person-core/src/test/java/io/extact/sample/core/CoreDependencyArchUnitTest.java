package io.extact.sample.core;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
        packages = "io.extact.sample",
        importOptions = ImportOption.DoNotIncludeTests.class)
public class CoreDependencyArchUnitTest {
    /**
     * coreパッケージ内のコードから依存OKなライブラリの定義。
     * extパッケージを除き依存してよいのは以下のライブラリのみ
     * <pre>
     * ・アプリ自身のクラス(io.extact.sample..)
     * ・SLF4J(org.slf4j..)
     * ・CDI
     * ・JavaSE API
     * </pre>
     */
    @ArchTest
    static final ArchRule test_coreパッケージで依存してOKなライブラリの定義 =
            classes()
                .that()
                    .resideInAPackage("io.extact.sample.core..")
                    .and().resideOutsideOfPackage("io.extact.sample.core..ext..")
                .should()
                    .onlyDependOnClassesThat(
                            resideInAnyPackage(
                                "io.extact.sample..",
                                "org.slf4j..",
                                "java.." // JavaSE
                            )
                    );

    /**
     * extパッケージ内のコードから依存OKなライブラリの定義。
     * <pre>
     * ・アプリ自身のクラス(io.extact.sample..)
     * ・SLF4J(org.slf4j..)
     * ・Apache Commons CSV(org.apache.commons.csv..)
     * ・JavaSE API
     * </pre>
     */
    @ArchTest
    static final ArchRule test_extパッケージで依存してOKなライブラリの定義 =
            classes()
                .that()
                    .resideInAPackage("io.extact.sample.core.io.ext..")
                .should()
                    .onlyDependOnClassesThat(
                            resideInAnyPackage(
                                "io.extact.sample..",
                                "org.slf4j..",
                                "org.apache.commons.csv..", // commons csv
                                "java.." // JavaSE
                            )
                    );
}
