package org.example.rs.flinkconnector.apps.stringreader;

import io.pravega.client.ClientConfig;
import io.pravega.client.ClientFactory;
import io.pravega.client.EventStreamClientFactory;
import io.pravega.client.stream.EventStreamWriter;
import io.pravega.client.stream.EventWriterConfig;
import io.pravega.client.stream.impl.DefaultCredentials;
import io.pravega.client.stream.impl.JavaSerializer;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.example.rs.flinkconnector.common.serializers.pravega.IntegerSerializer;
import org.example.rs.flinkconnector.shared.ScopeAndStreams;
import org.example.rs.flinkconnector.shared.StreamConfig;

@Slf4j
public class Writer {

    public static void main(String[] args) throws InterruptedException {

        ClientConfig clientConfig =  ClientConfig.builder()
                .controllerURI(Constants.CONTROLLER_URI)
                //.trustStore(Constants.TRUSTSTORE_PATH)
                //.validateHostName(false)
                .credentials(new DefaultCredentials("1111_aaaa", "admin"))
                .build();

        @Cleanup
        ScopeAndStreams setup = new ScopeAndStreams(Constants.SCOPE,
                StreamConfig.of(Constants.STREAM_NAME, 1), clientConfig);
        setup.init();

        @Cleanup
        EventStreamWriter<String> writer = EventStreamClientFactory.withScope(Constants.SCOPE, clientConfig)
                .createEventWriter(Constants.STREAM_NAME,
                        new JavaSerializer<String>(),
                        EventWriterConfig.builder().build());
        log.info("Done creating a writer.");

        for (int i = 0; i < 5; i++) {
            String message = "message: " + i;
            writer.writeEvent(message).join();
            log.info("Done writing an event: [{}]", message);

            Thread.sleep(2*1000);
        }
        System.exit(0);
    }
}
