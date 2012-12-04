package ru.omsu.avangard.omsk.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class LifecycleLogActivity extends Activity {

	private static final String TAG = LifecycleLogActivity.class.getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, getClass().getSimpleName() + " onCreate");
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG, getClass().getSimpleName() + " onDestroy");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v(TAG, getClass().getSimpleName() + " onPause");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.v(TAG, getClass().getSimpleName() + " onRestart");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.v(TAG, getClass().getSimpleName() + " onRestoreInstanceState");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(TAG, getClass().getSimpleName() + " onResume");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.v(TAG, getClass().getSimpleName() + " onSaveInstanceState");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.v(TAG, getClass().getSimpleName() + " onStart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.v(TAG, getClass().getSimpleName() + " onStop");
	}

}
