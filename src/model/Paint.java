/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Belarmino
 */
public class Paint extends JFrame {

    JLabel recado = new JLabel("TEADSDASDAS");
    ImageIcon imageIcon = new ImageIcon(getClass().getResource("teste.png"));

    public Paint() {
        setSize(915, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        recado.setFont(new Font("Arial", Font.BOLD, 10));
        
        Panel p = new Panel();
        p.setLayout(new BorderLayout());
        add(p);
        //add(recado);
    }

    public static void main(String[] args) {
        new Paint();
    }

    class Panel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image image = imageIcon.getImage();
            g.drawImage(image, WIDTH, WIDTH, this);
        }
    }

}
