package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButtonListener implements ActionListener {
    private final JFrame loginFrame;

    public BackButtonListener(JFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        loginFrame.setVisible(false); // 隐藏登录窗口，游戏进行中不要关闭程序
        MainMenus.fatherFrame = loginFrame;
        MainMenus.main(null);
    }
}
