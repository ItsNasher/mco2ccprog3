
import java.util.Objects;
import java.util.ArrayList;
/**
 * The reservation class represents a reservation when simulate booking is used in the reservation system
 * A reservation includes the guest name, its ID, room number, check in and out d ates, total price
 * And an array list that represents each element as the price for that specific date
 */
public class Reservation {
    final String guestName;
    final String reservationId;
    final int roomNumber;
    final int checkIn;
    final int checkOut;
    double totalPrice;
    private ArrayList<Double> pricePerNight;

    /**
     * Constructs a Reservation object given a specified reservation ID.
     * This is used for checking unique ID in methods such as the removeReservation method in Hotel).
     *
     * @param reservationId the reservation ID
     */
    public Reservation(final String reservationId) {
        this.reservationId = reservationId;
        this.guestName = null;
        this.roomNumber = 0;
        this.checkIn = 0;
        this.checkOut = 0;
        this.pricePerNight = null;
        this.totalPrice = 0.0;
    }
    /**
     * Constructs a Reservation object with the specified details.
     *
     * @param pricePerNight     the base price per night for the room
     * @param datePriceModifier the array of price modifiers for each date
     * @param roomNumber        the room number
     * @param name              the guest's name
     * @param reservationId     the reservation ID
     * @param checkIn           the check-in date
     * @param checkOut          the check-out date
     */
    public Reservation(double pricePerNight, double datePriceModifier[], final int roomNumber, final String name, final String reservationId, final int checkIn, final int checkOut){
        this.guestName = name;
        this.reservationId = reservationId;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.pricePerNight = new ArrayList<>();
        for (int i = checkIn-1; i < checkOut; i++){
            this.pricePerNight.add(pricePerNight*datePriceModifier[i]);
        }

        double total = 0.0;
        for (Double price : this.pricePerNight) {
            total += price;
        }
        this.totalPrice = total;
    }
    /**
     * This overrides the equals method for all reservation objects
     * Compares this reservation to the specified object.
     *
     * @param obj the object to compare this reservation with
     * @return true if the object's reservation ID is equal to the reservation object it's comparing with, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation reservation = (Reservation) obj;
        return reservationId == reservation.reservationId;
    }
    /**
     * Returns a hash code value for the reservation.
     * This overrides the hashCode method to return the reservation ID instead
     *
     * @return a hash code value for this reservation
     */
    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }
    /**
     * Prints the basic information of the reservation.
     *
     * @return a string containing the basic information of the reservation
     */
    public String printBasicInfo(){
        return "Guest Name: " + this.guestName + "\n" +
                "Room Number: " + this.roomNumber + "\n" +
                "Reservation ID: " + this.reservationId + "\n" +
                "Check In and Check Out Dates: " + this.checkIn + " to " + this.checkOut + "\n" +
                "Total Price: " + String.format("%.2f",this.totalPrice) + "\n" +
                "Price Per Night" + this.pricePerNight;
    }
    /**
     * Gets the reservation ID.
     *
     * @return the reservation ID
     */
    public String getReservationId(){
        return this.reservationId;
    }
    /**
     * Gets the check-in date.
     *
     * @return the check-in date
     */
    public int getCheckIn(){
        return this.checkIn;
    }
    /**
     * Gets the check-out date.
     *
     * @return the check-out date
     */
    public int getCheckOut(){
        return this.checkOut;
    }
    /**
     * Gets the total price of the reservation.
     *
     * @return the total price of the reservation
     */
    public double getTotalPrice(){
        return this.totalPrice;
    }
    /**
     * Gets the room number.
     *
     * @return the room number
     */
    public int getRoomNumber(){
        return this.roomNumber;
    }
    /**
     * Gets the price per night for a specific date.
     *
     * @param index the index of the date
     * @return the price per night for the specified date
     */
    public double getPricePerNight(int index){
        return this.pricePerNight.get(index);
    }
    /**
     * Sets the price per night for a specific date.
     *
     * @param index the index of the date
     * @param price the new price per night for the specified date
     */
    public void setPricePerNight(int index, double price){
        this.pricePerNight.set(index,price);
    }
    /**
     * Sets the total price of the reservation.
     *
     * @param totalPrice the new total price
     */
    public void setTotalPrice(double totalPrice){
        this.totalPrice = totalPrice;
    }
}
