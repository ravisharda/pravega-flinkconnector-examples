package org.example.rs.flinkconnector.apps.stringreader;

import java.net.URI;

public class Constants {

    static final String SCOPE = "stringReaderScope";
    static final String STREAM_NAME = "stringReaderStream";
    static final URI CONTROLLER_URI = URI.create("tcp://localhost:9090");
    static final int NO_OF_SEGMENTS = 1;
    static String READER_GROUP_NAME = "stringReaderTestReaderGroup";
}
