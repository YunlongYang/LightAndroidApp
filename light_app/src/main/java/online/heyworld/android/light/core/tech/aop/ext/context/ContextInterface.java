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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 2018/12/27.
 */

public interface ContextInterface{
   AssetManager getAssets();

   Resources getResources();

   PackageManager getPackageManager();

   ContentResolver getContentResolver();

   Looper getMainLooper();

   Context getApplicationContext();

   void registerComponentCallbacks(ComponentCallbacks callback);

   void unregisterComponentCallbacks(ComponentCallbacks callback);

   void setTheme(int resid);

   Resources.Theme getTheme();

   ClassLoader getClassLoader();

   String getPackageName();

   ApplicationInfo getApplicationInfo();

   String getPackageResourcePath();

   String getPackageCodePath();

   SharedPreferences getSharedPreferences(String name, int mode);

   boolean moveSharedPreferencesFrom(Context sourceContext, String name);

   boolean deleteSharedPreferences(String name);

   FileInputStream openFileInput(String name) throws FileNotFoundException;

   FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException;

   boolean deleteFile(String name);

   File getFileStreamPath(String name);

   File getDataDir();

   File getFilesDir();

   File getNoBackupFilesDir();

    @Nullable
   File getExternalFilesDir(@Nullable String type);

   File[] getExternalFilesDirs(String type);

   File getObbDir();

   File[] getObbDirs();

   File getCacheDir();

   File getCodeCacheDir();

    @Nullable
   File getExternalCacheDir();

   File[] getExternalCacheDirs();

   File[] getExternalMediaDirs();

   String[] fileList();

   File getDir(String name, int mode);

   SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory);

   SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, @Nullable DatabaseErrorHandler errorHandler);

   boolean moveDatabaseFrom(Context sourceContext, String name);

   boolean deleteDatabase(String name);

   File getDatabasePath(String name);

   String[] databaseList();

   Drawable getWallpaper();

   Drawable peekWallpaper();

   int getWallpaperDesiredMinimumWidth();

   int getWallpaperDesiredMinimumHeight();

   void setWallpaper(Bitmap bitmap) throws IOException;

   void setWallpaper(InputStream data) throws IOException;

   void clearWallpaper() throws IOException;

   void startActivity(Intent intent);

   void startActivity(Intent intent, @Nullable Bundle options);

   void startActivities(Intent[] intents);

   void startActivities(Intent[] intents, Bundle options);

   void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException;

   void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) throws IntentSender.SendIntentException;

   void sendBroadcast(Intent intent);

   void sendBroadcast(Intent intent, @Nullable String receiverPermission);

   void sendOrderedBroadcast(Intent intent, @Nullable String receiverPermission);

   void sendOrderedBroadcast(@NonNull Intent intent, @Nullable String receiverPermission, @Nullable BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras);

   void sendBroadcastAsUser(Intent intent, UserHandle user);

   void sendBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission);

   void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras);

   void sendStickyBroadcast(Intent intent);

   void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras);

   void removeStickyBroadcast(Intent intent);

   void sendStickyBroadcastAsUser(Intent intent, UserHandle user);

   void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras);

   void removeStickyBroadcastAsUser(Intent intent, UserHandle user);

    @Nullable
   Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter);

    @Nullable
   Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter, int flags);

    @Nullable
   Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, @Nullable String broadcastPermission, @Nullable Handler scheduler);

    @Nullable
   Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, @Nullable String broadcastPermission, @Nullable Handler scheduler, int flags);

   void unregisterReceiver(BroadcastReceiver receiver);

    @Nullable
   ComponentName startService(Intent service);

    @Nullable
   ComponentName startForegroundService(Intent service);

   boolean stopService(Intent service);

   boolean bindService(Intent service, @NonNull ServiceConnection conn, int flags);

   void unbindService(@NonNull ServiceConnection conn);

   boolean startInstrumentation(@NonNull ComponentName className, @Nullable String profileFile, @Nullable Bundle arguments);

    @Nullable
   Object getSystemService(@NonNull String name);

    @Nullable
   String getSystemServiceName(@NonNull Class<?> serviceClass);

   int checkPermission(@NonNull String permission, int pid, int uid);

   int checkCallingPermission(@NonNull String permission);

   int checkCallingOrSelfPermission(@NonNull String permission);

   int checkSelfPermission(@NonNull String permission);

   void enforcePermission(@NonNull String permission, int pid, int uid, @Nullable String message);

   void enforceCallingPermission(@NonNull String permission, @Nullable String message);

   void enforceCallingOrSelfPermission(@NonNull String permission, @Nullable String message);

   void grantUriPermission(String toPackage, Uri uri, int modeFlags);

   void revokeUriPermission(Uri uri, int modeFlags);

   void revokeUriPermission(String toPackage, Uri uri, int modeFlags);

   int checkUriPermission(Uri uri, int pid, int uid, int modeFlags);

   int checkCallingUriPermission(Uri uri, int modeFlags);

   int checkCallingOrSelfUriPermission(Uri uri, int modeFlags);

   int checkUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags);

   void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message);

   void enforceCallingUriPermission(Uri uri, int modeFlags, String message);

   void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message);

   void enforceUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags, @Nullable String message);

   Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException;

   Context createContextForSplit(String splitName) throws PackageManager.NameNotFoundException;

   Context createConfigurationContext(@NonNull Configuration overrideConfiguration);

   Context createDisplayContext(@NonNull Display display);

   Context createDeviceProtectedStorageContext();

    boolean isRestricted();

   boolean isDeviceProtectedStorage();
}
