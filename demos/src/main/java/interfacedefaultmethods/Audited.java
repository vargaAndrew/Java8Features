package interfacedefaultmethods;

import java.time.LocalDateTime;

public interface Audited {

    LocalDateTime getCreatedAt();//visszaadja, hogy mikor lett létrehozva

    default boolean createdAfter(LocalDateTime dateTime) {
        return getCreatedAt().isAfter(dateTime);
    }

}
