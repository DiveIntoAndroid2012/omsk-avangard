package ru.omsu.avangard.omsk.services.protocol;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import android.net.Uri;
import android.webkit.MimeTypeMap;

public class ProtocolUtils {

	public static HttpGet buildGetRequest(String scheme, String authority, String path, List<NameValuePair> params) throws RequestBuildingException {
		HttpGet get = new HttpGet();
		Uri.Builder b = new Uri.Builder();
		b.scheme(scheme);
		b.authority(authority);
		b.appendEncodedPath(path);
		if (params == null || params.isEmpty()) {
		} else {
			for (NameValuePair p : params) {
				b.appendQueryParameter(p.getName(), p.getValue());
			}
		}
		try {
			get.setURI(new URI(b.build().toString()));
		} catch (URISyntaxException e) {
			throw new RequestBuildingException(e);
		}
		return get;
	}

	public static HttpPost buildPostRequest(String scheme, String authority, String path, List<NameValuePair> params) throws RequestBuildingException {
		HttpPost post = new HttpPost();
		Uri.Builder b = new Uri.Builder();
		b.scheme(scheme);
		b.authority(authority);
		b.appendEncodedPath(path);

		try {
			if (params != null) {
				post.setEntity(new UrlEncodedFormEntity(params));
			}
			post.setURI(new URI(b.build().toString()));
			return post;
		} catch (URISyntaxException e) {
			throw new RequestBuildingException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RequestBuildingException(e);
		}
	}

	public static HttpPost buildMultipartPostRequest(String scheme, String authority, String path, List<NameValuePair> params) throws RequestBuildingException {
		HttpPost post = new HttpPost();
		Uri.Builder b = new Uri.Builder();
		b.scheme(scheme);
		b.authority(authority);
		b.appendEncodedPath(path);

		try {
			MultipartEntity mpEntity = new MultipartEntity();
			for (NameValuePair pair : params) {
				final String value = pair.getValue();
				if (value.startsWith("file://")) {
					URI fileURI = new URI(value);
					final String filename = fileURI.getPath();
					mpEntity.addPart(pair.getName(), new FileBody(new File(filename), getMimeType(filename)));
				} else {
					mpEntity.addPart(pair.getName(), new StringBody(value, Charset.forName("UTF-8")));
				}
			}
			post.setEntity(mpEntity);
			post.setURI(new URI(b.build().toString()));
			return post;
		} catch (URISyntaxException e) {
			throw new RequestBuildingException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RequestBuildingException(e);
		}
	}

	public static String getMimeType(String filename) {
		String type = null;
		String extension = MimeTypeMap.getFileExtensionFromUrl(filename);
		if (extension != null) {
			MimeTypeMap mime = MimeTypeMap.getSingleton();
			type = mime.getMimeTypeFromExtension(extension);
		}
		return type;
	}

}