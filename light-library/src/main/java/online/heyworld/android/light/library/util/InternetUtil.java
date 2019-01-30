package online.heyworld.android.light.library.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import online.heyworld.android.light.library.listener.net.ProgressListener;
import online.heyworld.android.light.library.listener.net.ResponseListener;
import online.heyworld.android.light.library.util.impl.internet.OkHttpSession;

public class InternetUtil {

    public static final Map<String,String> EMPTY_HEADER = Collections.unmodifiableMap(new HashMap<String,String>(0));
    public static final byte[] EMPTY_BODY = new byte[0];

    public static Session request(Method method, String url, Map<String,String> headers,byte[] body){
        return new OkHttpSession(new Request(method, url, headers, body));
    }

    public static Session get(String url){
        return request(Method.GET,url,EMPTY_HEADER,EMPTY_BODY);
    }

    public static Session get(String url,Map<String,String> headers){
        return request(Method.GET,url,headers,EMPTY_BODY);
    }

    public enum Method{
        GET,
        POST
    }

    public static class Request{
        private final Method method;
        private final String url;
        private final Map<String,String> headers;
        private final byte[] body;

        public Request(Method method, String url, Map<String, String> headers, byte[] body) {
            this.method = method;
            this.url = url;
            this.headers = headers;
            this.body = body;
        }

        public Method getMethod() {
            return method;
        }

        public String getUrl() {
            return url;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public byte[] getBody() {
            return body;
        }
    }

    public abstract static class Session{
        protected InternetUtil.Request request;

        public Session(InternetUtil.Request request) {
            this.request = request;
        }

        public abstract Session listenOn(ProgressListener progressListener);

        public abstract Response execute()throws Exception;
        public abstract FutureController executeAsyncForeground(ResponseListener responseListener);
        public abstract FutureController executeAsyncBackground(ResponseListener responseListener);
    }

    public abstract static class Response{
        private int code;
        private Map<String,String> headers;

        public Response(int code, Map<String, String> headers) {
            this.code = code;
            this.headers = headers;
        }

        public int getCode() {
            return code;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public abstract byte[] getBody()throws Exception;

        @Override
        public String toString() {
            return "Response{" +
                    "code=" + code +
                    ", headers=" + headers +
                    '}';
        }
    }

    public abstract static class FutureController{
        public abstract void cancel();
    }
}
