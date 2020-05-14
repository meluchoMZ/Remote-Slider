package remoteslider;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * This class provides a graphical user interface to the app.
 * @author Miguel Blanco Godón (github[meluchoMZ])
 */
public class GUI extends JFrame implements ActionListener {

    private JButton exitButton;
    private JLabel welcomeText;
    private JLabel noWarranty;
    private JLabel noWarrantyCont;
    private JLabel info;
    private JPanel panel;
    private JTextField portInput;
    private JButton startButton;
    boolean started = false;
    
    /**
     * Constructor of the RemoteSliderDesktop GUI.
     */
    public GUI() {
        initComponents();
    }
    
    /**
     * Initializes the components in the GUI. It is called by the constructor.
     */
    private void initComponents() {
        panel = new JPanel();
        welcomeText = new JLabel();
        noWarranty = new JLabel();
        noWarrantyCont = new JLabel();
        startButton = new JButton();
        exitButton = new JButton();
        portInput = new JTextField();
        info = new JLabel();
     
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        welcomeText.setText("Welcome to RemoteSlider");
        noWarranty.setText("This program was created by meluchoMZ and comes under no warranty");
        info.setText("Please insert the port where you desire to start the RS server");
        startButton.setText("Start server");     
        exitButton.setText("EXIT");
        noWarrantyCont.setText("only for educational purposes");

        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(welcomeText)
                        .addComponent(noWarranty)
                        .addComponent(noWarrantyCont)
                        .addComponent(info))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(portInput, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(exitButton)))
                .addContainerGap(62, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startButton)
                .addGap(230,230,230))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(welcomeText)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noWarranty)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noWarrantyCont)
                .addGap(16, 16, 16)
                .addComponent(info)
                .addGap(18, 18, 18)
                .addComponent(portInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(exitButton)
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        this.pack();
        
        this.setTitle("Remote Slider Desktop");
        
        panel.setVisible(true);

        /* Actions settings */
        startButton.addActionListener(this);
        exitButton.addActionListener(this);

    }
    
    /**
     * Manages the interaction with the GUI.
     * @param event an ActionEvent, the event initiated by the user.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        
        if (source == exitButton) {
            System.exit(0);
        }
        
        if (started) {
            try {
                JOptionPane.showMessageDialog(this, "The server is already working. To close press EXIT\nYour IP is " + InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException UHX) {
                JOptionPane.showMessageDialog(this, "The server is already working. To close press EXIT");
            }
            return;
        }
        
        if (source == startButton) {
            String text = portInput.getText();
            if (text.isEmpty())
                JOptionPane.showMessageDialog(this, "The port number cannot be empty");
            else{
                int port = Integer.parseInt(text);
                if (port <= 1024 || port >65535)
                    JOptionPane.showMessageDialog(this, "The port number must be between 1024 and 65535");
                else {
                    RSserver thread = new RSserver(port);
                    thread.start();
                    started = true;
                    try {
                        JOptionPane.showMessageDialog(this, "Server started. Now you can connect your phone\nYour IP is " + InetAddress.getLocalHost().getHostAddress());                
                    } catch (UnknownHostException UHX2) {
                        JOptionPane.showMessageDialog(this, "Server started. Now you can connect your phone");  
                    }
                }
            }
        }
    }
    
    public static void configureGUI(GUI gui) {
        gui.setVisible(true);
        gui.setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point pt = new Point((int) Math.ceil(screenSize.getWidth()/2)-gui.getWidth()/2,(int) Math.ceil(screenSize.getHeight()/2)-gui.getHeight()/2);
        gui.setLocation(pt);
    }
    
}
