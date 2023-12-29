#include <jni.h>
#include <string>
#include <android/log.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_demoapp_nativedemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_demoapp_nativedemo_MainActivity_makeCrash(JNIEnv *env, jobject thiz) {
    __android_log_print(ANDROID_LOG_INFO,
                        "NativeDemoMain", "Execute makeCrash");
    int *p = nullptr;
    *p = 1;
}