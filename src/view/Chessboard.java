//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package view;

import controller.ClickController;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import javax.swing.*;

import model.*;

public class Chessboard extends JComponent {
    private static final int CHESSBOARD_SIZE = 8;
    private final ChessComponent[][] chessComponents = new ChessComponent[8][8];
    public ChessColor currentColor;
    public final ClickController clickController;
    public final int CHESS_SIZE;
    public JLabel ColorLabel=new JLabel();
    public JLabel label;
    public int FileSource;

    public void setColorLabel(JLabel jLabel) {
        this.ColorLabel = jLabel;
    }

    public void setLabel(JLabel jLabel) {
        this.label = jLabel;

    }

    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }

    public Chessboard(int width, int height) {
        this.currentColor = ChessColor.WHITE;
        this.clickController = new ClickController(this);
        this.setLayout((LayoutManager) null);
        this.setSize(width, height);
        this.CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, this.CHESS_SIZE);
        this.initiateEmptyChessboard();
        this.initRookOnBoard(0, 0, ChessColor.BLACK);
        this.initRookOnBoard(0, 7, ChessColor.BLACK);
        this.initRookOnBoard(7, 0, ChessColor.WHITE);
        this.initRookOnBoard(7, 7, ChessColor.WHITE);


        this.initKingOnBoard(0, 4, ChessColor.BLACK);
        this.initKingOnBoard(7, 4, ChessColor.WHITE);

        this.initKnightOnBoard(0, 6, ChessColor.BLACK);
        this.initKnightOnBoard(0, 1, ChessColor.BLACK);
        this.initKnightOnBoard(7, 6, ChessColor.WHITE);
        this.initKnightOnBoard(7, 1, ChessColor.WHITE);

        this.initQueenOnBoard(0, 3, ChessColor.BLACK);
        this.initQueenOnBoard(7, 3, ChessColor.WHITE);

        this.initBishopOnBoard(0, 2, ChessColor.BLACK);
        this.initBishopOnBoard(0, 5, ChessColor.BLACK);
        this.initBishopOnBoard(7, 2, ChessColor.WHITE);
        this.initBishopOnBoard(7, 5, ChessColor.WHITE);

        for (int i = 0; i < 8; i++) {
            this.initPawnOnBoard(1, i, ChessColor.BLACK);
        }
        for (int i = 0; i < 8; i++) {
            this.initPawnOnBoard(6, i, ChessColor.WHITE);

        }

    }

    public ChessComponent[][] getChessComponents() {
        return this.chessComponents;
    }

    public ChessColor getCurrentColor() {
        return this.currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX();
        int col = chessComponent.getChessboardPoint().getY();
        if (this.chessComponents[row][col] != null) {
            this.remove(this.chessComponents[row][col]);
        }

        this.add(this.chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        if (!(chess2 instanceof EmptySlotComponent)) {
            this.remove((Component) chess2);
            this.add((Component) (chess2 = new EmptySlotComponent(((ChessComponent) chess2).getChessboardPoint(), ((ChessComponent) chess2).getLocation(), this.clickController, this.CHESS_SIZE)));
        }

        chess1.swapLocation((ChessComponent) chess2);
        int row1 = chess1.getChessboardPoint().getX();
        int col1 = chess1.getChessboardPoint().getY();
        this.chessComponents[row1][col1] = chess1;
        int row2 = ((ChessComponent) chess2).getChessboardPoint().getX();
        int col2 = ((ChessComponent) chess2).getChessboardPoint().getY();
        this.chessComponents[row2][col2] = (ChessComponent) chess2;
        chess1.repaint();
        ((ChessComponent) chess2).repaint();


        if (whiteKingIsCheckmated(getChessComponents())) {
            label.setText("white is chessmated!");
        } else if (blackKingIsCheckmated(getChessComponents())) {
            label.setText("black is chessmated!");
        } else if(!whiteKingIsCheckmated(getChessComponents())&&!blackKingIsCheckmated(getChessComponents())){
            label.setText(" ");}
        if(blackIsFailed(getChessComponents())){
            label.setText("White wins!");
        }else if(whiteIsFailed(getChessComponents())){
            label.setText("Black wins!");
        }
        if(blackIsFailed(getChessComponents())&&!blackKingIsCheckmated(getChessComponents())){
            label.setText("Draw");
        }
        else if(whiteIsFailed(getChessComponents())&&!whiteIsFailed(getChessComponents())){
            label.setText("Draw");
        }

        //兵的底线升变
        if(chess1 instanceof PawnChessComponent && chess1.getChessColor()==ChessColor.WHITE && chess1.getChessboardPoint().getX() == 0){
            this.remove(chess1);
            Object[] fruits = {"Queen","Rook","Bishop","Knight"};
            int op = JOptionPane.showOptionDialog(null, "选择升变的棋子", "标题",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null, fruits, fruits[0]);
            System.out.print((String)fruits[op]);
            switch (op){
                case 0:

                this.add(chess1 = new QueenChessComponent(((ChessComponent) chess1).getChessboardPoint(), ((ChessComponent) chess1).getLocation(), chess1.getChessColor(), this.clickController, this.CHESS_SIZE));
                chess1.repaint();
                case 1:

                    this.add(chess1 = new RookChessComponent(((ChessComponent) chess1).getChessboardPoint(), ((ChessComponent) chess1).getLocation(), chess1.getChessColor(), this.clickController, this.CHESS_SIZE));
                    chess1.repaint();
                case 2:

                    this.add(chess1 = new BishopChessComponent(((ChessComponent) chess1).getChessboardPoint(), ((ChessComponent) chess1).getLocation(), chess1.getChessColor(), this.clickController, this.CHESS_SIZE));
                    chess1.repaint();
                case 3:

                    this.add(chess1 = new KnightChessComponent(((ChessComponent) chess1).getChessboardPoint(), ((ChessComponent) chess1).getLocation(), chess1.getChessColor(), this.clickController, this.CHESS_SIZE));
                    chess1.repaint();
            }
        } else if (chess1 instanceof PawnChessComponent && chess1.getChessColor()==ChessColor.BLACK && chess1.getChessboardPoint().getX() == 7){
            this.remove(chess1);
            Object[] fruits = {"Queen","Rook","Bishop","Knight"};
            int op = JOptionPane.showOptionDialog(null, "选择升变的棋子", "标题",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null, fruits, fruits[0]);
            System.out.print((String)fruits[op]);
            switch (op){
                case 0:
                    this.add(chess1 = new QueenChessComponent(((ChessComponent) chess1).getChessboardPoint(), ((ChessComponent) chess1).getLocation(), chess1.getChessColor(), this.clickController, this.CHESS_SIZE));
                    chess1.repaint();
                case 1:
                    this.add(chess1 = new RookChessComponent(((ChessComponent) chess1).getChessboardPoint(), ((ChessComponent) chess1).getLocation(), chess1.getChessColor(), this.clickController, this.CHESS_SIZE));
                    chess1.repaint();
                case 2:
                    this.add(chess1 = new BishopChessComponent(((ChessComponent) chess1).getChessboardPoint(), ((ChessComponent) chess1).getLocation(), chess1.getChessColor(), this.clickController, this.CHESS_SIZE));
                    chess1.repaint();
                case 3:
                    this.add(chess1 = new KnightChessComponent(((ChessComponent) chess1).getChessboardPoint(), ((ChessComponent) chess1).getLocation(), chess1.getChessColor(), this.clickController, this.CHESS_SIZE));
                    chess1.repaint();
            }
        }

    }

    public void initiateEmptyChessboard() {

        for (int i = 0; i < this.chessComponents.length; ++i) {
            for (int j = 0; j < this.chessComponents[i].length; ++j) {
                this.putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), this.calculatePoint(i, j), this.clickController, this.CHESS_SIZE));
            }
        }

    }

    public void swapColor() throws IOException {
        this.currentColor = this.currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        String str = this.currentColor == ChessColor.WHITE ? "Round: White" : "Round: Black";
        ColorLabel.setText(str);
        this.outputChessBoard(this);
//        if(whiteKingIsCheckmated(getChessComponents())){
//            label.setText("white is chessmated!");
//        }
//        if(blackKingIsCheckmated(getChessComponents())){
//            label.setText("black is chessmated!");
//        }
    }


    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), this.calculatePoint(row, col), color, this.clickController, this.CHESS_SIZE);
        chessComponent.setVisible(true);
        this.putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), this.calculatePoint(row, col), color, this.clickController, this.CHESS_SIZE);
        chessComponent.setVisible(true);
        this.putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), this.calculatePoint(row, col), color, this.clickController, this.CHESS_SIZE);
        chessComponent.setVisible(true);
        this.putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), this.calculatePoint(row, col), color, this.clickController, this.CHESS_SIZE);
        chessComponent.setVisible(true);
        this.putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), this.calculatePoint(row, col), color, this.clickController, this.CHESS_SIZE);
        chessComponent.setVisible(true);
        this.putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), this.calculatePoint(row, col), color, this.clickController, this.CHESS_SIZE);
        chessComponent.setVisible(true);
        this.putChessOnBoard(chessComponent);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * this.CHESS_SIZE, row * this.CHESS_SIZE);
    }

    public void ResetChessBoard() {
for(int i=0;i<8;i++) {
    for (int j = 0; j < 8; j++) {
        this.remove(chessComponents[i][j]);
    }
}
        this.initiateEmptyChessboard();
        this.initRookOnBoard(0, 0, ChessColor.BLACK);
        this.initRookOnBoard(0, 7, ChessColor.BLACK);
        this.initRookOnBoard(7, 0, ChessColor.WHITE);
        this.initRookOnBoard(7, 7, ChessColor.WHITE);


        this.initKingOnBoard(0, 4, ChessColor.BLACK);
        this.initKingOnBoard(7, 4, ChessColor.WHITE);

        this.initKnightOnBoard(0, 6, ChessColor.BLACK);
        this.initKnightOnBoard(0, 1, ChessColor.BLACK);
        this.initKnightOnBoard(7, 6, ChessColor.WHITE);
        this.initKnightOnBoard(7, 1, ChessColor.WHITE);

        this.initQueenOnBoard(0, 3, ChessColor.BLACK);
        this.initQueenOnBoard(7, 3, ChessColor.WHITE);

        this.initBishopOnBoard(0, 2, ChessColor.BLACK);
        this.initBishopOnBoard(0, 5, ChessColor.BLACK);
        this.initBishopOnBoard(7, 2, ChessColor.WHITE);
        this.initBishopOnBoard(7, 5, ChessColor.WHITE);

        for (int i = 0; i < 8; i++) {
            this.initPawnOnBoard(1, i, ChessColor.BLACK);
        }
        for (int i = 0; i < 8; i++) {
            this.initPawnOnBoard(6, i, ChessColor.WHITE);

        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponents[i][j].repaint();
            }
        }
            currentColor = ChessColor.WHITE;
            ColorLabel.setText("Round: White");
            label.setText(" ");

    }

    public void LoadChessBoard(List<String> str,int Steps) {
        StringBuilder sb=new StringBuilder(str.get(str.size()-1-Steps));
  //      String currentChessBoard=sb.substring(sb.length()-65,sb.length());
  //      StringBuilder sb2=new StringBuilder(currentChessBoard);
        char[] Legal={'K','Q','R','B','N','P','_','k','q','r','b','n','p'};
        if(!(sb.charAt(sb.length()-1)=='w'||sb.charAt(sb.length()-1)=='b')){
            JOptionPane.showMessageDialog(this,"缺少行棋方\n错误编码： 103");
            return;

        }else if (sb.length()==65&&(sb.charAt(sb.length()-1)=='w'||sb.charAt(sb.length()-1)=='b')) {
            for (int i = 0; i < sb.length()-1; i++) {
                int exist = 0;
                for (int j = 0; j < Legal.length; j++) {
                    if (sb.charAt(i) == Legal[j]) {
                        exist++;
                    }
                }
                if (exist ==0) {
                    JOptionPane.showMessageDialog(this,"棋子错误\n错误编码： 102");
                    return;
                }
            }
        }else if
        (sb.length()!=65){
            JOptionPane.showMessageDialog(this,"棋盘错误（非8*8）\n错误编码： 101");
            return;
        }
        List<String>chessboard=new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            chessboard.add(sb.substring(8*i,8+8*i));
        }
        chessboard.add(sb.substring(sb.length()-1,sb.length()));

        this.initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (chessboard.get(i).charAt(j)) {
                    case 'R':
                        this.initRookOnBoard(i,j,ChessColor.BLACK);
                        break;
                    case 'N':
                        this.initKnightOnBoard(i,j,ChessColor.BLACK);
                        break;
                    case 'B':
                        this.initBishopOnBoard(i,j,ChessColor.BLACK);
                        break;
                    case 'Q':
                        this.initQueenOnBoard(i,j,ChessColor.BLACK);
                        break;
                    case 'K':
                        this.initKingOnBoard(i,j,ChessColor.BLACK);
                        break;
                    case 'P':
                        this.initPawnOnBoard(i,j,ChessColor.BLACK);
                        break;
                    case 'r':
                        this.initRookOnBoard(i,j,ChessColor.WHITE);
                        break;
                    case 'n':
                        this.initKnightOnBoard(i,j,ChessColor.WHITE);
                        break;
                    case 'b':
                        this.initBishopOnBoard(i,j,ChessColor.WHITE);
                        break;
                    case 'q':
                        this.initQueenOnBoard(i,j,ChessColor.WHITE);
                        break;
                    case 'k':
                        this.initKingOnBoard(i,j,ChessColor.WHITE);
                        break;
                    case 'p':
                        this.initPawnOnBoard(i,j,ChessColor.WHITE);
                        break;
//                    case '_':
//
//                        break;
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponents[i][j].repaint();
            }
        }
        currentColor = chessboard.get(8).charAt(0) == 'w' ? ChessColor.WHITE : ChessColor.BLACK;
