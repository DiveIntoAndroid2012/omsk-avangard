package ru.omsu.diveintoandroid.omskavangard.services.protocol;

public class RequestBuildingException extends Exception {

	private static final long serialVersionUID = -1823577566600321431L;

	public RequestBuildingException() {
		super();
	}

	public RequestBuildingException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public RequestBuildingException(String detailMessage) {
		super(detailMessage);
	}

	public RequestBuildingException(Throwable throwable) {
		super(throwable);
	}

}