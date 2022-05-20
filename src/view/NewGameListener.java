package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;

class NewGameListener implements ActionListener {
    private final JFrame loginFrame;

    public NewGameListener(JFrame loginFrame) {

        this.loginFrame = loginFrame;
    }

    private long len;
    private String path;
    private File src;
    private int FileNum;
    private int DirNum;

    public int DirCount(String path) {
        this.path=path;
        this.src=new File(path);
        count(this.src);
        return FileNum;
    }
    //统计大小
    private void count(File src) {
        if(null!=src||src.exists()) {//递归头
            if(src.isFile()) {//文件
                len+=src.length();
                this.FileNum++;
            }else {//目录
                this.DirNum++;
                for(File s:src.listFiles()) {
                    count(s);
                }
            }
        }
    }




     @Override
    public void actionPerformed(ActionEvent e) {
                loginFrame.setVisible(false); // 隐藏登录窗口，游戏进行中不要关闭程序
                ChessGameFrame.fatherFrame = loginFrame;
                ChessGameFrame.main(null);
         int count=(DirCount("C:\\Users\\DELL\\Desktop\\ChessDemo\\游戏存档"));
         String pathname="C:\\Users\\DELL\\Desktop\\ChessDemo\\游戏存档\\Game"+(count+1)+".txt";
        File fileX=new File(pathname);
         try {
             fileX.createNewFile();
         } catch (IOException ex) {
             ex.printStackTrace();
         }

                 File f = new File("C:\\Users\\DELL\\Desktop\\ChessDemo\\JumpBoard2\\JumpBoard2.txt");//指定文件
         FileOutputStream fos = null;//创建输出流fos并以f为参数
         try {
             fos = new FileOutputStream(f);
         } catch (FileNotFoundException ex) {
             ex.printStackTrace();
         }
         OutputStreamWriter osw = new OutputStreamWriter(fos);//创建字符输出流对象osw并以fos为参数
        BufferedWriter bw = new BufferedWriter(osw);//创建一个带缓冲的输出流对象bw，并以osw为参数
         try {
             bw.write(pathname);//使用bw写入一行文字，为字符串形式String
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




     }
    }
