package pl.adrianek.erp;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {

    JButton HrModule;
    Main(){
        setTitle("HRAPP");

        setLayout(null);

        HrModule = new JButton("HrModule");
        HrModule.setBounds(230, 150, 120, 30);
        HrModule.setBorderPainted(false);
        HrModule.setOpaque(true);
        HrModule.setBackground(Color.BLACK);
        HrModule.setForeground(Color.WHITE);
        HrModule.addActionListener(this);
        add(HrModule);

        getContentPane().setBackground(Color.WHITE);

        setSize(600, 400);
        setVisible(true);
        setLocation(600, 350);
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(HrModule)){
            setVisible(false);
            new Pracownicy().setVisible(true);
        }
    }
}