
import java.util.Objects;
import java.util.ArrayList;
/**
 * Class to handle the reservations of the guests.
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
     * @param reservationId Reservation ID of the guest.
     * Checks for unique ID.
     */
    // this exists for checking unique id
    // see removeReservation in hotel
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
     * @param pricePerNight Price per night of the reservation.
     * @param datePriceModifier Changes on the reservation price based on the days stayed.
     * @param roomNumber Number of the room in the reservation.
     * @param name Name of the guest.
     * @param reservationId Reservation ID given to the guest after succesful reservation.
     * @param checkIn Check in day of the reservation.
     * @param checkOut Check out day of the reservation.
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
     * Compares this Reservation to another object for equality.
     * Two reservations are considered equal if they have the same reservation ID.
     *
     * @param obj The object to compare with this reservation.
     * @return true if the reservations are equal; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation reservation = (Reservation) obj;
        return reservationId == reservation.reservationId;
    }

    /**
     * Returns a hash code value for this reservation.
     * The hash code is based on the reservation ID.
     *
     * @return A hash code value for this reservation.
     */
    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }

    /**
     * @return Returns a string containing the basic information of the reservation.
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
     * @return Returns the reservation ID.
     */
    public String getReservationId(){
        return this.reservationId;
    }
    /**
     * @return Returns the check in day.
     */
    public int getCheckIn(){
        return this.checkIn;
    }
    /**
     * @return Returns the check out day.
     */
    public int getCheckOut(){
        return this.checkOut;
    }
    /**
     * @return Returns the total price of the reservation.
     */
    public double getTotalPrice(){
        return this.totalPrice;
    }
    /**
     * @return Returns the room number.
     */
    public int getRoomNumber(){
        return this.roomNumber;
    }
    /**
     * Returns the price per night for a specific day in the reservation period.
     * @param index The index of the day (0-based) in the reservation period.
     * @return The price per night for the specified day.
     */
    public double getPricePerNight(int index){
        return this.pricePerNight.get(index);
    }
    /**
     * Sets the price per night for a specific day in the reservation period.
     * @param index The index of the day (1-31) in the reservation period.
     * @param price The new price per night for the specified day.
     */
    public void setPricePerNight(int index, double price){
        this.pricePerNight.set(index,price);
    }
    /**
     * Sets the total price of this reservation.
     * @param totalPrice The new total price of the reservation.
     */
    public void setTotalPrice(double totalPrice){
        this.totalPrice = totalPrice;
    }
}
