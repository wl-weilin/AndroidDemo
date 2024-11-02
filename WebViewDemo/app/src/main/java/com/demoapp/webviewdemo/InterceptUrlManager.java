package com.demoapp.webviewdemo;

import android.content.Context;
import android.os.FileObserver;
import android.util.Log;
import android.util.Xml;

import org.json.JSONArray;
import org.w3c.dom.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

import com.demoapp.webviewdemo.WebViewInterceptUrl.UrlRule;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class InterceptUrlManager {
    private static final String TAG = "XmlUtil";
    private static final String FILE_NAME = "url_intercept_list.xml";
    private static FileObserver mFileObserver;
    private Map<String, String> mIflyUrlRules = new HashMap<>();

    // 保存到Xml中的标签值，以及HashMap中的键值
    private static final String APP_TAG = "App";
    private static final String URL_TAG = "urlItem";
    private static final String RULE_KEY = "rule";
    private static final String PKG_KEY = "package";
    private static final String URL_KEY = "url";
    private static final String JUMP_KEY = "isJumpToBrowser";
    private static final String BACK_KEY = "isBack";

    public void init(Context context) {
        convert();
//        readInterceptUrlConfig(context);
    }

    // 将Map<String, List<UrlRule>>转换为Map<String, String>
    public void convert() {
        for (String packageName : InterceptUrlData.iflyUrlRules.keySet()) {
            ArrayList<UrlRule> urlRules = InterceptUrlData.iflyUrlRules.get(packageName);
            List<Map<String, String>> list = new ArrayList<>();
            for (UrlRule urlRule : urlRules) {
                Map<String, String> map = new HashMap<>();
                map.put(URL_KEY, urlRule.mUrl);
                map.put(JUMP_KEY, urlRule.isJumpToBrowser ? "true" : "false");
                map.put(BACK_KEY, urlRule.isBack ? "true" : "false");
                list.add(map);
            }
            JSONArray jsonArray = new JSONArray(list);
            mIflyUrlRules.put(packageName, jsonArray.toString());
        }
        Log.i(TAG,"Execute convert");
    }

    private Map<String, String> strToMap(String strMap) {
        Map<String, String> map = new HashMap<>();
        strMap = strMap.replaceAll("[{}]", "");
        String[] pairs = strMap.split(", ");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                map.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return map;
    }

    public void setFileObserver(Context context) {
        File appDataDir = context.getFilesDir();
        String dataDirPath = appDataDir.getAbsolutePath() + "/";

        Log.i(TAG, dataDirPath + FILE_NAME);

        mFileObserver = new FileObserver(dataDirPath) {
            @Override
            public void onEvent(int event, String path) {
                Log.i(TAG, path + " event=0x" + Integer.toHexString(event));

                if (!FILE_NAME.equals(path)) {
                    return;
                }
                // 处理文件事件
                switch (event) {
                    case FileObserver.CREATE:
                        // 文件被创建
                        Log.i(TAG, "创建文件");
                        break;
                    case FileObserver.DELETE_SELF:
                        // 文件被删除
                        Log.i(TAG, "删除文件");
                        break;
                    case FileObserver.MODIFY:
                        // 文件被修改
                        Log.i(TAG, "修改文件");
                        break;
                    case FileObserver.ACCESS:
                        // 文件被读取
                        Log.i(TAG, "读取文件");
                        break;
                    case FileObserver.CLOSE_NOWRITE:
                        // 文件被读取
                        Log.i(TAG, "只读文件");
                        break;
                    default:
                        // 其他事件
                        break;
                }
            }
        };
        mFileObserver.startWatching();
    }

    public void readInterceptUrlConfig(Context context) {
        File configFile = new File(context.getFilesDir(), FILE_NAME);
        if (!configFile.exists()) {
            Log.w(TAG, FILE_NAME + " is not exist");
            return;
        }
        Map<String, String> result = new HashMap<>();
        XmlPullParser parser = Xml.newPullParser();

        try {
            FileInputStream fis = new FileInputStream(configFile);
            parser.setInput(fis, null);

            int eventType = parser.getEventType();
            String packageName = null;
            String urlRules = null;
            String currentTag = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTag = parser.getName();
                        if (APP_TAG.equals(currentTag)) {
                            packageName = parser.getAttributeValue("", PKG_KEY);
                            urlRules = parser.getAttributeValue("", RULE_KEY);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        currentTag = parser.getName();
                        if (APP_TAG.equals(currentTag)) {
                            result.put(packageName, urlRules);
                            packageName = null;
                            urlRules = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            fis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (result.size() != 0) {
            mIflyUrlRules.clear();
            mIflyUrlRules.putAll(result);
        }
        Log.i(TAG, "Read " + FILE_NAME);
        Log.i(TAG, mIflyUrlRules.toString());
    }

    public void writeInterceptUrlConfig(Context context) {
        File configFile = new File(context.getFilesDir(), FILE_NAME);
        XmlSerializer serializer = Xml.newSerializer();

        try {
            FileOutputStream fos = new FileOutputStream(configFile);
            serializer.setOutput(fos, "UTF-8");

            serializer.startDocument("UTF-8", true);
            serializer.text("\n");
            serializer.startTag("", "root");
            for (String packageName : mIflyUrlRules.keySet()) {
                String urlRules = mIflyUrlRules.get(packageName);
                serializer.text("\n\n\t");
                serializer.startTag("", APP_TAG);
                serializer.attribute("", PKG_KEY, packageName);
                serializer.attribute("", RULE_KEY, urlRules);
                serializer.text("\n\t");
                serializer.endTag("", APP_TAG);
            }
            serializer.text("\n\n");
            serializer.endTag("", "root");
            serializer.endDocument();

            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        formatXmlDocuments(context);
    }

    private void formatXmlDocuments(Context context){
        File xmlFile = new File(context.getFilesDir(), FILE_NAME);
        try {
            // 1. 读取 XML 文件
            FileInputStream fis = new FileInputStream(xmlFile);

            // 2. 使用 DocumentBuilder 解析 XML 文件为 Document 对象
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(fis);

            // 关闭文件输入流
            fis.close();

            // 3. 使用 Transformer 对 XML 进行格式化
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // 设置输出属性，进行缩进
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");  // 设置缩进为 4 格
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");

            // 4. 将格式化后的 XML 写回文件
            FileOutputStream fos = new FileOutputStream(xmlFile);
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(fos);
            transformer.transform(source, result);

            // 关闭输出流
            fos.flush();
            fos.close();

            System.out.println("格式化后的 XML 文件已保存为 formatted_output.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getInterceptRuleList(String packageName) {
        if (packageName == null || mIflyUrlRules == null || mIflyUrlRules.size() == 0) {
            return null;
        }
        return mIflyUrlRules.get(packageName);
    }
}
