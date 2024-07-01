import com.ivy.buildsrc.Hilt

plugins {
    `android-library`
    `kotlin-android`
    id("de.mannodermaus.android-junit5") version "1.10.0.0"
}

apply<com.ivy.buildsrc.IvyComposePlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))

    implementation(project(":design-system"))
    implementation(project(":core:ui"))
    implementation(project(":core:data-model"))
}
android {
    namespace = "com.ivy.locked"
}
