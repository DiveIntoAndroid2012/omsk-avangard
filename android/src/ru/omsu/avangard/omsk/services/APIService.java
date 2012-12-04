package ru.omsu.avangard.omsk.services;

import ru.omsu.avangard.omsk.services.network.APIClient;
import ru.omsu.avangard.omsk.services.network.APIClientException;
import ru.omsu.avangard.omsk.services.protocol.Requests.Request;
import ru.omsu.avangard.omsk.services.protocol.Responses.Response;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

public class APIService extends IntentService {

	public static final class Result {
		public static final int STATUS_OK = 0;
		public static final int STATUS_ERROR_NETWORK = 1;
		public static final int STATUS_ERROR_INNER = 3;

		private Response mResponse;
		private Exception mException;
		private int mStatus = STATUS_OK;

		public Response getResponse() {
			return mResponse;
		}

		public boolean isError() {
			return !isOk();
		}

		public boolean isOk() {
			return true;
		}

		public Exception getException() {
			return mException;
		}
	}

	public static abstract class APIResultReceiver extends ResultReceiver {

		public APIResultReceiver() {
			this(new Handler());
		}

		public APIResultReceiver(Handler handler) {
			super(handler);
		}

		public abstract void onResult(Result result);

		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			final Result result = new Result();
			result.mResponse = (Response) resultData.getSerializable("response");
			result.mException = (Exception) resultData.getSerializable("error");
			result.mStatus = resultCode;
			onResult(result);
		}
	}

	private static final String TAG = APIService.class.getSimpleName();
	public static final String INTENT_REQUEST_KEY = "request";
	public static final String INTENT_RESPONSE_CLASS_KEY = "responseClass";
	public static final String INTENT_RESPONSE__HANDLER_CLASS_KEY = "responseHandlerClass";
	public static final String INTENT_RECEIVER_KEY = "receiver";

	private APIClient mClient;

	public APIService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mClient = new APIClient();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onHandleIntent(Intent intent) {
		final Request request = (Request) intent.getSerializableExtra(INTENT_REQUEST_KEY);
		final Class<? extends Response> responseClass = (Class<? extends Response>) intent.getSerializableExtra(INTENT_RESPONSE_CLASS_KEY);
		final ResultReceiver receiver = intent.getParcelableExtra(INTENT_RECEIVER_KEY);
		final Bundle resultData = new Bundle();
		int status = Result.STATUS_OK;
		try {
			final Response response = mClient.execute(request, responseClass);
			resultData.putSerializable("response", response);
		} catch (APIClientException e) {
			Log.d(TAG, "client exception", e);
			resultData.putSerializable("error", e);
			status = clientExceptionToStatus(e);
		} catch (Exception e) {
			Log.w(TAG, "unexpected inner exception", e);
			resultData.putSerializable("error", e);
			status = Result.STATUS_ERROR_INNER;
		}
		if (receiver != null) {
			receiver.send(status, resultData);
		}
	}

	protected int clientExceptionToStatus(APIClientException e) {
		if (e.errorType == APIClientException.NO_ERROR) {
			return Result.STATUS_OK;
		} else if (e.errorType == APIClientException.ERROR_NETWORK || e.errorType == APIClientException.ERROR_NETWORK_STATUS) {
			return Result.STATUS_ERROR_NETWORK;
		} else {
			return Result.STATUS_ERROR_INNER;
		}
	}

}