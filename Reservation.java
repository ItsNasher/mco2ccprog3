import java.util.Objects;

public class Reservation {
    String guestName;
    String reservationId;
    int roomNumber;
    int checkIn;
    int checkOut;
    double totalPrice;
    double pricePerNight;

    // this exists for checking unique id
    public Reservation(String reservationId) {
        this.reservationId = reservationId;
    }

    public Reservation(int pricePerNight, int roomNumber, String name, String reservationId, int checkIn, int checkOut){
        this.guestName = name;
        this.reservationId = reservationId;
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
