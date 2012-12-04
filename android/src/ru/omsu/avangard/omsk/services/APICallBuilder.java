package ru.omsu.avangard.omsk.services;

import ru.omsu.avangard.omsk.services.APIService.APIResultReceiver;
import ru.omsu.avangard.omsk.services.protocol.Requests.Request;
import ru.omsu.avangard.omsk.services.protocol.Responses.Response;
import android.content.Context;
import android.content.Intent;

public class APICallBuilder {
	public static final String SERVICE_ID = APIService.class.getName();
	public static final String TAG = APICallBuilder.class.getSimpleName();

	public static Intent makeIntent(Context context, Request request, Class<? extends Response> responseClass, APIResultReceiver receiver) {
		final Intent intent = new Intent(SERVICE_ID);
		intent.setClass(context, APIService.class);
		intent.putExtra(APIService.INTENT_REQUEST_KEY, request);
		intent.putExtra(APIService.INTENT_RESPONSE_CLASS_KEY, responseClass);
		intent.putExtra(APIService.INTENT_RECEIVER_KEY, receiver);
		return intent;
	}

}