#include "jni.h"
#include "com_leancloud_api_web_function_jni_HelloWorld.h"
#include <stdio.h>
JNIEXPORT void JNICALL Java_com_leancloud_api_web_function_jni_HelloWorld_hello(JNIEnv *env,jobject obj){
    printf("Hello World!\n");
    return;
}


JNIEXPORT jstring JNICALL Java_com_leancloud_api_web_function_jni_HelloWorld_testJni
(JNIEnv *env, jobject obj, jstring param){
  
    return param;
    
}
