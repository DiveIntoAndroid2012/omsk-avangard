package ru.omsu.avangard.omsk.services.network;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;

public class GZIPHttpClient extends DefaultHttpClient {
        
        public GZIPHttpClient(ClientConnectionManager connManager, HttpParams httpParams) {
                super(connManager, httpParams);
        }

        @Override
        protected BasicHttpProcessor createHttpProcessor() {
                BasicHttpProcessor result = super.createHttpProcessor();

                result.addRequestInterceptor(new HttpRequestInterceptor() {
                        public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
                                if (!request.containsHeader("Accept-Encoding")) {
                                        request.addHeader("Accept-Encoding", "gzip");
                                }
                        }
                });

                result.addResponseInterceptor(new HttpResponseInterceptor() {
                        public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
                                HttpEntity entity = response.getEntity();
                                Header ceheader = entity.getContentEncoding();
                                if (ceheader != null) {
                                        HeaderElement[] codecs = ceheader.getElements();
                                        for (int i = 0; i < codecs.length; i++) {
                                                if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                                                        response.setEntity(new HttpEntityWrapper(entity) {
                                                                public InputStream getContent() throws IOException {
                                                                        return new GZIPInputStream(wrappedEntity.getContent());
                                                                };

                                                                public long getContentLength() {
                                                                        return -1;
                                                                }
                                                        });
                                                        return;
                                                }
                                        }
                                }
                        }
                });
                return result;
        }
}