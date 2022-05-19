package io.extact.sample;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.*;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
        packages = "io.extact.sample",
        importOptions = ImportOption.DoNotIncludeTests.class)
public class LayerDependencyArchUnitTest {
    /**
     * レイヤー間の依存関係の定義
     * <pre>
     * ・webapiレイヤはどのレイヤからも依存されていないこと（webapiレイヤは誰も使ってはダメ）
     * ・serviceレイヤはwebapiレイヤからのみ依存から依存を許可（webapiレイヤ以外は誰も使ってはダメ）
     * ・persistenceレイヤはserviceレイヤからのみ依存を許可（persistenceレイヤを使って良いのはserviceレイヤのみ）
     * ・entityレイヤは上位のwebapiとserviceとpersistenceからのみ依存を許可
     * ・coreレイヤはすべてのレイヤから依存を許可
     * </pre>
     */
    @ArchTest
    static final ArchRule test_レイヤー間の依存関係の定義 = layeredArchitecture()
            .layer("webapi").definedBy("io.extact.sample.person.webapi..")
            .layer("service").definedBy("io.extact.sample.person.service..")
            .layer("persistence").definedBy("io.extact.sample.person.persistence..")
            .layer("entity").definedBy("io.extact.sample.person.entity..")
            .layer("core").definedBy("io.extact.sample.core..")

            .whereLayer("webapi").mayNotBeAccessedByAnyLayer()
            .whereLayer("service").mayOnlyBeAccessedByLayers("webapi")
            .whereLayer("persistence").mayOnlyBeAccessedByLayers("service")
            .whereLayer("entity").mayOnlyBeAccessedByLayers("webapi", "service", "persistence")
            .whereLayer("core").mayOnlyBeAccessedByLayers("webapi", "service", "persistence", "entity");

    /**
     * 物理モジュール(jar)間の依存関係の定義
     * <pre>
     * ・server.jarはどのjarからも依存されないこと
     * ・service.jarに依存してよいのは直接利用するserver.jarとrepositoryインタフェースを実現するpersistence-file.jarとpersistence-file.jarの3つ
     * ・entity.jarはcore.jarを除くどののjarからも依存してよい
     * ・core.jarはすべてのjarが依存してよい
     * </pre>
     */
    @ArchTest
    static final ArchRule test_物理モジュール間の依存関係の定義 = layeredArchitecture()
            .layer("server.jar").definedBy("io.extact.sample.person.webapi..")
            .layer("service.jar").definedBy(
                    "io.extact.sample.person.service..",
                    "io.extact.sample.person.persistence") // persistenceパッケージ直下。配下のパッケージは含まない
            .layer("persistence-file.jar").definedBy("io.extact.sample.person.persistence.file..")
            .layer("persistence-jpa.jar").definedBy("io.extact.sample.person.persistence.jpa..")
            .layer("entity.jar").definedBy("io.extact.sample.person.entity..")
            .layer("core.jar").definedBy("io.extact.sample.core..")

            .whereLayer("server.jar").mayNotBeAccessedByAnyLayer()
            .whereLayer("service.jar").mayOnlyBeAccessedByLayers(
                    "server.jar",
                    "persistence-file.jar",
                    "persistence-jpa.jar")
            .whereLayer("persistence-file.jar").mayNotBeAccessedByAnyLayer()
            .whereLayer("persistence-jpa.jar").mayNotBeAccessedByAnyLayer()
            .whereLayer("entity.jar").mayOnlyBeAccessedByLayers(
                    "server.jar",
                    "service.jar",
                    "persistence-file.jar",
                    "persistence-jpa.jar")
            .whereLayer("core.jar").mayOnlyBeAccessedByLayers(
                    "server.jar",
                    "service.jar",
                    "persistence-file.jar",
                    "persistence-jpa.jar",
                    "entity.jar");

    /**
     * アプリのコードから依存してOKなライブラリの定義。
     * 個別に依存を許可しているextパッケージを除き依存してよいのは以下のライブラリのみ
     * <pre>
     * ・アプリ自身のクラス(io.extact.sample.*)
     * ・SLF4J(org.slf4j..)
     * ・JavaEE API(javax..)
     * ・JavaSE API(java..)
     * </pre>
     * エントリポイントとなるMainクラスはHelidon(io.helidon..)に依存するため除外する
     */
    @ArchTest
    static final ArchRule test_アプリが依存してOKなライブラリの定義 =
            classes()
                .that()
                    .resideInAPackage("io.extact.sample..")
                    .and().haveSimpleNameNotEndingWith("Main")
                    .and().resideOutsideOfPackage("io.extact.sample..ext..")
                .should()
                    .onlyDependOnClassesThat(
                            resideInAnyPackage(
                                "io.extact.sample..",
                                "org.slf4j..",
                                "javax..",
                                "java.."
                            )
                    );

    }
