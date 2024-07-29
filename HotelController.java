import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelController extends JFrame implements ActionListener {
    private Hotel hotel;
    private JButton modifyRooms;
    private JButton addRooms;
    private JButton removeRooms;
    private JButton updatePrice;
    private JTextField roomField;
    private JTextField deluxeField;
    private JTextField executiveField;
    private JTextField addRoomField;
    private JTextField removeRoomField;
    private JTextField priceField;

    public HotelController(Hotel hotel) {
        this.hotel = hotel;
        initialize();
    }

    private void initialize() {
        modifyRooms = new JButton("Modify Rooms");
        modifyRooms.setBounds(50, 50, 200, 30);
        modifyRooms.addActionListener(this);

        addRooms = new JButton("Add Rooms");
        addRooms.setBounds(50, 100, 200, 30);
        addRooms.addActionListener(this);

        removeRooms = new JButton("Remove Rooms");
        removeRooms.setBounds(50, 150, 200, 30);
        removeRooms.addActionListener(this);

        updatePrice = new JButton("Update Base Price");
        updatePrice.setBounds(50, 200, 200, 30);
        updatePrice.addActionListener(this);

        roomField = new JTextField();
        roomField.setBounds(260, 50, 100, 30);
        deluxeField = new JTextField();
        deluxeField.setBounds(260, 100, 100, 30);
        executiveField = new JTextField();
        executiveField.setBounds(260, 150, 100, 30);
        addRoomField = new JTextField();
        addRoomField.setBounds(260, 200, 100, 30);
        removeRoomField = new JTextField();
        removeRoomField.setBounds(260, 250, 100, 30);
        priceField = new JTextField();
        priceField.setBounds(260, 300, 100, 30);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setSize(400, 400);
        this.setVisible(true);

        this.add(modifyRooms);
        this.add(addRooms);
        this.add(removeRooms);
        this.add(updatePrice);
        this.add(roomField);
        this.add(deluxeField);
        this.add(executiveField);
        this.add(addRoomField);
        this.add(removeRoomField);
        this.add(priceField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modifyRooms) {
            int standard = Integer.parseInt(roomField.getText());
            int deluxe = Integer.parseInt(deluxeField.getText());
            int executive = Integer.parseInt(executiveField.getText());
            boolean success = hotel.modifyRooms(standard, deluxe, executive);
            JOptionPane.showMessageDialog(this, success ? "Rooms successfully modified" : "Failed to modify rooms");
        }

        if (e.getSource() == addRooms) {
            int num = Integer.parseInt(addRoomField.getText());
            hotel.addRooms(num);
            JOptionPane.showMessageDialog(this, "Rooms successfully added");
        }

        if (e.getSource() == removeRooms) {
            int num = Integer.parseInt(removeRoomField.getText());
            boolean success = hotel.removeRooms(num);
            JOptionPane.showMessageDialog(this, success ? "Rooms successfully removed" : "Failed to remove rooms");
        }

        if (e.getSource() == updatePrice) {
            double newPrice = Double.parseDouble(priceField.getText());
            boolean success = hotel.updateBasePrice(newPrice);
            JOptionPane.showMessageDialog(this, success ? "Base price updated successfully" : "Failed to update price");
        }
    }
}