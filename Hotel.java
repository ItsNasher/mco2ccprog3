import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;

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
    private double[] datePriceModifier;

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
        this.datePriceModifier = new double[31];
        initializeRooms();
    }
    //system relies on the initialization of 50 rooms regardless of usage otherwise removedRoomIds break
    private void initializeRooms(){
        int floor = 1;
        int roomNumber = 1;
        
        for (int i = 0; i < 50; i++) {
            int roomId = floor * 100 + roomNumber;
            rooms.add(new Room(0,roomId,this.basePrice));
            roomNumber++;
            if (roomNumber == 11) {
                floor++;
                roomNumber = 1;
            }
        }
        for (int i = 0; i < 31; i++){
            datePriceModifier[i] = 1.00;
        }
    }
    public boolean setDatePriceModifier(int day, double multiplier){
        if (day < 1 || day > 31){
            System.out.println("You cannot choose a day beyond possible reservation dates");
            return false;
        }
        this.datePriceModifier[day-1] = multiplier/100;
        return true;
    }
    private void updateRooms(){
        for (int i = 0; i < this.standardRooms; i++){
            rooms.get(i).setRoomType(0);
            rooms.get(i).setActualPrice(this.basePrice);
        }
        for (int i = this.standardRooms; i < (this.standardRooms + this.deluxeRooms); i++){
            rooms.get(i).setRoomType(1);
            rooms.get(i).setActualPrice(this.basePrice);
        }
        for (int i = (this.standardRooms + this.deluxeRooms); i < (this.standardRooms + this.deluxeRooms + this.executiveRooms); i++){
            rooms.get(i).setRoomType(2);
            rooms.get(i).setActualPrice(this.basePrice);
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
        if ((stand + deluxe + exec) == this.numOfRooms){
            this.standardRooms = stand;
            this.deluxeRooms = deluxe;
            this.executiveRooms = exec;
            updateRooms();
            System.out.println("Sucessfully modified rooms");
            return true;
        } 
        System.out.println("Rooms must add up to "+this.numOfRooms);
        System.out.println("Cancelling . . .");
        return false;
    }
    // removal is from right to left
    public boolean removeRooms(int num) {
        if (num == this.numOfRooms){
            System.out.println("You cannot remove all the rooms of a hotel");
            return false;
        }
        int unreservedCount = this.rooms.size();
        if (unreservedCount < num) {
            System.out.println("Not enough unreserved rooms to delete");
            return false;
        }
    
        int removedCount = 0;
        for (int i = rooms.size() - 1; i >= 0 && removedCount < num; i--) {
            Room room = rooms.get(i);
            if (!room.hasReservation()) {
                removedRoomIds.add(room.getRoomNumber());
                rooms.remove(i);
                removedCount++;
            }
        }
        this.numOfRooms -= removedCount;
        System.out.println("Successfully removed " + removedCount + " rooms.");
        return true;
    }

    public boolean updateBasePrice(double newPrice){
        if (!this.allReservations.isEmpty()) {
            System.out.println("There must be no reservations when changing the base price");
            return false;
        }   
        if (newPrice < 100){
            System.out.println("The new price must be greater than or equal to 100");
            return false;
        }
        this.basePrice = newPrice;
        updateRooms();
        System.out.println("Sucessfully changed the base price across all rooms");
        return true;
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
    private void applyDiscountCode(String code, Reservation res){
        boolean discountApplied = false;

        if (code.equals("I_WORK_HERE")){
            double discountedPrice = res.getTotalPrice() * 0.90; // 10% OFF
            res.setTotalPrice(discountedPrice);
            System.out.println("Discount code successfully applied");
            discountApplied = true;
        } 
        else if (code.equals("STAY4_GET1")){
            if ((res.getCheckOut() - res.getCheckIn() + 1) >= 5){
                res.setPricePerNight(0, 0);
                System.out.println("Discount code successfully applied");
                discountApplied = true;
            } else {
                System.out.println("The super secret conditions were not satisfied");
            }
        }
        else if (code.equals("PAYDAY")){
            if ((res.getCheckIn() <= 15 && res.getCheckOut() > 15) || (res.getCheckIn() <= 30 && res.getCheckOut() > 30)) {
                double discountedPrice = res.getTotalPrice() * 0.93; // 7% OFF
                res.setTotalPrice(discountedPrice);
                System.out.println("Discount code successfully applied");
                discountApplied = true;
            } else {
                System.out.println("The super secret conditions were not satisfied");
            }
        } 

        if (!discountApplied){
            System.out.println("Wrong discount code");
        }
    }

    public String createReservation(int type, String guestName, int checkIn, int checkOut){
        Room emptyRoom = findEmptyRoom(type, checkIn, checkOut);

        if (emptyRoom != null) {
            String reservationId = randomNumber();
            Reservation res = new Reservation(emptyRoom.getActualPrice(),this.datePriceModifier,emptyRoom.getRoomNumber(),guestName,reservationId,checkIn,checkOut);
            emptyRoom.addReservation(res);
            allReservations.put(reservationId, res);
            return reservationId;
        } else {
            System.out.println("No available rooms of the specified type for the given date range.");
            System.out.println("No reservations were created");;
            return null;
        }
    }
    public String createReservation(int type, String guestName, int checkIn, int checkOut, String discountCode){
        Room emptyRoom = findEmptyRoom(type, checkIn, checkOut);

        if (emptyRoom != null) {
            String reservationId = randomNumber();
            Reservation res = new Reservation(emptyRoom.getActualPrice(),this.datePriceModifier,emptyRoom.getRoomNumber(),guestName,reservationId,checkIn,checkOut);

            applyDiscountCode(discountCode, res); // APPLY DISCOUNT CODE

            emptyRoom.addReservation(res);
            allReservations.put(reservationId, res);
            return reservationId;
        } else {
            System.out.println("No available rooms of the specified type for the given date range.");
            System.out.println("No reservations were created");;
            return null;
        }
    }

    public boolean removeReservation(String resId){
        Reservation res = this.allReservations.get(resId);
    
        if (res == null) {
            System.out.println("Reservation not found.");
            return false;
        }
    
        for (Room room : rooms) {
            if (room.removeReservationById(resId)) {
                this.allReservations.remove(resId);
                System.out.println("Reservation successfully removed.");
                return true;
            }
        }
    
        System.out.println("Reservation failed to be removed");
        return false;
    }

    // mostly for debug (does this count as bonus feature? lol)
    public void printAllRooms(){
        for (Room room : rooms){
            System.out.print(room.getRoomNumber()+" ");
            System.out.print(room.getRoomType()+" ");
            System.out.println(room.getActualPrice());
        }
    }
    public void printBasicInfo(){
        System.out.println("Hotel Name: " + this.getName());
        System.out.println("Total Number of Rooms: " + this.getNumOfRooms());
        System.out.println("Estimated Earnings for the Month: " + this.getTotalEarnings());
    }
    public boolean printRoomInfo(int roomNumber){
        for (Room room : this.rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.printBasicInfo();
                return true;
            }
        }
        System.out.println("Could not find room");
        return false;
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
    // DEBUG PURPOSES ONLY
    public void printDatePriceInfo(String resId, int dateStart, int dateEnd){
        Reservation res = allReservations.get(resId);
        int resIndex = 0;
        for (int i = dateStart; i <= dateEnd; i++){
            System.out.print("Day "+i+": "+String.format("%.0f%%", datePriceModifier[i-1] * 100));    
            System.out.println(" "+res.getPricePerNight(resIndex));
            resIndex++;
        }
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
        if (this.allReservations.isEmpty()){
            return this.numOfRooms;
        }
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
    public double getBasePrice(){
        return this.basePrice;
    }
    public void setHotelName(String newName){
        this.name = newName;
    }
    
}