//        if (currentColor == ChessColor.WHITE) ChessGameFrame.statusLabel.setText("Round: White");
//        else ChessGameFrame.statusLabel.setText("Round: Black");
//        this.ColorLabel.setText("Round: Whit");
//        String color=currentColor==ChessColor.WHITE?"Round: White":"Round: Black";
//        JLabel p=new JLabel();
//        p.setText(color);
//        setColorLabel(p);
//        ColorLabel.repaint();
    }

    public String SaveChessBoard(Chessboard chessboard) throws IOException {
        String Final = "";

        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                if (chessboard.chessComponents[i][k] instanceof EmptySlotComponent) {
                    Final = Final + "_";
                }
                if (chessboard.chessComponents[i][k] instanceof KingChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.BLACK) {
                    Final = Final + "K";
                }
                if (chessboard.chessComponents[i][k] instanceof KingChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.WHITE) {
                    Final = Final + "k";
                }
                if (chessboard.chessComponents[i][k] instanceof QueenChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.BLACK) {
                    Final = Final + "Q";
                }
                if (chessboard.chessComponents[i][k] instanceof QueenChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.WHITE) {
                    Final = Final + "q";
                }
                if (chessboard.chessComponents[i][k] instanceof RookChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.BLACK) {
                    Final = Final + "R";
                }
                if (chessboard.chessComponents[i][k] instanceof RookChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.WHITE) {
                    Final = Final + "r";
                }
                if (chessboard.chessComponents[i][k] instanceof BishopChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.BLACK) {
                    Final = Final + "B";
                }
                if (chessboard.chessComponents[i][k] instanceof BishopChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.WHITE) {
                    Final = Final + "b";
                }
                if (chessboard.chessComponents[i][k] instanceof KnightChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.BLACK) {
                    Final = Final + "N";
                }
                if (chessboard.chessComponents[i][k] instanceof KnightChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.WHITE) {
                    Final = Final + "n";
                }
                if (chessboard.chessComponents[i][k] instanceof PawnChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.BLACK) {
                    Final = Final + "P";
                }
                if (chessboard.chessComponents[i][k] instanceof PawnChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.WHITE) {
                    Final = Final + "p";
                }
            }
        }
        String str = getCurrentColor() == ChessColor.WHITE ? "w" : "b";
        Final = Final + str + "\n";
        return Final;
    }

    public void outputChessBoard(Chessboard chessboard) throws IOException {
        String Final = "";

        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                if (chessboard.chessComponents[i][k] instanceof EmptySlotComponent) {
                    Final = Final + "_";
                }
                if (chessboard.chessComponents[i][k] instanceof KingChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.BLACK) {
                    Final = Final + "K";
                }
                if (chessboard.chessComponents[i][k] instanceof KingChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.WHITE) {
                    Final = Final + "k";
                }
                if (chessboard.chessComponents[i][k] instanceof QueenChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.BLACK) {
                    Final = Final + "Q";
                }
                if (chessboard.chessComponents[i][k] instanceof QueenChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.WHITE) {
                    Final = Final + "q";
                }
                if (chessboard.chessComponents[i][k] instanceof RookChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.BLACK) {
                    Final = Final + "R";
                }
                if (chessboard.chessComponents[i][k] instanceof RookChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.WHITE) {
                    Final = Final + "r";
                }
                if (chessboard.chessComponents[i][k] instanceof BishopChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.BLACK) {
                    Final = Final + "B";
                }
                if (chessboard.chessComponents[i][k] instanceof BishopChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.WHITE) {
                    Final = Final + "b";
                }
                if (chessboard.chessComponents[i][k] instanceof KnightChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.BLACK) {
                    Final = Final + "N";
                }
                if (chessboard.chessComponents[i][k] instanceof KnightChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.WHITE) {
                    Final = Final + "n";
                }
                if (chessboard.chessComponents[i][k] instanceof PawnChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.BLACK) {
                    Final = Final + "P";
                }
                if (chessboard.chessComponents[i][k] instanceof PawnChessComponent && chessboard.chessComponents[i][k].getChessColor() == ChessColor.WHITE) {
                    Final = Final + "p";
                }
            }
        }
        String str = getCurrentColor() == ChessColor.WHITE ? "w" : "b";
        Final = Final + str+"\n";

