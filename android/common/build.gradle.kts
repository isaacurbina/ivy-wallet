import com.ivy.buildsrc.AppCompat
import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.Testing

plugins {
    `android-library`
    `kotlin-android`

    id("de.mannodermaus.android-junit5") version "1.10.0.0"
}

apply<com.ivy.buildsrc.IvyPlugin>()

dependencies {
    Hilt()
    AppCompat(api = true)
    Testing()
}
android {
    namespace = "com.ivy.android.common"
}
