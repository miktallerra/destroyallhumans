package com.atg.netcat;

import java.net.InetAddress;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import edu.dhbw.andopenglcam.CameraHolder;
import edu.dhbw.andopenglcam.CameraPreviewHandler;
import edu.dhbw.andopenglcam.MarkerInfo;
import edu.dhbw.andopenglcam.OpenGLCamRenderer;
import edu.dhbw.andopenglcam.OpenGLCamView;

public class Receiver extends Activity implements Callback
{
  public static Integer     controlPort   = 5555;

  public static Integer     videoPort     = 4444;

  public static InetAddress clientAddress;

  private int               sleepTime     = 200;

  private IPCommThread      ipComThread   = null;

  protected String          tostText      = "";

  protected String          logText       = "";

  public static Boolean     highQuality   = false;

  boolean                   stopListening = false;

  RobotState                state;

  private static Context    CONTEXT;

  // private Handler handler;
  private BTCommThread      bTcomThread;

  private ProgressDialog    btDialog;

  private ProgressDialog    ipDialog;
  
  private GLSurfaceView glSurfaceView;
  private Camera camera;
  private OpenGLCamRenderer renderer;
  private Resources res;
  private CameraPreviewHandler cameraHandler;
  private boolean mPreviewing = false;
  private boolean mPausing = false;
  private MarkerInfo markerInfo = new MarkerInfo();
  

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setFullscreen();
    disableScreenTurnOff();
    //orientation is set via the manifest

    res = getResources();     
    glSurfaceView = new OpenGLCamView(this);
    renderer = new OpenGLCamRenderer(res, markerInfo);
    cameraHandler = new CameraPreviewHandler(glSurfaceView, renderer, res, markerInfo);
    glSurfaceView.setRenderer(renderer);
    glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    glSurfaceView.getHolder().addCallback(this);
    setContentView(glSurfaceView);   
    
    CONTEXT = Receiver.this;
    state = new RobotState(handler);
    
    

  }
  
  private Handler handler = new Handler()
  {

    @Override
    public void handleMessage(Message msg)
    {
      state.flush(ipComThread, bTcomThread);
    }

  };

  @Override
  public void onStart()
  {
    super.onStart();
    startListening();
  }

  @Override
  public void onStop()
  {
    super.onStart();
    stopListening();
  }

  @Override
  protected void onPause()
  {
    // TODO Auto-generated method stub
    mPausing = true;
    this.glSurfaceView.onPause();
    super.onPause();
    // stopListening();

  }

  private void startListening()
  {

    String msg = "Listening on port " + controlPort + " for control server";

    ipDialog = ProgressDialog.show(this, "Current IP:" + state.getLocalIpAddress(), msg);

    ipComThread = new IPCommThread(controlPort, ipDialog, state);
    ipComThread.start();

    btDialog = ProgressDialog.show(this, "Connecting", "Searching for a Bluetooth serial port...");
    bTcomThread = new BTCommThread(BluetoothAdapter.getDefaultAdapter(), btDialog, state);
    bTcomThread.start();

    if (OrientationManager.isSupported())
    {
      OrientationManager.startListening(state);
    }

  }

  private void stopListening()
  {

    if (OrientationManager.isListening())
    {
      OrientationManager.stopListening();
    }

    if (ipDialog != null && ipDialog.isShowing())
      ipDialog.dismiss();

    if (ipComThread != null)
      ipComThread.cancel();
    ipComThread = null;

    if (btDialog != null && btDialog.isShowing())
      btDialog.dismiss();

    if (bTcomThread != null)
      bTcomThread.cancel();
    bTcomThread = null;
  }



  public static Context getContext()
  {
    return CONTEXT;
  }
  
  public void disableScreenTurnOff() {
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
}

public void setOrientation()  {
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
}

public void setFullscreen() {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
}

public void setNoTitle() {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
} 


@Override
protected void onResume() {
    mPausing = false;
    glSurfaceView.onResume();
    super.onResume();
}



private void openCamera()  {
    if (camera == null) {
        //camera = Camera.open();
        camera = CameraHolder.instance().open();
        Parameters params = camera.getParameters();
        params.setPreviewSize(240,160);
        //params.setPreviewFrameRate(1);//TODO remove restriction
        camera.setParameters(params);
        camera.setPreviewCallback(cameraHandler);
        ///camera.setOneShotPreviewCallback(cameraHandler);
        try {
            cameraHandler.init(camera);
        } catch (Exception e) {
            //TODO: notify the user
        }
    }
}

private void closeCamera() {
    if (camera != null) {
        CameraHolder.instance().keep();
        CameraHolder.instance().release();
        camera = null;
        mPreviewing = false;
    }
}

private void startPreview() {
    if(mPausing) return;
    if (mPreviewing) stopPreview();
    openCamera();
    camera.startPreview();
    mPreviewing = true;
}

private void stopPreview() {
    if (camera != null && mPreviewing) {
        camera.stopPreview();
    }
    mPreviewing = false;
}

/* The GLSurfaceView changed
 * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
 */
//@Override
public void surfaceChanged(SurfaceHolder holder, int format, int width,
        int height) {
}

/* The GLSurfaceView was created
 * The camera will be opened and the preview started 
 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
 */
//@Override
public void surfaceCreated(SurfaceHolder holder) {
    if(!mPreviewing)
        startPreview();  
}

/* GLSurfaceView was destroyed
 * The camera will be closed and the preview stopped.
 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
 */
//@Override
public void surfaceDestroyed(SurfaceHolder holder) {
    stopPreview();
    closeCamera();
}

/* (non-Javadoc)
 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
 */
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(0, CameraPreviewHandler.MODE_RGB, 0, res.getText(R.string.mode_rgb));
    menu.add(0, CameraPreviewHandler.MODE_GRAY, 0, res.getText(R.string.mode_gray));
    menu.add(0, CameraPreviewHandler.MODE_BIN, 0, res.getText(R.string.mode_bin));
    menu.add(0, CameraPreviewHandler.MODE_EDGE, 0, res.getText(R.string.mode_edges));
    menu.add(0, CameraPreviewHandler.MODE_CONTOUR, 0, res.getText(R.string.mode_contours));   
    return true;
}

/* (non-Javadoc)
 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
 */
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    this.cameraHandler.setMode(item.getItemId());
    return true;
}



}