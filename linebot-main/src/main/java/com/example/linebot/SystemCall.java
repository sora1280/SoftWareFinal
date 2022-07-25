package com.example.linebot;

import java.io.*;
import java.io.IOException;
import java.nio.charset.Charset;


public class SystemCall {
    String text;
    String str;
    String s;

    public SystemCall(String text) {
        this.text = text;
    }

    public String result() throws IOException {
        //判断したい文章の書き込み
        try {
            FileWriter file = new FileWriter("C:\\Users\\Owner\\Desktop\\AI_final\\getText.txt");
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            pw.println(text);

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Pythonの呼び出し
        ProcessBuilder pb = new ProcessBuilder("python", "C:\\Users\\Owner\\Desktop\\AI_final\\final.py");
        pb.redirectErrorStream(true);

        Process p = pb.start();

        try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.defaultCharset()))) {
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
        }

        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //result.txtを送る
        try {
            StringBuilder buf = new StringBuilder();
            BufferedReader bfReader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Owner\\Desktop\\AI_final\\result.txt"), Charset.forName("UTF-8")));
            while ((str = bfReader.readLine()) != null) {
                System.out.println(str);
                buf.append(str);
            }
            s = buf.toString();
            bfReader.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
            s = "error";
        }
        return s;
    }
}
