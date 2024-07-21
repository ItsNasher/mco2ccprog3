import java.time.LocalDateTime;
/**
 * Is representation of a room in a hotel
 */
public class Room {
    private String roomName;
    private String roomType;
    private static int roomCounter = 100;
    private double basePrice;
    private LocalDateTime unavailableStartDT;
    private LocalDateTime unavailableEndDT;
    private Reservation[] reservations;
    private int reservationCount;
    /**
     * Constructs a room with a name and a base price.
     * @param roomName name of the room
     * @param basePrice base price of the room
     */
    public Room(int roomName, double basePrice, String roomType) {
        this.roomName = String.valueOf(roomCounter++);
        this.roomType = roomType;
        this.unavailableStartDT = null;
        this.unavailableEndDT = null;
        this.reservations = new Reservation[10];
        this.reservationCount = 0;
        switch (roomType) {
            case "Deluxe":
                this.basePrice = basePrice * 1.20;
                break;
            case "Executive":
                this.basePrice = basePrice * 1.35;
                break;
            default:
                this.basePrice = basePrice;
                break;
        }
    }
    /**
     * Checks if the room is available on the given date.
     * @param checkDate date to check if its available
     * @return True if the room is available, otherwise false
     */
    public boolean isAvailable(LocalDateTime checkDate) {

        /*if the given date is within the start and end of the object's unavailabilityDT then return false
        if (!checkDate.isBefore(unavailableStartDT) && !checkDate.isAfter(unavailableEndDT)) {
            return false;
        } */
        if (unavailableStartDT != null && !checkDate.isBefore(unavailableStartDT))
            return false;
        if (unavailableEndDT != null && !checkDate.isAfter(unavailableEndDT))
            return false;

        //checks if room is available
        for (int i = 0; i < reservationCount; i++){
            Reservation reservation = reservations[i];
            if (!checkDate.isBefore(reservation.getCheckOutDate()) && !checkDate.isAfter(reservation.getCheckInDate())){
                return false; //room is booked
            }
        }

        return true;
    }
    /**
     * Returns the name of the room.
     * @return name of the room
     */
    public String getRoomName() {
        return roomName;
    }
    /**
     * Returns the base price of the room.
     * @return base price of the room
     */
    public double getBasePrice() {
        return basePrice;
    }

    public String getRoomType (){
        return roomType;
    }
    /**
     * Sets a new base price for the rooms.
     * @param newBasePrice new base price for the room
     */
    public void setBasePrice(double newBasePrice) {
        this.basePrice = newBasePrice;
    }
    /**
     * Adds a reservation to the room.
     * @param reservation reservation thats added
     */
    public void addReservation (Reservation reservation){
        if (reservationCount < reservations.length) {
            reservations[reservationCount++] = reservation;
        } else {
            System.out.println("Room is fully booked.");
        }
    }
}