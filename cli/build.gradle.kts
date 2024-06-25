plugins {
    kotlin("jvm")
    application
}

group = "tk.zwander"
version = "1.18.12"

dependencies {
    implementation(project(":common"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}
application {
    mainClass.set("MainKt")
}