package io.flutter.support;

import android.content.Context;

import io.flutter.view.FlutterMain;

public class ApplicationSupport {
    public static void init(Context context) {
        FlutterMain.startInitialization(context);
    }
}
