package model;

import view.ChessGameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class switchBackgroundListener implements ActionListener {
    ChessGameFrame frame;
    public switchBackgroundListener(ChessGameFrame jFrame){
        this.frame=jFrame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ImageIcon icon1 =new ImageIcon("images/162003D24-2.jpg" );
        JLabel Background = new JLabel(icon1);
        Background.setBounds(0,0,1000, 760);
        frame.remove(frame.Background);
        frame.Background=Background;
        frame.add(Background);
        frame.repaint();
    }
}
