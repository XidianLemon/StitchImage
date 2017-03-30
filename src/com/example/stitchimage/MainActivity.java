package com.example.stitchimage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Mat panorama;
	private static final File StitchImageDir = new File(Environment.getExternalStorageDirectory()+ File.separator  + "panoStitchIm");
	private static final String mImageExt = ".jpg";
	
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {
				System.loadLibrary("native_sample");
			}
				break;
			default: {
				super.onManagerConnected(status);
			}
				break;
			}
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button btnStitch = (Button) findViewById(R.id.btnStitch);
		btnStitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	stitchImages();
            }
        });
	}
	
	public void stitchImages() {
		int height = 0;
		int width = 0;
		panorama = new Mat(height, width, CvType.CV_8UC3);
		ImageProc.FindFeatures(panorama.getNativeObjAddr());
		writePano(panorama);
	}

	private void writePano(Mat image){
		Date dateNow = new  Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		if(!StitchImageDir.exists())
			StitchImageDir.mkdir();
		Highgui.imwrite(StitchImageDir.getPath()+ File.separator + "panoStich"+dateFormat.format(dateNow) +mImageExt, image);
	}

	@Override
	public void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this,
				mLoaderCallback);
	}
}
