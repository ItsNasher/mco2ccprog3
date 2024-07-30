
import java.util.ArrayList;

/**
 * Room class containing the initial information about the rooms in the hotel.
 */
public class Room {
    int roomNumber;
    int type;   // 0 is standard, 1 is deluxe, 2 is executive
    double actualPrice;
    private ArrayList<Reservation> reservationList;

    /**
     * @param type Type of room. 0 for standard, 1 for deluxe and 2 for executive.
     * @param roomNumber Room number designated to the room.
     * @param basePrice Base price per night of the room.
     */
    public Room (int type, int roomNumber, double basePrice){
        this.type = type;
        this.roomNumber = roomNumber;
        this.reservationList = new ArrayList<>();
        setActualPrice(basePrice);
    }

    /**
     * @param basePrice Based on the type of the room, sets the price.
     */
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

    /**
     * @param res Adds a reservation to the reservation list.
     */
    public void addReservation(Reservation res){
        this.reservationList.add(res);
    }

    /**
     * @param res Removes a reservation from the reservation list.
     */
    public void removeReservation(Reservation res){
        this.reservationList.remove(res);
    }

    /**
     * @param day The day to be checked if the room is available.
     * @return True if its available, and false otherwise.
     */
    public boolean isAvailable(int day){
        for (Reservation reservation : reservationList) {
            if (day >= reservation.getCheckIn() && day < reservation.getCheckOut()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the basic information of the room.
     */
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

    /**
     * @param resNo Reservation ID to be checked.
     * @return True if reservation is there, false otherwise.
     */
    public boolean printReservationInfo(String resNo){
        for (Reservation res : reservationList){
            if (res.getReservationId().equals(resNo)){
                res.printBasicInfo();
                return true;
            }
        }
        return false;
    }

    /**
     * @param resId Reservation ID given to the guest.
     * @return True if the reservation was removed, false if otherwise.
     */
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

    /**
     * @param res The reservation selected.
     * @return Returns that reservation if its part of the list, otherwise not.
     */
    public Reservation getReservation(Reservation res){
        for (Reservation reservation : reservationList){
            if (reservation.equals(res)){ // note this uses the equals overide
                return reservation;
            }
        }
        return null;
    }

    /**
     * @return Returns true if there is a reservation for the room, otherwise false.
     */
    public boolean hasReservation(){
        if (this.reservationList.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * @return The room number.
     */
    public int getRoomNumber(){
        return this.roomNumber;
    }

    /**
     * @return The type of the room.
     */
    public int getRoomType(){
        return this.type;
    }

    /**
     * @return The base price of the room.
     */
    public double getActualPrice(){
        return this.actualPrice;
    }

    /**
     * @param type The new type of the room.
     */
    public void setRoomType(int type){
        this.type = type;
    }

}
