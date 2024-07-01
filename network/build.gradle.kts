import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Ktor

plugins {
    `android-library`
    `kotlin-android`

    id("de.mannodermaus.android-junit5") version "1.10.0.0"

}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    Hilt()
    implementation(project(":common:main"))
    Ktor(api = true)
}
android {
    namespace = "com.ivy.network"
}
