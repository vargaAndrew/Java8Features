package interfacedefaultmethods;

import java.time.LocalDateTime;

public class Account implements Audited, CreatedAtJanuary {

    private LocalDateTime localDateTime;

    public Account(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return localDateTime;
    }

    //név ütközésnél nem tudja automatikusan feloldani a java, hanem nekünk kell explicit módon megmondani, hogy
    // melyik metódus kerüljön meghívásra
    @Override
    public boolean createdAfter(LocalDateTime dateTime) {
        //hogy ne kelljen kódot duplikálni ezért meghívjuk a megfelelő interfacenak a megfelelő mtódusát
        return Audited.super.createdAfter(dateTime);
        /*
        Ha másik interfaceban lévőt akkor
        return CreatedAtJanuary.super.createdAfter(dateTime);
        * */
    }
}
