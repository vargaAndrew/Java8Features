package java8io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorizationHeaderTest {

    //megjelent java8-ban SEben a base64 kodolasi lehetoseg
    @Test
    public void testCreate() {//feleadt, tudjuk hogy egy basic authentikacio eseten amikor a bongeszo szeretne
        // elkuldeni a bejelentkezesi adatokat, amit egy formon tudunk megadni a servernek akkor ez basic
        // authentikacio eseten ez ugy tortenik, hogz az authentication ertekkel pontosabban a kulccsal irja be a
        // headerbe. utana megmondja az authentikacio tipusat, hogy basic tipusu authentikacio es utana base64
        // kodolassal elkuldi a felhasznalo nevet osszekonkatenalva a jelszoval es kozejuk egy kettospontot teve. Ez
        // a header fog utazni a servernek (ez nem egy biztonsagos mod, mivel barmikor ki lehet olvasni ezert ezt
        // csak akkor hasznaljuk ha mondjuk https titkositott kapcsolatunk van, mert barki aki le tudja hallgatni az
        // uzeneteket az ebbol:amRvZTpjaGFuZ2VpdA== vissza tudja fejteni a felhasznalo nevet es jelszot)
        assertEquals("Authorization: Basic amRvZTpjaGFuZ2VpdA==", new AuthorizationHeader().create("jdoe", "changeit"));
    }
}
