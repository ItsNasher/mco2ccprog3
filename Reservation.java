import java.util.Objects;

public class Reservation {
    final String guestName;
    final String reservationId;
    final int roomNumber;
    final int checkIn;
    final int checkOut;
    double totalPrice;
    double pricePerNight;

    // this exists for checking unique id
    public Reservation(final String reservationId) {
        this.reservationId = reservationId;
        this.guestName = null;
        this.roomNumber = 0;
        this.checkIn = 0;
        this.checkOut = 0;
        this.pricePerNight = 0.0;
        this.totalPrice = 0.0;
    }

    public Reservation(int pricePerNight, final int roomNumber, final String name, final String reservationId, final int checkIn, final int checkOut){
        this.guestName = name;
        this.reservationId = reservationId;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.pricePerNight = pricePerNight;
        int numOfNights = checkOut - checkIn;
        this.totalPrice = pricePerNight * numOfNights;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation reservation = (Reservation) obj;
        return reservationId == reservation.reservationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }

    public void printBasicInfo(){
        System.out.println("Guest Name: "+this.guestName);
        System.out.println("Room Number: "+this.roomNumber);
        System.out.println("Reservation ID: "+this.reservationId);
        System.out.println("Check In and Out Dates: "+this.checkIn+" to "+this.checkOut);
        System.out.println("Total Price: "+this.totalPrice);
        System.out.println("Price Per Night: "+this.pricePerNight);
    }
    public String getReservationId(){
        return this.reservationId;
    }
    public int getCheckIn(){
        return this.checkIn;
    }
    public int getCheckOut(){
        return this.checkOut;
    }
    public double getTotalPrice(){
        return this.totalPrice;
    }
}
