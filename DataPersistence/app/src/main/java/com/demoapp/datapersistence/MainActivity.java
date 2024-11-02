package com.demoapp.datapersistence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DataPersistence";
    private String jsonPerson;
    private String jsonMap;
    private String strMap;
    private String jsonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.object_to_json).setOnClickListener(v -> {
            Person XiaoMing = new Person();
            XiaoMing.mName = "xiaoming";
            XiaoMing.mId = "123456";
            XiaoMing.mAge = 25;
            XiaoMing.careerInfo = new Person.CareerInfo("Java开发", 10000);
            Log.d(TAG, XiaoMing.toString());

            JSONObject jsonObject = XiaoMing.toJson();
            jsonPerson = jsonObject.toString();
            Log.d(TAG, jsonPerson);
        });

        findViewById(R.id.json_to_object).setOnClickListener(v -> {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(jsonPerson);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Person person = Person.fromJson(jsonObject);
            Log.d(TAG, person.toString());
        });

        findViewById(R.id.map_to_json).setOnClickListener(v -> {
            Map<String, String> map = new HashMap<>();
            map.put("key1", "value1, with, commas");
            map.put("key2", "value2=with=equals");
            Log.d(TAG, map.toString());

            JSONObject jsonObject = new JSONObject(map);
            jsonMap = jsonObject.toString();
            Log.d(TAG, jsonMap);
//
//            Gson gson = new Gson();
//            jsonMap = gson.toJson(map);
//            Log.d(TAG, jsonMap);


//            List<Map<String, String>> list = new ArrayList<>();
//            Map<String, String> map1 = new HashMap<>();
//            map1.put("key1", "value1, with, commas");
//            map1.put("key2", "value2=with=equals");
//            Map<String, String> map2 = new HashMap<>();
//            map2.put("key1", "https://www.douban.com/");
//            map2.put("key2", "https://www.baidu.com/");
//
//            list.add(map1);
//            list.add(map2);
//
//            JSONArray jsonArray = new JSONArray(list);
//            jsonStr = jsonArray.toString();
//
//            JSONObject jsonObject=new JSONObject(ArrayList)
//            jsonMap=jsonObject.toString();
//            Log.d(TAG, jsonStr);
        });

        findViewById(R.id.json_to_map).setOnClickListener(v -> {
            Map<String, String> map = new HashMap<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonMap);
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    map.put(key, (String) jsonObject.get(key));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Log.d(TAG, map.toString());

//            List<Map<String, String>> list = new ArrayList<>();
//
//            try {
//                JSONArray jsonArray = new JSONArray(jsonStr);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    Iterator<String> keys = jsonObject.keys();
//                    Map<String, String> map = new HashMap<>();
//                    while (keys.hasNext()) {
//                        String key = keys.next();
//                        map.put(key, (String) jsonObject.get(key));
//                    }
//                    list.add(map);
//                }
//
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }

//            Gson gson = new Gson();
//            Type type = new TypeToken<HashMap<String, String>>() {
//            }.getType();
//            Map<String, String> map = gson.fromJson(jsonMap, type);
//            Log.d(TAG, map.toString());
        });

        findViewById(R.id.map_to_string).setOnClickListener(v -> {
            Map<String, String> map = new HashMap<>();
            map.put("url", "https://www.csdn.net/");
            map.put("isJumpToBrowser", "true");
            map.put("isBack", "false");
            strMap = map.toString();
            Log.d(TAG, strMap);
        });

        findViewById(R.id.string_to_map).setOnClickListener(v -> {
            Map<String, String> map = new HashMap<>();
            // 去除花括号
            strMap = strMap.replaceAll("[{}]", "");
            String[] pairs = strMap.split(", ");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2); // 分割键和值
                if (keyValue.length == 2) {
                    map.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }
            Log.d(TAG, map.toString());
        });

    }
}