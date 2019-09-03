package com.cloud.common.utils;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * HttpClient池
 *
 * @author danquan.miao
 * @create 2018/10/12
 * @since 1.0.0
 */
public class PoolingHttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoolingHttpClient.class);
    /**
     * the total number of connections
     */
    private static final int MAX_CONNECT_NUMS = 800;
    /**
     * Set the maximum number of concurrent connections per route
     */
    private static final int MAX_CONNECT_PER_ROUTE = MAX_CONNECT_NUMS;

    private static final int DEFAULT_KEEP_ALIVE_MILLISECONDS = (30 * 1000);

    private static final int CONNECT_TIMEOUT_MIS = (10 * 1000);

    private static final int READ_TIMEOUT_MIS = (30 * 1000);

    private static final int WAIT_TIMEOUT_MIS = (10 * 1000);

    private static CloseableHttpClient httpClient = null;

    private static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

    private static ConnectionKeepAliveStrategy keepAliveStrategy = new ConnectionKeepAliveStrategy() {
        @Override
        public long getKeepAliveDuration(HttpResponse response,
                                         HttpContext context) {
            HeaderElementIterator it = new BasicHeaderElementIterator(
                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    return Long.parseLong(value) * 1000;
                }
            }

            return DEFAULT_KEEP_ALIVE_MILLISECONDS;
        }
    };

    private PoolingHttpClient() {

    }

    public static CloseableHttpClient getInstance() {
        try {
            if (null == httpClient) {
                Thread.sleep(500);
                synchronized (PoolingHttpClient.class) {
                    if (null == httpClient) {
                        //max total connection
                        connManager.setMaxTotal(MAX_CONNECT_NUMS);
                        // default max connection per route
                        connManager.setDefaultMaxPerRoute(MAX_CONNECT_PER_ROUTE);

                        // config timeout
                        RequestConfig config = RequestConfig.custom()
                                .setConnectTimeout(CONNECT_TIMEOUT_MIS)
                                .setConnectionRequestTimeout(WAIT_TIMEOUT_MIS)
                                .setSocketTimeout(READ_TIMEOUT_MIS).build();

                        httpClient = HttpClients.custom()
                                .setKeepAliveStrategy(keepAliveStrategy)
                                .setConnectionManager(connManager)
                                .setDefaultRequestConfig(config).build();

                        // detect idle and expired connections and close them
                        IdleConnectionMonitorThread staleMonitor = new IdleConnectionMonitorThread(
                                connManager);
                        staleMonitor.start();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("create CloseableHttpClient exception. e:{}", e);
        }

        return httpClient;
    }

    public static class IdleConnectionMonitorThread extends Thread {
        private final HttpClientConnectionManager connMgr;
        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        // Close expired connections
                        connMgr.closeExpiredConnections();
                        // Optionally, close connections
                        // that have been idle longer than 60 sec
                        connMgr.closeIdleConnections(60, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
                // terminate
                shutdown();
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }

    }


}
