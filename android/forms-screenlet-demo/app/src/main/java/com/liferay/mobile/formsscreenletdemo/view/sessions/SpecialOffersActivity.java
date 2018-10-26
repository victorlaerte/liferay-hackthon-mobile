package com.liferay.mobile.formsscreenletdemo.view.sessions;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.util.FormsUtil;
import com.liferay.mobile.formsscreenletdemo.view.FormsActivity;
import com.liferay.mobile.screens.base.ModalProgressBarWithLabel;
import com.liferay.mobile.screens.util.AndroidUtil;
import com.liferay.mobile.screens.web.WebListener;
import com.liferay.mobile.screens.web.WebScreenlet;
import com.liferay.mobile.screens.web.WebScreenletConfiguration;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayListener;

public class SpecialOffersActivity extends AppCompatActivity implements WebContentDisplayListener, WebListener {

	private WebScreenlet webScreenlet;
	private LinearLayout bannerLayout;
	private ModalProgressBarWithLabel progressBarWithLabel;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_special_offers);
		toolbar = findViewById(R.id.home_toolbar);
		toolbar.setTitle(R.string.special_offers);
		toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
		setSupportActionBar(toolbar);

		FormsUtil.setLightStatusBar(this, getWindow());

		webScreenlet = findViewById(R.id.web_screenlet);
		progressBarWithLabel = findViewById(R.id.progress_oferta);
		progressBarWithLabel.disableDimBackground();
		progressBarWithLabel.show("Loading....");

		WebScreenletConfiguration.Builder builder =
			new WebScreenletConfiguration.Builder("/web/guest/ofertas");

		builder.addLocalCss("banner.css");

		bannerLayout = findViewById(R.id.banner_layout);

		webScreenlet.setWebScreenletConfiguration(builder.load());
		webScreenlet.setScrollEnabled(true);
		webScreenlet.setListener(this);
		webScreenlet.load();

	}

	@Override
	public void error(Exception e, String userAction) {
		int icon = R.drawable.default_error_icon;
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.lightRed);

		AndroidUtil.showCustomSnackbar(webScreenlet, userAction, Snackbar.LENGTH_LONG, backgroundColor, Color.WHITE,
			icon);
	}

	@Override
	public WebContent onWebContentReceived(WebContent html) {
		return null;
	}

	@Override
	public boolean onUrlClicked(String url) {
		return false;
	}

	@Override
	public boolean onWebContentTouched(View view, MotionEvent event) {
		return false;
	}

	@Override
	public void onPageLoaded(String url) {
		progressBarWithLabel.hide();
		bannerLayout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
		bannerLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void onScriptMessageHandler(String namespace, String body) {

	}

	private void startFormActivity(View view) {
		Intent intent = new Intent(SpecialOffersActivity.this, FormsActivity.class);
		startActivity(intent);
	}
}
