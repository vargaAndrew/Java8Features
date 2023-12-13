package java8io;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthorizationHeader {

    public String create(String username, String password) {//osszekonkatenalja es letrehozza a headert
        String usernameAndPassword = username + ":" + password;
        Base64.Encoder encoder = Base64.getEncoder();
        String encoded = encoder.encodeToString(
            usernameAndPassword.getBytes(
                StandardCharsets.UTF_8));//parameterkent egy byte tombot kell letrehozni. Itt ugye a pass es 
        // usernamebol kell byte tombot letrehozni (getBytes). Valamint adjuk meg a karakter kodolasat (amivel meg
        // van adva a pass es a usernam), hogy ne a
        // platform alapertelmezett karakter enkodolasat hasznalja

        return "Authorization: Basic " + encoded;
    }
}
