package com.lyentech.use;

import com.lyentech.sdk.GreeNp;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;

@RunWith(Parameterized.class)
public class GreeNpTest {
    private static final String PUBLIC_DEV_URL = "https://ark-npt.gree.com/np-track/push?";
    private static final String PUBLIC_PRO_URL = "https://ark-np.gree.com/np-track/push?";
    private String url;
    private String urlArg;

    public GreeNpTest(String url, String args) {
        this.url = url;
        this.urlArg = args;
    }

    @Rule
    public GreeNpRule urlRule = new GreeNpRule();

    //测试的一组数据源，url  +  参数
    @Parameterized.Parameters
    public static Iterable<Object[]> data() throws JSONException, UnsupportedEncodingException {
        return Arrays.asList(new Object[][]{
                {PUBLIC_DEV_URL, encodeURI(getNpArg("02EBA73C32A2AF47552FA411135E2CBA","/1", "/launch", "tp", null, null))}, //启动
                //{PUBLIC_DEV_URL, encodeURI(getNpArg("02EBA73C32A2AF47552FA411135E2CBA","/1", "", "ev", "video_source", null))}, //trackEvent
                {PUBLIC_DEV_URL, encodeURI(getNpArg("02EBA73C32A2AF47552FA411135E2CBA","/1", "", "ev", "_search", new JSONObject().put("_default", "keyWord").toString()))}, //trackSearch
                {PUBLIC_DEV_URL, encodeURI(getNpArg("42105155A928803FEB5E5A4E2597809E","/1", "/launch", "tp", null, null))}, //启动
                //{PUBLIC_DEV_URL, encodeURI(getNpArg("42105155A928803FEB5E5A4E2597809E","/1", "", "ev", "video_source", null))}, //trackEvent
                {PUBLIC_DEV_URL, encodeURI(getNpArg("42105155A928803FEB5E5A4E2597809E","/1", "", "ev", "_search", new JSONObject().put("_default", "keyWord").toString()))}, //trackSearch
                //{PUBLIC_PRO_URL, encodeURI(getNpArg("FBE09CB25E62E61F86633F2B475FB982","/1", "/launch", "tp", null, null))}, //启动
                //{PUBLIC_PRO_URL, encodeURI(getNpArg("FBE09CB25E62E61F86633F2B475FB982","/1", "", "ev", "video_source", null))}, //trackEvent
                //{PUBLIC_PRO_URL, encodeURI(getNpArg("FBE09CB25E62E61F86633F2B475FB982","/1", "", "ev", "_search", new JSONObject().put("_default", "keyWord").toString()))} //trackSearch
        });
    }

    @Test
    public void testGetUrls() {
        if (url == null || url.length() == 0) return;
        String result = urlRule.doGet(url + urlArg);
        Assert.assertEquals("\"\"",result);
    }

    private static String getNpArg(String ak, String dsc, String src, String tp, String ev, String evv) {
        JSONObject js = setNpBody(ak, dsc, src, tp, ev, evv);
        StringBuilder sb = new StringBuilder();
        Iterator iterator = js.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            try {
                if ("evv".equals(key)) { //也就这个要处理255长度
                    sb.append(key + "=" + isValidStr(js.getString(key)) + "&");
                } else {
                    sb.append(key + "=" + js.getString(key) + "&");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    //设置公共参数
    private static JSONObject setNpBody(String ak, String dsc, String src, String tp, String ev, String evv) {
        JSONObject js = new JSONObject();
        try {
            js.put("ak",ak);
            js.put("u", dsc);
            js.put("pf", "Android");
            js.put("rf", src);
            js.put("sys", "Android 10");
            js.put("br", "OnePlus");//OnePlus
            js.put("brv", "ONEPLUS A6000");//ONEPLUS A6000
            int w = 1080, h = 1920;
            js.put("sr", w + "x" + h);
            js.put("uuid", "c7ea40d44bc87361");//c7ea40d44bc87361
            js.put("rnd", System.currentTimeMillis());
            js.put("tp", tp);
            js.put("ev", ev);
            js.put("evv", evv);
            js.put("xy", null);
            js.put("v", "0.0.1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js;
    }

    private static String isValidStr(String json) {
        if (json.length() > 255) {
            JSONObject jsonObject = null;
            JSONObject jsonTmp = new JSONObject();
            try {
                jsonObject = new JSONObject(json);
                Iterator iterator = jsonObject.keys();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    jsonTmp.put(key, jsonObject.getString(key));
                    if (jsonTmp.toString().length() > 255) {
                        jsonTmp.remove(key);
                        GreeNp.printLog("截断属性值长度超过255》" + json);
                        return jsonTmp.toString();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return json.substring(0, 255);//非json文本直接返回
            }
        }
        return json;
    }

    private static String encodeURI(String str)
            throws UnsupportedEncodingException {
        String isoStr = new String(str.getBytes("UTF8"), "ISO-8859-1");
        char[] chars = isoStr.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] <= 'z' && chars[i] >= 'a') || (chars[i] <= 'Z' && chars[i] >= 'A')
                    || chars[i] == '-' || chars[i] == '_' || chars[i] == '.' || chars[i] == '!'
                    || chars[i] == '~' || chars[i] == '*' || chars[i] == '\'' || chars[i] == '('
                    || chars[i] == ')' || chars[i] == ';' || chars[i] == '/' || chars[i] == '?'
                    /*|| chars[i] == ':' */ || chars[i] == '@' || chars[i] == '&' || chars[i] == '='
                    || chars[i] == '+' || chars[i] == '$' || chars[i] == ',' || chars[i] == '#'
                    || (chars[i] <= '9' && chars[i] >= '0')
                /*|| (chars[i] == '"')*/) {
                sb.append(chars[i]);
            } else {
                sb.append("%");
                sb.append(Integer.toHexString(chars[i]));
            }
        }
        return sb.toString();
    }
}
