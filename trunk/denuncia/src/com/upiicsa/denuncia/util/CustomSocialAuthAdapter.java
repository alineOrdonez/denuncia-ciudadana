package com.upiicsa.denuncia.util;

import java.io.UnsupportedEncodingException;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class CustomSocialAuthAdapter {

	public static SocialAuthAdapter adapter;

	public CustomSocialAuthAdapter() {
		adapter = new SocialAuthAdapter(new ResponseListener());
	}

	public void MyAuthorize(Context context, SocialAuthAdapter.Provider provider) {
		adapter.authorize(context, provider);
	}

	public void MyUpdateStatus(String msg) {
		adapter.updateStatus(msg, new MessageListener(), true);
		Log.e("Message", "Your message hv been updated");
	}

	public void myUpdateStory() throws UnsupportedEncodingException {
		adapter.updateStory(
				"Hello SocialAuth Android" + System.currentTimeMillis(),
				"Google SDK for Android",
				"Build great social apps and get more installs.",
				"The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.",
				"https://www.facebook.com",
				"http://carbonfreepress.gr/images/facebook.png",
				new MessageListener());
		Log.e("Message", "Your message hv been updated");
	}

	class ResponseListener implements DialogListener {

		@Override
		public void onComplete(Bundle values) {

		}

		@Override
		public void onError(SocialAuthError e) {
			// TODO Auto-generated method stub
			Log.d("ShareBar", e.getMessage());

		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			Log.d("ShareBar", "Authentication Cancelled");

		}

		@Override
		public void onBack() {
			// TODO Auto-generated method stub

		}

	}

	private final class MessageListener implements SocialAuthListener<Integer> {
		@Override
		public void onExecute(String provider, Integer t) {
			// TODO Auto-generated method stub
			Integer status = t;
			if (status.intValue() == 200 || status.intValue() == 201
					|| status.intValue() == 204)

				Log.e("Execute", "I am onExecute");

		}

		@Override
		public void onError(SocialAuthError e) {
			// TODO Auto-generated method stub
			Log.e("Error", "I am onExecute Error");

		}
	}
}