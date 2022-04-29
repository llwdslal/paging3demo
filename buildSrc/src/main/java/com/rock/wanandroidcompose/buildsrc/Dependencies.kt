package com.rock.wanandroidcompose.buildsrc


object Version {
    const val compose = "1.1.0"
    const val hiltPlugin = "2.41"
}

object Libs {
    object AndroidX {
        object Activity {
            const val compose = "androidx.activity:activity-compose:1.3.1"
        }

        object Appcompat{
            const val appcompat = "androidx.appcompat:appcompat:1.3.0"
        }

        object Core {
            private const val version = "1.7.0"
            const val ktx = "androidx.core:core-ktx:$version"
        }

        object Compose {
            private const val version = Version.compose
            const val ui = "androidx.compose.ui:ui:$version"
            const val material = "androidx.compose.material:material:$version"
            const val uiPreview = "androidx.compose.ui:ui-tooling-preview:$version"
            const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
        }

        object LifeCycle {
            private const val version = "2.4.1"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
        }

        object Room {
            private const val version = "2.4.2"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

        object Navigation{
            const val navigationCompose = "androidx.navigation:navigation-compose:2.4.1"
        }

        object Hilt{
            const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
        }

        object Datastore{
            private const val version = "1.0.0"
            const val dataStoreP = "androidx.datastore:datastore-preferences:$version"
        }

        object Paging{
            private const val version = "3.1.1"
            const val paging = "androidx.paging:paging-runtime:$version"
            const val pagingRoom = "androidx.room:room-paging:2.5.0-alpha01"
            const val pagingCompose = "androidx.paging:paging-compose:1.0.0-alpha14"
        }


        object Test {
            const val extJunit = "androidx.test.ext:junit:1.1.3"
            const val espresso = "espresso:espresso-core:3.4.0"
        }
    }

    object Accompanist {
        private const val version = "0.23.1"
        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
    }

    object Hilt {
        private const val version = "2.41"

        const val hiltAndroid = "com.google.dagger:hilt-android:$version"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$version"

    }

    object Junit {
        const val junit = "junit:junit:4.13.2"
    }

    object Kotlin{
        object Coroutine{
            private const val version = "1.6.1"
            const val Core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val Android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        }
    }

    object Okhttp {
        const val okhttp = "com.squareup.okhttp3:okhttp:4.9.3"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$version"
        const val coroutinesAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
    }


}