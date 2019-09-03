package org.example.rs.flinkconnector.apps.writerreader;

import io.pravega.client.stream.impl.DefaultCredentials;
import io.pravega.connectors.flink.FlinkPravegaWriter;
import io.pravega.connectors.flink.PravegaConfig;
import io.pravega.connectors.flink.PravegaEventRouter;
import io.pravega.connectors.flink.serialization.PravegaSerialization;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

@Slf4j
public class WriterApp {

    public static void main(String[] args) throws Exception {
        // Initialize the Flink execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        PravegaConfig conf = PravegaConfig.fromDefaults()
                .withControllerURI(Constants.CONTROLLER_URI)
                .withDefaultScope(Constants.SCOPE)
                .withCredentials(new DefaultCredentials("1111_aaaa", "admin"));
        //.withHostnameValidation(enableHostNameValidation)
        //.withTrustStore(getFileFromResource(CLIENT_TRUST_STORE_FILE));

        final FlinkPravegaWriter<String> writer = FlinkPravegaWriter.<String>builder()
                .forStream(Constants.STREAM_NAME)
                .enableMetrics(false)
                .withPravegaConfig(conf)
                .withEventRouter(new PravegaEventRouter<String>() {
                    @Override
                    public String getRoutingKey(String event) {
                        return "RoutingKey";
                    }
                })
                .withSerializationSchema(PravegaSerialization.serializationFor(String.class))
                .build();
        log.info("Done creating Flink Pravega Reader (Source)");

        /*DataStream<Tuple2<String, Integer>> inputStream = env.fromElements(
                Tuple2.of("Adam", 17),
                Tuple2.of("Sarah", 23),
                Tuple2.of("Hari", 27),
                Tuple2.of("Mani", 29));*/
        DataStream<String> inputStream = env.fromElements("message 1", "message 2", "message 3");

        inputStream.addSink(writer).name("Pravega Stream");

        env.execute("whateverJobName");
    }
}
