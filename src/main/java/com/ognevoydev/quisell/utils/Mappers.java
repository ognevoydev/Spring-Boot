package com.ognevoydev.quisell.utils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class Mappers {

    public static UUID principalToUUID(Principal principal) {
        Optional<Principal> principalOpt = Optional.ofNullable(principal);
        ArrayList<String> notNullPrincipal = new ArrayList<String>();
        principalOpt.ifPresentOrElse((value) -> {
            notNullPrincipal.add(String.valueOf(principal));
        }, () -> {
            notNullPrincipal.add("00000000-0000-0000-0000-000000000000");});
        return UUID.fromString(String.valueOf(notNullPrincipal.get(0)));
    };

}
