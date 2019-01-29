package online.heyworld.android.light.library.util.impl.internet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.internal.Version;
import okio.BufferedSink;
import online.heyworld.android.light.library.app.context.ContextProvider;
import online.heyworld.android.light.library.listener.net.ResponseListener;
import online.heyworld.android.light.library.util.InternetUtil;
import online.heyworld.android.light.library.util.SystemUtil;
import online.heyworld.android.light.library.util.ThreadUtils;

public class OkHttpSession extends InternetUtil.Session {
    private static OkHttpClient sClient;
    private synchronized static OkHttpClient getClient(){
        if(sClient==null){
            sClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    okhttp3.Request request = chain.request()
                            .newBuilder()
                            .addHeader("Accept-Language", SystemUtil.getLanguage(ContextProvider.get()))
                            .addHeader("User-Agent",Version.userAgent()+" "+SystemUtil.getUserAgent(ContextProvider.get()))
                            .build();
                    return chain.proceed(request);
                }
            }).build();
        }
        return sClient;
    }

    public OkHttpSession(InternetUtil.Request request) {
        super(request);
    }

    @Override
    public InternetUtil.Response execute() throws Exception{
        return executeOkHttp(doRequest());
    }

    private static InternetUtil.Response executeOkHttp(Call call)throws Exception{
        okhttp3.Response okResp =  call.execute();
        InternetUtil.Response response = new InternetUtil.Response(okResp.code(),
                convert(okResp.headers().toMultimap()),
                okResp.body().bytes());
        return response;
    }

    private static Map<String,String> convert(Map<String,List<String>> source){
        Map<String,String> dest = new HashMap<>();
        StringBuilder valueBuilder = new StringBuilder();
        for(Map.Entry<String,List<String>> item: source.entrySet()){
            for(String vI : item.getValue()){
                valueBuilder.append(vI).append(";");
            }
            valueBuilder.setLength(valueBuilder.length()-1);
            dest.put(item.getKey(),valueBuilder.toString());
        }
        return dest;
    }

    @Override
    public InternetUtil.FutureController executeAsyncForeground(final ResponseListener responseListener) {
        final Call call = doRequest();
        ThreadUtils.postOnBackgroundThread(()->{
            try {
                InternetUtil.Response response = executeOkHttp(call);
                ThreadUtils.postOnMainThread(()->responseListener.onResponse(response));
            } catch (Exception e) {
                ThreadUtils.postOnMainThread(()->responseListener.onError(e));
            }
        });
        return new OkHttpFutureController(call);
    }

    @Override
    public InternetUtil.FutureController executeAsyncBackground(final ResponseListener responseListener) {
        final Call call = doRequest();
        ThreadUtils.postOnBackgroundThread(()->{
            try {
                InternetUtil.Response response = executeOkHttp(call);
                responseListener.onResponse(response);
            } catch (Exception e) {
                responseListener.onError(e);
            }
        });
        return new OkHttpFutureController(call);
    }

    private static class OkHttpFutureController extends InternetUtil.FutureController{
        Call call;

        public OkHttpFutureController(Call call) {
            this.call = call;
        }

        @Override
        public void cancel() {
            call.cancel();
        }
    }

    private Call doRequest(){
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        builder.url(request.getUrl());
        for (Map.Entry<String, String> header:request.getHeaders().entrySet()){
            builder.addHeader(header.getKey(),header.getValue());
        }
        switch (request.getMethod()){
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(new RequestBody() {
                    @Nullable
                    @Override
                    public MediaType contentType() {
                        return MediaType.parse(request.getHeaders().get("Content-Type"));
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {
                        sink.write(request.getBody());
                    }
                });
        }
        okhttp3.Request okReq = builder.build();
        return getClient().newCall(okReq);
    }
}
