package mobile.liferay.com.formsscreenletdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import com.liferay.apio.consumer.ApioConsumerKt;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;

/**
 * @author Luísa Lima
 */
public class FormsActivity extends AppCompatActivity {

	private ThingScreenlet forms;
	private ProgressBar progressBar;
	private long formInstanceId = 36466;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forms);

		forms = findViewById(R.id.forms);
		progressBar = findViewById(R.id.forms_progress_bar);

		if (savedInstanceState == null) {
			loadResource();
		}
	}

	private String getResourcePath() {
		String serverUrl = getResources().getString(R.string.liferay_server);
		String formEndpoint = "/o/api/p/form-instance/%d?embedded=structure";

		return serverUrl + String.format(formEndpoint, formInstanceId);
	}

	private void loadResource() {
		String url = getResourcePath();

		progressBar.setVisibility(View.VISIBLE);

		forms.setVisibility(View.GONE);
		forms.load(url, Detail.INSTANCE, ApioConsumerKt.getCredentials(), thingScreenlet -> {
			progressBar.setVisibility(View.GONE);
			forms.setVisibility(View.VISIBLE);

			return null;
		});
	}
}
