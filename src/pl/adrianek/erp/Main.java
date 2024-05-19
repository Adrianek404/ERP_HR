package pl.adrianek.erp;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {

    JButton HrModule, UserModule, ProjectManagerModule;
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

        UserModule = new JButton("UserModule");
        UserModule.setBounds(100, 150, 120, 30);
        UserModule.setBorderPainted(false);
        UserModule.setOpaque(true);
        UserModule.setBackground(Color.BLACK);
        UserModule.setForeground(Color.WHITE);
        UserModule.addActionListener(this);
        add(UserModule);

        ProjectManagerModule = new JButton("ProjectManagerModule");
        ProjectManagerModule.setBounds(360, 150, 140, 30);
        ProjectManagerModule.setBorderPainted(false);
        ProjectManagerModule.setOpaque(true);
        ProjectManagerModule.setBackground(Color.BLACK);
        ProjectManagerModule.setForeground(Color.WHITE);
        ProjectManagerModule.addActionListener(this);
        add(ProjectManagerModule);

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
        if (e.getSource().equals(UserModule)){
            setVisible(false);
            new LoginUser().setVisible(true);
        }
        if (e.getSource().equals(ProjectManagerModule)){
            setVisible(false);
            new ProjectManager().setVisible(true);
        }
    }
}