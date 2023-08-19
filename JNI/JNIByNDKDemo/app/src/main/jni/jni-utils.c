#include "com_demoapp_jnibyndkdemo_HelloJNI.h"
#include <stdio.h>
#include <android/log.h>

#ifdef __cplusplus
extern "C" {
#endif

//添加名称为env与obj的两个参数
JNIEXPORT void JNICALL Java_com_demoapp_jnibyndkdemo_HelloJNI_printHello
        (JNIEnv *env, jclass obj) {
    __android_log_print(ANDROID_LOG_INFO,
                        "Native Output", "Hello World!");
}

JNIEXPORT void JNICALL Java_com_demoapp_jnibyndkdemo_HelloJNI_printString
        (JNIEnv *env, jclass obj, jstring string) {
    //将Java String转换为C字符串
    const char *str = (*env)->GetStringUTFChars(env, string, 0);
    // C++: env->GetStringUTFChars(string, 0);
    __android_log_print(ANDROID_LOG_INFO,
                        "Native Output", "%s", str);
}

JNIEXPORT jstring JNICALL Java_com_demoapp_jnibyndkdemo_HelloJNI_getJniString
        (JNIEnv *env, jclass obj) {
    return (*env)->NewStringUTF(env, "返回参数：Native->APP");
    // C++: env->NewStringUTF("Hello World");
}

#ifdef __cplusplus
}
#endif