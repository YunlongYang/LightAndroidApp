package online.heyworld.android.light.core.tech.aop.ext.context;

import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.ViewDebug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yunlong.yang on 2018/12/27.
 */

@SuppressWarnings("all")
public class DelegateContext extends Context {

    private final ContextInterface hostContext;

    public DelegateContext(ContextInterface hostContext) {
        this.hostContext = hostContext;
    }

    @Override
    public AssetManager getAssets() {
        return hostContext.getAssets();
    }

    @Override
    public Resources getResources() {
        return hostContext.getResources();
    }

    @Override
    public PackageManager getPackageManager() {
        return hostContext.getPackageManager();
    }

    @Override
    public ContentResolver getContentResolver() {
        return hostContext.getContentResolver();
    }

    @Override
    public Looper getMainLooper() {
        return hostContext.getMainLooper();
    }

    @Override
    public Context getApplicationContext() {
        return hostContext.getApplicationContext();
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        hostContext.registerComponentCallbacks(callback);
    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        hostContext.unregisterComponentCallbacks(callback);
    }

    @Override
    public void setTheme(int resid) {
        hostContext.setTheme(resid);
    }

    @Override
    @ViewDebug.ExportedProperty(
        deepExport = true
    )
    public Resources.Theme getTheme() {
        return hostContext.getTheme();
    }

    @Override
    public ClassLoader getClassLoader() {
        return hostContext.getClassLoader();
    }

    @Override
    public String getPackageName() {
        return hostContext.getPackageName();
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return hostContext.getApplicationInfo();
    }

    @Override
    public String getPackageResourcePath() {
        return hostContext.getPackageResourcePath();
    }

    @Override
    public String getPackageCodePath() {
        return hostContext.getPackageCodePath();
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return hostContext.getSharedPreferences(name, mode);
    }

    @Override
    public boolean moveSharedPreferencesFrom(Context sourceContext, String name) {
        return hostContext.moveSharedPreferencesFrom(sourceContext, name);
    }

    @Override
    public boolean deleteSharedPreferences(String name) {
        return hostContext.deleteSharedPreferences(name);
    }

    @Override
    public FileInputStream openFileInput(String name) throws FileNotFoundException {
        return hostContext.openFileInput(name);
    }

