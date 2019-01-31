package online.heyworld.android.light.route;

import online.heyworld.android.light.MainActivity;
import online.heyworld.android.light.glance.block.BlockActivity;
import online.heyworld.android.light.glance.context.LearnContextActivity;
import online.heyworld.android.light.glance.flutter.FlutterGuide;
import online.heyworld.android.light.glance.math.order.MathOrderActivity;
import online.heyworld.android.light.glance.math.order.MathOrderListActivity;
import online.heyworld.android.light.glance.plugin.PluginIntroActivity;
import online.heyworld.android.light.library.app.activity.ReferenceActivity;
import online.heyworld.android.light.library.app.activity.ReferenceWebActivity;
import online.heyworld.android.light.library.route.ActivityRoute;
import online.heyworld.android.light.plugin.ui.library.PluginLibraryActivity;

public class AppRoute {

    public static void installDefault() {
        ActivityRoute.register("/main", MainActivity.class);
        ActivityRoute.register("/learn_context", LearnContextActivity.class);
        ActivityRoute.register("/reference", ReferenceActivity.class);
        ActivityRoute.register("/reference/web", ReferenceWebActivity.class);
        ActivityRoute.register("/plugin", PluginIntroActivity.class);
        ActivityRoute.register("/plugin/library", PluginLibraryActivity.class);
        ActivityRoute.register("/game/block", BlockActivity.class);
        ActivityRoute.register("/sort", MathOrderActivity.class);
        ActivityRoute.register("/sort_list", MathOrderListActivity.class);
        FlutterGuide flutterGuide = new FlutterGuide();
        if (flutterGuide.isEnable()) {
            ActivityRoute.register("/flutter", flutterGuide.getLaunchActivity());
        }
    }
}
