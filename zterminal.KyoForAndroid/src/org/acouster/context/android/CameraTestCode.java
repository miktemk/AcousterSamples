package org.acouster.context.android;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

public class CameraTestCode
{
	private Activity activity;
    private Preview canvas;
    private Camera cam;
    
    public CameraTestCode(Activity activity)
    {
		this.activity = activity;
		
		canvas = new Preview(activity);
		startCamera(canvas, 0);
	}
    
	public View getPreviewPanel()
	{
		return canvas;
	}
	public Camera getCamera()
	{
		return cam;
	}
	
//	public String[] getCameraList()
//	{
//		int nCams = Camera.getNumberOfCameras();
//		CameraInfo info = new CameraInfo();
//		Vector<String> names = new Vector<String>();
//		for (int i = 0; i < nCams; i++)
//		{
//			Camera.getCameraInfo(i, info);
//			if (info.facing == CameraInfo.CAMERA_FACING_BACK)
//				names.add(CAMERA_NAME_BACK);
//			else if (info.facing == CameraInfo.CAMERA_FACING_FRONT)
//				names.add(CAMERA_NAME_FRONT);
//		}
//		return names.toArray(new String[names.size()]);
//	}
//	
	public void startCamera(Preview mPreview, int cameraId)
	{
		//Obtain an instance of Camera from open(int).
		cam = Camera.open(cameraId);
		//ContextCamera contextCam = new AndroidContextCamera(cam);
		//Get existing (default) settings with getParameters().
		Parameters params = cam.getParameters();
		//If necessary, modify the returned Camera.Parameters object and call setParameters(Camera.Parameters).
		params.setJpegQuality(80);
		//params.setPreviewSize(200, 150);
		//If desired, call setDisplayOrientation(int).
		cam.setParameters(params);
		//canvas.attachCameraToCanvas(contextCam);
		//Camera cam = androidContextCam.cam;
		mPreview.setCamera(cam);
		//Important: Call startPreview() to start updating the preview surface. Preview must be started before you can take a picture.
		cam.startPreview();
	}
	
	
	
	//===================================================================

	/**
	 * A simple wrapper around a Camera and a SurfaceView that renders a centered preview of the Camera
	 * to the surface. We need to center the SurfaceView because not all devices have cameras that
	 * support preview sizes at the same aspect ratio as the device's display.
	 */
	class Preview extends ViewGroup implements SurfaceHolder.Callback {
	    private final String TAG = "Preview";

	    SurfaceView mSurfaceView;
	    SurfaceHolder mHolder;
	    Size mPreviewSize;
	    List<Size> mSupportedPreviewSizes;
	    Camera mCamera;

	    Preview(Context context) {
	        super(context);

	        mSurfaceView = new SurfaceView(context);
	        addView(mSurfaceView);

	        // Install a SurfaceHolder.Callback so we get notified when the
	        // underlying surface is created and destroyed.
	        mHolder = mSurfaceView.getHolder();
	        mHolder.addCallback(this);
	        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	    }

	    public void setCamera(Camera camera) {
	        mCamera = camera;
	        if (mCamera != null) {
	            mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
	            requestLayout();
	        }
	    }

	    public void switchCamera(Camera camera) {
	       setCamera(camera);
	       try {
	           camera.setPreviewDisplay(mHolder);
	       } catch (IOException exception) {
	           Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
	       }
	       Camera.Parameters parameters = camera.getParameters();
	       parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
	       requestLayout();

	       camera.setParameters(parameters);
	    }

	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        // We purposely disregard child measurements because act as a
	        // wrapper to a SurfaceView that centers the camera preview instead
	        // of stretching it.
	        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
	        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
	        setMeasuredDimension(width, height);

	        if (mSupportedPreviewSizes != null) {
	            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
	        }
	    }

	    @Override
	    protected void onLayout(boolean changed, int l, int t, int r, int b) {
	    	Log.v("======>", "l: " + l);
	    	Log.v("======>", "t: " + t);
	    	Log.v("======>", "r: " + r);
	    	Log.v("======>", "b: " + b);
	        //if (changed && getChildCount() > 0) {
	    	{
	            final View child = getChildAt(0);

	            final int width = r - l;
	            final int height = b - t;

	            int previewWidth = width;
	            int previewHeight = height;
	            if (mPreviewSize != null) {
	                previewWidth = mPreviewSize.width;
	                previewHeight = mPreviewSize.height;
	            }

	            // Center the child SurfaceView within the parent.
	            if (width * previewHeight > height * previewWidth) {
	                final int scaledChildWidth = previewWidth * height / previewHeight;
	                child.layout((width - scaledChildWidth) / 2, 0,
	                        (width + scaledChildWidth) / 2, height);
	            } else {
	                final int scaledChildHeight = previewHeight * width / previewWidth;
	                child.layout(0, (height - scaledChildHeight) / 2,
	                        width, (height + scaledChildHeight) / 2);
	            }
	            

		        Log.v("======>", "width: " + child.getWidth());
		    	Log.v("======>", "height: " + child.getHeight());
	        }
	        
	    }

	    public void surfaceCreated(SurfaceHolder holder) {
	        // The Surface has been created, acquire the camera and tell it where
	        // to draw.
	        try {
	            if (mCamera != null) {
	                mCamera.setPreviewDisplay(holder);
	            }
	        } catch (IOException exception) {
	            Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
	        }
	    }

	    public void surfaceDestroyed(SurfaceHolder holder) {
	        // Surface will be destroyed when we return, so stop the preview.
	        if (mCamera != null) {
	            mCamera.stopPreview();
	        }
	    }


	    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
	        final double ASPECT_TOLERANCE = 0.1;
	        double targetRatio = (double) w / h;
	        if (sizes == null) return null;

	        Size optimalSize = null;
	        double minDiff = Double.MAX_VALUE;

	        int targetHeight = h;

	        // Try to find an size match aspect ratio and size
	        for (Size size : sizes) {
	            double ratio = (double) size.width / size.height;
	            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
	            if (Math.abs(size.height - targetHeight) < minDiff) {
	                optimalSize = size;
	                minDiff = Math.abs(size.height - targetHeight);
	            }
	        }

	        // Cannot find the one match the aspect ratio, ignore the requirement
	        if (optimalSize == null) {
	            minDiff = Double.MAX_VALUE;
	            for (Size size : sizes) {
	                if (Math.abs(size.height - targetHeight) < minDiff) {
	                    optimalSize = size;
	                    minDiff = Math.abs(size.height - targetHeight);
	                }
	            }
	        }
	        return optimalSize;
	    }

	    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
	        // Now that the size is known, set up the camera parameters and begin
	        // the preview.
	        Camera.Parameters parameters = mCamera.getParameters();
	        parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
	        requestLayout();

	        mCamera.setParameters(parameters);
	        mCamera.startPreview();
	    }

	}
	
}
