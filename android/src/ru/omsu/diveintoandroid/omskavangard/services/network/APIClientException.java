package ru.omsu.diveintoandroid.omskavangard.services.network;

public class APIClientException extends Exception {

        private static final long serialVersionUID = 34138947193281L;
        public static final int NO_ERROR = 0;
        public static final int ERROR_NETWORK = 1;
        public static final int ERROR_NETWORK_STATUS = 2;
        public static final int ERROR_PROTOCOL = 3;
        public static final int ERROR_INNER = 4;

        public int errorType = NO_ERROR;

        public APIClientException(int type, String detailMessage, Throwable throwable) {
                super(detailMessage, throwable);
                errorType = type;
        }

        public APIClientException(int type, String detailMessage) {
                super(detailMessage);
                errorType = type;
        }

        public int getErrorType() {
                return errorType;
        }
}