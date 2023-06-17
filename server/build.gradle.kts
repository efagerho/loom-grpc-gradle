plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("com.google.protobuf") version "0.9.3"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

val grpcVersion = "1.56.0"
val protobufVersion = "3.22.3"
val protocVersion = protobufVersion
val helidonVersion = "4.0.0-ALPHA6"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.protobuf:protobuf-java:$protobufVersion")
    implementation(platform("io.grpc:grpc-bom:$grpcVersion"))
    implementation("io.helidon.nima.webserver:helidon-nima-webserver:$helidonVersion")
    implementation("io.helidon.nima.http2:helidon-nima-http2-webserver:$helidonVersion")
    implementation("io.helidon.nima.grpc:helidon-nima-grpc-webserver:$helidonVersion")

    implementation("javax.annotation:javax.annotation-api:1.3.2")

    // Use TestNG framework, also requires calling test.useTestNG() below
    testImplementation("org.testng:testng:7.4.0")


    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.1.1-jre")
}

sourceSets {
    main {
        java {
            srcDir("build/generated/source/proto/main/java")
        }
    }
}


application {
    mainClass.set("io.github.efagerho.loom.ServerMain")
}

tasks {
    jar {
    }

    named<JavaExec>("run") {
        jvmArgs = listOf(
            "--enable-preview"
        )
        args = listOf(
            "run",
            "io.github.efagerho.loom.ServerMain",
        )
    }
    named<Test>("test") {
        useTestNG()
    }
}
