# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



# Disable Log in release build.
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
    public static int wtf(...);
}

# IMPLEMENTATIONS
# ------------------------
# General Android Keep Rules
# ------------------------

# NOTE: Keep Application class
-keep class com.fera.hymn.** { *; }




# ------------------------
# NOTE: Dto
# Ask chatGPT about: Enum and it's values, etc..
# ------------------------

# Keep ViewBinding generated classes. NOTE: binding also increase size.
-keep class **.databinding.*Binding { *; }



# Prevent obfuscation of resource identifiers
-keepclassmembers class **.R$* {
    public static <fields>;
}



# ------------------------
# Glide
# ------------------------

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public class * extends com.bumptech.glide.module.LibraryGlideModule
-keep class com.bumptech.glide.** { *; }
-keep class com.bumptech.glide.integration.** { *; }

# ------------------------
# Room (AndroidX Room with KSP)
# ------------------------

-keep class androidx.room.** { *; }
-keep class **Database_Impl { *; }
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# ------------------------
# SQLCipher
# ------------------------

-keep class net.sqlcipher.** { *; }
-dontwarn net.sqlcipher.**

# ------------------------
# Lifecycle ViewModel, LiveData, Runtime
# ------------------------

-keep class androidx.lifecycle.** { *; }
-keep class androidx.lifecycle.DefaultLifecycleObserver { *; }
-keep class androidx.lifecycle.DefaultLifecycleObserver** { *; }



# ------------------------
# Kotlin Coroutines
# ------------------------

-dontwarn kotlinx.coroutines.**
-keep class kotlinx.coroutines.** { *; }


# ------------------------
# Keep BuildConfig fields
# ------------------------

-keep class **.BuildConfig {
    public static final *** *;
}
