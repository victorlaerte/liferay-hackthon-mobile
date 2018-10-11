package mobile.liferay.com.formsscreenletdemo.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import mobile.liferay.com.formsscreenletdemo.R;

/**
 * @author Victor Oliveira
 */
public class FormsUtil {

	public static String getResourcePath(String serverURL, long formInstanceId) {
		String formEndpoint = "/o/api/p/form/%d?embedded=structure";
		return serverURL + String.format(formEndpoint, formInstanceId);
	}

	public static void setLightStatusBar(Context context, Window window) {
		window.setStatusBarColor(ContextCompat.getColor(context, R.color.login_status_bar_color));
		window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
	}
}
