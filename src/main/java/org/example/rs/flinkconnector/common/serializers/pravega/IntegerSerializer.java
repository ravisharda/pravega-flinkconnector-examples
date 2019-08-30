package org.example.rs.flinkconnector.common.serializers.pravega;

import io.pravega.client.stream.Serializer;

import java.nio.ByteBuffer;

public final class IntegerSerializer implements Serializer<Integer> {
    @Override
    public ByteBuffer serialize(Integer value) {
        ByteBuffer result = ByteBuffer.allocate(4).putInt(value);
        result.rewind();
        return result;
    }

    @Override
    public Integer deserialize(ByteBuffer serializedValue) {
        return serializedValue.getInt();
    }
}
