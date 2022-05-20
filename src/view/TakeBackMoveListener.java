package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TakeBackMoveListener implements ActionListener {
private Chessboard chessboard;
private int i=0;

public TakeBackMoveListener(Chessboard chessboard){
    this.chessboard=chessboard;
}
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            List<String> results = Files.readAllLines(Paths.get("JumpBoard/JumpBoard.txt"));
            chessboard.LoadChessBoard(results,2);
            i++;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
