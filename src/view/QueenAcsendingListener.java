package view;

import model.ChessComponent;
import model.QueenChessComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueenAcsendingListener implements ActionListener {
    private Chessboard chessboard;
    private ChessComponent chessComponent;

    public QueenAcsendingListener(Chessboard chessboard, ChessComponent chessComponent){
        this.chessboard=chessboard;
        this.chessComponent=chessComponent;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        chessboard.add(chessComponent = new QueenChessComponent(((ChessComponent) chessComponent).getChessboardPoint(), ((ChessComponent) chessComponent).getLocation(), chessComponent.getChessColor(), chessboard.clickController, chessboard.CHESS_SIZE));
        chessComponent.repaint();
    }
}
