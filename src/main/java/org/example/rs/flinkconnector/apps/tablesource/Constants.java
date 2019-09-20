package org.example.rs.flinkconnector.apps.tablesource;

import java.net.URI;

public class Constants {

    static final String SCOPE = "testScope";
    static final String STREAM_NAME = "testStream";
    static final URI CONTROLLER_URI = URI.create("tcp://localhost:9090");
    static final int NO_OF_SEGMENTS = 1;
    static String READER_GROUP_NAME = "testReaderGroup";
}
