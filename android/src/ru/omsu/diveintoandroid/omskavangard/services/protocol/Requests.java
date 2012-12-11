package ru.omsu.diveintoandroid.omskavangard.services.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

public class Requests {

	public static final String HTTP = "http";
	public static final String HTTPS = "https";
	public static final String BASE_AUTHORITY = "khlapp.khl.ru";

	public static abstract class Request implements Serializable {

		private static final long serialVersionUID = 4259689143733933949L;

		public abstract HttpRequestBase toHttpRequest() throws RequestBuildingException;

	}

	public static class Games{
		
		public static Request getCurrentTourmentGames(){
			return new Request() {
				
				private static final long serialVersionUID = 8334195613008726479L;

				@Override
				public HttpRequestBase toHttpRequest() throws RequestBuildingException {
					final List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("tournament", "current"));
					return ProtocolUtils.buildGetRequest(HTTP, BASE_AUTHORITY, "/games.php", params);
				}
			};
		}
	}
}