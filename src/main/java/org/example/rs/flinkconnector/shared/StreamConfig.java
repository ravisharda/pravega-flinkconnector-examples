package org.example.rs.flinkconnector.shared;

import io.pravega.client.stream.ScalingPolicy;
import io.pravega.client.stream.StreamConfiguration;
import lombok.Getter;
import lombok.NonNull;

public class StreamConfig {

    @Getter
    private String streamName;

    @Getter
    private StreamConfiguration conf;

    public StreamConfig(@NonNull String streamName, @NonNull StreamConfiguration config) {
        this.streamName = streamName;
        this.conf = config;
    }

    public StreamConfig(@NonNull String streamName, int numOfSegments) {
        this(streamName,  StreamConfiguration.builder()
                .scalingPolicy(ScalingPolicy.fixed(numOfSegments))
                .build());
    }

    public static StreamConfig of(String streamName, int numOfSegments) {
        return new StreamConfig(streamName, numOfSegments);
    }
}
