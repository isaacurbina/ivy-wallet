import com.ivy.buildsrc.AndroidXTest
import com.ivy.buildsrc.Coroutines
import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.HiltTesting
import com.ivy.buildsrc.Kotlin
import com.ivy.buildsrc.RoomDB
import com.ivy.buildsrc.Testing
import com.ivy.buildsrc.Versions
import com.ivy.buildsrc.kapt

plugins {
    `android-library`
    `kotlin-android`

    id("de.mannodermaus.android-junit5") version "1.9.3.0"

}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    Hilt()
    HiltTesting(
        dependency = { api(it) },
        kaptProcessor = { kapt(it) }
    )

    RoomDB(api = false)
    implementation(project(":core:persistence"))
    implementation(project(":core:domain"))
    implementation(project(":common:main"))
    implementation("app.cash.turbine:turbine:${Versions.turbine}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}")

    Kotlin(api = false)
    Coroutines(api = false)
    AndroidXTest(dependency = { api(it) })


    Testing(
        // :common:test needs to be added as implementation dep
        // else won't work
        commonTest = false,
        // Prevent circular dependency
        commonAndroidTest = false
    )
    api(project(":common:test")) // expose :common:test classes to all androidTest
}
android {
    namespace = "com.ivy.common.androidtest"
}
