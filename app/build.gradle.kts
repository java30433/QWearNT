import bakuen.plugin.apkhook.apkHook

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("org.jetbrains.kotlin.kapt")
    id("bakuen.plugin.apkhook") apply true
}

android {
    namespace = "com.tencent.qqlite"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tencent.qqlite"
        minSdk = 19
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    androidResources {
        additionalParameters.add("--package-id")
        additionalParameters.add("0x7f")
        val publicTxtFile = project.file("src/main/res/public.txt")
        if (publicTxtFile.exists()) {
            project.logger.error("$publicTxtFile exists, apply it.")
            //aapt2添加--stable-ids参数应用
            additionalParameters.add("--stable-ids")
            additionalParameters.add(publicTxtFile.toString())
        } else {
            project.logger.error("$publicTxtFile not exists")
        }
    }
}

apkHook {
    resources {
        replace("layout","new_input_method_layout.xml")
        replace("layout","item_contact.xml")
        replace("layout","item_recent_contact.xml")
        replace("drawable","bg_chat_list_item.xml")
        replace("drawable","icon_feed_more.png")
        replace("drawable","qq_select_hook.png")
        replace("layout","fragment_self_close_account.xml")
        /*replace("drawable","webp","bg", "bg_blue")
        replace("drawable","webp","bg", "bg_blue2white")
        replace("drawable","webp","bg", "bg_green2white")
        replace("drawable","webp","bg", "bg_red")
        replace("drawable","webp","bg", "bg_yellow")
        replace("drawable","webp","bg", "bg_green")
        replace("drawable","webp","bg", "bg_pink")
        replace("drawable","webp","bg", "bg")*/
    }
    smali("com/tencent/qqnt/watch/contact/data/ContactVM.smali") {
        replaceLine(377, "") //删除加好友方式中的碰一碰
    }
    //移除final使其可继承
    smali("com/tencent/qqnt/watch/ui/componet/AbsItem.smali") {
        replaceStr(".method public final", ".method public")
    }
}

dependencies {

    implementation(libs.androidx.constraintlayout)
    // https://mvnrepository.com/artifact/androidx.fragment/fragment
    implementation("androidx.fragment:fragment:1.4.0")

    implementation(project(":JustApkHook-annotation"))
    kapt(project(":JustApkHook-annotation-compiler"))
}