package com.hiar.sdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hiar.sdk.camera.CameraHolder;
import com.hiar.sdk.camera.CameraParameters;
import com.hiar.sdk.camera.CameraPreviewHandler;
import com.hiar.sdk.camera.CameraStatus;
import com.hiar.sdk.renderer.ContextFactory;
import com.hiar.sdk.renderer.HSCameraRenderer;
import com.hiar.sdk.renderer.HSRenderer;
import com.hiar.sdk.renderer.WindowSurfaceFactory;
import com.hiar.sdk.utils.FilePath;

import java.io.File;

public class HiARSDKForSuningActivity extends Activity {

    public FrameLayout hsFrame;
    public GLSurfaceView glSurfaceView;
    public GLSurfaceView cameraSurfaceView;
    private HSRenderer mRenderer;
    private HSCameraRenderer mCameraRenderer;
    private ContextFactory mContextFactory;
    private WindowSurfaceFactory mWindowSurfaceFactory;
    public static Context context;

    protected HSARToolkit hsarToolkit;

    private Camera camera;
    private CameraPreviewHandler cameraHandler;
    private CameraStatus camStatus = new CameraStatus();
    private TextView logView;
    private AssetManager assetManager;

    private RelativeLayout mScanLayout;
    private ImageView mScanBg;

    private RelativeLayout mDialogSuccess;
    private RelativeLayout mDialogFail;
    private RelativeLayout mDialogShare;
    private RelativeLayout mDialogSuccessBg;
    private RelativeLayout mDialogFailBg;
    private RelativeLayout mDialogShareBg;

    private ImageView mCatchCount;
    private ImageView mConstellation;

    private ImageButton mBtnBack;

    private ImageButton mBtnCatch;
    private ImageButton mIconCatch;

    private ImageButton mBtnWatch;
    private ImageButton mBtnShare;

    private ImageButton mBtnSuccessClose;
    private ImageButton mBtnSuccessWatch;
    private ImageButton mBtnSuccessCatch;

    private ImageButton mBtnFailClose;
    private ImageButton mBtnFailCatch;

    private ImageButton mBtnShareClose;
    private ImageButton mBtnShareShare;

    private TextView mCountView;
    private TextView mPrompt;
    private TextView mCardName;
    private TextView mFailPrompt;
    private TextView mShareLine1;
    private TextView mShareLine2;

    private boolean mCheckResource;

    private boolean mIsInitFinished = false;// added by zqsong for onPause crash bug.

    //private int mCountViewPaddingRight = 0;

    // added by zqsong 2016.9.22
    public static HiARSDKForSuningState mParam = new HiARSDKForSuningState();
    public static HiARSDKForSuningActivityListener mListener = null;

    // messages from invoker
    final protected static int MSGID_BASE = 100;
    final public static int MSGID_CATCH_RESULT = MSGID_BASE + 1;
    final public static int MSGID_TOTAL_COUNT_CHANGED = MSGID_BASE + 2;
    final public static int MSGID_USED_COUNT_CHANGED = MSGID_BASE + 3;
    final public static int MSGID_EXIT_AR = MSGID_BASE + 4;
    final public static int MSGID_SHARE_DISABLE = MSGID_BASE + 5;
    final public static int MSGID_SHARE_ENABLE = MSGID_BASE + 6;
    // catch result
    final public static int CATCH_RESULT_OK = 1;
    final public static int CATCH_RESULT_FAIL = 0;

    protected class HiARSDKForSuningHandler extends Handler {
        private HiARSDKForSuningActivity mActivity = null;

        public HiARSDKForSuningHandler(HiARSDKForSuningActivity activity) {
            this.mActivity = activity;
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            this.mActivity.handleMessage(msg);
        }
    }

    protected HiARSDKForSuningHandler mHandler = new HiARSDKForSuningHandler(this);

    protected void handleMessage(android.os.Message msg) {
        switch (msg.what) {
            case MSGID_CATCH_RESULT:
                onMsgCatchResult(msg);
                break;
            case MSGID_TOTAL_COUNT_CHANGED:
                onMsgTotalCountChanged(msg);
                break;
            case MSGID_USED_COUNT_CHANGED:
                onMsgUsedCountChanged(msg);
                break;
            case MSGID_EXIT_AR:
                onMsgExitAR(msg);
                break;
            case MSGID_SHARE_DISABLE:
                onShareDisable(msg);
                break;
            case MSGID_SHARE_ENABLE:
                onShareEnable(msg);
                break;
        }
    }

