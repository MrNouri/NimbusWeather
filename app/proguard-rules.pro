# Keep line numbers for stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Kotlin Serialization
-keepattributes *Annotation*
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
}
-keep class academy.nouri.nimbus.data.remote.model.** { *; }

# Ktor
-keep class io.ktor.** { *; }
-keepclassmembers class io.ktor.** { volatile <fields>; }
-dontwarn io.ktor.**

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Koin
-keep class org.koin.** { *; }
-keep class academy.nouri.nimbus.di.** { *; }

# Coil
-keep class coil3.** { *; }
-dontwarn coil3.**

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }

# DataStore
-keep class * extends com.google.protobuf.GeneratedMessageLite { *; }
