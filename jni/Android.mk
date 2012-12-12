LOCAL_PATH := $(call my-dir)

include $(LOCAL_PATH)/external/libffi.mk
include $(LOCAL_PATH)/external/wayland.mk

WAYLAND_JNI_UTIL_SRC := \
	src/object.c \
	src/wayland-jni.c

WAYLAND_JNI_SERVER_SRC := $(WAYLAND_JNI_UTIL_SRC) \
	src/server/display.c \
	src/server/client.c \
	src/server/event_loop.c \
	src/server/resource.c

include $(CLEAR_VARS)

LOCAL_MODULE 			:= libwayland-java-server
LOCAL_CFLAGS			:= -I$(LOCAL_PATH)/src
LOCAL_SRC_FILES 		:= $(WAYLAND_JNI_SERVER_SRC)
LOCAL_STATIC_LIBRARIES	:= libwayland-server

include $(BUILD_SHARED_LIBRARY)

