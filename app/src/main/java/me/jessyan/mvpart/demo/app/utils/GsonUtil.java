package me.jessyan.mvpart.demo.app.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import me.jessyan.mvpart.demo.mvp.model.entity.ResultData;

public class GsonUtil {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {
    }

    public static ResultData fromJson(String json, Class clazz) {
        return new GsonBuilder()
                .registerTypeAdapter(ResultData.class, new JsonFormatParser(clazz))
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .create()
                .fromJson(json, ResultData.class);
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String gsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * jsonStr 转成 listObject
     *
     * @param s
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> gsonToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> jsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> jsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    /**
     * 实现 JsonDeserializer 接口重写 deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 方法，这里进行解析处理，针对特殊字段特殊处理。
     */
    static class JsonFormatParser implements JsonDeserializer<ResultData> {

        private Class mClass;

        public JsonFormatParser(Class tClass) {
            this.mClass = tClass;
        }

        @Override
        public ResultData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            // 根据Json元素获取Json对象。
            JsonObject mJsonObject = json.getAsJsonObject();
            ResultData mResult = new ResultData();
            // 由于Json是以键值对的形式存在的，此处根据键(data)获取对应的Json字符串。
            String mJson = mJsonObject.get("data").toString();
            // 判断是Array还是Object类型。
            if (mJsonObject.get("data").isJsonArray() && !mJsonObject.get("data").isJsonNull()) {
                mResult.setData(fromJsonArray(mJson, mClass));
                mResult.setDataType(1);
            } else if (mJsonObject.get("data").isJsonObject() && !mJsonObject.get("data").isJsonNull()) {
                mResult.setData(fromJsonObject(mJson, mClass));
                mResult.setDataType(0);
            } else if (mJsonObject.get("data").isJsonPrimitive() && !mJsonObject.get("data").isJsonNull()) {
                // 服务端返回data的值为"{}","[]"，将对象或者集合以字符串的形式返回回来，先去除两边的双引号，再去掉转义字符。
                String mNewJson = mJson.substring(1, mJson.length() - 1).replaceAll("\\\\", "");
                // 根据处理好的Json字符串判断是集合还是对象，再进行解析。
                if (mNewJson.startsWith("[") || mNewJson.endsWith("]")) {
                    mResult.setData(fromJsonArray(mNewJson, mClass));
                    mResult.setDataType(1);
                } else if (mNewJson.startsWith("{") || mNewJson.endsWith("}")) {
                    mResult.setData(fromJsonObject(mNewJson, mClass));
                    mResult.setDataType(0);
                } else {
                    mResult.setData(fromJsonObject(mResult.toString(), mClass));
                    mResult.setDataType(2);
                }
            } else if (mJsonObject.get("data").isJsonNull() || mJsonObject.get("data").getAsString().isEmpty()) {
                mResult.setData(fromJsonObject(mResult.toString(), mClass));
                mResult.setDataType(2);
            }
            // 根据键获取返回的状态码。
            mResult.setCode(mJsonObject.get("code").getAsInt());
            // 根据键获取返回的状态信息。
            mResult.setMessage(mJsonObject.get("msg").getAsString());
            return mResult;
        }

        /**
         * 用来解析对象
         */
        private <T> T fromJsonObject(String json, Class<T> type) {
            return new Gson().fromJson(json, type);
        }

        /**
         * 用来解析集合
         */
        private <T> ArrayList<T> fromJsonArray(String json, Class<T> clazz) {
            Type type = new TypeToken<ArrayList<JsonObject>>() {
            }.getType();
            ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);
            ArrayList<T> arrayList = new ArrayList<>();
            for (JsonObject jsonObject : jsonObjects) {
                arrayList.add(new Gson().fromJson(jsonObject, clazz));
            }
            return arrayList;
        }
    }

}