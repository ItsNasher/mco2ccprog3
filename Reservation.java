import java.util.Random;
import java.time.Duration;
import java.time.LocalDateTime;
/**
 * Used for creating the reservations for the hotel and rooms.
 */
public class Reservation {
    private String guestName;
    private LocalDateTime checkIn; // LocalDateTime checkIn = LocalDateTime.of(year,month,day,hour,minute) // use 24 hour time
    private LocalDateTime checkOut;
    private Room room;
    private int reservationNumber;
    /**
     * Constructs a reservation with a guest name, check in and check out date and the room.
     * @param guestName name of the guest staying
     * @param checkIn day of checking in the room
     * @param checkOut day of checking out of the room
     * @param room name of the room
     */
    public Reservation(String guestName, LocalDateTime checkIn, LocalDateTime checkOut, Room room) {
        this.guestName = guestName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.room = room;
        this.reservationNumber = randomNumber();
    }
    /**
     * Returns a random reservation ID for the guest.
     * @return random reservation ID
     */
    private int randomNumber (){
        Random rand = new Random ();
        int ran = rand.nextInt(99999) + 100000;
        return ran;
    }

    /**
     * Calculates total price of the reservations based on how long they stayed.
     * @return total price of the reservation
     */
    // getTotalPrice and getCostPerNight gets its data from Room room
    public double getTotalPrice() {
        return this.room.getBasePrice() * Duration.between(checkIn,checkOut).toDays();
    }
    /**
     * Returns base price of the room per night.
     * @return base price of the room
     */
    public double getCostPerNight() {
        return this.room.getBasePrice();
    }
    /**
     * Returns the name of the guest.
     * @return name of the guest
     */
    public String getGuestName() {
        return this.guestName;
    }
    /**
     * Returns the check in date.
     * @return check in date
     */
    public LocalDateTime getCheckInDate() {
        return this.checkIn;
    }
    /**
     * Returns the check out date.
     * @return check out date
     */
    public LocalDateTime getCheckOutDate() {
        return this.checkOut;
    }
    /**
     * Returns the room used for the reservation.
     * @return room used for reservation
     */
    public Room getRoom() {
        return this.room;
    }
    /**
     * Returns the reservation id for the guest.
     * @return reservation id
     */
    public int getReservationId() {
        return this.reservationNumber;
    }
}
