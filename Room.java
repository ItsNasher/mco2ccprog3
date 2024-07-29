
import java.util.ArrayList;

public class Room {
    int roomNumber;
    int type;   // 0 is standard, 1 is deluxe, 2 is executive
    double actualPrice;
    private ArrayList<Reservation> reservationList;

    public Room (int type, int roomNumber, double basePrice){
        this.type = type;
        this.roomNumber = roomNumber;
        this.reservationList = new ArrayList<>();
        setActualPrice(basePrice);
    }
    public void setActualPrice(double basePrice){
        switch (this.type) {
            case 0:
                this.actualPrice = (basePrice * 1);
                break;
            case 1:
                this.actualPrice = (basePrice * 1.2);
                break;
            case 2:
                this.actualPrice = (basePrice * 1.35);
                break;
        }
    }
    public void addReservation(Reservation res){
        this.reservationList.add(res);
    }
    public void removeReservation(Reservation res){
        this.reservationList.remove(res);
    }
    public boolean isAvailable(int day){
        for (Reservation reservation : reservationList) {
            if (day >= reservation.getCheckIn() && day < reservation.getCheckOut()) {
                return false;
            }
        }
        return true;
    }

    public void printBasicInfo(){
        System.out.println("Room number: " + this.roomNumber);
        System.out.println("Price per night: " + this.actualPrice);
        System.out.print("Booked dates: ");

        if (this.reservationList.isEmpty()){
            System.out.println("ALL AVAILABLE");
        } else {
            System.out.println();
            for (Reservation reservation : this.reservationList) {
                System.out.println("Days "+reservation.getCheckIn() +" to "+ reservation.getCheckOut());
            }
        }
    }
    public boolean printReservationInfo(String resNo){
        for (Reservation res : reservationList){
            if (res.getReservationId().equals(resNo)){
                res.printBasicInfo();
                return true;
            }
        }
        return false;
    }
    public boolean removeReservationById(String resId){
        for (Reservation res : reservationList){
            if (res.getReservationId().equals(resId)) {
                reservationList.remove(res);
                System.out.println("Sucessfully removed reservation from room");
                return true;
            }
        }
        return false;
    }
    public Reservation getReservation(Reservation res){
        for (Reservation reservation : reservationList){
            if (reservation.equals(res)){ // note this uses the equals overide
                return reservation;
            }
        }
        return null;
    }
    public boolean hasReservation(){
        if (this.reservationList.size() > 0) {
            return true;
        }
        return false;
    }
    public int getRoomNumber(){
        return this.roomNumber;
    }
    public int getRoomType(){
        return this.type;
    }
    public double getActualPrice(){
        return this.actualPrice;
    }
    public void setRoomType(int type){
        this.type = type;
    }

}
