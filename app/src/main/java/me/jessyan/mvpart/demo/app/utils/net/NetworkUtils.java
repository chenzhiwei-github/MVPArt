package me.jessyan.mvpart.demo.app.utils.net;

import android.os.Message;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.art.base.BaseApplication;
import me.jessyan.art.http.HttpException;
import me.jessyan.art.integration.AppManager;
import me.jessyan.art.mvp.IRepositoryManager;
import me.jessyan.mvpart.demo.app.ApiConfiguration;
import me.jessyan.mvpart.demo.app.utils.GsonUtil;
import me.jessyan.mvpart.demo.app.utils.net.sign.ParameterSignUtils;
import me.jessyan.mvpart.demo.mvp.model.api.service.CommonService;
import me.jessyan.mvpart.demo.mvp.model.entity.ResultData;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

import static me.jessyan.art.integration.AppManager.HIDE_LOADING;
import static me.jessyan.art.integration.AppManager.SHOW_LOADING;

/**
 * Created by czw on 2017/11/21.
 */
public class NetworkUtils {

    /**
     * 请求默认BASE_UR
     *
     * @param mRepositoryManager
     * @param maps
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> Observable<T> requestToObject(IRepositoryManager mRepositoryManager, Map<String, String> maps, Class<T> tClass) {
        return requestToObject(mRepositoryManager, ApiConfiguration.Domain.BASE_URL, maps, tClass);
    }

    /**
     * 请求默认BASE_URL
     *
     * @param mRepositoryManager
     * @param maps
     * @param tClass
     * @param <T>
     * @return
     */
//    public static <T> Observable<List<T>> requestObjectToList(IRepositoryManager mRepositoryManager, Map<String, String> maps, Class<T> tClass) {
//        return requestObjectToList(mRepositoryManager, ApiConfiguration.Domain.BASE_URL, maps, tClass);
//    }

    /**
     * 解析对象
     *
     * @param mRepositoryManager
     * @param baseUrl            网络请求url
     * @param maps               参数
     * @param tClass             转换对象
     * @param <T>                需转换泛型
     * @return 解析后的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> Observable<T> requestToObject(IRepositoryManager mRepositoryManager, String baseUrl, Map<String, String> maps, Class<T> tClass) {
        return mRepositoryManager
                .createRetrofitService(CommonService.class)
                .executeEncodePost(baseUrl, ParameterSignUtils.buildCommonParameter(baseUrl, maps)) //拼接公共参数
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(responseBody -> Observable.create((ObservableOnSubscribe<T>) e -> {
                    try {
                        if (tClass.equals(ResultData.class)) {
                            //基类
                            ResultData baseJson = GsonUtil.fromJson(responseBody.string(), ResultData.class);
                            if (baseJson.getCode() == 200) {
                                e.onNext((T) baseJson);
                            } else {
                                e.onError(new HttpException(baseJson.getMessage(), baseJson.getCode())); //非200状态抛出给ResponseErrorListenerImpl统一处理
                            }
                        } else if (tClass.equals(String.class)) {
                            //JSON字符串
                            ResultData baseJson = GsonUtil.fromJson(responseBody.string(), ResultData.class);
                            if (baseJson.getCode() == 200) {
                                e.onNext((T) baseJson.getData().toString());
                            } else {
                                e.onError(new HttpException(baseJson.getMessage(), baseJson.getCode())); //非200状态抛出给ResponseErrorListenerImpl统一处理
                            }
                        } else {
                            //对象
                            ResultData<T> baseJson = GsonUtil.fromJson(responseBody.string(), tClass);
                            if (baseJson.getCode() == 200) {
                                T t = baseJson.data;
                                e.onNext(t);
                            } else {
                                e.onError(new HttpException(baseJson.getMessage(), baseJson.getCode())); //非200状态抛出给ResponseErrorListenerImpl统一处理
                            }
                        }
                    } catch (Exception error) {
                        e.onError(error);
                    }
                    e.onComplete();
                }));
    }

    /**
     * 解析对象
     *
     * @param mRepositoryManager
     * @param baseUrl            网络请求url
     * @param maps               参数
     * @param tClass             转换对象
     * @param <T>                需转换泛型
     * @return 解析后的对象
     */
//    @SuppressWarnings("unchecked")
//    public static <T> Observable<T> requestNormalObject(IRepositoryManager mRepositoryManager, String baseUrl, Map<String, String> maps, Class<T> tClass) {
//        return mRepositoryManager.createRetrofitService(CommonService.class)
//                .executeEncodePost(baseUrl, maps)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(responseBody -> Observable.create((ObservableOnSubscribe<T>) e -> {
//                    try {
//                        //基类
//                        T t = GsonUtil.fromJson(responseBody.string(), tClass);
//                        e.onNext((T) t);
//
//                    } catch (Exception error) {
//                        e.onError(error);
//                    }
//                    hideLoading();
//                }));
//    }

