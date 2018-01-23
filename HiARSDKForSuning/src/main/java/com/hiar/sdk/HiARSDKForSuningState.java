package com.hiar.sdk;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

/* * 识别图与模型的对应关系JSON结构
{
	"name": "normal", // 场景名，对应的文件全路径为：<mSceneDir>/<name>.scene
	"material": "normal", // 资源名，对应的文件全路径为：<mMaterialDir>/<name>.material
	"audio": "2.wav", // 音频文件，对应的文件全路径为：<mAudioDir>/<audio>
	"key2Model": [ // 识别图与模型的对应关系
		{
			"keyID": "2193791743194", // 识别图的key，对应的文件全路径为：<mKeyFileDir>/<keyID>.db
			"models": [
				{
					"model": "aries",	// 模型名，对应的模型相关的信息存放在：<mModelFileDir>/<name> 目录下，有以下文件内容
										// <name>.gpd
					"ratio": 0.2
				},
				{
					"model": "taurus",
					"ratio": 0.8
				}
			]
		},
		{
			"keyID": "219398174911443194",
			"models": [
				{
					"model": "pisces",
					"ratio": 1
				}
			]
		}
	]
}
* */
public class HiARSDKForSuningState {
    private String mRootPath = null;    // 传入的根目录
    private String mSceneName = null; //场景名
    private String mSceneDir = null;            //所有 .scene 文件的存放目录，目前应该只有两种场景
    private String mMaterialDir = null;        //所有 .material 文件的存放目录，每个场景会有一个资源文件与其对应
    private String mAudioDir = null;            //所有音频文件的存放目录
    private String mKeyFileDir = null;        //所有 .db 文件的存放目录
    private String mModelFileDir = null;    //所有模型文件的存放目录
    private int mUserData = 0;                //用户参数，每次回调时带上该参数，目前最直观的使用是区分普通扫图，还是面对面扫图
    private JSONObject mScene = null;            //场景信息，包括3D必须的功能，识别图的 db 文件和模型的对应关系，及模型的显示概率，结构如上面的 JSON 数据
    private int mTotalCatchCount = 10;        //总共允许抓捕次数
    private int mUsedCatchCount = 0;        //已使用的抓捕次数
    private int mNormalTotalCatchCount = 10;   //每默认允许抓捕的最多次数，当用户抓捕操作达到这个次数时，提示结果后，点“继续捕捉”进入分享页
    private boolean mSaveRecoImage = false;    //是否保存识别时的图片（如用于二维码识别）
    private Handler mHandler = null;            //给HiARSDKForSuningActivity发送消息的Handler
    private String mUIDir = null;                //存放UI资源的目录
    private JSONObject mConfig = null;
    private String mLastModelID = null;

    final public static int STATE_RECO = 0;
    final public static int STATE_RECO_RUNNING = 1;
    final public static int STATE_LOSE = 2;
    final public static int STATE_LOSE_RUNNING = 3;
    final public static int STATE_CATCH = 4;
    final public static int STATE_CATCH_RUNNING = 5;
    final public static int STATE_RESET = 6;
    final public static int STATE_DISAPPEAR = 7;
    final public static int STATE_DISAPPEAR_RUNNING = 8;
    public int mState = STATE_LOSE_RUNNING;                    // 状态机
    public Bitmap recoImg = null;

    public HiARSDKForSuningState() {
    }

    /*
    * rootPath: 所有资源存文件的根目录，最后面不带“/”，如: /storage/sdcard/0/data
    * */
    public HiARSDKForSuningState(String rootPath, String sceneName) {
        initWithRootPath(rootPath, sceneName);
    }

    /*
    * rootPath: 所有资源存文件的根目录，最后面不带“/”，如: /storage/sdcard/0/data
    * */
    public void initWithRootPath(String rootPath, String sceneName) {
        mRootPath = rootPath + File.separator;
//		String gameDir = tmpPath+"game";
        String gameDir = "game";
        mSceneDir = mRootPath + gameDir + File.separator;
        mMaterialDir = gameDir;
        mAudioDir = "audio";
        mModelFileDir = "model";
        mKeyFileDir = mRootPath + "db/";
        mUIDir = mRootPath + "ui";
        mSceneName = sceneName;
    }

