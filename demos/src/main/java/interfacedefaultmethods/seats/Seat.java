package interfacedefaultmethods.seats;

public interface Seat {

    default int getNumberOfSeat() {
        return 5;
    }
}
