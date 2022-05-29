# Person Multi Module App
> 豆蔵デベロッパーサイトのブログ記事で利用しているサンプルアプリ

## 利用している記事

|記事| 利用内容 |
|---|---|
|[ArchUnitで考えるアーキテクチャ構造とその検証](https://developer.mamezou-tech.com/blogs/2022/05/19/archunit-and-architechure/)| ArchUnitのサンプル|
|[JUnit5のExtension実装 - テストライフサイクルコールバックと引数の解決](https://developer.mamezou-tech.com/blogs/2022/05/29/junit5-extension/)| JUnit5のExtension実装サンプル|

## ArchUnitのサンプルテストクラス
||クラス|実装内容|
|---|---|---|
|全体|[LayerDependencyArchUnitTest](/person-server/src/test/java/io/extact/sample/LayerDependencyArchUnitTest.java) | レイヤー間の依存関係の定義<br>物理モジュール間の依存関係の定義<br>アプリが依存してOKなライブラリの定義|
|レイヤ別|[CoreDependencyArchUnitTest](/person-core/src/test/java/io/extact/sample/core/CoreDependencyArchUnitTest.java)|coreパッケージで依存してOKなライブラリの定義|
||[EntityDependencyArchUnitTest](/person-entity/src/test/java/io/extact/sample/person/entity/EntityDependencyArchUnitTest.java)|entityパッケージで依存してOKなライブラリの定義|
||[WebApiDependencyArchUnitTest](/person-server/src/test/java/io/extact/sample/person/webapi/WebApiDependencyArchUnitTest.java)|webapiパッケージで依存してOKなライブラリの定義|
||[ServiceDependencyArchUnitTest](/person-service/src/test/java/io/extact/sample/service/ServiceDependencyArchUnitTest.java)|serviceパッケージで依存してOKなライブラリの定義<br>persistenceパッケージ直下で依存してOKなライブラリの定義<br>persistenceの実装パッケージへの依存がないことの定義|
||[JpaDependencyArchUnitTest](/person-persistence-jpa/src/test/java/io/extact/sample/person/persistence/jpa/JpaDependencyArchUnitTest.java)|jpaパッケージで依存してOKなライブラリの定義|
||[FileDependencyArchUnitTest](/person-persistence-file/src/test/java/io/extact/sample/person/persistence/file/FileDependencyArchUnitTest.java)|fileパッケージで依存してOKなライブラリの定義|

## JUnit5のExtension実装サンプルクラス
|クラス|実装内容|
|---|---|
|[JpaPersonRepository](/person-persistence-jpa/src/main/java/io/extact/sample/person/persistence/jpa/JpaPersonRepository.java) | テスト対象クラス|
|[JpaPersonRepositoryTest](/person-persistence-jpa/src/test/java/io/extact/sample/person/persistence/jpa/JpaPersonRepositoryTest.java) | Extensionを使ったテストクラス|
|[JpaTransactionalExtension](/person-persistence-jpa/src/test/java/io/extact/sample/person/persistence/jpa/junit5/JpaTransactionalExtension.java) | Extension実装|
|[TransactionalForTest](/person-persistence-jpa/src/test/java/io/extact/sample/person/persistence/jpa/junit5/TransactionalForTest.java) | トランザクション対象を表すアノテーション|


## ビルドと実行
サンプルアプリのビルドにはJava11以上とMavenが必要です

1. moduleのローカルインストール
``` shell
# Clone this repository
git clone https://github.com/extact-io/person-multi-module-app.git
# Go into the repository
cd person-multi-module-app
# Install dependencies
mvn clean install -DskipTests=true
```

2. サーバ側（RESTリソースアプリ）のビルドと起動
``` shell
# Go into the app directory
cd person-server
# Build the app
mvn -Prunnable,jpa clean package -DskipTests=true
# Run the app
java -jar target/person-server.jar
```

## 動作確認
```shell
# Invoke PersonResource#get(id)
curl -X GET http://localhost:7001/api/persons/1 -w ':%{http_code}\n'
{"age":18,"id":1,"name":"soramame"}:200

# Invoke PersonResource#getAll()
curl -X GET http://localhost:7001/api/persons -w ':%{http_code}\n'
[{"age":18,"id":1,"name":"soramame"},{"age":32,"id":2,"name":"edamame"}]:200

# Invoke PersonResource#add(person)
curl -X POST -H "Content-Type: application/json" -d '{"id":null,"name":"daizu","age":20}' http://localhost:7001/api/persons -w ':%{http_code}\n'
{"age":20,"id":3,"name":"daizu"}:200

```


