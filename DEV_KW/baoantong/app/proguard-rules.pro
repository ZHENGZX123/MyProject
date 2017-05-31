# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/lixinke/Tool/android-eclipse/adt-bundle-mac-x86_64-20140702/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
##weex
-keep class com.taobao.weex.bridge.**{*;}
-keep class com.taobao.weex.dom.**{*;}
-keep class com.taobao.weex.adapter.**{*;}
-keep class com.taobao.weex.common.**{*;}
-keep class * implements com.taobao.weex.IWXObject{*;}
-keep class com.taobao.weex.ui.**{*;}
-keep class com.taobao.weex.ui.component.**{*;}
-keep class com.taobao.weex.utils.**{
    public <fields>;
    public <methods>;
    }
-keep class com.taobao.weex.view.**{*;}
-keep class com.taobao.weex.module.**{*;}
-keep public class * extends com.taobao.weex.common.WXModule{*;}
-keep public class com.taobao.weex.WXDebugTool{*;}

##bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-dontwarn
-dontskipnonpubliclibraryclassmembers
-ignorewarnings
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


 -keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


# 泛型与反射
-keepattributes Signature
-keepattributes EnclosingMethod
-keepattributes *Annotation*

-keep class com.alibaba.sdk.android.** {*;}
-keep class com.squareup.okhttp.** {*;}
-keep class com.squareup.okhttp3.** {*;}
-keep class com.squareup.retrofit2.** {*;}
-keep class com.jakewharton.** {*;}
-keep class io.reactivex.** {*;}
-keep class rx.** {*;}
-keep class com.jakewharton.rxbinding.** {*;}
-keep class com.facebook.** {*;}
-keep class com.butterknife.** {*;}
-keep class com.google.code.gson.** {*;}
-keep class com.orhanobut.** {*;}
-dontwarn de.greenrobot.daogenerator.**
-keep class de.greenrobot.** {*;}
-keep class com.github.flavienlaurent.datetimepicker.** {*;}
-keep class gun0912.ted.** {*;}
-keep class org.apache.** {*;}