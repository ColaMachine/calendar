import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年12月27日
 * 文件说明: 
 */

public class FreeMakerTest {
    public static void main(String[] args) {
        try {
            
            Configuration cfg = new Configuration();
            StringTemplateLoader temp = new StringTemplateLoader();
            temp.putTemplate("a", "欢迎：${user.user.user}");
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateLoader(temp);
            Template template;

            template = cfg.getTemplate("a");

            Map root = new HashMap();
            Map child = new HashMap();
            child.put("user", "zhang");
            root.put("user", child);
            StringWriter writer = new StringWriter();
            template.process(root, writer);
            System.out.println(writer.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
