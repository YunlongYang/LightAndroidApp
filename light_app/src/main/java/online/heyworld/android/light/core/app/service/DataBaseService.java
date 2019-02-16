package online.heyworld.android.light.core.app.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import online.heyworld.android.light.library.listener.ArgListener;
import online.heyworld.android.light.library.util.ThreadUtils;

public class DataBaseService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(DataBaseService.class);

    private JSONObject mData;
    private File mDataFile;

    @Override
    public void init() {
        try {
            mDataFile = new File(context.getFilesDir(), "data/user.json");
            Files.createParentDirs(mDataFile);
            if (!mDataFile.exists()) {
                mDataFile.createNewFile();
            }
        } catch (IOException e) {
        }
    }

    private ArgListener<ArgListener<JSONObject>> open() {
        if (mData != null){
            return argListener -> argListener.on(mData);
        }else{
            return jsonObjectArgListener -> {
                ThreadUtils.postOnBackgroundThread(()->{
                    JSONObject data = null;
                    try {
                        data = JSONObject.parseObject(new String(Files.toByteArray(mDataFile)));
                    } catch (IOException e) {
                    }finally {
                        if(data==null){
                            data = new JSONObject();
                        }
                    }
                    DataBaseService.this.mData = data;
                    jsonObjectArgListener.on(mData);
                });
            };
        }
    }

    private void close() {
        ThreadUtils.getUiThreadHandler().postDelayed(() -> {
            ThreadUtils.postOnBackgroundThread(() -> {
                try {
                    if (mData != null) {
                        Files.write(mData.toJSONString().getBytes(), mDataFile);
                        mData = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }, 2000);

    }

    @Override
    public void exit() {
        super.exit();
        ThreadUtils.postOnBackgroundThread(() -> {
            try {
                if (mData != null) {
                    Files.write(mData.toJSONString().getBytes(), mDataFile);
                    mData = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        while (mData!=null){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void record(String key, Object value) {
        open().on(jsonObject -> {
            doRecord(jsonObject, key, value);
            close();
        });

    }

    public void recordWhenQuery(JSONObject jsonObject, String key, Object value) {
        doRecord(jsonObject, key, value);
    }


    public void query(ArgListener<JSONObject> queryExector) {
        open().on(jsonObject -> {
            queryExector.on(jsonObject);
            close();
        });
    }

    private static void doRecord(JSONObject jsonObject, String key, Object value){
        Object object = JSONArray.toJSON(value);
        logger.debug("record key="+key+" value:"+object);
        jsonObject.put(key, object);
    }
}
