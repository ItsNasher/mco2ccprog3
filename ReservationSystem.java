import java.util.HashMap;
import java.util.Scanner;

public class ReservationSystem {
    private HashMap<String,Hotel> hotels = new HashMap<>();

    public boolean createHotel(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Input name of new hotel: ");
        String newName = sc.nextLine();
        

        if (hotels.containsKey(newName)) {
            System.out.println("Hotel name already exists");
            return false;
        } 
        Hotel hotel = new Hotel(newName); 
        hotels.put(newName,hotel);
        System.out.println("Hotel successfully created!");

        return true;
    }

    public void viewHotel(){
        Scanner sc = new Scanner (System.in);

        System.out.print("Enter the name of the hotel you would you like to view: ");
        String hotelName = sc.nextLine();

        Hotel selectedHotel = hotels.get(hotelName);
        
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
                    selectedHotel.printBasicInfo();
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
    
    public void manageHotel(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the name of the hotel you would you like to manage: ");
        String hotelName = sc.nextLine();

        Hotel selectedHotel = hotels.get(hotelName);
         
        if (selectedHotel == null) {
            System.out.println("Hotel not found.");
            return;
        }
        boolean menu = true;
        while (menu){
            System.out.println("0. Exit the menu");
            System.out.println("1. Change name of the hotel");
            System.out.println("2. Add rooms");
            System.out.println("3. Modify rooms");
            System.out.println("4. Remove rooms");
            System.out.println("5. Update base price of each room");
            System.out.println("6. Remove reservation");
            System.out.println("7. Remove hotel");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
            case 0:
                menu = false;
                break;
            case 1:
                updateHotelName(selectedHotel);
                break;

            case 2:
                System.out.println("Enter number of rooms to add: ");
                int addRooms = sc.nextInt();
                sc.nextLine();
                selectedHotel.addRooms(addRooms);
                break;    

            case 3:
                System.out.println("0. Go back");
                selectedHotel.printRoomTypeInfo();
                System.out.println("Select a room to modify: ");
                int modifyRoom = sc.nextInt();
                sc.nextLine();

                if(modifyRoom == 0){
                    System.out.println("Going back");
                    break;
                }

                System.out.println("Enter new number of standard rooms");
                int standard = sc.nextInt();
                sc.nextLine();

                System.out.println("Enter new number of deluxe rooms");
                int deluxe = sc.nextInt();
                sc.nextLine();

                System.out.println("Enter new number of executive rooms");
                int executive = sc.nextInt();
                sc.nextLine();

                System.out.println("Standard rooms: "+standard);
                System.out.println("Deluxe rooms: "+deluxe);
                System.out.println("Executive rooms: "+executive);

                System.out.println("Press 1 to confirm, 0 to cancel");
                if (sc.nextInt() == 1){
                    selectedHotel.modifyRooms(standard, deluxe, executive);
                }
                else{
                    System.out.println("Cancelling. . .");
                }
                break;
            
            case 4:
                System.out.println("Enter number of rooms to delete: ");
                int deleteRooms = sc.nextInt();
                sc.nextLine();

                System.out.println("Press 1 to confirm you will delete "+deleteRooms+" rooms\nPress 0 to cancel");
                int confirmDelete = sc.nextInt();
                sc.nextLine();

                if (confirmDelete == 1){
                    selectedHotel.removeRooms(deleteRooms);
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
                break;
            case 7:
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateHotelName(Hotel selectedHotel) {
        Scanner sc = new Scanner(System.in);

        while(true) { // infinite loop btw
            System.out.println("Type 0 to exit.");
            System.out.println("Enter new name of the hotel: ");
            String newName = sc.nextLine();
    
            if (newName.equals("0")) {
                return;
            }
    
            if (hotels.containsKey(newName)) {
                System.out.println("Hotel name already exists");
            } else {
                System.out.println("New hotel name: " + newName);
                System.out.println("Type 1 to confirm, 0 to cancel: ");
                int choice = sc.nextInt();
                sc.nextLine();
    
                if (choice == 1) {
                    hotels.remove(selectedHotel.getName());
                    selectedHotel.setHotelName(newName);
                    hotels.put(newName, selectedHotel); 
                    return;
                }
            }
        }
    }
}
