package interfacedefaultmethods;

import java.time.LocalDateTime;

//interfacet úgy tudunk tesztelni, hogy létrehozunk egy implementációt is
public interface Audited {

    LocalDateTime getCreatedAt();//visszaadja, hogy mikor lett létrehozva

    default boolean createdAfter(LocalDateTime dateTime) {
        //default kulcsszó esetén mindenféleképpen kötelező implementációt megadni
        return getCreatedAt().isAfter(dateTime);
    }

}
