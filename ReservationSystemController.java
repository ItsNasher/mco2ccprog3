import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Scanner;

public class ReservationSystemController extends JFrame implements ActionListener {
    JButton createhotel;
    JButton viewhotel;
    JButton managehotel;
    JButton simulatebooking;

    private ArrayList<Hotel> hotels = new ArrayList<>();

    ReservationSystemController() {
        //separate buttons for each
        createhotel = new JButton();
        createhotel.setBounds(175, 70, 150, 45);
        createhotel.addActionListener(this);
        createhotel.setText("Create Hotel");
        createhotel.setFocusable(false);

        viewhotel = new JButton();
        viewhotel.setBounds(175, 130, 150, 45);
        viewhotel.addActionListener(this);
        viewhotel.setText("View Hotel");
        viewhotel.setFocusable(false);

        managehotel = new JButton();
        managehotel.setBounds(175, 190, 150, 45);
        managehotel.addActionListener(this);
        managehotel.setText("Manage Hotel");
        managehotel.setFocusable(false);

        simulatebooking = new JButton();
        simulatebooking.setBounds(175, 250, 150, 45);
        simulatebooking.addActionListener(this);
        simulatebooking.setText("Simulate Booking");
        simulatebooking.setFocusable(false);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(createhotel);
        this.add(viewhotel);
        this.add(managehotel);
        this.add(simulatebooking);
    }

