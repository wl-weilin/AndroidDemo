package com.demoapp.jsondemo;


import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;

public class Person {
    public String mName;
    public String mId;
    public int mAge;
    public float mHeight;
    public CareerInfo careerInfo;

    public Person() {
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mName.toString() + "{");
        sb.append("Id=" + mId + ",");
        sb.append("Name=" + mName + ",");
        sb.append("Age=" + mAge + ",");
        sb.append("Height=" + mHeight + ",");
        sb.append(careerInfo);
        sb.append("}");
        return sb.toString();
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", mName);
            jsonObject.put("id", mId);
            jsonObject.put("age", mAge);
            jsonObject.put("height", mHeight);
            jsonObject.put("careerInfo", careerInfo.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Person fromJson(JSONObject jsonObject) {
        Person person = new Person();
        try {
            person.mName = jsonObject.getString("name");
            person.mId = jsonObject.getString("id");
            person.mAge = jsonObject.getInt("age");
            person.mHeight = (float) jsonObject.getDouble("height");
            JSONObject careerInfoJson = jsonObject.getJSONObject("careerInfo");
            person.careerInfo=CareerInfo.fromJson(careerInfoJson);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    /**
     * 职业信息
     */
    public static class CareerInfo {
        // 岗位
        public String mStation;
        // 薪水
        public int mSalary;

        public CareerInfo(String station, int salary) {
            mStation = station;
            mSalary = salary;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("CareerInfo" + "{");
            sb.append("Station=" + mStation + ",");
            sb.append("Salary=" + mSalary);
            sb.append("}");
            return sb.toString();
        }

        public JSONObject toJson() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("station", mStation);
                jsonObject.put("salary", mSalary);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        public static CareerInfo fromJson(JSONObject jsonObject) {
            try {
                String station = jsonObject.getString("station");
                int salary = jsonObject.getInt("salary");
                return new CareerInfo(station, salary);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
