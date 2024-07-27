import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ReservationSystemController extends JFrame implements ActionListener{
    JButton createhotel;
    JButton viewhotel;
    JButton managehotel;
    JButton simulatebooking;
   ReservationSystemController(){
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
    public void actionPerformed (ActionEvent e){
       if (e.getSource() == createhotel){

       }
       if (e.getSource() == viewhotel){

       }
       if(e.getSource() == managehotel){

       }
       if(e.getSource() == simulatebooking){

       }
   }
}
