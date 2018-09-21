package mobile.liferay.com.formsscreenletdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.AndroidUtil;

/**
 * @author Luísa Lima
 */
public class LoginActivity extends AppCompatActivity implements LoginListener {
	CoordinatorLayout coordinatorLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		coordinatorLayout = findViewById(R.id.coordinator);
		LoginScreenlet loginScreenlet = findViewById(R.id.login);
		loginScreenlet.setListener(this);

		getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.login_status_bar_color));
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

	}

	@Override
	public void onLoginSuccess(User user) {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}

	@Override
	public void onLoginFailure(Exception e) {
		int icon = R.drawable.default_error_icon;
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.lightRed);
		int textColor = Color.WHITE;
		String message = getString(R.string.login_failed);

		AndroidUtil.showCustomSnackbar(coordinatorLayout, message, Snackbar.LENGTH_LONG, backgroundColor, textColor,
			icon);
	}

	@Override
	public void onAuthenticationBrowserShown() {

	}
}
