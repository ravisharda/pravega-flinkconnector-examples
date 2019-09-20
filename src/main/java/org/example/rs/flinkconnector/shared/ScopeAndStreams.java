package org.example.rs.flinkconnector.shared;

import io.pravega.client.ClientConfig;
import io.pravega.client.admin.StreamManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ScopeAndStreams implements AutoCloseable {

    private final String scope;

    private @NonNull final ClientConfig clientConfig;

    private @NonNull final List<StreamConfig> streamConfigs;

    private StreamManager streamManager;

    public ScopeAndStreams(String scope, StreamConfig config, ClientConfig clientConfig) {
        this.scope = scope;
        this.clientConfig = clientConfig;
        if (config != null) {
            this.streamConfigs = Arrays.asList(config);
        } else {
            this.streamConfigs = new ArrayList<>();
        }
    }

    public void init() {
        streamManager = StreamManager.create(clientConfig);
        log.debug("Created a stream manager");

        createScope();

        for(StreamConfig conf : streamConfigs) {
            createStream(conf);
        }
    }

    private void createScope() {
        streamManager.createScope(scope);
        log.debug("Created a scope: " + scope);
    }

    public void createStream(StreamConfig conf) {
        streamManager.createStream(scope, conf.getStreamName(), conf.getConf());
    }

    public void close() {
        if (this.streamManager != null) {
            this.streamManager.close();
        }
    }
}
