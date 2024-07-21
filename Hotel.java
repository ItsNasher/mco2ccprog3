import java.time.LocalDateTime;
import java.util.Random;
/**
 * This is the hotel itself which allows for editing of the room and reservations.
 */
public class Hotel {
    private String hotelName;
    private Room[] roomList;
    private Reservation[] reservationList;
    private int roomCount;
    private int reservationCount;
    private /*final*/ double BASE_PRICE = 1299.0; // shouldn't be final "The base price across all rooms may be changed in the Manage Hotel feature."

    /**
     * Constructs a hotel object with the given name.
     * @param hotelName name given to the hotel
     */
    public Hotel(String hotelName) {
        this.hotelName = hotelName;
        this.roomList = new Room[50];
        this.reservationList = new Reservation[50]; //unsure limit // 50 reservations since 1 room = 1 reservation?
        this.roomCount = 50;
        this.reservationCount = 0;

        //making sure there are more standard rooms than deluxe and executive (70% standard, 20% deluxe, 10% exec
        int standardCount = (int) (roomCount * 0.70);
        int deluxeCount = (int) (roomCount * 0.20);
        int executiveCount = roomCount - standardCount - deluxeCount;  // Remaining rooms

        int currentStandard = 0;
        int currentDeluxe = 0;
        int currentExecutive = 0;
        Random rand = new Random();

        for (int i = 0; i < roomCount; i++) {
            String roomType;
            if (currentStandard < standardCount) {
                roomType = "Standard";
                currentStandard++;
            } else if (currentDeluxe < deluxeCount) {
                roomType = "Deluxe";
                currentDeluxe++;
            } else {
                roomType = "Executive";
                currentExecutive++;
            }
            roomList[i] = new Room(99 + (i + 1), BASE_PRICE, roomType);
        }
    }

    /**
     * Adds a number of rooms to the hotel.
     * @param roomName name of the room
     */
    public void createRoom(int roomName) { // always follows BASE_PRICE, only update if needed
        if (roomCount < roomList.length) {
            String[] roomtypes = {"Standard", "Deluxe", "Executive"};
            Random rand = new Random();
            String roomtype = roomtypes[rand.nextInt(roomtypes.length)];
            roomList[roomCount] = new Room(roomName, this.BASE_PRICE, roomtype);
            roomCount++;
        } else {
            System.out.println("Room Capacity Reached.");
        }
    }

    /**
     * Adds a reservation to the hotel and room.
     * @param reservations Array of reservations
     */
    public void addReservation(Reservation[] reservations) {
        for (int i = 0; i < reservations.length; i++) {
            Reservation reservation = reservations[i];
            if (reservationCount < reservationList.length) {
                reservationList[reservationCount] = reservation;
                reservationCount++;
            } else {
                System.out.println("Reservation Capacity Reached.");
            }
        }
    }

    /**
     * Removes reservations from the hotel.
     * @param reservation The removed reservation
     */
    public void removeReservation(Reservation reservation) {
        for (int i = 0; i < reservationCount; i++) {
            if (reservationList[i] != null && reservationList[i].equals(reservation)) {
                reservationList[i] = null;
                System.out.println("Reservation removed.");
                return;
            }
        }
        System.out.println("Reservation not found.");
    }

    /**
     * Returns total number of rooms.
     * @return total number of rooms.
     */
    public int totalRooms() {
        return roomCount;
    }

    /**
     * Total number of earnings based on the reservations and price.
     * @return total earnings.
     */
    public double estimateEarnings() {
        double earnings = 0;
        for (int i = 0; i < reservationCount; i++) {
            if (reservationList[i] != null) {
                earnings += reservationList[i].getTotalPrice();
            }
        }
        return earnings;
    }

