#include <jni.h>
#include <android/log.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL
printHello(JNIEnv *env, jclass clazz) {
    __android_log_print(ANDROID_LOG_INFO, "Native Output", "Hello World!");
}

JNIEXPORT void JNICALL
printString(JNIEnv *env, jclass clazz, jstring str) {
    //将Java String转换为C字符串
    const char *out = env->GetStringUTFChars(str, 0);
    __android_log_print(ANDROID_LOG_INFO, "Native Output", "%s", out);
}

JNIEXPORT jstring JNICALL
getJniString(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("返回参数：Native->APP");
}

// 声明待注册方法所在类的路径
static const char *mClassName = "com/demoapp/dynamicregdemo/HelloJNI";

/**
 * JNINativeMethod结构体
 * 包含：name-Java方法名；signature-方法签名(描述返回值和入参)；fnPtr-C中实现的函数指针
 * 方法签名格式：()内为表示函数参数，后面的表示返回值
 */
static const JNINativeMethod mMethod[] = {
        {"printHello",   "()V",                   (void *) printHello},
        {"printString",  "(Ljava/lang/String;)V", (void *) printString},
        {"getJniString", "()Ljava/lang/String;",  (void *) getJniString}
};

/**
 *  JNI_OnLoad方法实现
 *  在java中执行System.loadLibrary("libname")时候加载完libname动态库后会调用此方法
 * @param vm JNIInvokeInterface_结构体的二级指针（*vm是JNIInvokeInterface_的一级指针因为JavaVM本来就是个一级指针类型
 * @param reserved unknow
 * @return
 */
JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = nullptr;

    /*
     * 调用GetEnv函数获得JNIEnv指针
     * version表示告诉JVM该组件使用哪一个jni版本（若未提供JNI_Onload函数，JVM会默认使用最老的JNI1.1版本）
     * 如果要使用新的版本的JNI，如JNI 1.4版本，
     * 则必须由JNI_OnLoad()函数返回常量JNI_VERSION_1_4(该常量定义在jni.h中)来告知JVM
     */
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        __android_log_print(ANDROID_LOG_INFO, "Native Output", "failed in vm->GetEnv");
        return JNI_ERR;
    }

    // 调用FindClass函数获取class的地址引用
    jclass mClass = env->FindClass(mClassName);

    //获取所有的函数的个数
    jint size = sizeof(mMethod) / sizeof(JNINativeMethod);

    // 通过RegisterNatives方法把C/C++中的方法映射到Java中的native方法
    if (env->RegisterNatives(mClass, mMethod, size) != JNI_OK) {
        __android_log_print(ANDROID_LOG_INFO, "Native Output", "failed in env->RegisterNatives");
        return JNI_ERR;
    }
    return JNI_VERSION_1_6;
} ;
#ifdef __cplusplus
}
#endif