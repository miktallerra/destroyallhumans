APP_PROJECT_PATH := $(call my-dir)
APP_MODULES      := yuv420sp2rgb imageprocessing ar libjpeg framebuffet
#cflags for the t-mobile g1, make break app on other phones
APP_CFLAGS :=  -march=armv6 -mfloat-abi=softfp -mfpu=vfp

