package com.liferay.mobile.formsscreenletdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.liferay.mobile.formsscreenletdemo.util.NotificationUtil;
import java.util.List;

/**
 * @author Victor Oliveira
 */
public class GeofenceTransitionsReceiver extends BroadcastReceiver {
	Context context;
	Intent broadcastIntent = new Intent();

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;

		broadcastIntent.addCategory(Intent.CATEGORY_APP_MAPS);

		GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
		if (geofencingEvent.hasError()) {
			Log.e("AAA", geofencingEvent.getErrorCode() + "");
			return;
		}

		// Get the transition type.
		int geofenceTransition = geofencingEvent.getGeofenceTransition();

		// Test that the reported transition was of interest.
		if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER
			|| geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
			handleEnterExit(geofencingEvent);
		} else {
			handleError(intent);
		}
	}

	private void handleError(Intent intent) {
		// Get the error code
		// Log the error
		Log.e("Test", "TEST");

		// Set the action and error message for the broadcast intent
		broadcastIntent.setAction(Intent.ACTION_APP_ERROR).putExtra("errorMsg", "ERROR");

		// Broadcast the error *locally* to other components in this app
		LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
	}

	private void handleEnterExit(GeofencingEvent geofencingEvent) {
		// Post a notification
		//List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
		//
		//String[] geofenceIds = new String[geofences.size()];
		//String ids = TextUtils.join(GeofenceUtils.GEOFENCE_ID_DELIMITER, geofenceIds);
		//String transitionType = GeofenceUtils.getTransitionString(transition);
		//
		//for (int index = 0; index < geofences.size(); index++) {
		//	Geofence geofence = geofences.get(index);
		//	//...do something with the geofence entry or exit. I'm saving them to a local sqlite db
		//
		//}
		//// Create an Intent to broadcast to the app
		//broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCE_TRANSITION)
		//	.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES)
		//	.putExtra(GeofenceUtils.EXTRA_GEOFENCE_ID, geofenceIds)
		//	.putExtra(GeofenceUtils.EXTRA_GEOFENCE_TRANSITION_TYPE, transitionType);

		//LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);

		NotificationUtil.sendNotification(context, "", "teste", "teste");
	}
}
