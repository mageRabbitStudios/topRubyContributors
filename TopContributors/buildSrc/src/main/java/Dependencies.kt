import org.gradle.api.JavaVersion

object Versions {

    const val kotlinVersion = "1.3.21"
    const val coroutinesVersion = "1.0.0"

    const val jacocoVersion = "0.8.1"
    const val androidJunit5Version = "1.2.0.0"
    const val jarTest = "1.0.1"
    const val googleServices = "4.2.0"

    const val javaxInjectVersion = "1"
    const val javaxAnnotationVersion = "1.0"

    const val ktlintVersion = "0.29.0"
    const val ktlintGradlePluginVersion = "1.20.1"
    const val androidXCore = "1.0.0"
    const val archComponentsVersion = "2.0.0"
    const val materialComponentsVersion = "1.0.0"
    const val constraintLayoutVersion = "1.1.2"
    const val navigationVersion = "2.0.0-rc02"

    const val recyclerViewVersion = "1.0.0"

    const val firebaseCore = "16.0.6"
    const val firebaseAuth = "16.1.0"
    const val firebaseFirestore = "17.1.5"
    const val firebaseCrashlytics = "2.9.8"

    const val playServicesAuth = "16.0.1"

    const val daggerVersion = "2.16"

    const val androidGradlePluginVersion = "3.3.2"
    const val supportLibraryVersion = "1.0.0"

    const val picassoVersion = "2.71828"

    const val circularImageViewVersion = "1.1"
    const val biometrics = "1.0.0-alpha04"
    const val insLoadingViewVersion = "1.1.0"

    const val robolectricVersion = "4.1"
    const val androidxTestVersion = "1.1.1"
    const val androidTestRunnerVersion = "1.1.0-alpha4"
    const val uiAutomatorVersion = "2.2.0-alpha4"
    const val junitVersion = "4.12"
    const val mockitoKotlinVersion = "2.0.0"
    const val mockitoVersion = "2.23.0"
    const val assertJVersion = "3.11.1"
    const val assertJAndroidVersion = "1.2.0"
    const val dexOpenerVersion = "2.0.0-alpha01"
    const val espressoVersion = "3.1.1"
    const val androidxJunitVersion = "1.1.0"
    const val androidxFragmentTestingVersion = "1.1.0-alpha06"
    const val androidxTestCoreVersion = "1.0.0"

    const val baristaVersion = "2.8.0"

    const val fabricGradlePluginVersion = "1.28.0"
    const val detektGradlePluginVersion = "1.0.0-RC14"

    const val moshiVersion = "1.8.0"
}

object DefaultConfigurations {
    const val minimumSdkVersion = 21
    const val targetSdkVersion = 28
    const val compileSdkVersion = 28
    const val buildToolsVersion = "28.0.3"
    val sourceCompatibilityVersion = JavaVersion.VERSION_1_8
    val targetCompatibilityVersion = JavaVersion.VERSION_1_8
}

@SuppressWarnings("MaxLineLength")
object ClasspathxDependencies {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePluginVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val jacocoGradlePlugin = "org.jacoco:org.jacoco.core:${Versions.jacocoVersion}"
    const val androidJunit5GradlePlugin = "de.mannodermaus.gradle.plugins:android-junit5:${Versions.androidJunit5Version}"
    const val jarTestGradlePlugin = "gradle.plugin.gradle-plugins:jartest:${Versions.jarTest}"
    const val googleServicesGradlePlugin = "com.google.gms:google-services:${Versions.googleServices}"
    const val fabricGradlePlugin = "io.fabric.tools:gradle:${Versions.fabricGradlePluginVersion}"
    const val detektGradlePlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detektGradlePluginVersion}"
    const val ktlintGradlePlugin = "org.jmailen.gradle:kotlinter-gradle:${Versions.ktlintGradlePluginVersion}"
}

@SuppressWarnings("MaxLineLength")
object Dependencies {

    // Kotlin
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroidExtension = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"

    // Support Libraries
    const val appCompatsupportLibrary = "androidx.appcompat:appcompat:${Versions.supportLibraryVersion}"
    const val materialComponents = "com.google.android.material:material:${Versions.materialComponentsVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"
    const val androidXCore = "androidx.core:core-ktx:${Versions.androidXCore}"

    // Architecture Components
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.archComponentsVersion}"
    // lifeCycleExtension already contains LiveData & ViewModel dependencies
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.archComponentsVersion}"

    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    // Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.daggerVersion}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.daggerVersion}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.daggerVersion}"
    const val daggerAndroidCompiler = "com.google.dagger:dagger-android-processor:${Versions.daggerVersion}"

    // JavaX
    const val javaxInject = "javax.inject:javax.inject:${Versions.javaxInjectVersion}"
    const val javaxAnnotation = "javax.annotation:jsr250-api:${Versions.javaxAnnotationVersion}"

    // Ktlint
    const val ktlint = "com.github.shyiko:ktlint:${Versions.ktlintVersion}"

    // Moshi
    const val moshiKotlin = "com.squareup.moshi:moshi-adapters:${Versions.moshiVersion}"
    const val moshiAdapters = "com.squareup.moshi:moshi-kotlin:${Versions.moshiVersion}"
}

@SuppressWarnings("MaxLineLength")
object TestDependencies {
    // Unit Testing
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlinVersion}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoVersion}"
    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoVersion}"
    const val assertJ = "org.assertj:assertj-core:${Versions.assertJVersion}"
    const val testSupportRunner = "androidx.test:runner:${Versions.androidxTestVersion}"
    const val androidxTestRules = "androidx.test:rules:${Versions.androidxTestVersion}"
    const val androidxTestCore = "androidx.test:core:${Versions.androidxTestCoreVersion}"
    const val lifecycleTestHelpers = "androidx.arch.core:core-testing:${Versions.archComponentsVersion}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectricVersion}"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espressoVersion}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espressoVersion}"
    const val androidxJunit = "androidx.test.ext:junit:${Versions.androidxJunitVersion}" // Holds non deprecated AndroidJUnit4 runner
    const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archComponentsVersion}"
    const val androidxFragmentTesting = "androidx.fragment:fragment-testing:${Versions.androidxFragmentTestingVersion}"
    const val barista = "com.schibsted.spain:barista:${Versions.baristaVersion}"
}

@SuppressWarnings("MaxLineLength")
object AndroidTestDependencies {
    const val assertJAndroid = "com.squareup.assertj:assertj-android:${Versions.assertJAndroidVersion}"
    const val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunnerVersion}"
    const val androidTestRules = "androidx.test:rules:${Versions.androidTestRunnerVersion}"
    const val uiAutomator = "androidx.test.uiautomator:uiautomator:${Versions.uiAutomatorVersion}"
    const val lifecycleTestHelpers = "androidx.arch.core:core-testing:${Versions.archComponentsVersion}"
    const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockitoVersion}"
    const val dexOpener = "com.github.tmurakami:dexopener:${Versions.dexOpenerVersion}"
}