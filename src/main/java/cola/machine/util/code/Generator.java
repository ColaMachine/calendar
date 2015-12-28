/**
\ * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年12月26日
 * 文件说明: 
 */
package cola.machine.util.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import cola.machine.mng.PathManager;
import cola.machine.util.FreeMarkerUtil;
import cola.machine.util.StringUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Generator {
    private String ctrl="\r\n";
    private static Logger logger =LoggerFactory.getLogger(Generator.class);
    public void genController(ZTable table){

        try {
            BufferedReader br =new BufferedReader(new FileReader(new File("/home/colamachine/workspace/calendar/src/main/resources/code/Controller.tpl")));
            String s ;
            StringBuffer templateStr= new StringBuffer();
            while  ((s=br.readLine())!=null){
                templateStr.append(s+"\r\n");
            }
            Configuration cfg = new Configuration();
            StringTemplateLoader temp = new StringTemplateLoader();
            temp.putTemplate("controller", templateStr.toString());
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateLoader(temp);
            Template template;

            template = cfg.getTemplate("controller");

            Map root = new HashMap();

            root.put("table", table);
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

    public void genService(ZTable table){
        StringBuffer sb =new StringBuffer();
        String type="";
        String typeName="";
        try {
            BufferedReader br =new BufferedReader(new FileReader(new File("/home/colamachine/workspace/calendar/src/main/resources/code/Service.tpl")));
            String s ;
            StringBuffer templateStr= new StringBuffer();
            while  ((s=br.readLine())!=null){
                templateStr.append(s+"\r\n");
            }
            Configuration cfg = new Configuration();
            StringTemplateLoader temp = new StringTemplateLoader();
            temp.putTemplate("service", templateStr.toString());
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateLoader(temp);
            Template template;

            template = cfg.getTemplate("service");

            Map root = new HashMap();
            root.put("javaType", new JavaTypeDirective());
            root.put("table", table);
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
    public void genMapper(){
        
    }
    public String changeMySqlType2JavaType(String type){
        String typeName=null;
        if(type.startsWith("varchar")){
            typeName= "String";
        }else if(type.startsWith("int")){
            typeName= "Integer";
        }else if(type.startsWith("bigint")){
            typeName= "Long";
        }else if(type.startsWith("float")){
            typeName= "Float";
        } else if(type.startsWith("double")){
            typeName= "Double";
        } else if(type.startsWith("date")){
            typeName= "Date";
        } else if(type.startsWith("timestamp")){
            typeName= "Date";
        }
        return typeName;
    }
    public void genBean(ZTable table){
        StringBuffer sb =new StringBuffer();
        String type="";
        String typeName="";
        for(ZColum col: table.getCols()){
            type= col.getType().toLowerCase();
            sb.append("/**"+col.getRemark()+"**/").append(ctrl);
            typeName= this.changeMySqlType2JavaType(type);
            sb.append("private "+typeName+" "+col.getName()+";").append(ctrl);
            sb.append("public "+typeName+" get"+StringUtil.getAbc(col.getName())+"(){").append(ctrl)
            .append("return "+col.getName()+";").append(ctrl)
            .append("}")
            .append("public void set"+StringUtil.getAbc(col.getName())+"("+typeName+" "+col.getName()+"){").append(ctrl)
            .append("this."+col.getName()+"="+col.getName()+";").append(ctrl)
            .append("}");
        }
        

        try {
            BufferedReader br =new BufferedReader(new FileReader(new File("/home/colamachine/workspace/calendar/src/main/resources/code/Bean.tpl")));
            String s ;
            StringBuffer templateStr= new StringBuffer();
            while  ((s=br.readLine())!=null){
                templateStr.append(s+"\r\n");
            }
            Configuration cfg = new Configuration();
            StringTemplateLoader temp = new StringTemplateLoader();
            temp.putTemplate("controller", templateStr.toString());
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateLoader(temp);
            Template template;

            template = cfg.getTemplate("controller");

            Map root = new HashMap();

            root.put("table", table);
            root.put("content", sb.toString());
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
    public void genListHtml(){
        
    }
    public void genEditHtml(){
        
    }
    public void genViewHtml(){
        
    }
    public static Gson createGson() {
        return new GsonBuilder().
        
          /*registerTypeAdapter(ZTable.class, new ZTable.Handler()).*/create();
         
    }
    
    public ZTable load(Path fromFile) throws IOException{
        
        logger.info("read config file{}", fromFile);
       if(!fromFile.toFile().exists()){
           logger.debug("code.cfg not exists");
           return null;
       }
        try (Reader reader = Files.newBufferedReader(fromFile, Charset.forName("UTF-8"))) {
            Gson gson = createGson();
            JsonElement baseConfig = gson.toJsonTree(new ZTable());
            JsonParser parser = new JsonParser();
            JsonElement config = parser.parse(reader);
            if (!config.isJsonObject()) {
                return new ZTable();
            } else {
                merge(baseConfig.getAsJsonObject(), config.getAsJsonObject());
                return gson.fromJson(baseConfig, ZTable.class);
            }
        } catch (JsonParseException e) {
            throw new IOException("Failed to load config", e);
        }
    }
    public String readFile2Str(String path ) throws IOException{
        File file=PathManager.getInstance().getHomePath().resolve(path).toFile();
        
        BufferedReader br =new BufferedReader(new FileReader(file));
        String s ;
        StringBuffer templateStr= new StringBuffer();
        while  ((s=br.readLine())!=null){
            templateStr.append(s+"\r\n");
        }
        return templateStr.toString();
    }
    public void init(){ 
        
        StringBuffer sb =new StringBuffer();
        String type="";
        String typeName="";
        try {
           
            
         /*   Configuration config=new Configuration();
             //设置要解析的模板所在的目录，并加载模板文件
            config.setDirectoryForTemplateLoading(file);
            //设置包装器，并将对象包装为数据模型
            config.setObjectWrapper(new DefaultObjectWrapper());*/
            
            Configuration cfg = new Configuration();
            StringTemplateLoader temp = new StringTemplateLoader();
            
            String serviceTpl = this.readFile2Str("src/main/resources/code/Service.tpl");
            String beanTpl = this.readFile2Str("src/main/resources/code/Bean.tpl");
            String controllerTpl = this.readFile2Str("src/main/resources/code/Controller.tpl");
            String mapperTpl = this.readFile2Str("src/main/resources/code/Mapper.tpl");
            String sqlTpl = this.readFile2Str("src/main/resources/code/sql.tpl");
            String listHtmlTpl = this.readFile2Str("src/main/resources/code/ListHtml.tpl");
            String editHtmlTpl = this.readFile2Str("src/main/resources/code/EditHtml.tpl");
            String viewHtmlTpl = this.readFile2Str("src/main/resources/code/ViewHtml.tpl");
            temp.putTemplate("service", serviceTpl);
            temp.putTemplate("bean", beanTpl);
            temp.putTemplate("controller", controllerTpl);
            temp.putTemplate("mapper", mapperTpl);
            temp.putTemplate("listHtml", listHtmlTpl);
            temp.putTemplate("editHtml", editHtmlTpl);
            temp.putTemplate("viewHtml", viewHtmlTpl);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateLoader(temp);
        }catch(Exception e){
            
        }
        FreeMarkerUtil.processTemplate(templatePath, templateName, templateEncoding, root, out);
    }
    public static void main(String[] args) {
        Generator gen =new Generator();
        try {
           
            ZTable table =gen.load( PathManager.getInstance().getHomePath().resolve("src/main/resources/code.cfg"));
            gen.int();
            table.init();
            System.out.println(table.getCols().size());
            //gen.genController(table);
           // gen.genBean(table);
            gen.genService(table);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 两个jsonobject 合并
     * @param target 参数
     * @param from 参数
     */
    public static void merge(JsonObject target, JsonObject from) {
        for (Map.Entry<String, JsonElement> entry : from.entrySet()) {
            if (entry.getValue().isJsonObject()) {
                if (target.has(entry.getKey()) && target.get(entry.getKey()).isJsonObject()) {
                    merge(target.get(entry.getKey()).getAsJsonObject(), entry.getValue().getAsJsonObject());
                } else {
                    target.remove(entry.getKey());
                    target.add(entry.getKey(), entry.getValue());
                }
            } else {
                target.remove(entry.getKey());
                target.add(entry.getKey(), entry.getValue());
            }
        }

    }
}
