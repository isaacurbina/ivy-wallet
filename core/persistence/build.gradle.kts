import com.ivy.buildsrc.DataStore
import com.ivy.buildsrc.Hilt
import com.ivy.buildsrc.RoomDB
import com.ivy.buildsrc.Testing

plugins {
    `android-library`
    `kotlin-android`
    `kotlin-kapt` // for Room DB

    id("de.mannodermaus.android-junit5") version "1.9.3.0"
}

apply<com.ivy.buildsrc.IvyPlugin>()

android {
    defaultConfig {
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/../room-db-schemas")
            }
        }
    }
    namespace = "com.ivy.core.persistence"
}

dependencies {
    Hilt()
    implementation(project(":common:main"))
    RoomDB(api = false)
    DataStore(api = true)

    Testing()
}