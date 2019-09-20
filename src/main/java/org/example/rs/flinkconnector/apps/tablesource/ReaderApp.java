package org.example.rs.flinkconnector.apps.tablesource;

// import io.pravega.client.stream.impl.DefaultCredentials;
// import io.pravega.connectors.flink.FlinkPravegaReader;
// import io.pravega.connectors.flink.PravegaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.rs.flinkconnector.common.serializers.flink.IntegerDeserializationSchema;

@Slf4j
public class ReaderApp {

    /*public static void main(String[] args) throws Exception {

        // Initialize the Flink execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.enableCheckpointing(100);
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, 0L));

        // we currently need this to work around the case where tasks are
        // started too late, a checkpoint was already triggered, and some tasks
        // never see the checkpoint event
        env.getCheckpointConfig().setCheckpointTimeout(20000);

        PravegaConfig conf = PravegaConfig.fromDefaults()
                .withControllerURI(Constants.CONTROLLER_URI)
                .withDefaultScope(Constants.SCOPE)
                .withCredentials(new DefaultCredentials("1111_aaaa", "admin"));
                //.withHostnameValidation(enableHostNameValidation)
                //.withTrustStore(getFileFromResource(CLIENT_TRUST_STORE_FILE));

        // the Pravega reader
        final FlinkPravegaReader<Integer> pravegaSource = FlinkPravegaReader.<Integer>builder()
                .forStream(Constants.STREAM_NAME)
                .enableMetrics(false)
                .withPravegaConfig(conf)
                .withDeserializationSchema(new IntegerDeserializationSchema())
                .build();
        log.info("Done creating Flink Pravega Reader (Source)");

        DataStreamSource streamSource = env.addSource(pravegaSource);
        streamSource.filter(i -> true)
                .print();

        env.execute();

        log.info("Exiting reader...");
    }*/
}
