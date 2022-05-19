package io.extact.sample.person.persistence.file;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
        packages = "io.extact.sample",
        importOptions = ImportOption.DoNotIncludeTests.class)
public class FileDependencyArchUnitTest {
    /**
     * persistence.fileパッケージ内のコードから依存OKなライブラリの定義。
     * 依存してよいのは以下のライブラリのみ
     * <pre>
     * ・アプリ自身のクラス(io.extact.sample..)
     * ・SLF4J(org.slf4j..)
     * ・CDI
     * ・JavaSE API
     * </pre>
     */
    @ArchTest
    static final ArchRule test_fileパッケージで依存してOKなライブラリの定義 =
            classes()
                .that()
                    .resideInAPackage("io.extact.sample.person.persistence.file..")
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
}
