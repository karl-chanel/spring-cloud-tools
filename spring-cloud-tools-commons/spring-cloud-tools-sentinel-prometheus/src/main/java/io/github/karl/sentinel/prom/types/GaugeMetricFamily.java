package io.github.karl.sentinel.prom.types;


import io.prometheus.client.Collector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GaugeMetricFamily extends Collector.MetricFamilySamples {

    private final List<String> labelNames;

    public GaugeMetricFamily(String name, String help, double value) {
        super(name, Collector.Type.GAUGE, help, new ArrayList<Sample>());
        labelNames = Collections.emptyList();
        samples.add(new Sample(
                name,
                labelNames,
                Collections.<String>emptyList(),
                value));
    }

    public GaugeMetricFamily(String name, String help, List<String> labelNames) {
        super(name, Collector.Type.GAUGE, help, new ArrayList<Sample>());
        this.labelNames = labelNames;
    }

    public GaugeMetricFamily addMetric(List<String> labelValues, double value, long timestampMs) {
        if (labelValues.size() != labelNames.size()) {
            throw new IllegalArgumentException("Incorrect number of labels.");
        }
        samples.add(new Sample(name, labelNames, labelValues, value, timestampMs));
        return this;
    }
}