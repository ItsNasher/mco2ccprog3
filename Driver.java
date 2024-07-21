import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;

public class Driver {
    public static void main (String[] args){
        Hotel hotel = new Hotel("Sample Hotel");
        for (Room room : hotel.getRoomList()) {
            System.out.println("Room " + room.getRoomName() + " is a " + room.getRoomType() + " room with price " + room.getBasePrice());
        }
    } //comment this out
    }
        /*ArrayList<Hotel> hotelList = new ArrayList<>();
        Scanner sc = new Scanner (System.in);
        ReservationSystem test = new ReservationSystem();
        int userInput;

        System.out.println("Welcome to your Hotel Management System!");
        System.out.println("========================================");
        do {
            System.out.println("[1] Create Hotel");
            System.out.println("[2] View Hotel");
            System.out.println("[3] Manage Hotel");
            System.out.println("[4] Simulate Booking");
            System.out.println("[5] Exit");
            System.out.print("Enter a Number: ");

            userInput = sc.nextInt();
            sc.nextLine();

            switch (userInput) {
                case 1: {
                    test.createHotel(hotelList);
                    break;
                }
                case 2: {
                    test.viewHotel(hotelList);
                    break;
                }
                case 3: {
                    test.manageHotel(hotelList);
                    break;
                }
                case 4: {
                    test.simulateBooking(hotelList);
                    break;
                }
                default: {
                    if (userInput < 1 || userInput > 5)
                       System.out.println("\nEnter a number between 1 - 5.");
                }
            }
        } while (userInput != 5);
    }
}*/
