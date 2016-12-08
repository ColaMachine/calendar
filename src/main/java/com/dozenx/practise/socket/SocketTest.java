package com.dozenx.practise.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by dozen.zhang on 2016/10/11.
 */
public class SocketTest {

    public static void main(String[] args)throws IOException {
        try(Socket s =new Socket("www.163.com",80)){
            OutputStream out = s.getOutputStream();
            out.write("GET / HTTP/1.1 \\n Host:www.163.com \\n \\n ".getBytes());
            out.flush();
            InputStream inStream  = s.getInputStream();
            Scanner in =new Scanner(inStream);
            while(in.hasNextLine()){
                String line =in.nextLine();
                System.out.println(line);
            }
            /*
            GET / HTTP/1.1
            HOST: baidu.com
            HOST: hostname.com

             */
        }
    }
}