    /**
     * Checks if the room is available on this date.
     * @param dateTime date that is checked
     * @return array with available and booked rooms.
     */
    public int[] availableRooms(LocalDateTime dateTime) {
        int availableCount = 0;
        int bookedCount = 0;
        for (int i = 0; i < roomCount; i++) {
            if (roomList[i].isAvailable(dateTime)) {
                availableCount++;
            } else {
                bookedCount++;
            }
        }
        return new int[]{availableCount, bookedCount};
    }

    /**
     * Provides information about the room.
     * @param roomName name of the room
     * @return Information of the room
     */
    public String roomInformation(String roomName) {
        String status = "AVAILABLE"; // default case
        int index = -1;

        // Find the room by room name
        for (int i = 0; i < this.roomCount; i++) {
            if (this.getRoomList()[i].getRoomName().equals(roomName)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            status = "ERROR FETCHING ROOM AVAILABILITY";
            return status;
        }


        if (!this.getRoomList()[index].isAvailable(LocalDateTime.now())) { // Use the current date for availability
            status = "BOOKED";
        }

        return "\nRoom " + this.getRoomList()[index].getRoomName() + " is " + status+"\n";
    }

    /**
     * Displays the information of the reservation that is made.
     * @param reservationId reservationID of the reservation
     * @return Information of the reservation
     */
    public String reservationInformation(int reservationId) {
        for (int i = 0; i < reservationCount; i++) {
            if (reservationList[i] != null && reservationList[i].getReservationId() == reservationId) {
                Reservation reservation = reservationList[i];
                return "Reservation ID: " + reservation.getReservationId() +
                        ", Guest Name: " + reservation.getGuestName() +
                        ", Check-In Date: " + reservation.getCheckInDate() +	// LocalDateTime checkIn = LocalDateTime.of(year,month,day,hour,minute) 
                        ", Check-Out Date: " + reservation.getCheckOutDate() +	// use 24 hour time for this
                        ", Total Price: " + reservation.getTotalPrice();
            }
        }
        return "Reservation not found.";
    }
    /**
     * Finds the room by the name.
     * @param roomName name of the room to be found
     * @return The room is found, otherwise null
     */
    public Room RoomName(String roomName) {
        for (int i = 0; i < roomCount; i++) {
            if (roomList[i].getRoomName().equals(roomName)) {
                return roomList[i];
            }
        }
        return null;
    }

// GETTERS ==================================================
    /**
     * Returns the name of the hotel.
     * @return name of the hotel
     */
    public String getHotelName() {
        return hotelName;
    }
    /**
     * Returns the base price of the rooms.
     * @return base price of the rooms
     */
    public double getBasePrice() {
        return BASE_PRICE;
    }
    /**
     * Returns the total amount of rooms.
     * @return total amount of rooms
     */
    public int getRoomCount() {
        return this.roomCount;
    }
    /**
     * Current number of reservations.
     * @return number of reservations
     */
    public int getReservationCount() {
        return this.reservationCount;
    }
    /**
     * Returns the list of rooms.
     * @return list of rooms.
     */
    public Room[] getRoomList() {
        return roomList;
    }
    /**
     * Returns the list of reservations.
     * @return list of reservations
     */
    public Reservation[] getReservationList() {
        return reservationList;
    }

    // SETTERS ================================================
    /**
     * Sets a new name for the hotel.
     * @param newName new name of the hotel
     */
    public void setHotelName(String newName) {
        this.hotelName = newName;
    }
    /**
     * Sets a new base price for the rooms.
     * @param newBasePrice new base price for the rooms
     */
    public void setBasePrice(double newBasePrice) {
        this.BASE_PRICE = newBasePrice;
    }
    /**
     * Sets a new room count for the hotel.
     * @param newRoomCount new room count for the hotel
     */
    public void setRoomCount(int newRoomCount) {
        this.roomCount = newRoomCount;
    }
    /**
     * Sets a new reservation count for the hotel.
     * @param newReservationCount new reservation count for the hotel
     */
    public void setReservationCount(int newReservationCount) {
        this.reservationCount = newReservationCount;
    }
}