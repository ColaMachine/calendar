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
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private String ctrl = "\r\n";
    private Path homePath;
    private static Logger logger = LoggerFactory.getLogger(Generator.class);

    private Map root = new HashMap();

    public static Gson createGson() {
        return new GsonBuilder().

        /* registerTypeAdapter(ZTable.class, new ZTable.Handler()). */create();

    }
    /**
     * 两个jsonobject 合并
     * 
     * @param target
     *            参数
     * @param from
     *            参数
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
    public ZTable load(Path fromFile) throws IOException {

        logger.info("read config file{}", fromFile);
        if (!fromFile.toFile().exists()) {
            logger.info("code.cfg not exists");
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

  

    private Configuration cfg;

    public void init() throws IOException {
        // 创建目录
        Path homePath = PathManager.getInstance().getHomePath();
        Files.createDirectories(homePath.resolve("temp"));
        this.homePath = homePath;
        StringBuffer sb = new StringBuffer();
        String type = "";
        String typeName = "";

        table = load(homePath.resolve("src/main/resources/SmsEach.cfg"));
        table.init();
        root.put("javaType", new JavaTypeDirective());
        root.put("jdbcType", new JdbcTypeDirective());
        root.put("table", table);
        /*
         * Configuration config=new Configuration(); //设置要解析的模板所在的目录，并加载模板文件
         * config.setDirectoryForTemplateLoading(file); //设置包装器，并将对象包装为数据模型
         * config.setObjectWrapper(new DefaultObjectWrapper());
         */

        cfg = new Configuration();
        StringTemplateLoader temp = new StringTemplateLoader();

        String serviceTpl = this.readFile2Str("src/main/resources/code/Service.tpl");
        String beanTpl = this.readFile2Str("src/main/resources/code/Bean.tpl");
        String controllerTpl = this.readFile2Str("src/main/resources/code/Controller.tpl");
        String mapperTpl = this.readFile2Str("src/main/resources/code/Mapper.tpl");
        String mapperXmlTpl = this.readFile2Str("src/main/resources/code/MapperXml.tpl");
        String list = this.readFile2Str("src/main/resources/code/list.tpl");
        String edit = this.readFile2Str("src/main/resources/code/edit.tpl");
        String sqlTpl = this.readFile2Str("src/main/resources/code/sql.tpl");
        // String listHtmlTpl =
        // this.readFile2Str("src/main/resources/code/ListHtml.tpl");
        // String editHtmlTpl =
        // this.readFile2Str("src/main/resources/code/EditHtml.tpl");
        // String viewHtmlTpl =
        // this.readFile2Str("src/main/resources/code/ViewHtml.tpl");
        temp.putTemplate("service", serviceTpl);
        temp.putTemplate("bean", beanTpl);
        temp.putTemplate("controller", controllerTpl);
        temp.putTemplate("mapper", mapperTpl);
        temp.putTemplate("sql", sqlTpl);
        temp.putTemplate("mapperXml", mapperXmlTpl);
        temp.putTemplate("list", list);
        temp.putTemplate("edit", edit);
        // temp.putTemplate("listHtml", listHtmlTpl);
        // temp.putTemplate("editHtml", editHtmlTpl);
        // temp.putTemplate("viewHtml", viewHtmlTpl);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateLoader(temp);

        // FreeMarkerUtil.processTemplate(templatePath, templateName,
        // templateEncoding, root, out);
    }

    ZTable table;
    public void writeFile(String path,String name, String templateName) throws IOException, TemplateException {
        Template template;
        template = cfg.getTemplate(templateName,"UTF-8");
        homePath.resolve("temp").resolve(path).toFile().mkdirs();
        File file = homePath.resolve("temp").resolve(path).resolve(StringUtil.getAbc(name)).toFile();
        // Files.createFile(homePath.resolve(StringUtil.getAbc(table.getName())));
        FileWriter writer = new FileWriter(file);
        template.process(root, writer);
        writer.flush();
        writer.close();
        System.out.println(writer.toString());
    }
    public String readFile2Str(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            templateStr.append(s + "\r\n");
        }
        return templateStr.toString();
    }
    public String getValidStr(){
       StringBuffer sb=new StringBuffer("    ValidateUtil vu = new ValidateUtil();").append(ctrl);;
       sb.append("    String validStr=\"\";").append(ctrl);;
       for(int i=0;i<table.getCols().size();i++){
           ZColum zcol =table.getCols().get(i); 
           String type = zcol.getType();
         
           List rules =new ArrayList();
           List jsrules =new ArrayList();
           List message=new ArrayList();
           if(zcol.getType().toLowerCase().startsWith("varchar")){
        	   int length=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(")")));
        	   rules.add(String.format("new Length(%d)", length));
        	   jsrules.add(String.format("minlength:%d", length));
        	   message.add(String.format("%s不能少于%d个字符", zcol.getName(),length));
           }
           if(zcol.getType().toLowerCase().startsWith("int")){
        	   rules.add(String.format("new Digits()"));
        	   jsrules.add(String.format("digits:true"));
        	   message.add(String.format("digits:必须输入整数"));
           }
           if(zcol.getType().toLowerCase().startsWith("float")){
        	   int integer=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(",")));
        	   int fraction=Integer.valueOf(type.substring(type.indexOf(",")+1, type.indexOf(")")));
        	   rules.add(String.format("new Digits(%d,%d)",integer,fraction));
        	   jsrules.add(String.format("number:true"));
        	   message.add(String.format("number:必须输入合法的数字（负数，小数）"));
           }
           if(zcol.getType().toLowerCase().startsWith("double")){
        	   int integer=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(",")));
        	   int fraction=Integer.valueOf(type.substring(type.indexOf(",")+1, type.indexOf(")")));
        	   rules.add(String.format("new Digits(%d,%d)",integer,fraction));
        	   jsrules.add(String.format("number:true"));
        	   message.add(String.format("number:必须输入合法的数字（负数，小数）"));
           }
           if(zcol.getType().toLowerCase().equals("date")){
        	   rules.add(String.format("new Regex(\"yyyy-MM-dd\")"));
        	   jsrules.add(String.format("date:true"));
        	   message.add(String.format("number:必须输入合法日期"));
           }
           if(zcol.getType().toLowerCase().equals("datetime")){
        	   rules.add(String.format("new Regex(\"yyyy-MM-dd HH:mm:ss\")"));
           }
           if(zcol.getType().toLowerCase().equals("timestamp")){
        	   rules.add(String.format("new Regex(\"yyyy-MM-dd HH:mm:ss\")"));
           }
           if(zcol.isNn()){
        	   rules.add(String.format("new NotEmpty()"));
           }
           if(StringUtil.isNotEmpty(zcol.getValid())){
        	   String[] validAry= zcol.getValid().split("||");
        	   for(int j=0;j<validAry.length;j++){
        		   if(validAry[j].toLowerCase().startsWith("regex")){
        			   String content=StringUtil.getContentBetween(validAry[j], "(", ")");
        			   rules.add(String.format("new Regex(%s)",content));
        		   }
        		   if(validAry[j].toLowerCase().startsWith("email")){
        			   String content=StringUtil.getContentBetween(validAry[j], "(", ")");
        			   rules.add(String.format("new Email(%s)",content));
        		   }
        		   if(validAry[j].toLowerCase().startsWith("phone")){
        			   String content=StringUtil.getContentBetween(validAry[j], "(", ")");
        			   rules.add(String.format("new Email(%s)",content));
        		   }
        		   if(validAry[j].toLowerCase().startsWith("money")){
        			   String content=StringUtil.getContentBetween(validAry[j], "(", ")");
        			   rules.add(String.format("new Email(%s)",content));
        		   }
        	   }
           }
           String ruleStr=StringUtil.join(",",rules.toArray());
           ruleStr=" new Rule[]{"+ruleStr+"}";
           sb.append("vu.add(\""+zcol.getName()+"\", "+zcol.getName()+", \""+zcol.getRemark()+"\", "+ruleStr+");").append(ctrl);
           sb.append(" validStr = vu.validateString();").append(ctrl);
           sb.append("if(StringUtil.isNotEmpty(validStr)) {").append(ctrl);
           sb.append(String.format("return ResultUtil.getResult(%d,%s);",302,"validStr")).append(ctrl);
           sb.append("}").append(ctrl);
       }
      return sb.toString();
        
    }
    public String changeMySqlType2JavaType(String type) {
        String typeName = null;
        type = type.toLowerCase();
        if (type.startsWith("varchar")) {
            typeName = "String";
        } else if (type.startsWith("int")) {
            typeName = "Integer";
        } else if (type.startsWith("bigint")) {
            typeName = "Long";
        } else if (type.startsWith("float")) {
            typeName = "Float";
        } else if (type.startsWith("double")) {
            typeName = "Double";
        } else if (type.startsWith("date")) {
            typeName = "Date";
        } else if (type.startsWith("timestamp")) {
            typeName = "Timestamp";
        } else if (type.startsWith("text")) {
            typeName = "String";
        }
        return typeName;
    }
    public void genController() throws IOException, TemplateException {
        logger.info("genController");
        root.put("validCode", getValidStr());
        writeFile("src/main/java/cola/machine/action/",table.getName() + "Controller.java", "controller");
    }

    public void genService() throws IOException, TemplateException {
        logger.info("genService");
        writeFile("src/main/java/cola/machine/service/",table.getName() + "Service.java", "service");
    }

    public void genMapper() throws IOException, TemplateException {
        logger.info("genMapper");
        writeFile("src/main/java/cola/machine/dao/",table.getName() + "Mapper.java", "mapper");
    }
    public void genMapperXml() throws IOException, TemplateException {
        logger.info("genMapperXml");
        writeFile("src/main/resources/config/mapper/",table.getName() + "Mapper.xml", "mapperXml");
    }

    public void genBean() throws IOException, TemplateException {
        logger.info("genBean");
        StringBuffer sb = new StringBuffer();
        String type = "";
        String typeName = "";
        for (ZColum col : table.getCols()) {
            type = col.getType().toLowerCase();
            sb.append("/**" + col.getRemark() + "**/").append(ctrl);
            typeName = this.changeMySqlType2JavaType(type);
            sb.append("    private " + typeName + " " + col.getName() + ";").append(ctrl);
            sb.append("    public " + typeName + " get" + StringUtil.getAbc(col.getName()) + "(){").append(ctrl)
                    .append("        return " + col.getName() + ";").append(ctrl).append("    }")
                    .append("    public void set" + StringUtil.getAbc(col.getName()) + "(" + typeName + " "
                            + col.getName() + "){")
                    .append(ctrl).append("        this." + col.getName() + "=" + col.getName() + ";").append(ctrl)
                    .append("    }");
        }

        root.put("content", sb);
        writeFile("src/main/java/cola/machine/bean/",table.getName() + ".java", "bean");

        /*
         * StringWriter writer = new StringWriter(); template.process(root,
         * writer); System.out.println(writer.toString());
         */
    }

    public void genSql() throws IOException, TemplateException {
        logger.info("genSql");
        writeFile("",table.getName() + ".sql", "sql");
    }

    public void genListHtml() throws IOException, TemplateException {
        logger.info("genListHtml");
        writeFile("",table.getName() + "List.html", "list");
    }

    public void genEditHtml() throws IOException, TemplateException {
        
        logger.info("genEditHtml");
        writeFile("",table.getName() + "Edit.html", "edit");
    }

    public void genViewHtml() {
        logger.info("genViewHtml");
    }


    public static void main(String[] args) {
        Generator gen = new Generator();
        try {
            gen.init();
            gen.genBean();
            gen.genMapper();
            gen.genService();
            gen.genController();
            gen.genSql();
            gen.genMapperXml();
            gen.genListHtml();
            gen.genEditHtml();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // System.out.println(table.getCols().size());
        // gen.genController(table);
        // gen.genBean(table);
        catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

  
}
