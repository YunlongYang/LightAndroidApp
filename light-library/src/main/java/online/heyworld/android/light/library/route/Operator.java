package online.heyworld.android.light.library.route;


import java.util.Map;

public interface Operator {
    void back();
    void go(String where,Map<String,Object> args);
    void go(String where);
}
