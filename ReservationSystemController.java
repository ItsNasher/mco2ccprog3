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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createhotel) {
            String newName = JOptionPane.showInputDialog("Input name of new hotel:");
            if (newName != null && !newName.trim().isEmpty()) {
                boolean success = createHotel(newName);
                JOptionPane.showMessageDialog(this, success ? "Hotel successfully created!" : "Hotel name already exists");
            }
            if (e.getSource() == viewhotel) {
                String hotelName = JOptionPane.showInputDialog("Enter the name of the hotel to view:");
                if (hotelName != null && !hotelName.trim().isEmpty()) {
                    Hotel hotel = viewHotel(hotelName);
                    if (hotel != null) {
                        JOptionPane.showMessageDialog(this, hotel.toString()); // Assuming Hotel has a proper toString method
                    } else {
                        JOptionPane.showMessageDialog(this, "Hotel not found.");
                    }
                }

            }
            if (e.getSource() == managehotel) {

            }
            if (e.getSource() == simulatebooking) {

            }
        }
    }
    private boolean createHotel(String newName) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equalsIgnoreCase(newName)) {
                return false; // Hotel name already exists
            }
            return true;
        }
        Hotel hotel = new Hotel(newName);
        hotels.add(hotel);
        return true; // Hotel successfully created
    }

    private Hotel viewHotel(String hotelName) {
        String hotelName = JOptionPane.showInputDialog("Enter the name of the hotel you would you like to view: ");
        if (hotelName != null && !hotelName.trim().isEmpty()){
            Hotel selectedHotel = getHotel(hotelName);
            if (selectedHotel == null){
                JOptionPane.showMessageDialog(this, "Hotel not found.");
                return;
            }
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
}