    /**
     * 解析列表对象
     *
     * @param mRepositoryManager
     * @param baseUrl            网络请求url
     * @param maps               参数
     * @param tClass             转换对象
     * @param <T>                需转换泛型
     * @return 解析后的列表对象
     */
//    @SuppressWarnings("unchecked")
//    public static <T> Observable<List<T>> requestObjectToList(IRepositoryManager mRepositoryManager, String baseUrl, Map<String, String> maps, Class<T> tClass) {
////        RetrofitUrlManager.getInstance().setGlobalDomain(baseUrl); //修改全局的BaseUrl
//        Map<String, String> parameterMaps = ParameterSignUtils.buildCommonParameter(baseUrl, maps); //拼接公共参数
//        return mRepositoryManager.createRetrofitService(CommonService.class)
//                .executeEncodePost(baseUrl, parameterMaps)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(responseBody -> Observable.create(e -> {
//                    try {
//                        //列表对象
//                        ResultData<T> baseJson = GsonUtil.fromJson(responseBody.string(), ResultData.class);
//                        if (baseJson.getCode() == 200 && baseJson.isSuccess()) {
//                            List<T> t = FastJsonUtils.stringToArrayList(baseJson.getData().toString(), tClass);
//                            e.onNext(t);
//                        } else {
//                            e.onError(new HttpException(baseJson.getMsg(), baseJson.getCode())); //非200状态抛出给ResponseErrorListenerImpl统一处理
//                        }
//                    } catch (Exception error) {
//                        e.onError(error);
//                    }
//                    hideLoading();
//                }));
//    }

    /**
     * 上传文件
     *
     * @param mRepositoryManager
     * @param baseUrl            网络请求url
     * @param parameterMap       参数
     * @param pathList           文件路径
     * @return 解析后的列表对象
     */
    @SuppressWarnings("unchecked")
    public static Observable<List<String>> uploadFile(IRepositoryManager mRepositoryManager, String baseUrl, Map<String, String> parameterMap, List<String> pathList) {
//        RetrofitUrlManager.getInstance().setGlobalDomain(baseUrl); //修改全局的BaseUrl
        Map<String, String> parameterMaps = ParameterSignUtils.buildCommonParameter(baseUrl, parameterMap); //拼接公共参数
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM); //表单类型

        if (parameterMap != null && parameterMap.size() > 0) {
            //传入参数
            Set<Map.Entry<String, String>> parameterSet = parameterMaps.entrySet();
            for (Map.Entry<String, String> entry : parameterSet) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        return Observable.just(pathList).subscribeOn(Schedulers.io()).map(new Function<List<String>, List<File>>() {
            @Override
            public List<File> apply(List<String> list) throws Exception {
                return Luban.with(BaseApplication.getInstance()).load(list).get();
            }
        }).flatMap(new Function<List<File>, ObservableSource<List<String>>>() {
            @Override
            public ObservableSource<List<String>> apply(List<File> files) throws Exception {
                for (int i = 0; i < files.size(); i++) {
                    File file = files.get(i);
                    builder.addFormDataPart("file" + i, file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
                }
                return mRepositoryManager.createRetrofitService(CommonService.class)
                        .executePost(baseUrl, builder.build())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(responseBody -> Observable.create(e -> {
                            try {
                                ResultData baseJson = GsonUtil.fromJson(responseBody.string(), ResultData.class);
                                if (baseJson.getCode() == 200) {
                                    ResultData<List<String>> t = GsonUtil.fromJson(baseJson.getData().toString(), String.class);
                                    e.onNext(t.data);
                                } else {
                                    e.onError(new HttpException(baseJson.getMessage(), baseJson.getCode())); //非200状态抛出给ResponseErrorListenerImpl统一处理
                                }
                            } catch (Exception error) {
                                e.onError(error);
                            }
//                            hideLoading();
                        }));
            }
        });


    }

    /**
     * 参数判空
     *
     * @param map
     * @param key
     * @param value
     * @return
     */
    private static Map filterNull(Map map, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            map.put(key.trim(), value.trim());
        }
        return map;
    }

    public static Observable<List<String>> uploadFile2(IRepositoryManager mRepositoryManager, String baseUrl, Map<String, String> parameterMap, List<String> pathList) {
//        RetrofitUrlManager.getInstance().setGlobalDomain(baseUrl); //修改全局的BaseUrl
        Map<String, String> parameterMaps = ParameterSignUtils.buildCommonParameter(baseUrl, parameterMap); //拼接公共参数
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM); //表单类型

        if (parameterMap != null && parameterMap.size() > 0) {
            //传入参数
            Set<Map.Entry<String, String>> parameterSet = parameterMaps.entrySet();
            for (Map.Entry<String, String> entry : parameterSet) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        return Observable.just(pathList).subscribeOn(Schedulers.io()).map(new Function<List<String>, List<File>>() {
            @Override
            public List<File> apply(List<String> list) throws Exception {
                List<File> fileList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    File file = new File(list.get(i));
                    if (file.exists()) {
                        fileList.add(file);
                    }

                }
                return fileList;
            }
        }).flatMap(new Function<List<File>, ObservableSource<List<String>>>() {
            @Override
            public ObservableSource<List<String>> apply(List<File> files) throws Exception {
                for (int i = 0; i < files.size(); i++) {
                    File file = files.get(i);
                    builder.addFormDataPart("file" + i, file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
                }
                return mRepositoryManager.createRetrofitService(CommonService.class)
                        .executePost(baseUrl, builder.build())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(responseBody -> Observable.create(e -> {
                            try {
                                ResultData baseJson = GsonUtil.fromJson(responseBody.string(), ResultData.class);
                                if (baseJson.getCode() == 200) {
                                    List<String> t = GsonUtil.jsonToList(baseJson.getData().toString(), String.class);
                                    e.onNext(t);
                                } else {
                                    e.onError(new HttpException(baseJson.getMessage(), baseJson.getCode())); //非200状态抛出给ResponseErrorListenerImpl统一处理
                                }
                            } catch (Exception error) {
                                e.onError(error);
                            }
//                            hideLoading();
                        }));
            }
        });
    }

    public static void showLoading() {
        Message message = new Message();
        message.what = SHOW_LOADING;
        AppManager.post(message);
    }

    public static void hideLoading() {
        Message message = new Message();
        message.what = HIDE_LOADING;
        AppManager.post(message);
    }

}
