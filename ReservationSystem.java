import java.util.ArrayList;
import java.util.Scanner;

public class ReservationSystem {
    private ArrayList<Hotel> hotels = new ArrayList<>();

    /**
     * Creates a new hotel with a unique name.
     * 
     * @return true if the hotel was successfully created, false otherwise
     */
    public boolean createHotel(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Input name of new hotel: ");
        String newName = sc.nextLine();

        if (newName.equalsIgnoreCase("temp")){
            System.out.println("The name temp cannot be used");
            return false;
        }
        
        for (Hotel hotel : hotels) {
            if (hotel.getName().equalsIgnoreCase(newName)) {
                System.out.println("Hotel name already exists");
                return false;
            }
        }
        Hotel hotel = new Hotel(newName); 
        hotels.add(hotel);
        System.out.println("Hotel successfully created!");

        return true;
    }
    /**
     * Displays information about a selected hotel depending on user input
     */
    public void viewHotel(){
        Scanner sc = new Scanner (System.in);

        System.out.print("Enter the name of the hotel you would you like to view: ");
        String hotelName = sc.nextLine();

        Hotel selectedHotel = null;

        for (Hotel hotel : hotels) {
            if (hotel.getName().equalsIgnoreCase(hotelName)) {
                selectedHotel = hotel;
                break;
            }
        }
        
        if (selectedHotel == null) {
            System.out.println("Hotel not found.");
            return; 
        }

        boolean menu = true;
        while (menu) {
            System.out.println("1. View basic information about the hotel");
            System.out.println("2. View total number of available and booked rooms for a selected date");
            System.out.println("3. View information about a selected room");
            System.out.println("4. View information about a selected reservation");
            System.out.println("5. Back to main menu");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
            case 1:
                System.out.println("1. View basic hotel info");
                System.out.println("2. Show all room names");
                int basicInfo = sc.nextInt();
                sc.nextLine();
                if (basicInfo == 1){
                    selectedHotel.printBasicInfo();
                }
                if (basicInfo == 2){
                    selectedHotel.printAllRooms();
                }
                break;

            case 2:
                System.out.println("Enter a day in a month (31): ");
                int day = sc.nextInt();
                sc.nextLine();

                System.out.println("Number of available Rooms: " +selectedHotel.getNumAvailableRooms(day));
                System.out.println("Number of booked Rooms: " +selectedHotel.getNumBookedRooms(day));
                break;

            case 3:
                System.out.println("Enter the room number: ");
                int roomNumber = sc.nextInt();
                sc.nextLine();
                selectedHotel.printRoomInfo(roomNumber);
                break;

            case 4:
                System.out.println("Enter a reservation number: ");
                String resNo = sc.nextLine();
                    
                selectedHotel.printReservationInfo(resNo);
                break;
            case 5:
                menu = false;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    /**
     * Sets various parameters and settings across a specified hotel based on the user input
     */
    public void manageHotel(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the name of the hotel you would you like to manage: ");
        String hotelName = sc.nextLine();

        Hotel selectedHotel = null;
        
        for (Hotel hotel : hotels) {
            if (hotel.getName().equalsIgnoreCase(hotelName)) {
                selectedHotel = hotel;
                break;
            }
        }
         
        if (selectedHotel == null) {
            System.out.println("Hotel not found.");
            return;
        }
        boolean menu = true;
        
        while (menu){
            int confirmDelete = 0; 

            System.out.println("0. Exit the menu");
            System.out.println("1. Change name of the hotel");
            System.out.println("2. Add rooms");
            System.out.println("3. Modify rooms");
            System.out.println("4. Remove rooms");
            System.out.println("5. Update base price of each room");
            System.out.println("6. Remove reservation");
            System.out.println("7. Remove hotel");
            System.out.println("8. Edit date price modifiers");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
            case 0:
                menu = false;
                break;
            case 1:
                System.out.println("Enter new hotel name: ");
                String newHotelName = sc.nextLine();
                int isUnique = 1;
                for (Hotel hotel : hotels) {
                    if (hotel.getName().equalsIgnoreCase(newHotelName)) {
                        isUnique = 0;
                        System.out.println("Hotel name already exists");
                        break;
                    }
                }
                if (isUnique == 1){
                    System.out.println("New hotel name: " + newHotelName);
                    System.out.println("Type 1 to confirm, 0 to cancel: ");
                    int confirmHotel = sc.nextInt();
                    sc.nextLine();
            
                    if (confirmHotel == 1) {
                        selectedHotel.setHotelName(newHotelName);
                        System.out.println("Sucessfully renamed hotel");
                        return;
                    }
                }
                
                break;

            case 2:
                System.out.println("Enter number of rooms to add: ");
                int addRooms = sc.nextInt();
                sc.nextLine();
                selectedHotel.addRooms(addRooms);
                break;    
            case 4:
                System.out.println("Enter number of rooms to delete: ");
                int deleteRooms = sc.nextInt();
                sc.nextLine();

                System.out.println("Press 1 to confirm you will delete "+deleteRooms+" rooms\nPress 0 to cancel");
                confirmDelete = sc.nextInt();
                sc.nextLine();

                if (confirmDelete == 1){
                    selectedHotel.removeRooms(deleteRooms);
                }
            // dont add a break; here, it purposely flows to case 3
            case 3:
                if (confirmDelete == 0) {
                    System.out.println("Current rooms, ");
                    selectedHotel.printRoomTypeInfo();
                    System.out.println("");
                    System.out.println("Press 1 to edit, press 0 to cancel");
                    int modifyRoom = sc.nextInt();
                    sc.nextLine();

                    if(modifyRoom == 0){
                        System.out.println("Going back");
                        break;
                    }
                }
                
                int totalRooms = selectedHotel.getNumOfRooms();
                int standard, deluxe, executive;

                while (true) {
                    System.out.println("Enter new number of standard rooms");
                    standard = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter new number of deluxe rooms");
                    deluxe = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter new number of executive rooms");
                    executive = sc.nextInt();
                    sc.nextLine();

                    if (standard + deluxe + executive == totalRooms) {
                        break;
                    } else {
                        System.out.println("The total number of rooms must equal " + totalRooms + ". Please try again.");
                    }
                }

                System.out.println("Standard rooms: " + standard);
                System.out.println("Deluxe rooms: " + deluxe);
                System.out.println("Executive rooms: " + executive);

                System.out.println("Press 1 to confirm, 0 to cancel");
                if (sc.nextInt() == 1) {
                    selectedHotel.modifyRooms(standard, deluxe, executive);
                } else {
                    System.out.println("Cancelling. . .");
                }
                break;
            case 5:
                System.out.println("Enter the new base price: ");
                double newPrice = sc.nextDouble();
                sc.nextLine();

                System.out.println("Press 1 to confirm new base price of "+newPrice+"\nPress 0 to cancel");
                int confirmPrice = sc.nextInt();
                sc.nextLine();

                if (confirmPrice == 1){
                    selectedHotel.updateBasePrice(newPrice);
                }else{
                    System.out.println("Cancelling. . .");
                }
                break;
            case 6:
                System.out.println("Enter reservation ID to remove: ");
                String removeId = sc.nextLine();
                System.out.println("Are you sure you will delete reservation no. "+removeId);
                System.out.println("Press 1 to confirm, 0 to cancel");
                int removeConfirm = sc.nextInt();
                sc.nextLine();

                if (removeConfirm == 1){
                    System.out.println("Removing reservation");
                    selectedHotel.removeReservation(removeId);
                } else {
                    System.err.println("Cancelling . . .");
                }
                break;
            case 7:
                Hotel removeHotel = null;

                System.out.println("Enter name of hotel to remove: ");
                String removeHotelName = sc.nextLine();

                for (Hotel hotel : hotels){
                    if (hotel.getName().equals(removeHotelName)){
                        removeHotel = hotel;
                    }
                }

                if (removeHotel == null){
                    System.out.println("Could not find hotel to remove");
                    break;
                }
                System.out.println("Are you sure you will delete hotel "+removeHotelName);
                System.out.println("Press 1 to confirm, 0 to cancel");
                int confirmRemoveHotel = sc.nextInt();
                sc.nextLine();

                if (confirmRemoveHotel == 1){
                    System.out.println("Removing hotel");
                    this.hotels.remove(removeHotel);
                } else {
                    System.err.println("Cancelling . . .");
                }
                break;
            case 8:
                System.out.println("Set date price modifier for,");
                System.out.println("1. Single date");
                System.out.println("2. Multiple dates");
                System.out.println("3. Return to menu");
                int dpmChoice = sc.nextInt();
                sc.nextLine();
                if (dpmChoice == 1){
                    System.out.println("Enter date to modify: ");
                    int date = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter new date price modifier [Default is 100%]: ");
                    double multiplier =sc.nextDouble();
                    sc.nextLine();
                    System.out.println("Day "+date+" will be sold at "+multiplier+" of its prices");

                    System.out.println("Press 1 to confirm, 0 to cancel");
                    int dpmConfirm = sc.nextInt();
                    sc.nextLine();
                    if (dpmConfirm == 1 && selectedHotel.setDatePriceModifier(date, multiplier)){
                        System.out.println("Sucessfully changed date price modifier");
                        break;
                    } else {
                        System.out.println("Canceling . . .");
                        break;
                    }
                } else if (dpmChoice == 2){
                    System.out.println("Enter the first day to modify: ");
                    int dateStart = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter the last day to modify: ");
                    int dateEnd = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter new date price modifier [Default is 100%]: ");
                    double multiplier =sc.nextDouble();
                    sc.nextLine();
                    System.out.println("Dates "+dateStart+" to "+dateEnd+" will be sold at "+multiplier+" of its prices");
                    System.out.println("Press 1 to confirm, 0 to cancel");
                    int dpmConfirm = sc.nextInt();
                    sc.nextLine();
                    if (dpmConfirm == 1){
                        for (int i = dateStart; i <= dateEnd; i++){
                            selectedHotel.setDatePriceModifier(i, multiplier);
                        }
                        System.out.println("Sucessfully changed date price modifier");
                        break;
                    } else {
                        System.out.println("Canceling . . .");
                        break;
                    }
                } else {
                    System.out.println("Going back");
                }
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    /**
     * Simulates a booking based on the user inputs
     */
    public void simulateBooking(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the guest name for reservation: ");
        String guestName = sc.nextLine();

        System.out.print("Enter the name of the hotel you would you like to book: ");
        String hotelName = sc.nextLine();

        Hotel selectedHotel = null;
        
        for (Hotel hotel : hotels) {
            if (hotel.getName().equalsIgnoreCase(hotelName)) {
                selectedHotel = hotel;
                break;
            }
        }
        if (selectedHotel == null) {
            System.out.println("Hotel not found.");
            return;
        }

        System.out.println("Enter type of room to book [S,D,E]: ");
        String stringType = sc.nextLine();
        int type = 0;
        switch (stringType) {
        case "S":
            type = 0;
            break;
        case "D":
            type = 1;
            break;
        case "E":
            type = 2;
            break;
        default:
            System.out.println("User input error, defaulting to standard room");
            break;
        }
        System.out.println("Enter a check in date [1-30]: ");
        int checkIn = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter a check out date [2-31]: ");
        int checkOut = sc.nextInt();
        sc.nextLine();
        if (checkIn == checkOut){
            System.out.println("The check in and check out dates cannot be the same");
            return;
        }
        if (checkIn == 31 || checkOut == 1){
            System.out.println("The check in and check out dates must not be the 1st or 31st");
            return;
        }
        if (checkIn > checkOut){
            System.out.println("The check out date must not occur before the check in date");
            return;
        }
        String discountCode = null;
        System.out.println("Enter a discount code (if applicable): ");
        System.out.println("Type 0 if none");
        discountCode = sc.nextLine();

        if (checkOut > checkIn){ 
            String resId = null;
            if (discountCode == "0") {
                resId = selectedHotel.createReservation(type, guestName, checkIn, checkOut);
            } else {
                resId = selectedHotel.createReservation(type, guestName, checkIn, checkOut, discountCode);
            }
            if (resId == null){
                System.out.println("Failed to create reservation");
                return;
            }
            selectedHotel.printReservationInfo(resId);
            selectedHotel.printDatePriceInfo(resId,checkIn,checkOut); // DEBUG PURPOSES ONLY
            System.out.println("Press 1 to confirm, 0 to cancel");
            int confirmBooking = sc.nextInt();
            sc.nextLine();
            if (confirmBooking == 0){
                selectedHotel.removeReservation(resId);
                System.out.println("Reservation was cancelled");
                return;
            }
            else {
                System.out.println("Sucessfully created reservation ID "+resId);
            }
        }

    }
}
