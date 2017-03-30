#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/calib3d/calib3d.hpp>
#include <opencv2/stitching/stitcher.hpp>
#include <vector>
#include <iostream>
#include <stdio.h>
#include <list>
#include <sstream>
#include <string>
#include <string.h>
#include "com_example_stitchimage_ImageProc.h"

using namespace std;
using namespace cv;

extern "C"
{
	JNIEXPORT void JNICALL Java_com_example_stitchimage_ImageProc_FindFeatures(JNIEnv*, jobject,jlong image)
	{
		vector<Mat> imgs;
		bool try_use_gpu = false;
		Mat& pano = *((Mat*) image);

		Mat img = imread("/storage/emulated/0/panoTmpImage/snow1.jpg");
			imgs.push_back(img);
			img = imread("/storage/emulated/0/panoTmpImage/snow2.jpg");
			imgs.push_back(img);

		Stitcher stitcher = Stitcher::createDefault(try_use_gpu);
		Stitcher::Status status = stitcher.stitch(imgs, pano);
	}
}
