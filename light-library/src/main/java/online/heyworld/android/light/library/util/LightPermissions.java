package online.heyworld.android.light.library.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunlong.yang on 2019/1/2.
 */

public class LightPermissions {
    public static class PermissionSession{
        Activity activity;
        List<String> permissions;
        List<String> permissionsLoss;
        Runnable onUserDeny;
        Runnable onUserGrant;

        public PermissionSession(Activity activity, List<String> permissions) {
            this.activity = activity;
            this.permissions = permissions;
        }

        public PermissionSession on(Runnable onUserDeny,Runnable onUserGrant){
            this.onUserDeny = onUserDeny;
            this.onUserGrant = onUserGrant;
            return this;
        }

        public void doRequest(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permissionsLoss = new ArrayList<>();
                for(String p : permissions){
                    if(ContextCompat.checkSelfPermission(activity,p)!= PackageManager.PERMISSION_GRANTED){
                        permissionsLoss.add(p);
                    }
                }
                if(permissionsLoss.isEmpty()){
                    onUserGrant.run();
                }else{
                    ActivityCompat.requestPermissions(activity,permissions.toArray(new String[0]),permissionsLoss.hashCode()%10000);
                }
            }else{
                onUserGrant.run();
            }

        }


        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, Activity activity) {
            if(requestCode == permissionsLoss.hashCode()%10000){
                if(activity == this.activity){
                    boolean userGrant = true;
                    for(int result: grantResults){
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            userGrant = false;
                            break;
                        }
                    }
                    if(userGrant){
                        onUserGrant.run();
                    }else{
                        onUserDeny.run();
                    }
                }
            }
        }
    }

    public static PermissionSession setUp(Activity activity,List<String> permissions) {
        return new PermissionSession(activity,permissions);
    }
}
