
cmake_minimum_required(VERSION 3.22.1)
project("JNISample")
add_library(
        # Sets the name of the library.
        JNISample

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        jni-utils.c)

find_library(
        # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

target_link_libraries(
        # Specifies the target library.
        JNISample

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})