import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {
    private static GUI guiInstance;
    private JButton buttonA, buttonB, buttonC, buttonD;
    private JLabel panel;
    private JLayeredPane pane;
    private int frameWidth = 510;
    private int frameHeight = 640;
    private Controller controller;


    public GUI() {
        this.controller = new Controller();

        setTitle("DWS T2");
        setSize(frameWidth, frameHeight);
        setBackground(Color.WHITE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        pane = new JLayeredPane();
        pane.setBounds(0, 0, frameWidth, frameHeight);

        /*----- Layer0 <버튼> ------*/
        JPanel layer0 = new JPanel();
        layer0.setBounds(0, 0, frameWidth, frameHeight);
        layer0.setOpaque(false);
        layer0.setLayout(null);

        buttonA = new JButton(new ImageIcon("data/button/button_a.png"));
        buttonB = new JButton(new ImageIcon("data/button/button_b.png"));
        buttonC = new JButton(new ImageIcon("data/button/button_c.png"));
        buttonD = new JButton(new ImageIcon("data/button/button_d.png"));
        buttonA.setBorderPainted(false);
        buttonB.setBorderPainted(false);
        buttonC.setBorderPainted(false);
        buttonD.setBorderPainted(false);
        buttonA.setFocusPainted(false);
        buttonB.setFocusPainted(false);
        buttonC.setFocusPainted(false);
        buttonD.setFocusPainted(false);
        buttonA.setContentAreaFilled(false);
        buttonB.setContentAreaFilled(false);
        buttonC.setContentAreaFilled(false);
        buttonD.setContentAreaFilled(false);
        buttonA.setBounds(10,10,230,280);
        buttonB.setBounds(10, 310, 230, 280);
        buttonC.setBounds(260, 10, 230, 280);
        buttonD.setBounds(260, 310, 230, 280);
        buttonA.addActionListener(this);
        buttonB.addActionListener(this);
        buttonC.addActionListener(this);
        buttonD.addActionListener(this);
        layer0.add(buttonA);
        layer0.add(buttonB);
        layer0.add(buttonC);
        layer0.add(buttonD);

        /*----- <시계 panel> ------*/
        panel = new JLabel(new ImageIcon("data/base/back.png"));
        panel.setBounds(25, 75, 450, 450);


        /*----- Layer1 <mode> ------*/
        JPanel layer1 = new JPanel();
        layer1.setBounds(0, 0, frameWidth, frameHeight);
        layer1.setLayout(null);
        layer1.setOpaque(false);

        JLabel modeImage[] = new JLabel[6];
        String[] modeImagePath = new String[6];
        for (int i = 0; i < 6; i++) {
            modeImagePath[i] = "data/indicator/mode"+(i+1)+".png";
            modeImage[i] = new JLabel(new ImageIcon(modeImagePath[i]));
            if(i<3) modeImage[i].setBounds((240+50*i), 175, 30, 30);
            else modeImage[i].setBounds((260+50*(i-3)), 220, 30, 30);
            layer1.add(modeImage[i]);
        }


        /*----- Layer2 <change> ------*/

        changeGUI gui = new changeGUI();
        gui.setSize(frameWidth,frameHeight);

        pane.add(layer0, new Integer(0));
        pane.add(panel, new Integer(1));
        pane.add(layer1, new Integer(2));
        pane.add(gui, new Integer(3));

        add(pane);
        setVisible(true);
    }

    public class changeGUI extends JPanel {
        private BufferedImage image;
        private int[] modeIndicator;
        private String segment1;
        private String segment2;
        private String[] segPath1;
        private String[] segPath2;

        public changeGUI() {
            setOpaque(false);
        }

        public BufferedImage loadImage(String imagePath) {
            image = null;
            try {
                image = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return image;
        }

        @Override
        public void paint(Graphics g) {
            modeIndicator = controller.getModeIndicator();
            segment1 = controller.getSegment1();
            segment2 = controller.getSegment2();
            segPath1 = new String[6];
            segPath2 = new String[9];
            super.paintComponent(g);

            for (int i = 0; i < modeIndicator.length; i++) {
                if(controller.getCurrentMode() == i) g.setColor(Color.GREEN);
                else {
                    if (modeIndicator[i] == 1) g.setColor(Color.ORANGE);
                    else g.setColor(Color.GRAY);
                }
                if (i < 3) g.fillRect((250 + 50 * i), 208, 10, 3);
                else g.fillRect((270 + 50 * (i - 3)), 253, 10, 3);
            }

            for (int i = 0; i < segment1.length(); i++) {
                segPath1[i] = "data/mainseg/" + segment1.charAt(i) + ".png";
                this.image = loadImage(segPath1[i]);
                if(i<2) g.drawImage(this.image, 135+(43*i), 295, 45, 67, this);
                else if(i>=2 && i<4) g.drawImage(this.image, 142+(43*i), 295, 45, 67, this);
                else g.drawImage(this.image, 207+(28*i), 317, 30, 45, this);
            }

            for (int i = 0; i < segment2.length(); i++) {
                segPath2[i] = "data/subseg/" + segment2.charAt(i) + ".png";
                this.image = loadImage(segPath2[i]);
                if(i<3) g.drawImage(this.image, 138+(24*i), 385, 23, 35, this);
                else g.drawImage(this.image, 158+(24*i), 385, 23, 35, this);

            }

        }
    }





    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==buttonA) {
            pressButtonA();
        }
        if(e.getSource()==buttonB) {
            pressButtonB();
        }
        if(e.getSource()==buttonC) pressButtonC();
        if(e.getSource()==buttonD) pressButtonD();
        this.invalidate();
        this.repaint();
    }

    public static GUI getGUIInstance() {
        if(guiInstance == null) guiInstance = new GUI();
        return guiInstance;
    }

    public void pressButtonA() {
        //controller.getCurrentMode();
        controller.testA();
        System.out.println("press A");
    }

    public void pressButtonB() {
        controller.testB();
        System.out.println("press B");
    }

    public void pressButtonC() {
        System.out.println("press C");
    }

    public void pressButtonD() {
        System.out.println("press D");
    }


    public static void main(String[] args) {
        GUI.getGUIInstance();
    }
}

