package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class InputListener extends Component implements ActionListener {
    private Chessboard chessboard;
    JFrame loginFrame = new JFrame();

    public void setCurrentChessBoard(String currentChessBoard) {
        CurrentChessBoard = currentChessBoard;
    }

    public String getCurrentChessBoard() {
        return CurrentChessBoard;
    }

    private String CurrentChessBoard;
    public static int count = 0;

    public InputListener(JFrame loginFrame) {
        this.loginFrame = loginFrame;

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        // Display an open file chooser

        JFileChooser fileChooser = new JFileChooser("游戏存档");


        int returnValue = fileChooser.showOpenDialog(null);

        loginFrame.setVisible(false); // 隐藏登录窗口，游戏进行中不要关闭程序
        ChessGameFrame.fatherFrame = loginFrame;
        ChessGameFrame.main(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("we selected: " + selectedFile);
            InputStream fis = null;

//            String fileName = "ChessData.txt";
//            Stream<String> lines = null;
//            try {
//                lines = Files.lines(Paths.get(fileName));
//
//            }catch(IOException ex){
//                ChessGameFrame.loadinwrong=1;
//                count=0;
//                return;
//            }
            String suffixName = selectedFile.toString().substring(selectedFile.toString().lastIndexOf("."));
            if (!(selectedFile.toString().endsWith(".txt"))){
                ChessGameFrame.loadinwrong=1;
                count=0;
                return;
            }

            try {
                fis = new FileInputStream(selectedFile);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
// 3. 进行读操作
            byte[] buf = new byte[1024];    // 所有的内容读到此数组中临时存放
            int len = 0;    //用于记录读取的数据个数
            String myStr = "";
            while (true) {
                try {
                    if (!((len = fis.read(buf)) != -1)) break;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }   //将内容读到byte数组中，同时返回个数，若为-1，则内容读到底
                try {
                    myStr += new String(buf, 0, len, "UTF-8");
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
            }
// 4. 关闭输出
            try {
                fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            //上面已经打开选择栏了
            try {
                List<String> results = Files.readAllLines(Paths.get("JumpBoard/JumpBoard.txt"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

//            for()
            CurrentChessBoard = myStr;
            File f = new File("JumpBoard/JumpBoard.txt");//指定文件
//            FileOutputStream o = null;
//            String path="";
//            String filename="";
//            byte[] buff = new byte[]{};
//            try{
//                File file = f;
//                if(!file.exists()){
//                    file.createNewFile();
//                }
//                buff=myStr.getBytes();
//                o=new FileOutputStream(file,true);
//                o.write(buff);
//                o.flush();
//                o.close();
//            }catch(Exception t){
//                t.printStackTrace();
//            }
            FileOutputStream fos = null;//创建输出流fos并以f为参数
            try {
                fos = new FileOutputStream(f);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            OutputStreamWriter osw = new OutputStreamWriter(fos);//创建字符输出流对象osw并以fos为参数
            BufferedWriter bw = new BufferedWriter(osw);//创建一个带缓冲的输出流对象bw，并以osw为参数
            try {
                bw.write(myStr);//使用bw写入一行文字，为字符串形式String
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                bw.newLine();//换行
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                }

                System.out.println(myStr);
                count = 1;
            }
        }
    }

