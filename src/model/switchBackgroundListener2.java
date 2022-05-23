package model;

import view.ChessGameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class switchBackgroundListener2 implements ActionListener {
    ChessGameFrame frame;
    public switchBackgroundListener2(ChessGameFrame jFrame){
        this.frame=jFrame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ImageIcon icon1 =new ImageIcon("images/01193959eeec64a801202b0c23804b.jpg@1280w_1l_2o_100sh.jpg" );
        JLabel Background = new JLabel(icon1);
        Background.setBounds(0,0,1000, 760);
        frame.remove(frame.Background);
        frame.Background=Background;
        frame.add(Background);
        frame.repaint();
    }
}
