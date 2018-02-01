/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月13日
 * 文件说明:
 */
package com.dozenx.util;


import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.StreamGobbler;
import com.dozenx.core.Path.PathManager;
import org.slf4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FileUtil.class);

    public static List<File> readAllFileInFold(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();
        if (!file.exists()) {
            throw new IOException("path file not exist");
        }
        if (!file.isDirectory()) {
            throw new IOException("path file not exist");
        }
        return listFile(file);
    }

    public static List<File> listFile(File file) {
        List<File> fileList = new ArrayList<>();
        File[] fileAry = file.listFiles();
        for (File childFile : fileAry) {
            if (childFile.isDirectory()) {
                fileList.addAll(listFile(childFile));
            } else {
                fileList.add(childFile);
            }
        }
        return fileList;
    }

    /**
     * 读取文件内容
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String readFile2Str(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();
        String content = "";
        try{
            content =  readFile2Str(file);
        }catch(Exception e ){
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 读取文件内容 忽略行注释
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String readFile2Str(File file) throws IOException {
        if (!file.exists()) {
            //throw new IOException("path file "+file.getPath()+" not exist");
            return "";
        }
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s;
        StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            //过滤掉注释内容
            s = s.trim();
            if (s.startsWith("//")) {
                continue;
            }
            templateStr.append(s + "\r\n");
        }
        if (templateStr == null || templateStr.toString().length() == 0) {
            throw new IOException("file is empty: " + file);
        }
        return templateStr.toString();
    }


    /**
     * 写文件
     *
     * @param filePath
     * @param content
     * @throws IOException
     */
    public static void writeFile(String filePath, String content) throws IOException {
        FileWriter fileWritter = null;
        File file = new File(filePath);
        // BufferedWriter bufferWritter=null;
        try {
            // if file doesnt exists, then create it
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            // true = append file
            fileWritter = new FileWriter(file, false);
            fileWritter.write(content);
            // bufferWritter = new BufferedWriter(fileWritter);
            // bufferWritter.write(content);
            System.out.println("Done");
        } catch (IOException e) {
            System.out.println(file.getAbsolutePath().toString());
            e.printStackTrace();
            throw e;
        } finally {
            /*
			 * bufferWritter.flush(); if(bufferWritter!=null )
			 * bufferWritter.close();
			 */

            fileWritter.flush();
            if (fileWritter != null)
                fileWritter.close();

        }
    }

    /**
     * 写文件
     *
     * @param file
     * @param content
     * @throws IOException
     */
    public static void writeFile(File file, String content) throws IOException {
        try {
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            // true = append file
            FileWriter fileWritter = new FileWriter(file, false);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(content);
            bufferWritter.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String args[]) {
        try {
            FileReader fr = new FileReader("G://V2.2.7.txt");
            BufferedReader br = new BufferedReader(fr);
            String s;
            String name = "";
            StringBuffer result = new StringBuffer();
            while ((s = br.readLine()) != null) {
                // System.out.println(s);
                // System.out.println(s.split("\\s+").length);
                String arr[] = s.split("\\s+");
                System.out.println(arr[0]);
                if (name.equals("")) {
                    name = arr[arr.length - 1];
                } else if (arr[arr.length - 1].trim().equals(name.trim())) {

                    result.append("'").append(arr[0]).append("',");

                } else {

                    // System.out.println(name+"select distinct IPAddress from
                    // wii_device_ssid where deviceid in(select id from
                    // wii_device where DevId in ("+result.toString()+"))");
                    result = new StringBuffer();
                    name = arr[arr.length - 1];
                }
                if (arr.length == 3) {

                }

            }
            // System.out.println(name+"select distinct IPAddress from
            // wii_device_ssid where id in(select id from wii_device where DevId
            // in ("+result.toString()+")");
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> readFile2List(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();
        if (!file.exists()) {
            throw new IOException("read file failed path" + path);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        // BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList();
        String s;
        // StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            lines.add(s);
            // templateStr.append(s + "\r\n");
        }
        return lines;
    }

    public static List<String> readFile2List(File file) throws IOException {

        if (!file.exists()) {
            throw new IOException("read file failed path");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        // BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList();
        String s;
        // StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            lines.add(s);
            // templateStr.append(s + "\r\n");
        }
        return lines;
    }


    /**
     * 上传文件 uploadfile
     *
     * @param localRootPath  c:/forder
     * @param remoteRootPath /folder
     * @param relativePaths  a.txt
     * @param userName       username
     * @param pwd            password
     * @param serverIp       192.168.11.231
     */
    public static void upload(String localRootPath, String remoteRootPath, String[] relativePaths, String userName,
                              String pwd, String serverIp) {
        Connection con = new Connection(serverIp);

        try {
            con.connect();
            boolean isAuthed = con.authenticateWithPassword(userName, pwd);
            if (!isAuthed) {
                logger.error("ssh upload file username & pwd authed failed");
                return;
            }

            SCPClient scpClient = con.createSCPClient(); //
            SFTPv3Client sftpClient = new SFTPv3Client(con);
            for (int i = 0; i < relativePaths.length; i++) {
                String relativePath = relativePaths[i];
                if (relativePath.startsWith(File.separator)) {
                    relativePath = relativePath.substring(1);
                }
                String localPath = localRootPath + File.separator + relativePath;
                String remotePath = remoteRootPath + File.separator + relativePath;
                int index = remotePath.lastIndexOf(File.separator);
                String remoteFileDir = "";
                if (index != -1) {
                    ch.ethz.ssh2.Session session = con.openSession();
                    remoteFileDir = remotePath.substring(0, index);

                    session.execCommand("mkdir -p " + remoteFileDir); //
                    logger.debug("Here is some information about the remote host:");
                    InputStream stdout = null;
                    BufferedReader br = null;
                    try {
                        stdout = new StreamGobbler(session.getStdout());
                        br = new BufferedReader(new InputStreamReader(stdout));
                    } catch (Exception e) {
                        logger.error("error in print ssh mkdir log", e);
                    } finally {
                        if (br != null) {
                            br.close();
                        }
                        if (stdout != null) {
                            stdout.close();
                        }
                    }
                    while (true) {
                        String line = br.readLine();
                        if (line == null)
                            break;
                        logger.debug(line);
                    }
                    // Show exit status,if available(otherwise"null")
                    logger.debug("ExitCode: " + session.getExitStatus()); // sftpClient.mkdir(theDir,
                    // 6);
                    logger.info("创建目录+" + remoteFileDir);
                    session.close();
                    logger.debug("local image file :" + localPath);
                } else {
                    logger.debug("error:in copy " + localPath);
                }
                scpClient.put(localPath, remoteFileDir); // 从本地复制文件到远程目录
            }

        } catch (IOException e) {
            logger.error("ssh upload file failed ", e);

        } finally {
            con.close();
        }

    }

    private static boolean saveImageToDisk(byte[] data, String path, String imageName) throws IOException {
        int len = data.length;

        File file =new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        // 写入到文件
   /*     System.out.println(data);
        for(int i=0;i<data.length;i++){
            System.out.print(data[i]);
        }*/
        FileOutputStream outputStream = new FileOutputStream(new File(path, imageName));
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        return true;
    }

    /**
     * 获得指定文件的byte数组
     */
    public static  byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static void getFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath+"\\"+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void getFile(byte[] bfile, String filePath) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
