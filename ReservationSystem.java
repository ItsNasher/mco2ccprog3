import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
/**
 *  Used for creating, viewing, editing and simulating the Hotel Reservation System.
 */
public class ReservationSystem {
    private Hotel selectedHotel = null; // will serve as a pointer to the actual hotel in hotelList

    /**
     * Selects a hotel from hotelList.
     * @param hotelList contains the list of hotels
     */
    public void selectHotel(ArrayList<Hotel> hotelList) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Input name of the hotel you would like to select: ");
        String hotelName = sc.nextLine();

        sc.close();

        for (Hotel searchHotel : hotelList) {
            if (searchHotel.getHotelName().equalsIgnoreCase(hotelName)) {
                this.selectedHotel = searchHotel; // selectedHotel is a reference pointer to the hotel object in hotelLIst
                break;
            }
        }
    }

    /**
     * Creates a hotel and adds it into the list.
     * @param hotelList where the hotels are placed into
     * @return true if hotel is created, false if not
     */
    public boolean createHotel(ArrayList<Hotel> hotelList) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Input name of new hotel: ");
        String hotelName = sc.nextLine();

        for (Hotel searchHotel : hotelList) {
            if (searchHotel.getHotelName().equalsIgnoreCase(hotelName)) {
                System.out.println("Hotel name already exists!");
                return false;
            }
        }
        Hotel hotel = new Hotel(hotelName);
        hotelList.add(hotel);

        System.out.println("Hotel successfully created!");
        return true;
    }

    /**
     * Allows for viewing the information of a specific hotel.
     * @param hotelList the list of hotels to search
     */
    public void viewHotel (ArrayList<Hotel> hotelList){
        Scanner sc = new Scanner (System.in);
        System.out.print("Which hotel would you like to view?: ");
        String hotelName = sc.nextLine();

        selectedHotel = getHotel(hotelList, hotelName);
        boolean continueMenu = true;
        while (this.selectedHotel != null && continueMenu) {
            System.out.println("Which information would you like to view?");
            System.out.println("[1] Name");
            System.out.println("[2] Room Count");
            System.out.println("[3] Base Price");
            System.out.println("[4] Total Earnings");
            System.out.println("[5] Rooms Availability");
            System.out.println("[6] Room Information");
            System.out.println("[7] Reservation Information");
            System.out.println("[8] Exit");
            System.out.print("Enter a number: ");

            int userInput = sc.nextInt();
            sc.nextLine();

            switch (userInput) {
                case 1: {
                    System.out.println("\n" + "Hotel Name: " + this.selectedHotel.getHotelName() + "\n");
                    break;
                }
                case 2: {
                    System.out.println("\n" + "Number of Rooms: " + this.selectedHotel.getRoomCount() + "\n");
                    break;
                }
                case 3: {
                    System.out.println("\n" + "Room Price: " + this.selectedHotel.getBasePrice() + "\n");
                    break;
                }
                case 4: {
                    System.out.println("\n" + "Total Earnings: " + this.selectedHotel.estimateEarnings() + "\n");
                    break;
                }
                case 5: {
                    System.out.print("Enter a day (1-31) to view availability: ");
                    int day = sc.nextInt();
                    sc.nextLine();
                    LocalDateTime date = LocalDateTime.of(2024, 5, day, 0, 0);
                    int[] availability = this.selectedHotel.availableRooms(date);
                    System.out.println("\n" + "Available Rooms: " + availability[0] + "\n");
                    System.out.println("\n" + "Booked Rooms: " + availability[1] + "\n");
                    break;
                }
                case 6: {
                    System.out.print("Enter room name to view information: ");
                    String roomName = sc.nextLine();
                    System.out.println(this.selectedHotel.roomInformation(roomName));
                    break;
                }
                case 7: {
                    System.out.print("Enter reservation ID to view information: ");
                    int reservationId = sc.nextInt();
                    sc.nextLine();
                    String reservationInfo = this.selectedHotel.reservationInformation(reservationId);
                    System.out.println(reservationInfo);
                    break;
                }
                case 8: {
                    continueMenu = false;
                    break;
                }
                default: {
                    System.out.println("Invalid input.");
                    break;
                }
            }
        }
    }


    /**
     * Allows for editing and managing the hotel's details.
     * @param hotelList list of hotels to search from
     */
    public void manageHotel(ArrayList<Hotel> hotelList) {
        Scanner sc = new Scanner (System.in);
        System.out.print("Which hotel would you like to manage?: ");
        String namehotel = sc.nextLine();

        selectedHotel = getHotel(hotelList, namehotel);
        boolean continueMenu = true;


        while(this.selectedHotel != null && continueMenu) {
            System.out.println("Currently editing " + this.selectedHotel.getHotelName());
            System.out.println("Change or edit the following parameters ");
            System.out.println("[1] Change name");
            System.out.println("[2] Add rooms");
            System.out.println("[3] Remove rooms");
            System.out.println("[4] Base Price");
            System.out.println("[5] Exit");
            System.out.print("Enter a number: ");

            int userInput = sc.nextInt();
            sc.nextLine();

            switch (userInput) {
                case 1: {
                    System.out.print("Change hotel name to? ");
                    String hotelName = sc.nextLine();
                    boolean name = false;

                    for (Hotel searchHotel : hotelList) {
                        if (searchHotel.getHotelName().equalsIgnoreCase(hotelName)) {
                            System.out.println("Hotel name already exists!");
                            name = true;
                        }
                    }
                    if (!name) {
                        this.selectedHotel.setHotelName(hotelName);
                        System.out.println("Hotel Name Successfully changed to " + hotelName);
                        break;
                    }
                    break;
                }
                case 2: {
                    System.out.println("Current hotel room capacity: "+this.selectedHotel.getRoomCount()+" (Must be within 1 - 50)");

                    System.out.print("Input amount of rooms to add: ");
                    int temp = sc.nextInt();
                    sc.nextLine();

                    if ((temp + this.selectedHotel.getRoomCount()) <= 50) {
                        this.selectedHotel.setRoomCount(sc.nextInt());
                        sc.nextLine();
                    }
                    else {
                        System.out.println("Must be within 1 - 50");
                    }
                    break;
                }
                case 3: {
                    System.out.print("Enter number of rooms to remove: ");
                    int roomsRemove = sc.nextInt();
                    sc.nextLine();

                    Room[] rooms = this.selectedHotel.getRoomList();
                    int roomCount = this.selectedHotel.getRoomCount();

                    if (roomsRemove > roomCount) {
                        System.out.println("Cannot remove more rooms than available.");
                        break;
                    } else {
                        for (int i = 0; i < roomsRemove; i++) {
                            rooms[roomCount - 1 - i] = null; //removes
                        }
                        this.selectedHotel.setRoomCount(roomCount - roomsRemove); //update
                        System.out.println("Successfully removed " + roomsRemove + " rooms!");
                        break;
                    }
                }
                case 4: {
                    System.out.println("Current room price: "+this.selectedHotel.getBasePrice());

                    System.out.print("Input new base price: ");
                    double temp = sc.nextDouble();
                    sc.nextLine();

                    if (temp >= 0) {
                        this.selectedHotel.setBasePrice(temp);
                        System.out.println("New base price: " + this.selectedHotel.getBasePrice());
                        break;
                    }
                    else
                        System.out.println("Must be a positive integer");
                    break;
                }
                case 5: {
                    continueMenu = false;
                    break;
                }
            }
        }
        if (this.selectedHotel == null){
            System.out.println("Invalid Hotel Name");
            continueMenu = false;
        }

    }

    /**
     * Simulates the booking process in a hotel.
     * @param hotelList list of hotels to stay in
     */
    public void simulateBooking (ArrayList<Hotel> hotelList){
        Scanner sc = new Scanner (System.in);
        System.out.print("Which hotel would you like to stay in?: ");
        String hotelName = sc.nextLine();

        selectedHotel = getHotel(hotelList, hotelName);
        boolean continueMenu = true;

        if (selectedHotel != null && continueMenu){
            System.out.println("Welcome to hotel " + selectedHotel.getHotelName());
            System.out.print("Enter name: ");
            String guest = sc.nextLine();
            LocalDateTime checkin = null, checkout = null; //checkin var for storing and checkingIn for scan
            do{
                System.out.print("Check in Day (1-31): ");
                int checkingIn = sc.nextInt();
                sc.nextLine();
                if (checkingIn >= 1 && checkingIn < 31)
                    checkin = LocalDateTime.of(2024, 5, checkingIn, 0, 0);
                else
                    System.out.println("Choose another day.");
            }while (checkin == null);

            do{
                System.out.print("Check out Day (1-31): ");
                int checkingOut = sc.nextInt();
                sc.nextLine();
                if (checkingOut > 1 && checkingOut <= 31 && checkingOut > checkin.getDayOfMonth())
                    checkout = LocalDateTime.of(2024, 5, checkingOut, 0, 0);
                else
                    System.out.println("Choose another day.");
            }while (checkout == null);

            Room available = null;
            for (int i = 0; i < selectedHotel.getRoomList().length; i++){ //checks if room is available
                Room room = selectedHotel.getRoomList()[i];
                if (room.isAvailable(checkin) && room.isAvailable(checkout)) {
                    available = room;
                    break;
                }
            }
            if (available == null)
                System.out.println("No available rooms.");
            if (available != null) {
                Reservation reservation = new Reservation(guest, checkin, checkout, available); //create
                selectedHotel.addReservation(new Reservation[]{reservation}); //add to hotel
                System.out.println("Reservation Successful! Reservation ID: " + reservation.getReservationId());
                System.out.println("Room " + available.getRoomName() + " booked for " + guest + " from " + checkin + " to " + checkout);
                continueMenu = true;
            }
        }
        else
            System.out.println("Invalid hotel name.");
        continueMenu = true;
    }

    /**
     * Gets the hotel from the list of hotels.
     * @param hotelList list of hotels.
     * @param hotelName used for finding the hotel in hotelList
     * @return returns the hotel if found, null if there isn't a hotel
     */
    public Hotel getHotel (ArrayList<Hotel> hotelList, String hotelName){
        for (int i = 0; i < hotelList.size(); i++){
            Hotel hotel = hotelList.get(i);
            if (hotel.getHotelName().equalsIgnoreCase(hotelName)) {
                return hotel;
            }
        }
        return null;
    }
}