import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A version of the hotel class that can be handled by the GUI. Not necessarily replacing the Hotel class,
 * but moreso making it easier to change information about the hotel.
 */
public class HotelController extends JFrame implements ActionListener {
    private Hotel hotel;
    private ReservationSystemController controller;
    private JTextField newHotelNameField;
    private JButton changeNameButton, addRoomsButton, removeRoomsButton, modifyRoomsButton, updateBasePriceButton, removeReservationButton, removeHotelButton, editDatePriceModifiersButton;

    /**
     * @param controller A instance of ReservationSystemController to make it easier to use methods from that class.
     * @param hotel Instance of the hotel chosen to be modified.
     */
    public HotelController(ReservationSystemController controller, Hotel hotel) {
        this.controller = controller;
        this.hotel = hotel;

        // Initialize components
        newHotelNameField = new JTextField(20);
        changeNameButton = new JButton("Change Name");
        addRoomsButton = new JButton("Add Rooms");
        removeRoomsButton = new JButton("Remove Rooms");
        modifyRoomsButton = new JButton("Modify Rooms");
        updateBasePriceButton = new JButton("Update Base Price");
        removeReservationButton = new JButton("Remove Reservation");
        removeHotelButton = new JButton("Remove Hotel");
        editDatePriceModifiersButton = new JButton("Edit Date Price Modifiers");

        // Add action listeners
        changeNameButton.addActionListener(this);
        addRoomsButton.addActionListener(this);
        removeRoomsButton.addActionListener(this);
        modifyRoomsButton.addActionListener(this);
        updateBasePriceButton.addActionListener(this);
        removeReservationButton.addActionListener(this);
        removeHotelButton.addActionListener(this);
        editDatePriceModifiersButton.addActionListener(this);

        // Layout components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));
        panel.add(new JLabel("New Hotel Name:"));
        panel.add(newHotelNameField);
        panel.add(changeNameButton);
        panel.add(addRoomsButton);
        panel.add(removeRoomsButton);
        panel.add(modifyRoomsButton);
        panel.add(updateBasePriceButton);
        panel.add(removeReservationButton);
        panel.add(removeHotelButton);
        panel.add(editDatePriceModifiersButton);

        add(panel);

        // Set frame properties
        setTitle("Manage Hotel - " + hotel.getName());
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * @param choice the event to be processed.
     */
    @Override
    public void actionPerformed(ActionEvent choice) {
        if (choice.getSource() == changeNameButton) {
            changeHotelName();
        } else if (choice.getSource() == addRoomsButton) {
            addRooms();
        } else if (choice.getSource() == removeRoomsButton) {
            removeRooms();
        } else if (choice.getSource() == modifyRoomsButton) {
            modifyRooms();
        } else if (choice.getSource() == updateBasePriceButton) {
            updateBasePrice();
        } else if (choice.getSource() == removeReservationButton) {
            removeReservation();
        } else if (choice.getSource() == removeHotelButton) {
            removeHotel();
        } else if (choice.getSource() == editDatePriceModifiersButton) {
            editDatePriceModifiers();
        }
    }

    /**
     * Used to change the name of the hotel.
     */
    private void changeHotelName() {
        String newHotelName = newHotelNameField.getText();
        if (newHotelName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hotel name cannot be empty.");
            return;
        }

        int isUnique = 1;
        for (Hotel hotel : controller.getHotels()) {
            if (hotel.getName().equalsIgnoreCase(newHotelName)) {
                isUnique = 0;
                JOptionPane.showMessageDialog(this, "Hotel name already exists.");
                return;
            }
        }
        if (isUnique == 1) {
            int confirmHotel = JOptionPane.showConfirmDialog(this, "New hotel name: " + newHotelName + "\nType OK to confirm, Cancel to cancel.");
            if (confirmHotel == JOptionPane.OK_OPTION) {
                hotel.setHotelName(newHotelName);
                JOptionPane.showMessageDialog(this, "Successfully renamed hotel.");
            }
        }
    }

    /**
     * Adds to the total number of rooms.
     */
    private void addRooms() {
        String addRoomsStr = JOptionPane.showInputDialog(this, "Enter number of rooms to add:");
        if (addRoomsStr != null) {
            int addRooms = Integer.parseInt(addRoomsStr);
            hotel.addRooms(addRooms);
            JOptionPane.showMessageDialog(this, "Added " + addRoomsStr + " Room/s.");
        }
    }

    /**
     * Removes from the total number of rooms.
     */
    private void removeRooms() {
        int totalRooms = hotel.getNumOfRooms();
        String deleteRoomsStr = JOptionPane.showInputDialog(this, "Enter number of rooms to delete:");
        if (deleteRoomsStr != null) {
            int deleteRooms = Integer.parseInt(deleteRoomsStr);
            int confirmDelete = JOptionPane.showConfirmDialog(this, "Press OK to confirm you will delete " + deleteRooms + " rooms\nPress Cancel to cancel");
            if (confirmDelete == JOptionPane.OK_OPTION) {
                hotel.removeRooms(deleteRooms);
                JOptionPane.showMessageDialog(this, "Room count = " + totalRooms);
            }
        }
    }

    /**
     * Allows to modify the amount of types of rooms in the hotel.
     */
    private void modifyRooms() {
        int totalRooms = hotel.getNumOfRooms();
        int standard = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new number of standard rooms:"));
        int deluxe = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new number of deluxe rooms:"));
        int executive = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new number of executive rooms:"));

        if (standard + deluxe + executive == totalRooms) {
            int confirm = JOptionPane.showConfirmDialog(this, "Press OK to confirm, Cancel to cancel.");
            if (confirm == JOptionPane.OK_OPTION) {
                hotel.modifyRooms(standard, deluxe, executive);
            }
        } else {
            JOptionPane.showMessageDialog(this, "The total number of rooms must equal " + totalRooms + ". Please try again.");
        }
    }

    /**
     * Updates the base price per night of the rooms in the hotel.
     */
    private void updateBasePrice() {
        double price = hotel.getBasePrice();
        String newPriceStr = JOptionPane.showInputDialog(this, "Current Price: " +
                price + "\n" + "Enter the new base price:");
        if (newPriceStr != null) {
            double newPrice = Double.parseDouble(newPriceStr);
            int confirmPrice = JOptionPane.showConfirmDialog(this, "Press OK to confirm new base price of " + newPrice + "\nPress Cancel to cancel");
            if (confirmPrice == JOptionPane.OK_OPTION) {
                hotel.updateBasePrice(newPrice);
            }
        }
    }

    /**
     * Using a reservation ID, removes that reservation from the room.
     */
    private void removeReservation() {
        String removeId = JOptionPane.showInputDialog(this, "Enter reservation ID to remove:");
        if (removeId != null) {
            int removeConfirm = JOptionPane.showConfirmDialog(this, "Are you sure you will delete reservation no. " + removeId + "\nPress OK to confirm, Cancel to cancel");
            if (removeConfirm == JOptionPane.OK_OPTION) {
                hotel.removeReservation(removeId);
            }
        }
    }

    /**
     * Removes a hotel from the list.
     */
    private void removeHotel() {
        int confirmRemoveHotel = JOptionPane.showConfirmDialog(this, "Are you sure you will delete hotel " + hotel.getName() + "\nPress OK to confirm, Cancel to cancel");
        if (confirmRemoveHotel == JOptionPane.OK_OPTION) {
            controller.getHotels().remove(hotel);
            JOptionPane.showMessageDialog(this, "Removing hotel");
            dispose(); // Close the window
        }
    }

    /**
     * Allows for editing of price on certain dates.
     */
    private void editDatePriceModifiers() {
        String[] options = {"Single date", "Multiple dates", "Return to menu"};
        int dpmChoice = JOptionPane.showOptionDialog(this, "Set date price modifier for:", "Edit Date Price Modifiers",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (dpmChoice) {
            case 0:
                String singleDate = JOptionPane.showInputDialog(this, "Enter the date:");
                double singleDatePrice = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter the price:"));
                hotel.setDatePriceModifier(Integer.parseInt(singleDate), singleDatePrice);
                JOptionPane.showMessageDialog(this, "Date price modifier added");
                break;
            default:
                break;
        }
    }
}
