import java.util.Objects;
import java.util.ArrayList;

public class Reservation {
    final String guestName;
    final String reservationId;
    final int roomNumber;
    final int checkIn;
    final int checkOut;
    double totalPrice;
    private ArrayList<Double> pricePerNight;

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
    public int getRoomNumber(){
        return this.roomNumber;
    }
    public double getPricePerNight(int index){
        return this.pricePerNight.get(index);
    }
    public void setPricePerNight(int index, double price){
        this.pricePerNight.set(index,price);
    }
    public void setTotalPrice(double totalPrice){
        this.totalPrice = totalPrice;
    }
}
