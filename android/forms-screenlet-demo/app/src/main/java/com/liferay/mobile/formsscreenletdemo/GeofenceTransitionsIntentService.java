package com.liferay.mobile.formsscreenletdemo;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.liferay.mobile.formsscreenletdemo.util.NotificationUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Victor Oliveira
 */
public class GeofenceTransitionsIntentService extends IntentService {

	public GeofenceTransitionsIntentService() {
		super("GeofenceTransitionsIntentService");
	}

	@Override
	public void onHandleIntent(Intent intent) {
		//GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
		//if (geofencingEvent.hasError()) {
		//	Log.e("AAA", geofencingEvent.getErrorCode() + "");
		//	return;
		//}
		//
		//// Get the transition type.
		//int geofenceTransition = geofencingEvent.getGeofenceTransition();
		//
		//// Test that the reported transition was of interest.
		//if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER
		//	|| geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
		//
		//	// Get the geofences that were triggered. A single event can trigger
		//	// multiple geofences.
		//	List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
		//
		//	// Get the transition details as a String.
		//	String geofenceTransitionDetails =
		//		getGeofenceTransitionDetails(this, geofenceTransition, triggeringGeofences);
		//
		//	// Send notification and log the transition details.
		//	sendNotification(geofenceTransitionDetails);
		//	Log.i("TEST", geofenceTransitionDetails);
		//} else {
		//	// Log the error.
		//	//Log.e("TEST", "ERRO", geofenceTransition);
		//}
	}

	//private String getTransitionString(int transitionType) {
		//switch (transitionType) {
		//	case Geofence.GEOFENCE_TRANSITION_ENTER:
		//		return "transition - enter";
		//	case Geofence.GEOFENCE_TRANSITION_EXIT:
		//		return "transition - exit";
		//	default:
		//		return "unknown transition";
		//}
	//}

	//private String getGeofenceTransitionDetails(Context context, int geofenceTransition,
	//	List<Geofence> triggeringGeofences) {
	//
	//	String geofenceTransitionString = getTransitionString(geofenceTransition);
	//
	//	// Get the Ids of each geofence that was triggered.
	//	ArrayList triggeringGeofencesIdsList = new ArrayList();
	//	for (Geofence geofence : triggeringGeofences) {
	//		triggeringGeofencesIdsList.add(geofence.getRequestId());
	//	}
	//	String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);
	//
	//	return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
	//}

	private void sendNotification(String geofenceTransitionDetails) {
		Log.d("test", "send Notification");
		NotificationUtil.sendNotification(getApplicationContext(), "", "Localização perto", "Promoção carai");
	}
}
