package net.esmatov.individuals_api.utils;

import java.net.URI;

public class Tools {

    public static String extractUserIdFromLocationHeader(URI locationHeader) {
        String path = locationHeader.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

}