    public boolean prepareData() {
        String configPath = mRootPath + "config.json";
        mConfig = readJsonConfig(configPath);
        if (mConfig != null) {
            mScene = searchScene(mSceneName);
            Log.i("rmy", "mScene = " + mScene + ", mSceneName = " + mSceneName);
            if (mScene != null) {
                return true;
            }
        }
        return false;
    }

    public String getRootPath() {
        return mRootPath + File.separator;
    }

    private JSONObject searchScene(String name) {
        JSONObject retObj = null;
        try {
            JSONArray ss = mConfig.getJSONArray("scenes");
            for (int i = 0; i < ss.length(); i++) {
                JSONObject o = ss.getJSONObject(i);
                String n = o.getString("name");
                if (n.compareTo(name) == 0) {
                    retObj = o;
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retObj;
    }

    private JSONObject readJsonConfig(String fPath) {
        StringBuilder content = new StringBuilder(); //文件内容字符串
        JSONObject retObj = null;
        File file = new File(fPath);
        if (file.isDirectory()) {
            Log.d("HiARSDKForSuning", "The File doesn't not exist: " + fPath);
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                while ((line = buffreader.readLine()) != null) {
                    content.append(line);
                }
                instream.close();
            } catch (java.io.FileNotFoundException e) {
                Log.d("HiARSDKForSuning", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.d("HiARSDKForSuning", e.getMessage());
            }
        }
        try {
            retObj = new JSONObject(content.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retObj;
    }

    public String getSceneDir() {
        return mSceneDir;
    }

    protected void setSceneDir(String mSceneDir) {
        this.mSceneDir = mSceneDir;
    }

    protected String getMaterialDir() {
        return mMaterialDir;
    }

    protected void setMaterialDir(String mMaterialDir) {
        this.mMaterialDir = mMaterialDir;
    }

    protected String getAudioDir() {
        return mAudioDir;
    }

    protected void setAudioDir(String mAudioDir) {
        //this.mAudioDir = mAudioDir;
    }

    public String getKeyFileDir() {
        return mKeyFileDir;
    }

    protected void setKeyFileDir(String mKeyFileDir) {
        this.mKeyFileDir = mKeyFileDir;
    }

    public String getModelFileDir() {
        return mModelFileDir;
    }

    protected void setModelFileDir(String mModelFileDir) {
        this.mModelFileDir = mModelFileDir;
    }

    public int getUserData() {
        return mUserData;
    }

    public void setUserData(int mUserData) {
        this.mUserData = mUserData;
    }

    public JSONObject getScene() {
        return mScene;
    }

    protected void setScene(JSONObject mScene) {
        this.mScene = mScene;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void setHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public int getTotalCatchCount() {
        return mTotalCatchCount;
    }

    public void setTotalCatchCount(int mTotalCatchCount) {
        this.mTotalCatchCount = mTotalCatchCount;
    }

    public int getUsedCatchCount() {
        return mUsedCatchCount;
    }

    public void setUsedCatchCount(int mUsedCatchCount) {
        this.mUsedCatchCount = mUsedCatchCount;
    }

    public void setNormalTotalCatchCount(int normalTotalCatchCount) {
        mNormalTotalCatchCount = normalTotalCatchCount;
    }

    public int getNormalTotalCatchCount() {
        return mNormalTotalCatchCount;
    }

    protected boolean isSaveRecoImage() {
        return mSaveRecoImage;
    }

    public void setSaveRecoImage(boolean mSaveRecoImage) {
        this.mSaveRecoImage = mSaveRecoImage;
    }

    protected String getUIDir() {
        return mUIDir;
    }

    protected void setUIDir(String mUIDir) {
        this.mUIDir = mUIDir;
    }

    /*
    * Parameters:
    *    name: the image file's name, for example: bg.9
    * */
    public String getControlImagePath(String name) {
        return getUIDir() + File.separator + "controls" + File.separator + name + ".png";
    }

    /*
    * Parameters:
    *    name: the image file's name, for example: aries
    * */
    public String getConstellationIconPath(String name) {
        return getUIDir() + File.separator + "constellation" + File.separator + name + ".png";
    }

    public String sceneAudioPath() {
        String ret = null;
        Log.i("rmy", mScene.toString());
        try {
            String audio = mScene.getString("audio");
            ret = mAudioDir + File.separator + audio;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String scenePath() {
        String ret = null;
        try {
            String name = mScene.getString("name");
            ret = mSceneDir + name + ".scene";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void setLastModelID(String lastModelID) {
        this.mLastModelID = lastModelID;
    }

    public String getLastModelID() {
        return mLastModelID;
    }

    public String serverIdFromModelID(String rID) {
        String ret = null;
        try {
            JSONArray maps = mConfig.getJSONArray("constellation");
            for (int i = 0; i < maps.length(); i++) {
                JSONObject o = maps.getJSONObject(i);
                if (rID.compareToIgnoreCase(o.getString("name")) == 0) {
                    ret = o.getString("id");
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String chineseNameFromModelID(String rID) {
        String ret = null;
        try {
            JSONArray maps = mConfig.getJSONArray("constellation");
            for (int i = 0; i < maps.length(); i++) {
                JSONObject o = maps.getJSONObject(i);
                if (rID.compareToIgnoreCase(o.getString("name")) == 0) {
                    ret = o.getString("ChineseName");
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

	/*
    private String preName = "";
	private int preIndex = 0;
	public JSONObject getModelWithProbability(String recoName) {
		if(preName.equalsIgnoreCase(recoName)){
			preIndex++;
		}else{
			preName = recoName;
			preIndex = 0;
		}

		JSONObject ret = null;
		try {
			JSONArray maps = mScene.getJSONArray("key2Model");
			for(int i=0;i<maps.length();i++){
				JSONObject o = maps.getJSONObject(i);
				if(recoName.startsWith(o.getString("keyID"))){
					JSONArray models = o.getJSONArray("models");
					preIndex %= models.length();
					ret = models.getJSONObject(preIndex);
					break;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}
	*/


    public JSONObject getModelWithProbability(String recoName) {
        JSONObject ret = null;
        try {
            JSONArray maps = mScene.getJSONArray("key2Model");
            for (int i = 0; i < maps.length(); i++) {
                JSONObject o = maps.getJSONObject(i);
                if (recoName.startsWith(o.getString("keyID"))) {
                    JSONArray models = o.getJSONArray("models");
                    double rnd = Math.random();
                    double p = 0;
                    for (int j = 0; j < models.length(); j++) {
                        JSONObject model = models.getJSONObject(j);
                        p += model.getDouble("ratio");
                        if (p > rnd) {
                            ret = models.getJSONObject(j);
                            break;
                        }
                    }
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public HashSet<String> getKeySet() {

        HashSet<String> ret = new HashSet<String>();
        try {
            JSONArray maps = mScene.getJSONArray("key2Model");
            for (int i = 0; i < maps.length(); i++) {
                JSONObject o = maps.getJSONObject(i);
                ret.add(o.getString("keyID"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;

    }

    public String[] getCheckFileList() {

        String[] list = {
                "db/ad.db",
                "db/aries.db",
                "db/cancer.db",
                "db/capricorn.db",
                "db/gemini.db",
                "db/leo.db",
                "db/libra.db",
                "db/logo.db",
                "db/sagittarius.db",
                "db/scorpio.db",
                "db/taurus.db", "config.json",
                "game/audio/background.wav",
                "game/model/aquarius/aquarius.animation",
                "game/model/aquarius/aquarius.gpb",
                "game/model/aquarius/aquarius.material",
                "game/model/aquarius/aquarius.png",
                "game/model/aries/aries.animation",
                "game/model/aries/aries.gpb",
                "game/model/aries/aries.material",
                "game/model/aries/aries.png",
                "game/model/ball/ball.gpb",
                "game/model/ball/ball.material",
                "game/model/ball/ball.png",
                "game/model/cancer/cancer.animation",
                "game/model/cancer/cancer.gpb",
                "game/model/cancer/cancer.material",
                "game/model/cancer/cancer.png",
                "game/model/capricorn/capricorn.animation",
                "game/model/capricorn/capricorn.gpb",
                "game/model/capricorn/capricorn.material",
                "game/model/capricorn/capricorn.png",
                "game/model/gemini/gemini.animation",
                "game/model/gemini/gemini.gpb",
                "game/model/gemini/gemini.material",
                "game/model/gemini/gemini.png",
                "game/model/leo/leo.animation",
                "game/model/leo/leo.gpb",
                "game/model/leo/leo.material",
                "game/model/leo/leo.png",
                "game/model/libra/libra.animation",
                "game/model/libra/libra.gpb",
                "game/model/libra/libra.material",
                "game/model/libra/libra.png",
                "game/model/pisces/pisces.animation",
                "game/model/pisces/pisces.gpb",
                "game/model/pisces/pisces.material",
                "game/model/pisces/pisces.png",
                "game/model/sagittarius/sagittarius.animation",
                "game/model/sagittarius/sagittarius.gpb",
                "game/model/sagittarius/sagittarius.material",
                "game/model/sagittarius/sagittarius.png",
                "game/model/scorpio/scorpio.animation",
                "game/model/scorpio/scorpio.gpb",
                "game/model/scorpio/scorpio.material",
                "game/model/scorpio/scorpio.png",
                "game/model/taurus/taurus.animation",
                "game/model/taurus/taurus.gpb",
                "game/model/taurus/taurus.material",
                "game/model/taurus/taurus.png",
                "game/model/virgo/virgo.animation",
                "game/model/virgo/virgo.gpb",
                "game/model/virgo/virgo.material",
                "game/model/virgo/virgo.png",
                "game/model/zbgj/zbgj.animation",
                "game/model/zbgj/zbgj.gpb",
                "game/model/zbgj/zbgj.material",
                "game/model/zbgj/zbgj.png",
                "game/normal.material",
                "game/normal.scene",
                "game/shaders/colored.frag",
                "game/shaders/colored.vert",
                "game/shaders/font.frag",
                "game/shaders/font.vert",
                "game/shaders/lighting.frag",
                "game/shaders/lighting.vert",
                "game/shaders/skinning-none.vert",
                "game/shaders/skinning.vert",
                "game/shaders/sprite.frag",
                "game/shaders/sprite.vert",
                "game/shaders/terrain.frag",
                "game/shaders/terrain.vert",
                "game/shaders/textured.frag",
                "game/shaders/textured.vert",
                "ui/constellation/aquarius.png",
                "ui/constellation/aries.png",
                "ui/constellation/cancer.png",
                "ui/constellation/capricorn.png",
                "ui/constellation/gemini.png",
                "ui/constellation/leo.png",
                "ui/constellation/libra.png",
                "ui/constellation/pisces.png",
                "ui/constellation/sagittarius.png",
                "ui/constellation/scorpio.png",
                "ui/constellation/taurus.png",
                "ui/constellation/virgo.png",
                "ui/controls/bg.9.png",
                "ui/controls/bg.png",
                "ui/controls/bg_catch_fail.png",
                "ui/controls/bg_counter.png",
                "ui/controls/bg_success.png",
                "ui/controls/bg_success_last_time.png",
                "ui/controls/btn_add.png",
                "ui/controls/btn_back.png",
                "ui/controls/btn_catch.png",
                "ui/controls/btn_close.png",
                "ui/controls/btn_continue.png",
                "ui/controls/btn_continue_large.png",
                "ui/controls/btn_pets.png",
                "ui/controls/btn_share.png",
                "ui/controls/btn_watchpets.png",
                "ui/controls/icon_catch.png"};

        return list;
    }

}
