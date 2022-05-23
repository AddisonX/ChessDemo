package view;

import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TakeBackMoveListener extends Component implements ActionListener {
private Chessboard chessboard;
private int i=0;

public TakeBackMoveListener(Chessboard chessboard){
    this.chessboard=chessboard;
}
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            List<String> results = Files.readAllLines(Paths.get("JumpBoard/JumpBoard.txt"));
            chessboard.LoadChessBoard(results,1+i);
            i++;
            chessboard.currentColor=chessboard.getCurrentColor()== ChessColor.BLACK?ChessColor.BLACK:ChessColor.WHITE;
            if (chessboard.currentColor == ChessColor.WHITE) chessboard.ColorLabel.setText("Round: White");
            else chessboard.ColorLabel.setText("Round: Black");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"已经到了第一步");
            ex.printStackTrace();
        }

    }
}
