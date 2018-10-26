package com.liferay.mobile.formsscreenletdemo.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.view.WorkflowActivity;
import java.util.Random;

/**
 * @author Victor Oliveira
 */
public class NotificationUtil {

	public static void sendNotification(Context context, String extra, String message, String title) {
		Intent homeIntent = new Intent(context, WorkflowActivity.class);
		homeIntent.putExtra("workflow", extra);

		PendingIntent pendingIntent =
			PendingIntent.getActivity(context, 0, homeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder notificationBuilder =
			new NotificationCompat.Builder(context, "default").setLargeIcon(
				BitmapFactory.decodeResource(context.getResources(), R.drawable.icon))
				.setSmallIcon(R.drawable.icon)
				.setContentTitle(title)
				.setContentText(message)
				.setPriority(NotificationCompat.PRIORITY_MAX)
				.setAutoCancel(true)
				.setContentIntent(pendingIntent)
				.setDefaults(Notification.DEFAULT_ALL);

		NotificationManager notificationManager =
			(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		Random random = new Random();
		int myRandomInt = random.nextInt(10000);
		notificationManager.notify(myRandomInt, notificationBuilder.build());
	}
}
