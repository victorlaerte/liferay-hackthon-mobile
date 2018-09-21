package mobile.liferay.com.formsscreenletdemo;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.viewsets.defaultviews.auth.login.LoginView;

/**
 * @author Luísa Lima
 */
public class LoginDemoView extends LoginView {
	public LoginDemoView(Context context) {
		super(context);
	}
	public LoginDemoView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public LoginDemoView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	protected int getLoginEditTextDrawableId() {
		return 0;
	}

	protected void refreshLoginEditTextStyle() {

	}
}
