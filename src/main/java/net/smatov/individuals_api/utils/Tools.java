package net.smatov.individuals_api.utils;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class Tools {

    public static String extractUserIdFromLocationHeader(URI locationHeader) {
        String path = locationHeader.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

    public static List<String> extractUserAccountRolesFromJwt(Map<String, Object> resourceAccess) {
        Map accountRoles = (Map) resourceAccess.get("account");
        List<String> roles = (List<String>) accountRoles.get("roles");
        return roles;
    }

}