//        File f = new File("D:\\Chessww\\hhh.txt");//指定文件
//        FileOutputStream fos = new FileOutputStream(f);//创建输出流fos并以f为参数
//        OutputStreamWriter osw = new OutputStreamWriter(fos);//创建字符输出流对象osw并以fos为参数
//        BufferedWriter bw = new BufferedWriter(osw);//创建一个带缓冲的输出流对象bw，并以osw为参数
//        bw.write(Final);//使用bw写入一行文字，为字符串形式String
//        bw.newLine();//换行
//        bw.close();
        File F=new File("JumpBoard2/JumpBoard2.txt");//指定文件
        FileInputStream fis=new FileInputStream(F);//创建输入流fis并以f为参数
        InputStreamReader isr=new InputStreamReader(fis);//创建字符输入流对象isr并以fis为参数
        BufferedReader br=new BufferedReader(isr);//创建一个带缓冲的输入流对象br，并以isr为参数
        String result=br.readLine();//使用bufferedreader读取一行文字并将读取值赋给字符串result。每执行一次br.readLine();,就会往下读取一行。
        System.out.println(result);
        //获取本次new game的txt地址（result）



        FileOutputStream o = null;
        String path="";
        String filename="";
        byte[] buff = new byte[]{};
        try{
            File file = new File(result);
            if(!file.exists()){
                file.createNewFile();
            }
            buff=Final.getBytes();
            o=new FileOutputStream(file,true);
            o.write(buff);
            o.flush();
            o.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        FileOutputStream o1 = null;
        byte[] buff1 = new byte[]{};
        try{
            File file = new File("JumpBoard/JumpBoard.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            buff1=Final.getBytes();
            o1=new FileOutputStream(file,true);
            o1.write(buff1);
            o1.flush();
            o1.close();
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public void loadGame(List<String> chessData) {
        PrintStream var10001 = System.out;
        Objects.requireNonNull(var10001);
        chessData.forEach(var10001::println);
    }

    //通过棋点找棋子
//    private ChessComponent getChess(ChessboardPoint chessboardPoint){
//        return chessboard.getChessComponents()[chessboardPoint.getX()][chessboardPoint.getY()];
//    }

    //判定白王是否被将
    public boolean whiteKingIsCheckmated(ChessComponent[][] chessComponents) {

        int x = 0;
        int y = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent
                        && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        ChessComponent king = chessComponents[x][y];//找白王
        for (int i1 = 0; i1 < 8; i1++) {
            for (int j1 = 0; j1 < 8; j1++) {
                if (chessComponents[i1][j1].getChessColor() == ChessColor.BLACK
                        && chessComponents[i1][j1].canMoveTo(chessComponents, king.getChessboardPoint())) {
                    return true;

                }
            }
        }

        return false;
    }

    //判定黑王是否被将
    public boolean blackKingIsCheckmated(ChessComponent[][] chessComponents) {

        int x = 0;
        int y = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent
                        && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        ChessComponent king = chessComponents[x][y];//找白王
        for (int i1 = 0; i1 < 8; i1++) {
            for (int j1 = 0; j1 < 8; j1++) {
                if (chessComponents[i1][j1].getChessColor() == ChessColor.WHITE
                        && chessComponents[i1][j1].canMoveTo(chessComponents, king.getChessboardPoint())) {
                    return true;

                }
            }
        }

        return false;
    }
    //判断白方是否输棋（如果所有棋子都无处可去则判负），如果输棋了则返回true
    public boolean whiteIsFailed(ChessComponent[][] chessComponents){
        int counts = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(chessComponents[i][j].getChessColor() == ChessColor.WHITE){
                    counts += chessComponents[i][j].getPointsCanMoveTo(chessComponents).size();
                }
            }
        }

        return counts == 0;
    }

    //判断黑方是否输棋（如果所有棋子都无处可去则判负），如果输棋了则返回true
    public boolean blackIsFailed(ChessComponent[][] chessComponents){
        int counts = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(chessComponents[i][j].getChessColor() == ChessColor.BLACK){
                    counts += chessComponents[i][j].getPointsCanMoveTo(chessComponents).size();
                }
            }
        }

        return counts == 0;
    }



//

}