    @Override
    public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
        return hostContext.openFileOutput(name, mode);
    }

    @Override
    public boolean deleteFile(String name) {
        return hostContext.deleteFile(name);
    }

    @Override
    public File getFileStreamPath(String name) {
        return hostContext.getFileStreamPath(name);
    }

    @Override
    public File getDataDir() {
        return hostContext.getDataDir();
    }

    @Override
    public File getFilesDir() {
        return hostContext.getFilesDir();
    }

    @Override
    public File getNoBackupFilesDir() {
        return hostContext.getNoBackupFilesDir();
    }

    @Nullable
    @Override
    public File getExternalFilesDir(@Nullable String type) {
        return hostContext.getExternalFilesDir(type);
    }

    @Override
    public File[] getExternalFilesDirs(String type) {
        return hostContext.getExternalFilesDirs(type);
    }

    @Override
    public File getObbDir() {
        return hostContext.getObbDir();
    }

    @Override
    public File[] getObbDirs() {
        return hostContext.getObbDirs();
    }

    @Override
    public File getCacheDir() {
        return hostContext.getCacheDir();
    }

    @Override
    public File getCodeCacheDir() {
        return hostContext.getCodeCacheDir();
    }

    @Nullable
    @Override
    public File getExternalCacheDir() {
        return hostContext.getExternalCacheDir();
    }

    @Override
    public File[] getExternalCacheDirs() {
        return hostContext.getExternalCacheDirs();
    }

    @Override
    public File[] getExternalMediaDirs() {
        return hostContext.getExternalMediaDirs();
    }

    @Override
    public String[] fileList() {
        return hostContext.fileList();
    }

    @Override
    public File getDir(String name, int mode) {
        return hostContext.getDir(name, mode);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return hostContext.openOrCreateDatabase(name, mode, factory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, @Nullable DatabaseErrorHandler errorHandler) {
        return hostContext.openOrCreateDatabase(name, mode, factory, errorHandler);
    }

    @Override
    public boolean moveDatabaseFrom(Context sourceContext, String name) {
        return hostContext.moveDatabaseFrom(sourceContext, name);
    }

    @Override
    public boolean deleteDatabase(String name) {
        return hostContext.deleteDatabase(name);
    }

    @Override
    public File getDatabasePath(String name) {
        return hostContext.getDatabasePath(name);
    }

    @Override
    public String[] databaseList() {
        return hostContext.databaseList();
    }

    @Override
    @Deprecated
    public Drawable getWallpaper() {
        return hostContext.getWallpaper();
    }

    @Override
    @Deprecated
    public Drawable peekWallpaper() {
        return hostContext.peekWallpaper();
    }

    @Override
    @Deprecated
    public int getWallpaperDesiredMinimumWidth() {
        return hostContext.getWallpaperDesiredMinimumWidth();
    }

    @Override
    @Deprecated
    public int getWallpaperDesiredMinimumHeight() {
        return hostContext.getWallpaperDesiredMinimumHeight();
    }

    @Override
    @Deprecated
    public void setWallpaper(Bitmap bitmap) throws IOException {
        hostContext.setWallpaper(bitmap);
    }

    @Override
    @Deprecated
    public void setWallpaper(InputStream data) throws IOException {
        hostContext.setWallpaper(data);
    }

    @Override
    @Deprecated
    public void clearWallpaper() throws IOException {
        hostContext.clearWallpaper();
    }

    @Override
    public void startActivity(Intent intent) {
        hostContext.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        hostContext.startActivity(intent, options);
    }

    @Override
    public void startActivities(Intent[] intents) {
        hostContext.startActivities(intents);
    }

    @Override
    public void startActivities(Intent[] intents, Bundle options) {
        hostContext.startActivities(intents, options);
    }

    @Override
    public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        hostContext.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    @Override
    public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) throws IntentSender.SendIntentException {
        hostContext.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        hostContext.sendBroadcast(intent);
    }

    @Override
    public void sendBroadcast(Intent intent, @Nullable String receiverPermission) {
        hostContext.sendBroadcast(intent, receiverPermission);
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, @Nullable String receiverPermission) {
        hostContext.sendOrderedBroadcast(intent, receiverPermission);
    }

    @Override
    public void sendOrderedBroadcast(@NonNull Intent intent, @Nullable String receiverPermission, @Nullable BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {
        hostContext.sendOrderedBroadcast(intent, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle user) {
        hostContext.sendBroadcastAsUser(intent, user);
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission) {
        hostContext.sendBroadcastAsUser(intent, user, receiverPermission);
    }

    @Override
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {
        hostContext.sendOrderedBroadcastAsUser(intent, user, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Override
    @Deprecated
    public void sendStickyBroadcast(Intent intent) {
        hostContext.sendStickyBroadcast(intent);
    }

    @Override
    @Deprecated
    public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {
        hostContext.sendStickyOrderedBroadcast(intent, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Override
    @Deprecated
    public void removeStickyBroadcast(Intent intent) {
        hostContext.removeStickyBroadcast(intent);
    }

    @Override
    @Deprecated
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {
        hostContext.sendStickyBroadcastAsUser(intent, user);
    }

    @Override
    @Deprecated
    public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {
        hostContext.sendStickyOrderedBroadcastAsUser(intent, user, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Override
    @Deprecated
    public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {
        hostContext.removeStickyBroadcastAsUser(intent, user);
    }

    @Nullable
    @Override
    public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter) {
        return hostContext.registerReceiver(receiver, filter);
    }

    @Nullable
    @Override
    public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter, int flags) {
        return hostContext.registerReceiver(receiver, filter, flags);
    }

    @Nullable
    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, @Nullable String broadcastPermission, @Nullable Handler scheduler) {
        return hostContext.registerReceiver(receiver, filter, broadcastPermission, scheduler);
    }

    @Nullable
    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, @Nullable String broadcastPermission, @Nullable Handler scheduler, int flags) {
        return hostContext.registerReceiver(receiver, filter, broadcastPermission, scheduler, flags);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        hostContext.unregisterReceiver(receiver);
    }

    @Nullable
    @Override
    public ComponentName startService(Intent service) {
        return hostContext.startService(service);
    }

    @Nullable
    @Override
    public ComponentName startForegroundService(Intent service) {
        return hostContext.startForegroundService(service);
    }

    @Override
    public boolean stopService(Intent service) {
        return hostContext.stopService(service);
    }

    @Override
    public boolean bindService(Intent service, @NonNull ServiceConnection conn, int flags) {
        return hostContext.bindService(service, conn, flags);
    }

    @Override
    public void unbindService(@NonNull ServiceConnection conn) {
        hostContext.unbindService(conn);
    }

    @Override
    public boolean startInstrumentation(@NonNull ComponentName className, @Nullable String profileFile, @Nullable Bundle arguments) {
        return hostContext.startInstrumentation(className, profileFile, arguments);
    }

    @Nullable
    @Override
    public Object getSystemService(@NonNull String name) {
        return hostContext.getSystemService(name);
    }

    @Nullable
    @Override
    public String getSystemServiceName(@NonNull Class<?> serviceClass) {
        return hostContext.getSystemServiceName(serviceClass);
    }

    @Override
    public int checkPermission(@NonNull String permission, int pid, int uid) {
        return hostContext.checkPermission(permission, pid, uid);
    }

    @Override
    public int checkCallingPermission(@NonNull String permission) {
        return hostContext.checkCallingPermission(permission);
    }

    @Override
    public int checkCallingOrSelfPermission(@NonNull String permission) {
        return hostContext.checkCallingOrSelfPermission(permission);
    }

    @Override
    public int checkSelfPermission(@NonNull String permission) {
        return hostContext.checkSelfPermission(permission);
    }

    @Override
    public void enforcePermission(@NonNull String permission, int pid, int uid, @Nullable String message) {
        hostContext.enforcePermission(permission, pid, uid, message);
    }

    @Override
    public void enforceCallingPermission(@NonNull String permission, @Nullable String message) {
        hostContext.enforceCallingPermission(permission, message);
    }

    @Override
    public void enforceCallingOrSelfPermission(@NonNull String permission, @Nullable String message) {
        hostContext.enforceCallingOrSelfPermission(permission, message);
    }

    @Override
    public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
        hostContext.grantUriPermission(toPackage, uri, modeFlags);
    }

    @Override
    public void revokeUriPermission(Uri uri, int modeFlags) {
        hostContext.revokeUriPermission(uri, modeFlags);
    }

    @Override
    public void revokeUriPermission(String toPackage, Uri uri, int modeFlags) {
        hostContext.revokeUriPermission(toPackage, uri, modeFlags);
    }

    @Override
    public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
        return hostContext.checkUriPermission(uri, pid, uid, modeFlags);
    }

    @Override
    public int checkCallingUriPermission(Uri uri, int modeFlags) {
        return hostContext.checkCallingUriPermission(uri, modeFlags);
    }

    @Override
    public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
        return hostContext.checkCallingOrSelfUriPermission(uri, modeFlags);
    }

    @Override
    public int checkUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags) {
        return hostContext.checkUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags);
    }

    @Override
    public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {
        hostContext.enforceUriPermission(uri, pid, uid, modeFlags, message);
    }

    @Override
    public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {
        hostContext.enforceCallingUriPermission(uri, modeFlags, message);
    }

    @Override
    public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {
        hostContext.enforceCallingOrSelfUriPermission(uri, modeFlags, message);
    }

    @Override
    public void enforceUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags, @Nullable String message) {
        hostContext.enforceUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags, message);
    }

    @Override
    public Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
        return hostContext.createPackageContext(packageName, flags);
    }

    @Override
    public Context createContextForSplit(String splitName) throws PackageManager.NameNotFoundException {
        return hostContext.createContextForSplit(splitName);
    }

    @Override
    public Context createConfigurationContext(@NonNull Configuration overrideConfiguration) {
        return hostContext.createConfigurationContext(overrideConfiguration);
    }

    @Override
    public Context createDisplayContext(@NonNull Display display) {
        return hostContext.createDisplayContext(display);
    }

    @Override
    public Context createDeviceProtectedStorageContext() {
        return hostContext.createDeviceProtectedStorageContext();
    }

    @Override
    public boolean isRestricted() {
        return hostContext.isRestricted();
    }

    @Override
    public boolean isDeviceProtectedStorage() {
        return hostContext.isDeviceProtectedStorage();
    }
}
