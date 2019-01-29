package online.heyworld.android.light.library.listener.net;

import online.heyworld.android.light.library.util.InternetUtil;

public interface ResponseListener {
    void onResponse(InternetUtil.Response response);

    void onError(Exception e);
}
