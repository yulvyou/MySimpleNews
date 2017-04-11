package com.example.mysimplenews.utitls;

import com.example.mysimplenews.beans.NewsDetailBean;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/19
 */
public class NewsDetailJsonUtils {

    private final static String TAG = "NewsDetailJsonUtils";


    public static NewsDetailBean readJsonNewsDetailBeans(String res, String docId) {
        NewsDetailBean newsDetailBean = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(docId);
            if(jsonElement == null) {
                return null;
            }
            newsDetailBean = JsonUtils.deserialize(jsonElement.getAsJsonObject(), NewsDetailBean.class);
        } catch (Exception e) {
            LogUtils.e(TAG, "readJsonNewsBeans error" , e);
        }
        return newsDetailBean;
    }

}