    @Override
    public void actionPerformed(ActionEvent choice) { //scanning the input
            if (choice.getSource() == createhotel) {
                String newName = JOptionPane.showInputDialog("Input name of new hotel:");
                if (newName != null && !newName.trim().isEmpty()) {
                    boolean success = createHotel(newName);
                    JOptionPane.showMessageDialog(this, success ? "Hotel successfully created!" : "Hotel name already exists");
                }
            }
            if (choice.getSource() == viewhotel)
                viewHotel();

            if (choice.getSource() == managehotel)
                manageHotel();

            if (choice.getSource() == simulatebooking)
                simulateBooking();
    }
    private Hotel getHotel (String hotelName){
        for (Hotel hotel : hotels){
            if (hotel.getName().equalsIgnoreCase(hotelName))
                return hotel;
        }
        return null;
    }
    private boolean createHotel(String newName) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equalsIgnoreCase(newName)) {
                return false; // Hotel name already exists
            }
        }
        Hotel hotel = new Hotel(newName);
        hotels.add(hotel);
        return true; // Hotel successfully created
    }

    private void viewHotel() {
        String hotel = JOptionPane.showInputDialog("Enter the name of the hotel you would you like to view: ");
        if (hotel != null || !hotel.trim().isEmpty()) {
            Hotel selectedHotel = getHotel(hotel);
            if (selectedHotel == null) {
                JOptionPane.showMessageDialog(this, "Hotel not found.");
                return;
            }
            boolean menu = true;
            while (menu) {
                String[] options = {"View Basic Info", "View Room Availability",
                        "View Room Info", "View Reservation Info", "Back to Main Menu"};

                int choice = JOptionPane.showOptionDialog(this, "Choose an Option: ",
                        "Hotel Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        options, options[0]);
                //for x
                if (choice == -1){
                    menu = false;
                    break;
                }

                switch (choice) {
                    case 0: //basic info
                        JOptionPane.showMessageDialog(this, selectedHotel.printBasicInfo());
                        break; // printBasicinfo isnt inline with what the parameter is looking for ^

                    case 1: //room availability
                        String chooseday = JOptionPane.showInputDialog("Enter a day in the month (1-31): ");
                        int day = Integer.parseInt(chooseday);
                        if (day < 1 || day > 31){
                            JOptionPane.showMessageDialog(this, "Invalid day. Enter a value between 1 to 31.");
                            break;
                        }
                        JOptionPane.showMessageDialog(this, "Number of available rooms" +
                                selectedHotel.getNumAvailableRooms(day) + "\n" + "Number of booked rooms" +
                                selectedHotel.getNumBookedRooms(day));

                        System.out.println("Number of available Rooms: " + selectedHotel.getNumAvailableRooms(day));
                        System.out.println("Number of booked Rooms: " + selectedHotel.getNumBookedRooms(day));
                        break;

                    case 2: //room info
                        String roomNumber = JOptionPane.showInputDialog("Enter the room number: ");
                        try {
                            int room = Integer.parseInt(roomNumber);
                            String info = selectedHotel.printRoomInfo(room);
                            if (info != null)
                                JOptionPane.showMessageDialog(this, info);
                            else
                                JOptionPane.showMessageDialog(this, info);
                        }catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Invalid input, enter another value.");
                        }
                        break;
                    case 3: //reservation info
                        String reservationId = JOptionPane.showInputDialog("Enter a reservation number: ");
                        String reservationInfo = selectedHotel.printReservationInfo(reservationId);
                        if (reservationInfo != null)
                            JOptionPane.showMessageDialog(this, reservationInfo);
                        else
                            JOptionPane.showMessageDialog(this, "Reservation not found.");
                        break;
                    case 4: //main menu
                        menu = false;
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Invalid choice. Please try again");
                }
            }
        }
    }
    private void manageHotel(){ //to do (use the hotel controller)
        String hotel = JOptionPane.showInputDialog("Enter the name of the hotel: ");
        if (hotel != null && !hotel.trim().isEmpty()){
            Hotel selectedHotel = getHotel(hotel);
            if (selectedHotel == null)
                JOptionPane.showMessageDialog(this, "Hotel not found.");
            else
                new HotelController(this, selectedHotel).setVisible(true);
        }
    }
    public void simulateBooking(){ //to do
        String guestName = JOptionPane.showInputDialog("Enter the guest name for reservation:");
        if (guestName == null || guestName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Guest name cannot be empty.");
            return;
        }

        String hotelName = JOptionPane.showInputDialog("Enter the name of the hotel you would like to book:");
        if (hotelName == null || hotelName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hotel name cannot be empty.");
            return;
        }

        Hotel selectedHotel = getHotel(hotelName);
        if (selectedHotel == null) {
            JOptionPane.showMessageDialog(this, "Hotel not found.");
            return;
        }

        String[] roomTypes = {"Standard", "Deluxe", "Executive"};
        String roomType = (String) JOptionPane.showInputDialog(this, "Enter type of room to book:", "Room Type",
                JOptionPane.QUESTION_MESSAGE, null, roomTypes, roomTypes[0]);
        int type = 0;
        if (roomType != null) {
            switch (roomType) {
                case "Standard":
                    type = 0;
                    break;
                case "Deluxe":
                    type = 1;
                    break;
                case "Executive":
                    type = 2;
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Invalid room type, defaulting to standard room.");
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(this, "No room type selected, defaulting to standard room.");
        }

        int checkIn = Integer.parseInt(JOptionPane.showInputDialog("Enter a check-in date (1-30):"));
        int checkOut = Integer.parseInt(JOptionPane.showInputDialog("Enter a check-out date (2-31):"));

        if (checkIn == checkOut) {
            JOptionPane.showMessageDialog(this, "The check-in and check-out dates cannot be the same.");
            return;
        }
        if (checkIn == 31 || checkOut == 1) {
            JOptionPane.showMessageDialog(this, "The check-in and check-out dates must not be the 1st or 31st.");
            return;
        }
        if (checkIn > checkOut) {
            JOptionPane.showMessageDialog(this, "The check-out date must not occur before the check-in date.");
            return;
        }

        String discountCode = JOptionPane.showInputDialog("Enter a discount code (if applicable). Type 0 if none:");
        if (discountCode == null || discountCode.trim().isEmpty() || discountCode.equals("0")) {
            discountCode = null;
        }

        String resId;
        if (discountCode == null) {
            resId = selectedHotel.createReservation(type, guestName, checkIn, checkOut);
        }
        else
            resId = selectedHotel.createReservation(type, guestName, checkIn, checkOut, discountCode);

        if (resId == null) {
            JOptionPane.showMessageDialog(this, "Failed to create reservation.");
            return;
        }

        selectedHotel.printReservationInfo(resId);
        selectedHotel.printDatePriceInfo(resId, checkIn, checkOut);
        int confirmBooking = JOptionPane.showConfirmDialog(this, "Press OK to confirm, Cancel to cancel.");
        if (confirmBooking == JOptionPane.CANCEL_OPTION) {
            selectedHotel.removeReservation(resId);
            JOptionPane.showMessageDialog(this, "Reservation was cancelled.");
        }
        else
            JOptionPane.showMessageDialog(this, "Successfully created reservation ID " + resId);
    }
    public ArrayList<Hotel> getHotels(){
        return hotels;
    }
}