    protected void onMsgCatchResult(android.os.Message msg) {
        stopAllClip();
        if (CATCH_RESULT_OK == msg.arg1) {
            playClip("success");
            mDialogSuccess.setVisibility(View.VISIBLE);
        } else if (CATCH_RESULT_FAIL == msg.arg1) {
            mFailPrompt.setText((String) msg.obj);
            playClip("fail");
            mDialogFail.setVisibility(View.VISIBLE);
        } else {
            mFailPrompt.setText("别灰心还有机会");
            String str = "unknown arg1 code" + msg.arg1;
            playClip("fail");
            mDialogFail.setVisibility(View.VISIBLE);
        }
    }

    protected void onMsgTotalCountChanged(android.os.Message msg) {
        mParam.setTotalCatchCount(msg.arg1);
        updateCountView();
    }

    protected void onMsgUsedCountChanged(android.os.Message msg) {
        mParam.setUsedCatchCount(msg.arg1);
        updateCountView();
    }

    protected void onMsgExitAR(android.os.Message msg) {
//		if (cameraHandler != null) {
//			cameraHandler.stopThreads();
//			cameraHandler = null;
//		}
//		closeCamera();
//		glSurfaceView = null;
//		context = null;
//		GamePlay.exit();
//		if(mRenderer != null) {
//			mRenderer.isGamePlayRunning = false;
//			mRenderer = null;
//		}
        finish();
    }

    protected void onShareDisable(android.os.Message msg) {
        mBtnShare.setEnabled(false);
        mBtnShare.setVisibility(View.INVISIBLE);
        updateCountView();
    }

    protected void onShareEnable(android.os.Message msg) {
        mBtnShare.setEnabled(true);
        mBtnShare.setVisibility(View.VISIBLE);
        updateCountView();
    }

    @SuppressLint("SetTextI18n")
    protected void updateCountView() {
        if (mBtnShare.getVisibility() == View.VISIBLE) {
            mCountView.setText((mParam.getTotalCatchCount() - mParam.getUsedCatchCount()) + "/" + mParam.getTotalCatchCount() + "  ");
            //mCountView.setPadding(
            //		mCountView.getPaddingLeft(),
            //		mCountView.getPaddingTop(),
            //		mCountViewPaddingRight,
            //		mCountView.getPaddingBottom()
            //);
        } else {
            mCountView.setText((mParam.getTotalCatchCount() - mParam.getUsedCatchCount()) + "/" + mParam.getTotalCatchCount());
            //mCountView.setPadding(
            //		mCountView.getPaddingLeft(),
            //		mCountView.getPaddingTop(),
            //		0,
            //		mCountView.getPaddingBottom()
            //);
        }
    }

