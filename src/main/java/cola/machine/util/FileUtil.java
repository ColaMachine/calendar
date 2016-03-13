/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月13日
 * 文件说明: 
 */
package cola.machine.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cola.machine.mng.PathManager;

public class FileUtil {
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
}
