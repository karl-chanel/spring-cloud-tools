package io.github.karl.sentinel.prom;


import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.log.RecordLog;
import io.github.karl.sentinel.prom.collector.SentinelCollector;
import io.github.karl.sentinel.prom.config.PrometheusGlobalConfig;
import io.prometheus.client.exporter.HTTPServer;

public class PromExporterInit implements InitFunc {

    @Override
    public void init() throws Exception {
        HTTPServer server = null;
        try {
            new SentinelCollector().register();
            // 开启http服务供prometheus调用
            // 默认只提供一个接口 http://ip:port/metrics，返回所有指标
            int promPort = PrometheusGlobalConfig.getPromFetchPort();
            server = new HTTPServer(promPort);
        } catch (Throwable e) {
            RecordLog.warn("[PromExporterInit] failed to init prometheus exporter with exception:", e);
        }

        HTTPServer finalServer = server;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (finalServer != null) {
                finalServer.stop();
            }
        }));
    }

}
