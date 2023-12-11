package interfacedefaultmethods;

import java.time.LocalDateTime;

public interface CreatedAtJanuary {

    LocalDateTime getCreatedAt();//visszaadja, hogy mikor lett létrehozva

    default boolean createdAfter(LocalDateTime dateTime) {
        //default kulcsszó esetén mindenféleképpen kötelező implementációt megadni
        return getCreatedAt().isAfter(dateTime);
    }

}
