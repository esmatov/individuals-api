package net.smatov.individuals_api.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestTestObjects {

    public static Map<String, Object> validUserTonyHawkRegistrationData() {
        return new HashMap<>() {{
            put("username", "tonyhawk");
            put("firstName", "Tony");
            put("lastName", "Hawk");
            put("email", "thawk@email.com");
            put("password", "test123");
            put("confirmPassword", "test123");
        }};
    }

    public static Map<String, Object> validUserTonyHawkLoginData() {
        return new HashMap<>() {{
            put("username", "tonyhawk");
            put("password", "test123");
        }};
    }

    public static Map<String, Object> validUserNinjaTurtleRegistrationData() {
        return new HashMap<>() {{
            put("username", "mutantninjaturtle");
            put("firstName", "Raphael");
            put("lastName", "Turtle");
            put("email", "raphael@turtles.com");
            put("enabled", "true");
            put("credentials", List.of(new HashMap<>() {{
                put("type", "password");
                put("value", "booyakasha123");
                put("temporary", "false");
            }}));
        }};
    }

    public static Map<String, Object> validUserNinjaTurtleLoginData() {
        return new HashMap<>() {{
            put("username", "mutantninjaturtle");
            put("password", "booyakasha123");
        }};
    }

    public static Map<String, Object> validUserWalterWhiteRegistrationData() {
        return new HashMap<>() {{
            put("username", "heisenberg");
            put("firstName", "Walter");
            put("lastName", "White");
            put("email", "walter.white@abqhigh.edu");
            put("enabled", "true");
            put("credentials", List.of(new HashMap<>() {{
                put("type", "password");
                put("value", "saymyname");
                put("temporary", "false");
            }}));
        }};
    }

    public static Map<String, Object> validUserWalterWhiteLoginData() {
        return new HashMap<>() {{
            put("username", "heisenberg");
            put("password", "saymyname");
        }};
    }

    public static Map<String, Object> validUserAndreyTarkovskyRegistrationData() {
        return new HashMap<>() {{
            put("username", "andrey_tarkovsky");
            put("firstName", "Andrei");
            put("lastName", "Tarkovsky");
            put("email", "andrei.tarkovsky@cinema.com");
            put("enabled", "true");
            put("credentials", List.of(new HashMap<>() {{
                put("type", "password");
                put("value", "solaris1972");
                put("temporary", "false");
            }}));
        }};
    }

    public static Map<String, Object> validUserAndreyTarkovskyLoginData() {
        return new HashMap<>() {{
            put("username", "andrey_tarkovsky");
            put("password", "solaris1972");
        }};
    }

}
