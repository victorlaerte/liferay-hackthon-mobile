package com.liferay.mobile.formsscreenletdemo.view;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.liferay.apio.consumer.model.Thing;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.formsscreenletdemo.GeofenceTransitionsIntentService;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.util.PersonUtil;
import com.liferay.mobile.formsscreenletdemo.view.login.LoginActivity;
import com.liferay.mobile.formsscreenletdemo.view.sessions.SpecialOffersActivity;
import com.liferay.mobile.push.Push;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Custom;
import com.liferay.mobile.screens.util.AndroidUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Luísa Lima
 * @author Victor Oliveira
 */
public class HomeActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

	private DrawerLayout drawerLayout;
	private NavigationView navigationView;
	private ThingScreenlet userPortrait;
	private Toolbar toolbar;
	private TextView userName;
	private static final int PORTRAIT_WIDTH = 90;
	private static final int PORTRAIT_HEIGHT = 90;
	private final int REQUEST_PERMISSION_PHONE_STATE = 1;
	private final double LATITUDE = -8.0211708;
	private final double LONGITUDE = -34.9246806;
	private PendingIntent mGeofencePendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		toolbar = findViewById(R.id.home_toolbar);
		toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
		toolbar.setTitle("Extrato");
		setSupportActionBar(toolbar);
		setupForPushNotification();

		if (savedInstanceState == null) {
			checkForDraft();
		}

		setupNavigationDrawer();

		if (savedInstanceState == null) {
			try {
				loadPortrait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Log.d("test", getMockedJSON().toString());
		//setupGeoLocation();

	}

	private void setupGeoLocation() {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
			!= PackageManager.PERMISSION_GRANTED
			&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
			!= PackageManager.PERMISSION_GRANTED) {

			ActivityCompat.requestPermissions(this, new String[] {
				Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
			}, REQUEST_PERMISSION_PHONE_STATE);
			return;
		}

		GeofencingClient mGeofencingClient = LocationServices.getGeofencingClient(this);
		mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
			.addOnSuccessListener(this, aVoid -> {
				Log.d("TEST", "hahaha");
			})
			.addOnFailureListener(this, e -> {
				Log.d("TEST", "hahaha");
			});
	}

	private GeofencingRequest getGeofencingRequest() {
		List<Geofence> mGeofenceList = new ArrayList<>();

		Geofence geofencePaoDeAcucar = new Geofence.Builder().setRequestId("paoacucar")
			.setCircularRegion(LATITUDE, LONGITUDE, 200)
			.setExpirationDuration(1000000)
			.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
			.build();

		mGeofenceList.add(geofencePaoDeAcucar);

		GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
		builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
		builder.addGeofences(mGeofenceList);

		return builder.build();
	}

	private PendingIntent getGeofencePendingIntent() {
		// Reuse the PendingIntent if we already have it.
		if (mGeofencePendingIntent != null) {
			return mGeofencePendingIntent;
		}
		Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
		// We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
		// calling addGeofences() and removeGeofences().
		mGeofencePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.
			FLAG_UPDATE_CURRENT);
		return mGeofencePendingIntent;
	}

	private void setupForPushNotification() {
		Session session = SessionContext.createSessionFromCurrentSession();

		try {
			Push.with(session).withPortalVersion(71).onSuccess(jsonObject -> {

				try {
					String registrationId = jsonObject.getString("token");
					LiferayLogger.d("Device registrationId: " + registrationId);
				} catch (JSONException e) {
					LiferayLogger.e(e.getMessage(), e);
				}
			}).onFailure(e -> {
				LiferayLogger.e(e.getMessage(), e);
			}).register(this, getString(R.string.push_sender_id));
		} catch (Exception e) {
			LiferayLogger.e(e.getMessage(), e);
		}
	}

	@Override
	public void onBackPressed() {
		if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
			drawerLayout.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				drawerLayout.openDrawer(GravityCompat.START);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void selectDrawerItem(MenuItem item) {
		switch (item.getItemId()) {
			//case R.id.blog_postings:
			//	startActivity(BlogPostingsActivity.class);
			//	break;
			case R.id.special_offers:
				startActivity(SpecialOffersActivity.class);
				break;
			case R.id.sign_out:
				signOut();
				break;
		}
	}

	private void signOut() {
		SessionContext.logout();
		SessionContext.removeStoredCredentials(CredentialsStorageBuilder.StorageType.SHARED_PREFERENCES);

		finish();

		startActivity(LoginActivity.class);
	}

	private void startActivity(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}

	private void checkForDraft() {
		//String server = getResources().getString(R.string.liferay_server);
		//String url = FormsUtil.getResourcePath(server, Constants.FORM_INSTANCE_ID);
		//
		//new APIOFetchResourceService().fetchResource(url, this::onThingLoaded, this::logError);
	}

	private Unit logError(Exception e) {
		LiferayLogger.e(e.getMessage());
		return Unit.INSTANCE;
	}

	private Unit onThingLoaded(Thing thing) {
		loadDraft(thing);
		return Unit.INSTANCE;
	}

	private void loadDraft(Thing thing) {
		//new APIOFetchLatestDraftService().fetchLatestDraft(thing, this::onDraftLoaded, this::logError);
	}

	private void loadPortrait() throws Exception {
		String url =
			PersonUtil.getResourcePath(getResources().getString(R.string.liferay_server), SessionContext.getUserId());

		userPortrait.load(url, new Custom("portrait"), SessionContext.getCredentialsFromCurrentSession());
	}

	private Unit onDraftLoaded(Thing thing) {
		if (thing != null) {
			FormInstanceRecord formInstanceRecord = FormInstanceRecord.getConverter().invoke(thing);

			if (formInstanceRecord != null) {
				setupDialog();
			}
		}

		return Unit.INSTANCE;
	}

	private void setupDrawerContent(NavigationView navigationView) {
		navigationView.setNavigationItemSelectedListener(item -> {
			selectDrawerItem(item);
			return true;
		});
	}

	private void setupDialog() {
		//LayoutInflater inflater = getLayoutInflater();
		//View dialogView = inflater.inflate(R.layout.custom_dialog, null);
		//Button positiveButton = dialogView.findViewById(R.id.dialog_positive_button);
		//Button negativeButton = dialogView.findViewById(R.id.dialog_negative_button);
		//
		//AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
		//builder.setView(dialogView);
		//AlertDialog alertDialog = builder.create();
		//
		//negativeButton.setOnClickListener(v -> alertDialog.dismiss());
		//positiveButton.setOnClickListener(view -> {
		//	alertDialog.dismiss();
		//	startFormActivity(view);
		//});
		//
		//alertDialog.show();
	}

	private void setupNavigationDrawer() {
		drawerLayout = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle =
			new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();

		navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);

		setupDrawerContent(navigationView);

		View navHeaderView = navigationView.getHeaderView(0);
		userName = navHeaderView.findViewById(R.id.user_name);
		userName.setText(SessionContext.getCurrentUser().getFullName());

		userPortrait = navHeaderView.findViewById(R.id.user_portrait);
	}

	private Unit showError(String message) {

		int icon = R.drawable.default_error_icon;
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.lightRed);

		AndroidUtil.showCustomSnackbar(userPortrait, message, Snackbar.LENGTH_LONG, backgroundColor, Color.WHITE, icon);

		return Unit.INSTANCE;
	}

	private JSONObject getMockedJSON() {
		try {

			JSONObject json = new JSONObject();
			JSONArray periods = new JSONArray();
			JSONObject october = new JSONObject();
			JSONArray purchases = new JSONArray();

			JSONObject purchaseRow = new JSONObject();
			purchaseRow.put("data", "10/10/18");
			purchaseRow.put("description", "Netflix");
			purchaseRow.put("value", 50.00);

			purchases.put(purchaseRow);
			october.put("total", 1.450);
			october.put("purchases", purchases);

			periods.put(october);

			json.put("periods", periods);

			return json;
		} catch (Exception e) {

		}

		return null;
	}
}
