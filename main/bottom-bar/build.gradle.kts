import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Testing

plugins {
    `android-library`
    `kotlin-android`

    id("de.mannodermaus.android-junit5") version "1.10.0.0"

}

apply<com.ivy.buildsrc.IvyComposePlugin>()

dependencies {
    Hilt()
    implementation(project(":design-system"))
    implementation(project(":core:ui"))
    implementation(project(":navigation"))
    implementation(project(":resources"))
    Testing()
}
android {
    namespace = "com.ivy.main.bottombar"
}
