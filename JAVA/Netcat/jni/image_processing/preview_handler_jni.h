/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class edu_dhbw_andopenglcam_CameraPreviewHandler */

#ifndef _Included_edu_dhbw_andopenglcam_CameraPreviewHandler
#define _Included_edu_dhbw_andopenglcam_CameraPreviewHandler
#ifdef __cplusplus
extern "C" {
#endif
#undef edu_dhbw_andopenglcam_CameraPreviewHandler_MODE_RGB
#define edu_dhbw_andopenglcam_CameraPreviewHandler_MODE_RGB 0L
#undef edu_dhbw_andopenglcam_CameraPreviewHandler_MODE_GRAY
#define edu_dhbw_andopenglcam_CameraPreviewHandler_MODE_GRAY 1L
#undef edu_dhbw_andopenglcam_CameraPreviewHandler_MODE_BIN
#define edu_dhbw_andopenglcam_CameraPreviewHandler_MODE_BIN 2L
#undef edu_dhbw_andopenglcam_CameraPreviewHandler_MODE_EDGE
#define edu_dhbw_andopenglcam_CameraPreviewHandler_MODE_EDGE 3L
#undef edu_dhbw_andopenglcam_CameraPreviewHandler_MODE_CONTOUR
#define edu_dhbw_andopenglcam_CameraPreviewHandler_MODE_CONTOUR 4L
/*
 * Class:     edu_dhbw_andopenglcam_CameraPreviewHandler
 * Method:    detectTargetBlob
 * Signature: ([BIIIIILcom/atg/netcat/TargetBlob;)I
 */
JNIEXPORT jint JNICALL Java_edu_dhbw_andopenglcam_CameraPreviewHandler_detectTargetBlob
  (JNIEnv *, jobject, jbyteArray, jint, jint, jint, jint, jint, jobject);

/*
 * Class:     edu_dhbw_andopenglcam_CameraPreviewHandler
 * Method:    getAvgColor
 * Signature: ([BIIIIILcom/atg/netcat/TargetBlob;)I
 */
JNIEXPORT jint JNICALL Java_edu_dhbw_andopenglcam_CameraPreviewHandler_getAvgColor
  (JNIEnv *, jobject, jbyteArray, jint, jint, jint, jobject);

/*
 * Class:     edu_dhbw_andopenglcam_CameraPreviewHandler
 * Method:    setupJPEG
 * Signature: ([BIIII)I
 */
JNIEXPORT jint JNICALL Java_edu_dhbw_andopenglcam_CameraPreviewHandler_setupJPEG
  (JNIEnv *, jobject, jbyteArray, jint, jint, jint, jint);

/*
 * Class:     edu_dhbw_andopenglcam_CameraPreviewHandler
 * Method:    sendJPEG
 * Signature: ([B)I
 */
JNIEXPORT jint JNICALL Java_edu_dhbw_andopenglcam_CameraPreviewHandler_sendJPEG
  (JNIEnv *, jobject, jbyteArray);

/*
 * Class:     edu_dhbw_andopenglcam_CameraPreviewHandler
 * Method:    yuv420sp2rgb
 * Signature: ([BIII[B)V
 */
JNIEXPORT void JNICALL Java_edu_dhbw_andopenglcam_CameraPreviewHandler_yuv420sp2rgb
  (JNIEnv *, jobject, jbyteArray, jint, jint, jint, jbyteArray);

/*
 * Class:     edu_dhbw_andopenglcam_CameraPreviewHandler
 * Method:    binarize
 * Signature: ([BII[BI)V
 */
JNIEXPORT void JNICALL Java_edu_dhbw_andopenglcam_CameraPreviewHandler_binarize
  (JNIEnv *, jobject, jbyteArray, jint, jint, jbyteArray, jint);

/*
 * Class:     edu_dhbw_andopenglcam_CameraPreviewHandler
 * Method:    detect_edges
 * Signature: ([BII[BI)V
 */
JNIEXPORT void JNICALL Java_edu_dhbw_andopenglcam_CameraPreviewHandler_detect_1edges
  (JNIEnv *, jobject, jbyteArray, jint, jint, jbyteArray, jint);

/*
 * Class:     edu_dhbw_andopenglcam_CameraPreviewHandler
 * Method:    detect_edges_simple
 * Signature: ([BII[BI)V
 */
JNIEXPORT void JNICALL Java_edu_dhbw_andopenglcam_CameraPreviewHandler_detect_1edges_1simple
  (JNIEnv *, jobject, jbyteArray, jint, jint, jbyteArray, jint);

#ifdef __cplusplus
}
#endif
#endif
/* Header for class edu_dhbw_andopenglcam_CameraPreviewHandler_ConversionWorker */

#ifndef _Included_edu_dhbw_andopenglcam_CameraPreviewHandler_ConversionWorker
#define _Included_edu_dhbw_andopenglcam_CameraPreviewHandler_ConversionWorker
#ifdef __cplusplus
extern "C" {
#endif
#undef edu_dhbw_andopenglcam_CameraPreviewHandler_ConversionWorker_MIN_PRIORITY
#define edu_dhbw_andopenglcam_CameraPreviewHandler_ConversionWorker_MIN_PRIORITY 1L
#undef edu_dhbw_andopenglcam_CameraPreviewHandler_ConversionWorker_NORM_PRIORITY
#define edu_dhbw_andopenglcam_CameraPreviewHandler_ConversionWorker_NORM_PRIORITY 5L
#undef edu_dhbw_andopenglcam_CameraPreviewHandler_ConversionWorker_MAX_PRIORITY
#define edu_dhbw_andopenglcam_CameraPreviewHandler_ConversionWorker_MAX_PRIORITY 10L
#ifdef __cplusplus
}
#endif
#endif
