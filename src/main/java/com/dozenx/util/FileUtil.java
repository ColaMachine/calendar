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

    public static String readFile2Str(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            templateStr.append(s + "\r\n");
        }
        return templateStr.toString();
    }


    public static List<String > readFile2List(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();

        BufferedReader br = new BufferedReader(new FileReader(file));
       List<String> lines =new ArrayList();
        String s ;
       // StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            lines.add(s);
           // templateStr.append(s + "\r\n");
        }
        return lines;
    }

    /**
     * 上传文件 uploadfile
     * @param localRootPath c:/forder
     * @param remoteRootPath    /folder
     * @param relativePaths a.txt
     * @param userName  username
     * @param pwd   password
     * @param serverIp  192.168.11.231
     */
    public static void upload(String localRootPath, String remoteRootPath, String[] relativePaths, String userName, String pwd, String serverIp) {
        Connection con = new Connection(serverIp);

        try {
            con.connect();
            boolean isAuthed = con.authenticateWithPassword(userName, pwd);
            if(!isAuthed){
                logger.error("ssh upload file username & pwd authed failed");
                return;
            }

            SCPClient scpClient =con.createSCPClient(); //
            SFTPv3Client sftpClient = new SFTPv3Client(con);
            for (int i = 0; i < relativePaths.length; i++) {
                String relativePath = relativePaths[i];
                if (relativePath.startsWith(File.separator)) {
                    relativePath = relativePath.substring(1);
                }
                String localPath = localRootPath +File.separator + relativePath;
                String remotePath = remoteRootPath + File.separator + relativePath;
                int index = remotePath.lastIndexOf(File.separator);
                String remoteFileDir = "";
                if (index != -1) {
                    ch.ethz.ssh2.Session session = con.openSession();
                    remoteFileDir = remotePath.substring(0, index);

                    session.execCommand("mkdir -p " + remoteFileDir); //
                    logger.debug( "Here is some information about the remote host:");
                    InputStream stdout =null;
                    BufferedReader br=null;
                    try {
                        stdout = new StreamGobbler(session.getStdout());
                        br = new BufferedReader(new InputStreamReader(stdout));
                    }catch (Exception e){
                       logger.error("error in print ssh mkdir log",e);
                    }finally {
                        if(br!=null){
                            br.close();
                        }
                        if(stdout!=null ){
                            stdout.close();
                        }
                    }
                    while (true) {
                        String line= br.readLine();
                        if (line == null) break;
                        logger.debug(line);
                    }
                    // Show exit status,if available(otherwise"null")
                    logger.debug("ExitCode: " + session.getExitStatus()); // sftpClient.mkdir(theDir, 6);
                    logger.info("创建目录+" + remoteFileDir);
                    session.close();
                    logger.debug("local image file :" + localPath);
                } else {
                    logger.debug( "error:in copy " + localPath);
                }
                scpClient.put(localPath, remoteFileDir); //从本地复制文件到远程目录
            }

        } catch (IOException e) {
            logger.error("ssh upload file failed ",e);

        }finally {
            con.close();
        }

    }

    public static void writeFile(File file, String content) throws IOException {
        FileWriter fileWritter = null;
        // BufferedWriter bufferWritter=null;
        try {
            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            //true = append file
            fileWritter = new FileWriter(file, false);
            fileWritter.write(content);
            //  bufferWritter = new BufferedWriter(fileWritter);
            //bufferWritter.write(content);
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
           /* bufferWritter.flush();
            if(bufferWritter!=null )
                bufferWritter.close();*/
            fileWritter.flush();
            if (fileWritter != null)
                fileWritter.close();

        }
    }

    public static void main(String args[]) {
        try {


            FileUtil.writeFile(new File("c:/zzw/a.txt"), "hello123123\n");
            FileUtil.upload("c:/zzw", "/service", new String[]{"a.txt"}, "root", "opms@2016", "192.168.11.231");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
