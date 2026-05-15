# Add project specific ProGuard rules here.
# Keep rules for Jetpack Compose & common Firebase SDK classes.

# AndroidX/Compose basics
-keep class androidx.** { *; }
-dontwarn androidx.**

# Firebase (avoid stripping of Firestore/Storage internal reflection usage)
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

# Jetpack Compose (needed for tooling/runtime)
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Coil (optional: keep model metadata)
-dontwarn coil.**

# Coroutines
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**


