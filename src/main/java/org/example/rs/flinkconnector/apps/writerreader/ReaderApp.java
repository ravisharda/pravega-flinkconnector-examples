package org.example.rs.flinkconnector.apps.writerreader;

import io.pravega.client.stream.impl.DefaultCredentials;
import io.pravega.connectors.flink.FlinkPravegaReader;
import io.pravega.connectors.flink.PravegaConfig;
import io.pravega.connectors.flink.serialization.PravegaSerialization;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

@Slf4j
public class ReaderApp {

    public static void main(String[] args) throws Exception {

        // Initialize the Flink execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        PravegaConfig conf = PravegaConfig.fromDefaults()
                .withControllerURI(Constants.CONTROLLER_URI)
                .withDefaultScope(Constants.SCOPE)
                .withCredentials(new DefaultCredentials("1111_aaaa", "admin"));
                //.withHostnameValidation(enableHostNameValidation)
                //.withTrustStore(getFileFromResource(CLIENT_TRUST_STORE_FILE));

        // the Pravega reader
        final FlinkPravegaReader<String> pravegaSource = FlinkPravegaReader.<String>builder()
                .forStream(Constants.STREAM_NAME)
                .enableMetrics(false)
                .withPravegaConfig(conf)
                .withDeserializationSchema(PravegaSerialization.deserializationFor(String.class))
                .build();
        log.info("Done creating Flink Pravega Reader (Source)");

        DataStreamSource streamSource = env.addSource(pravegaSource);
        streamSource.print();

        env.execute();

        log.info("Exiting reader...");
    }
}
