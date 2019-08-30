package org.example.rs.flinkconnector.app1;

import io.pravega.client.stream.impl.DefaultCredentials;
import io.pravega.connectors.flink.FlinkPravegaReader;
import io.pravega.connectors.flink.PravegaConfig;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.rs.flinkconnector.common.serializers.flink.IntegerDeserializationSchema;

public class Reader {

    public static void main(String[] args) {
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


    }
}
