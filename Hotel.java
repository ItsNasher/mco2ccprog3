import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Objects;

public class Hotel {
    private String name;
    private int numOfRooms;
    private ArrayList<Room> rooms;
    private double basePrice;
    private HashMap<String,Reservation> allReservations;
    private int standardRooms;
    private int deluxeRooms;
    private int executiveRooms;
    private ArrayList<Integer> removedRoomIds;

    public Hotel(String name){
        this.name = name;
        this.numOfRooms = 50;
        this.basePrice = 1299.0;
        this.allReservations = new HashMap<>();
        this.rooms = new ArrayList<>();
        this.removedRoomIds = new ArrayList<>();
        this.standardRooms = 50;
        this.deluxeRooms = 0;
        this.executiveRooms = 0;
        initializeRooms();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Hotel hotel = (Hotel) obj;
        return name.equals(hotel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // Assumes 50 rooms exist in the array but will only name them
    // The system will only access rooms until numOfRooms
    private void initializeRooms(){
        int floor = 1;
        int roomNumber = 1;
        int type = 0;
        
        for (int i = 0; i < 50; i++) {
            int roomId = floor * 100 + roomNumber;
            rooms.add(new Room(type,roomId,this.basePrice));
            roomNumber++;
            if (roomNumber == 10) {
                floor++;
                roomNumber = 1;
            }
        }
    }
    private void updateRoomTypes(){
        for (int i = 0; i < this.standardRooms; i++){
            rooms.get(i).setRoomType(0);
        }
        for (int i = this.standardRooms; i < this.deluxeRooms; i++){
            rooms.get(i).setRoomType(1);
        }
        for (int i = this.deluxeRooms; i < this.executiveRooms; i++){
            rooms.get(i).setRoomType(2);
        }
    }

    public void addRooms(int num) {
        if (num <= 0) {
            System.out.println("Number of rooms cannot be less than 1.");
            return;
        }
        if (this.numOfRooms + num > 50) {
            System.out.println("Number of rooms cannot exceed 50.");
            return;
        }
        this.numOfRooms += num;
        int roomId;
        for (int i = 0; i < num; i++){
            //if (removedRoomIds.size() > 0){       theres no actually need to check for this since all the 50 room names are initialized
            //       if the user wants to have a smaller hotel they will always go through room removal, which adds to the removedRoomIds

            roomId = removedRoomIds.get(removedRoomIds.size()-1);
            removedRoomIds.remove(removedRoomIds.size()-1);
            rooms.add(new Room(0,roomId,this.basePrice));
        }
        System.out.println("Sucessfully added "+num+" rooms");
    }

    public boolean modifyRooms(int stand, int deluxe, int exec){
        if ((stand + deluxe + exec) <= 50){
            this.standardRooms = stand;
            this.deluxeRooms = deluxe;
            this.executiveRooms = exec;
            updateRoomTypes();
            System.out.println("Sucessfully modified rooms");
            return true;
        }
        System.out.println("Error! You cannot have more than 50 rooms");
        return false;
    }
    // removal is from right to left
    public boolean removeRooms(int num){
        int unreservedCount = this.numOfRooms - this.rooms.size();
        if (unreservedCount < num) {
            System.out.println("Not enough unreserved rooms to delete");
            return false;
        }

        int removedCount = 0;
        for (int i = this.numOfRooms; i >= 0 && removedCount < num; i--) {
            Room room = rooms.get(i);
            if (!room.hasReservation()) {
                removedRoomIds.add(room.getRoomNumber());
                rooms.remove(i);
                removedCount++;
            }
        }
        System.out.println("Successfully removed " + removedCount + " rooms.");
        return true;
    }

    public boolean updateBasePrice(double newPrice){
        if (this.allReservations.size() == 0) {
            for (Room room : rooms){
                room.setBasePrice(newPrice);
            }
            System.out.println("Sucessfully changed the base price across all rooms");
            return true;
        }
        System.out.println("There must be no reservations when changing the base price");
        return false;
    }

    // the name of function is misleading btw
    private String randomNumber() {
        Random rand = new Random();
        int ran = rand.nextInt(900000) + 100000;
        String newNumber = Integer.toString(ran);
    
        if (allReservations.containsKey(newNumber)) {
            return randomNumber();
        }
        return newNumber;
    }

    public Room findEmptyRoom(int type, int checkIn, int checkOut) {
        return findEmptyRoomRecursive(type, checkIn, checkOut, 0);
    }
    private Room findEmptyRoomRecursive(int type, int checkIn, int checkOut, int index) {
        // Check if it has reached all the rooms of the hotel
        if (index >= numOfRooms) {
            return null;
        }
        // Checks if the room of the same type
        if (rooms.get(index).getRoomType() != type) {
            return findEmptyRoomRecursive(type, checkIn, checkOut, index + 1);
        }
        // Iterates each day of the check in dates to see if its available
        boolean isRoomAvailable = true;
        for (int day = checkIn; day < checkOut; day++) { // note checkOut shouldnt be <= since checkout and checkin can overlap same day
            if (!rooms.get(index).isAvailable(day)) {
                isRoomAvailable = false;
                break;
            }
        }
        if (isRoomAvailable) {
            return rooms.get(index);
        } else {
            return findEmptyRoomRecursive(type, checkIn, checkOut, index + 1);
        }
    }

    public boolean createReservation(int type, int pricePerNight, String guestName, int checkIn, int checkOut){
        Room emptyRoom = findEmptyRoom(type, checkIn, checkOut);

        if (emptyRoom != null) {
            String reservationId = randomNumber();
            Reservation res = new Reservation(pricePerNight,emptyRoom.getRoomNumber(),guestName,reservationId,checkIn,checkOut);
            emptyRoom.addReservation(res);
            allReservations.put(reservationId, res);
            return true;
        } else {
            System.out.println("No available rooms of the specified type for the given date range.");
            return false;
        }
    }

    public boolean removeReservation(String reservationId){
        if (!this.allReservations.containsKey(reservationId)) {
            System.out.println("Reservation not found.");
            return false;
        }
        
        String resId = this.allReservations.get(reservationId).getReservationId();

        for (Room room : rooms){
            if (room.searchReservation(resId) != -1){
                room.removeReservation(reservationId);
                this.allReservations.remove(reservationId);
                System.out.println("Sucessfully removed reservation from hotel");
                return true;
            }
        
        }
        System.out.println("Reservation failed to be removed");
        return false;
    }

    public void printBasicInfo(){
        System.out.println("Hotel Name: " + this.getName());
        System.out.println("Total Number of Rooms: " + this.getNumOfRooms());
        System.out.println("Estimated Earnings for the Month: " + this.getTotalEarnings());
    }
    public void printRoomInfo(int roomNumber){
        for (Room room : this.rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.printBasicInfo();
                break;
            }
        }
    }
    public void printReservationInfo(String reservationId){
        if (allReservations.containsKey(reservationId)){
            allReservations.get(reservationId).printBasicInfo();
        } else {
            System.out.println("Reservation not found.");
        }
    }
    public void printRoomTypeInfo(){
        System.out.println("Number of rooms, ");
        System.out.println("Standard rooms: "+this.standardRooms);
        System.out.println("deluxe rooms: "+this.deluxeRooms);
        System.out.println("Executive rooms: "+this.executiveRooms);
    }

    /**
     * Total number of earnings based on the reservations and price.
     * @return total earnings.
     */
    public double getTotalEarnings() {
        double totalEarnings = 0.0;

        for (Reservation reservation : allReservations.values()) {
            totalEarnings += reservation.getTotalPrice();
        }
        return totalEarnings;
    }

    public int getNumAvailableRooms(int date){
        int count = 0;

        for (Room room : rooms) {
            if (room.isAvailable(date)) {
                count++;
            }
        }
        return count;
    }

    public int getNumBookedRooms(int date){
        return this.numOfRooms - this.getNumAvailableRooms(date);
    }

    public String getName(){
        return this.name;
    }

    public int getNumOfRooms(){
        return this.numOfRooms;
    }
    public void setHotelName(String newName){
        this.name = newName;
    }
}
