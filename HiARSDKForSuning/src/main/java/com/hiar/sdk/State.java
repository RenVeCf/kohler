
package com.hiar.sdk;

import android.opengl.Matrix;

import com.hiar.sdk.camera.CameraPreviewHandler;
import com.hiar.sdk.core.NativeInterface;

import org.json.JSONObject;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReentrantLock;

import javax.microedition.khronos.opengles.GL10;

public class State {

    public boolean bIsReady = false;

    public ByteBuffer frameRenderBuffer = null;

    private ReentrantLock mapLock = new ReentrantLock();
    private int frameCnt = 0;

    public State() {
    }

    public void renderWidgets(GL10 gl) {
        mapLock.lock();

        if (HSARToolkit.getInstance().mSuningState.mState == HiARSDKForSuningState.STATE_LOSE_RUNNING && pose != null &&
                recoName != null) {
            HSARToolkit.getInstance().mSuningState.mState = HiARSDKForSuningState.STATE_RECO;
        }

        if (HSARToolkit.getInstance().mSuningState.mState == HiARSDKForSuningState.STATE_RECO_RUNNING && pose == null) {
            HSARToolkit.getInstance().mSuningState.mState = HiARSDKForSuningState.STATE_LOSE;
        }

        if (HSARToolkit.getInstance().mSuningState.mState == HiARSDKForSuningState.STATE_RECO) {

            lastPose = pose;
            GamePlay.frame(pose);

            HSARToolkit.getInstance().mSuningActivity.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            HSARToolkit.getInstance().mSuningActivity.onReco();
                        }
                    }
            );

            try {
                JSONObject model = HSARToolkit.getInstance().mSuningState.getModelWithProbability(recoName);

                recoModelName = model.getString("model");
                String modelPath = HSARToolkit.getInstance().mSuningState.getModelFileDir();
                String gpbPath = modelPath + File.separator + recoModelName + File.separator + recoModelName + ".gpb";
                String animationPath = modelPath + File.separator + recoModelName + File.separator + recoModelName + ".animation";
                String bipPath = modelPath + File.separator + recoModelName + File.separator + recoModelName + ".animation";

                GamePlay.addModel(gpbPath, animationPath, bipPath, model.getString("node"));

                String catchModelName = HSARToolkit.getInstance().mSuningState.getScene().getString("catch");
                String catchNodeName = HSARToolkit.getInstance().mSuningState.getScene().getString("catch_node");
                modelPath = HSARToolkit.getInstance().mSuningState.getModelFileDir();
                gpbPath = modelPath + File.separator + catchModelName + File.separator + catchModelName + ".gpb";
                animationPath = modelPath + File.separator + catchModelName + File.separator + catchModelName + ".animation";

                GamePlay.addCatchModel(gpbPath, animationPath, catchNodeName);

                GamePlay.stopAllClip();
                GamePlay.playClip("idle");

                HSARToolkit.getInstance().mSuningState.mState = HiARSDKForSuningState.STATE_RECO_RUNNING;

            } catch (Exception exception) {
            }

        } else if (HSARToolkit.getInstance().mSuningState.mState == HiARSDKForSuningState.STATE_RECO_RUNNING) {
            lastPose = pose;
            GamePlay.frame(pose);
        } else if (HSARToolkit.getInstance().mSuningState.mState == HiARSDKForSuningState.STATE_LOSE) {
            HSARToolkit.getInstance().mSuningActivity.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            HSARToolkit.getInstance().mSuningActivity.onLose();
                        }
                    }
            );
            GamePlay.delCatchModel(null);
            GamePlay.delModel(null);
            GamePlay.frame(null);
            GamePlay.startModelMatrixAnimation(null, null, 0, 0, 0);
            GamePlay.startCatchModelMatrixAnimation(null, null, 0);
            recoName = null;
            HSARToolkit.getInstance().mSuningState.mState = HiARSDKForSuningState.STATE_LOSE_RUNNING;
        } else if (HSARToolkit.getInstance().mSuningState.mState == HiARSDKForSuningState.STATE_LOSE_RUNNING) {
            GamePlay.frame(null);
        } else if (HSARToolkit.getInstance().mSuningState.mState == HiARSDKForSuningState.STATE_CATCH) {
            GamePlay.startModelMatrixAnimation(lastPose, modelScreen, 100, 50, 10);
            GamePlay.startCatchModelMatrixAnimation(catchModelScreen, catchModelTop, 30);
            GamePlay.playCatchClip("fly");
            frameCnt = 0;
            HSARToolkit.getInstance().mSuningState.mState = HiARSDKForSuningState.STATE_CATCH_RUNNING;
        } else if (HSARToolkit.getInstance().mSuningState.mState == HiARSDKForSuningState.STATE_CATCH_RUNNING) {
            if (frameCnt < 31) frameCnt++;
            if (frameCnt == 30) {
                GamePlay.playCatchClip("stay");
                HSARToolkit.getInstance().mSuningState.mState = HiARSDKForSuningState.STATE_DISAPPEAR;
            }
            GamePlay.frame(modelScreen);
        } else if (HSARToolkit.getInstance().mSuningState.mState == HiARSDKForSuningState.STATE_DISAPPEAR) {
            GamePlay.startModelMatrixAnimation(modelScreen, modelDisappear, 50, 0, 20);
            GamePlay.frame(modelScreen);
            HSARToolkit.getInstance().mSuningState.mState = HiARSDKForSuningState.STATE_DISAPPEAR_RUNNING;
        } else if (HSARToolkit.getInstance().mSuningState.mState == HiARSDKForSuningState.STATE_DISAPPEAR_RUNNING) {
            GamePlay.frame(modelScreen);
        } else if (HSARToolkit.getInstance().mSuningState.mState == HiARSDKForSuningState.STATE_RESET) {
            GamePlay.delCatchModel(null);
            GamePlay.delModel(null);
            GamePlay.frame(null);
            GamePlay.startModelMatrixAnimation(null, null, 0, 0, 0);
            GamePlay.startCatchModelMatrixAnimation(null, null, 0);
            recoName = null;
            HSARToolkit.getInstance().mSuningState.mState = HiARSDKForSuningState.STATE_LOSE_RUNNING;
        }

        mapLock.unlock();

    }

    public float[] pose = null;
    float[] lastPose = null;
    //大概是坐标系
    float[] modelScreen = {
            0, 1, 0, 0,
            -1, 0, 0, 0,
            0, 0, 1, 0,
            300, 0, -1000, 1
    };
    float[] modelDisappear = {
            0, 1, 0, 0,
            -1, 0, 0, 0,
            0, 0, 1, 0,
            -190, 0, -1000, 1
    };
    float[] catchModelScreen = {
            0, 2f, 0, 0,
            -2f, 0, 0, 0,
            0, 0, 2f, 0,
            8, 0, -25, 1
    };
    float[] catchModelTop = {
            0, 100, 0, 0,
            -100, 0, 0, 0,
            0, 0, 100, 0,
            -200, 0, -1000, 1
    };
    private float[] targetSize = new float[2];
    public String recoModelName;
    private String recoName = null;

    public boolean updateStateWithTrackableResult(int width, int height, float[] result, String name, CameraPreviewHandler.TargetState targetState) {
        mapLock.lock();
        if (width != 0) {
            pose = new float[16];
            NativeInterface.hiarqGetGLPose(result, pose);
            Matrix.translateM(pose, 0, width / 2.0f, height / 2.0f, 0);
            targetSize[0] = width;
            targetSize[1] = height;
            if (name != null && name != "") {
                recoName = name;
            }
        } else {
            pose = null;
        }
        mapLock.unlock();
        return true;
    }

}
