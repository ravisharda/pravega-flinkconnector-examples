package org.example.rs.flinkconnector.common.serializers.flink;

import org.apache.flink.api.common.serialization.AbstractDeserializationSchema;

import java.io.IOException;
import java.nio.ByteBuffer;

public class IntegerDeserializationSchema extends AbstractDeserializationSchema<Integer> {
    @Override
    public Integer deserialize(byte[] message) throws IOException {
        return ByteBuffer.wrap(message).getInt();
    }

    @Override
    public boolean isEndOfStream(Integer nextElement) {
        return false;
    }
}
