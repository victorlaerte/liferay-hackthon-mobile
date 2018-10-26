package com.liferay.mobile.formsscreenletdemo.push;

import com.liferay.mobile.formsscreenletdemo.util.NotificationUtil;
import com.liferay.mobile.push.PushNotificationsService;
import com.liferay.mobile.screens.util.LiferayLogger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Victor Oliveira
 */
public class PushService extends PushNotificationsService {

	@Override
	public void onPushNotification(JSONObject jsonObject) {
		super.onPushNotification(jsonObject);

		if (jsonObject != null && jsonObject.has("notificationMessage")) {

			try {
				String message = jsonObject.getString("notificationMessage");
				String title = jsonObject.getString("from");

				String extra = jsonObject.toString();
				NotificationUtil.sendNotification(getApplicationContext(), extra, message, title);
			} catch (JSONException e) {
				LiferayLogger.e(e.getMessage(), e);
			}
		}
	}
}