    public void setCatchAndPromptVisibility(boolean visibility) {
        if (mParam.getUsedCatchCount() >= mParam.getTotalCatchCount()) {
            mBtnCatch.setVisibility(View.INVISIBLE);
            if (visibility) {
                mPrompt.setVisibility(View.VISIBLE);
            } else {
                mPrompt.setVisibility(View.INVISIBLE);
            }
        } else {
            mPrompt.setVisibility(View.INVISIBLE);
            if (visibility) {
                mBtnCatch.setVisibility(View.VISIBLE);
            } else {
                mBtnCatch.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void onReco() {
        setCatchAndPromptVisibility(true);
    }

    public void onLose() {
        setCatchAndPromptVisibility(false);
    }

    public void onBtnCatchPressed(View view) {
        mParam.setLastModelID(hsarToolkit.mState.recoModelName);
        mConstellation.setImageDrawable(getConstellationDrawableWithDensityByName(hsarToolkit.mState.recoModelName));
        mCardName.setText("获得" + mParam.chineseNameFromModelID(hsarToolkit.mState.recoModelName) + "卡片");
        mParam.mState = HiARSDKForSuningState.STATE_CATCH;
        setCatchAndPromptVisibility(false);
//		if(HSARToolkit.getInstance().mState.pose == null) return;
        if (null == mListener) return;
        mListener.OnCatchButtonClicked(mParam);
        stopAllClip();
        playClip("catching");
        disableButtons();
    }

    public void onBtnSharePressed(View view) {
        mDialogShare.setVisibility(View.VISIBLE);
        mShareLine1.setVisibility(View.VISIBLE);
        mShareLine1.setText("分享可额外");
        mShareLine2.setText("获得1次抓捕机会");
        disableButtons();
    }

    public void onBtnWatchPressed(View view) {
        if (null == mListener) return;
        mListener.OnAllpetsButtonClicked(mParam);
    }

    public void onBtnSuccessWatchPressed(View view) {
        mDialogSuccess.setVisibility(View.INVISIBLE);
        if (null == mListener) return;
        mListener.OnSeeResultButtonClicked(mParam);
        reset();
    }

    public void onBtnSuccessCatchPressed(View view) {
        mDialogSuccess.setVisibility(View.INVISIBLE);
        stopAllClip();
        playClip("idle");
        reset();
        if ((mParam.getTotalCatchCount() == mParam.getUsedCatchCount()) &&
                (mParam.getUsedCatchCount() == mParam.getNormalTotalCatchCount())) {
            mShareLine1.setVisibility(View.INVISIBLE);
            mShareLine2.setText("分享可额外获得一次抓捕机会哦");
            onBtnSharePressed(null);
        } else {
            reset();
        }
    }

    public void onBtnSuccessClosePressed(View view) {
        mDialogSuccess.setVisibility(View.INVISIBLE);
        reset();
    }

    public void onBtnFailCatchPressed(View view) {
        mDialogFail.setVisibility(View.INVISIBLE);
        stopAllClip();
        playClip("idle");
        if ((mParam.getTotalCatchCount() == mParam.getUsedCatchCount()) &&
                (mParam.getUsedCatchCount() == mParam.getNormalTotalCatchCount())) {
            mShareLine1.setVisibility(View.INVISIBLE);
            mShareLine2.setText("分享可额外获得一次抓捕机会哦");
            onBtnSharePressed(null);
        } else {
            reset();
        }
    }

    public void onBtnFailClosePressed(View view) {
        mDialogFail.setVisibility(View.INVISIBLE);
        reset();
    }

    public void onBtnShareSharePressed(View view) {
        mDialogShare.setVisibility(View.INVISIBLE);
        if (null == mListener) return;
        mListener.OnShareButtonClicked(mParam);
        reset();
    }

    public void onBtnShareClosePressed(View view) {
        mDialogShare.setVisibility(View.INVISIBLE);
        reset();
    }

    public void onBtnBackPressed(View view) {
        mListener.OnBackButtonClicked(mParam);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mListener.OnBackButtonClicked(mParam);
    }

    private void disableButtons() {
        mBtnCatch.setEnabled(false);
        mBtnShare.setEnabled(false);
        mBtnWatch.setEnabled(false);
        mBtnBack.setEnabled(false);
    }

    private void reset() {
        mParam.mState = HiARSDKForSuningState.STATE_RESET;
        enableButtons();
        setCatchAndPromptVisibility(false);
    }

    private void enableButtons() {

        mBtnCatch.setEnabled(true);
        mBtnShare.setEnabled(true);
        mBtnWatch.setEnabled(true);
        mBtnBack.setEnabled(true);

    }

    //end of added by zqsong 2016.9.22

    protected void playClip(final String name) {
        if (glSurfaceView == null) return;
        glSurfaceView.queueEvent(new Runnable() {
            @Override
            public void run() {
                GamePlay.playClip(name);
            }
        });
    }

    protected void stopAllClip() {
        if (glSurfaceView == null) return;
        glSurfaceView.queueEvent(new Runnable() {
            @Override
            public void run() {
                GamePlay.stopAllClip();
            }
        });
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏

        setContentView(R.layout.activity_hiarsdkforsuning);

        //mCountViewPaddingRight = mCountView.getPaddingRight();//save the count view's right padding

        mParam.setHandler(mHandler);

        mCheckResource = checkResource();
        if (!mCheckResource) return;
        mParam.prepareData();

        mScanLayout = (RelativeLayout) findViewById(R.id.scan_layout);
        boolean b = initLayout();
        if (!b) {
            mListener.OnResourceError(mParam, "load image failed");
            return;
        }

        // set key path
        FilePath.setPublicKeyPath(mParam.getKeyFileDir());

        context = this;
        hsarToolkit = HSARToolkit.getInstance();
        hsarToolkit.mSuningState = mParam;

        GamePlay.initialGamePlay(getAssets());

        initValues();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        addContentView(hsFrame, params);

        mScanLayout.bringToFront();
        mIsInitFinished = true;
    }

    private BitmapDrawable getDrawableWithDensityByName(String name) {
        Bitmap b = BitmapFactory.decodeFile(mParam.getControlImagePath(name));
        BitmapDrawable bmd = new BitmapDrawable(b);
        bmd.setTargetDensity((int) (getResources().getDisplayMetrics().densityDpi));
        return bmd;
    }

    private BitmapDrawable getConstellationDrawableWithDensityByName(String name) {
        Bitmap b = BitmapFactory.decodeFile(mParam.getConstellationIconPath(name));
        BitmapDrawable bmd = new BitmapDrawable(b);
        bmd.setTargetDensity((int) (getResources().getDisplayMetrics().densityDpi));
        return bmd;
    }

    private boolean checkResource() {

        String[] filePaths = mParam.getCheckFileList();
        for (int i = 0; i < filePaths.length; i++) {
            String path = mParam.getRootPath() + filePaths[i];
            File file = new File(path);
            if (!file.exists()) {
                mListener.OnResourceError(mParam, path);
                return false;
            }
        }
        return true;

	}

    private boolean initLayout() {
        try {
            mScanBg = (ImageView) findViewById(R.id.scan_bg);
            NinePatchDrawable ninepatch;
            Bitmap image = BitmapFactory.decodeFile(mParam.getControlImagePath("bg.9"));
            if (image.getNinePatchChunk() != null) {
                byte[] chunk = image.getNinePatchChunk();
                Rect paddingRectangle = new Rect(image.getWidth() / 2, image.getHeight() / 2, image.getWidth() / 2 + 1, image.getHeight() / 2 + 1);
                ninepatch = new NinePatchDrawable(getResources(), image, chunk, paddingRectangle, null);
                ninepatch.setTargetDensity((int) (getResources().getDisplayMetrics().densityDpi * 1.5));
                mScanBg.setImageDrawable(ninepatch);
            }

            mCatchCount = (ImageView) findViewById(R.id.catch_count);
            mCatchCount.setImageDrawable(getDrawableWithDensityByName("bg_counter"));
            mCatchCount.setScaleType(ImageView.ScaleType.FIT_CENTER);

            mBtnBack = (ImageButton) findViewById(R.id.btn_back);
            mBtnBack.setImageDrawable(getDrawableWithDensityByName("btn_back"));
            mBtnBack.setScaleType(ImageView.ScaleType.FIT_CENTER);

            mDialogSuccess = (RelativeLayout) findViewById(R.id.dialog_success);
            mDialogFail = (RelativeLayout) findViewById(R.id.dialog_fail);
            mDialogShare = (RelativeLayout) findViewById(R.id.dialog_share);

            mDialogSuccessBg = (RelativeLayout) findViewById(R.id.dialog_success_bg);
            mDialogSuccessBg.setBackgroundDrawable(getDrawableWithDensityByName("bg_success"));
            mDialogFailBg = (RelativeLayout) findViewById(R.id.dialog_fail_bg);
            mDialogFailBg.setBackgroundDrawable(getDrawableWithDensityByName("bg_catch_fail"));
            mDialogShareBg = (RelativeLayout) findViewById(R.id.dialog_share_bg);
            mDialogShareBg.setBackgroundDrawable(getDrawableWithDensityByName("bg_success_last_time"));

            mBtnCatch = (ImageButton) findViewById(R.id.btn_catch);
            mBtnCatch.setImageDrawable(getDrawableWithDensityByName("btn_catch"));
            mBtnCatch.setVisibility(View.INVISIBLE);
            mBtnCatch.setScaleType(ImageView.ScaleType.FIT_CENTER);

            mIconCatch = (ImageButton) findViewById(R.id.icon_catch);
            mIconCatch.setImageDrawable(getDrawableWithDensityByName("icon_catch"));
            mIconCatch.setScaleType(ImageView.ScaleType.FIT_CENTER);

            mBtnShare = (ImageButton) findViewById(R.id.btn_share);
            mBtnShare.setImageDrawable(getDrawableWithDensityByName("btn_add"));
            mBtnShare.setScaleType(ImageView.ScaleType.FIT_CENTER);

            mBtnWatch = (ImageButton) findViewById(R.id.btn_watch);
            mBtnWatch.setImageDrawable(getDrawableWithDensityByName("btn_pets"));
            mBtnWatch.setScaleType(ImageView.ScaleType.FIT_CENTER);

            mBtnSuccessCatch = (ImageButton) findViewById(R.id.btn_success_catch);
            mBtnSuccessCatch.setImageDrawable(getDrawableWithDensityByName("btn_continue"));
            mBtnSuccessCatch.setScaleType(ImageView.ScaleType.FIT_CENTER);

            mBtnSuccessClose = (ImageButton) findViewById(R.id.btn_success_close);
            mBtnSuccessClose.setImageDrawable(getDrawableWithDensityByName("btn_close"));
            mBtnSuccessClose.setScaleType(ImageView.ScaleType.FIT_CENTER);

            mBtnSuccessWatch = (ImageButton) findViewById(R.id.btn_success_watch);
            mBtnSuccessWatch.setImageDrawable(getDrawableWithDensityByName("btn_watchpets"));
            mBtnSuccessWatch.setScaleType(ImageView.ScaleType.FIT_CENTER);

            mBtnFailCatch = (ImageButton) findViewById(R.id.btn_fail_catch);
            mBtnFailCatch.setImageDrawable(getDrawableWithDensityByName("btn_continue_large"));
            mBtnFailCatch.setScaleType(ImageView.ScaleType.FIT_CENTER);
            mBtnFailClose = (ImageButton) findViewById(R.id.btn_fail_close);
            mBtnFailClose.setImageDrawable(getDrawableWithDensityByName("btn_close"));
            mBtnFailClose.setScaleType(ImageView.ScaleType.FIT_CENTER);

            mBtnShareClose = (ImageButton) findViewById(R.id.btn_share_close);
            mBtnShareClose.setImageDrawable(getDrawableWithDensityByName("btn_close"));
            mBtnShareClose.setScaleType(ImageView.ScaleType.FIT_CENTER);
            mBtnShareShare = (ImageButton) findViewById(R.id.btn_share_share);
            mBtnShareShare.setImageDrawable(getDrawableWithDensityByName("btn_share"));
            mBtnShareShare.setScaleType(ImageView.ScaleType.FIT_CENTER);

            mCountView = (TextView) findViewById(R.id.count_view);
            mConstellation = (ImageView) findViewById(R.id.constellation);

            mPrompt = (TextView) findViewById(R.id.prompt);
            mPrompt.setVisibility(View.INVISIBLE);

            mCardName = (TextView) findViewById(R.id.card_name);
            mFailPrompt = (TextView) findViewById(R.id.fail_prompt);
            mShareLine1 = (TextView) findViewById(R.id.dialog_share_line_1);
            mShareLine2 = (TextView) findViewById(R.id.dialog_share_line_2);

            updateCountView();
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }

    }

    private void initValues() {

        logView = new TextView(this);
        logView.setTypeface(Typeface.MONOSPACE);
        logView.setVisibility(View.GONE);
        CameraSurface cSurface = new CameraSurface(this);

        cameraSurfaceView = new GLSurfaceView(this);
        cameraSurfaceView.setEGLContextClientVersion(2);
        mCameraRenderer = new HSCameraRenderer(this);
        cameraSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 24, 8);
        cameraSurfaceView.setRenderer(mCameraRenderer);
        cameraSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        mRenderer = new HSRenderer(this);
//		mContextFactory = new ContextFactory();
//		mWindowSurfaceFactory = new WindowSurfaceFactory();
        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 24, 0);
        glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
//		glSurfaceView.setEGLContextFactory(mContextFactory);
//		glSurfaceView.setEGLWindowSurfaceFactory(mWindowSurfaceFactory);
        glSurfaceView.setRenderer(mRenderer);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        HSARToolkit.getInstance().hsarRenderer = mRenderer;
        HSARToolkit.getInstance().glSurfaceView = glSurfaceView;
        HSARToolkit.getInstance().mSuningActivity = this;

        cameraHandler = new CameraPreviewHandler(glSurfaceView, cameraSurfaceView, mCameraRenderer, hsarToolkit, camStatus, logView);

        hsFrame = new FrameLayout(this);
        hsFrame.addView(cSurface);
        hsFrame.addView(cameraSurfaceView);
        hsFrame.addView(glSurfaceView);
        hsFrame.addView(logView);

        glSurfaceView.setZOrderMediaOverlay(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mCheckResource) return;
        if (!mIsInitFinished) return;
        hsarToolkit.mState.bIsReady = false;
        hsarToolkit.startFrame();
        GamePlay.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!mCheckResource) return;
        if (!mIsInitFinished) return;

        hsarToolkit.mState.bIsReady = false;
        if (hsarToolkit.isUpdateFrame()) {
            HSARToolkit.getInstance().stopFrame();
        }
        GamePlay.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!mCheckResource) return;
        if (!mIsInitFinished) return;
        if (cameraHandler != null) {
            cameraHandler.stopThreads();
            cameraHandler = null;
        }
        closeCamera();
        glSurfaceView = null;
        cameraSurfaceView = null;
        context = null;
        GamePlay.exit();
        HSRenderer.isGamePlayRunning = false;
        mParam.mState = HiARSDKForSuningState.STATE_LOSE_RUNNING;
        mRenderer = null;
        mCameraRenderer = null;
        freeDrawable();

    }

    private void freeDrawable() {
        mScanBg.setImageDrawable(null);
        mCatchCount.setImageDrawable(null);
        mBtnBack.setImageDrawable(null);
        mDialogSuccessBg.setBackgroundDrawable(null);
        mDialogFailBg.setBackgroundDrawable(null);
        mDialogShareBg.setBackgroundDrawable(null);
        mBtnCatch.setImageDrawable(null);
        mIconCatch.setImageDrawable(null);
        mBtnShare.setImageDrawable(null);
        mBtnWatch.setImageDrawable(null);
        mBtnSuccessCatch.setImageDrawable(null);
        mBtnSuccessClose.setImageDrawable(null);
        mBtnSuccessWatch.setImageDrawable(null);
        mBtnFailCatch.setImageDrawable(null);
        mBtnFailClose.setImageDrawable(null);
        mBtnShareClose.setImageDrawable(null);
        mBtnShareShare.setImageDrawable(null);
        mConstellation.setImageDrawable(null);
    }

    private void startPreview(SurfaceHolder holder) {
        try {
            CameraParameters.setCameraParameters(camera, glSurfaceView.getHeight(), glSurfaceView.getWidth());
            camera.setPreviewDisplay(holder);
            cameraHandler.init(camera);
            camera.setPreviewCallbackWithBuffer(cameraHandler);
            camera.startPreview();
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private void stopPreview() {
        if (camera != null && camStatus.previewing) {
            cameraHandler.UnrealizeGallery();
            camera.setPreviewCallbackWithBuffer(null);
            camStatus.previewing = false;
            camera.stopPreview();
        }
    }

    private boolean openCamera() {
        if (camera == null) {
            try {
                camera = CameraHolder.instance().open();
                camera.setDisplayOrientation(90);
            } catch (Exception e) {
                return false;
            }
        }
        if (camera == null) {
            return false;
        }
        return true;
    }

    private void closeCamera() {
        if (camera != null) {
            CameraHolder.instance().release();
            camera = null;
        }
    }

    class CameraSurface extends SurfaceView implements SurfaceHolder.Callback {

        public CameraSurface(Context context) {
            super(context);
            getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            getHolder().addCallback(this);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            startPreview(holder);
            if (cameraHandler != null) {
                cameraHandler.startThreads();
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            openCamera();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            try {
                if (cameraHandler != null) {
                    cameraHandler.pauseThreads();
                }
                stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
