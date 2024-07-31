
import java.util.ArrayList;
/**
 * The room class represents a room with a room number, type, and price
 * This class holds an array list of reservations of its own
 */
public class Room {
    int roomNumber;
    int type;   // 0 is standard, 1 is deluxe, 2 is executive
    double actualPrice;
    private ArrayList<Reservation> reservationList;

    /**
     * Constructs a Room object with the specified type, room number, and base price.
     *
     * @param type       the type of the room (0 for standard, 1 for deluxe, 2 for executive)
     * @param roomNumber the room number
     * @param basePrice  the base price of the room
     */
    public Room (int type, int roomNumber, double basePrice){
        this.type = type;
        this.roomNumber = roomNumber;
        this.reservationList = new ArrayList<>();
        setActualPrice(basePrice);
    }
    /**
     * Sets the actual price of the room based on its type.
     *
     * @param basePrice the base price of the room
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
     * Adds a reservation to the room.
     *
     * @param res the reservation to add
     */
    public void addReservation(Reservation res){
        this.reservationList.add(res);
    }
    /**
     * Removes a reservation from the room.
     *
     * @param res the reservation to remove
     */
    public void removeReservation(Reservation res){
        this.reservationList.remove(res);
    }
    /**
     * Checks if the room is available on a specific day.
     *
     * @param day the day to check
     * @return true if the room is available, otherwise false
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
     *
     * @return an ArrayList containing the basic information of the room
     */
    public ArrayList<String> printBasicInfo(){
        ArrayList<String> strReservations = new ArrayList<>();

        System.out.println("Room number: " + this.roomNumber);
        System.out.println("Price per night: " + this.actualPrice);
        System.out.print("Booked dates: ");

        strReservations.add("Room number: " + this.roomNumber);
        strReservations.add("Price per night: " + this.actualPrice);
        strReservations.add("Booked dates: ");
        
        if (this.reservationList.isEmpty()){
            System.out.println("ALL AVAILABLE");
            strReservations.add("ALL AVAILABLE");
        } else {
            for (Reservation reservation : this.reservationList) {
                System.out.println("Days "+reservation.getCheckIn() +" to "+ reservation.getCheckOut());
                strReservations.add("Days "+reservation.getCheckIn() +" to "+ reservation.getCheckOut());
            }
        }
        return strReservations;
    }
    /**
     * Prints the information of a specific reservation.
     *
     * @param resNo the reservation number
     * @return true if the reservation is found and printed, otherwise false
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
     * Removes a reservation with the specified ID from the room.
     *
     * @param resId the reservation ID
     * @return true if the reservation is successfully removed, otherwise false
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
     * Gets a reservation from the room.
     *
     * @param res the reservation to get
     * @return the reservation if found, otherwise null
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
     * Checks if the room has any reservations.
     *
     * @return true if the room has reservations, otherwise false
     */
    public boolean hasReservation(){
        if (this.reservationList.size() > 0) {
            return true;
        }
        return false;
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
     * Gets the type of the room.
     *
     * @return the type of the room (0 for standard, 1 for deluxe, 2 for executive)
     */
    public int getRoomType(){
        return this.type;
    }
    /**
     * Gets the actual price of the room.
     *
     * @return the actual price of the room
     */
    public double getActualPrice(){
        return this.actualPrice;
    }
    /**
     * Sets the type of the room.
     *
     * @param type the new type of the room (0 for standard, 1 for deluxe, 2 for executive)
     */
    public void setRoomType(int type){
        this.type = type;
    }

}
