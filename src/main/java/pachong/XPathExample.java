package pachong;


/**
 * Created by dozen.zhang on 2016/12/2.
 */

import com.dozenx.core.Path.PathManager;
import com.dozenx.util.FileUtil;
import com.dozenx.util.HttpHeader;
import com.dozenx.util.HttpRequestUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class XPathExample {

    public static void main(String[] args)
            throws ParserConfigurationException, SAXException,
            IOException, XPathExpressionException {
           //String result =  HttpRequestUtil.doPost("http://www.mayadisc.com/forumdisplay.php?fid=5",null,"gbk",true);
           // System.out.println(result);
try {
    XPathExample.testHtml();
}catch(Exception e){
    e.printStackTrace();
}
    }

    public static void testHtml()throws Exception {
        HttpHeader loginHttpHeader=new  HttpHeader();
        loginHttpHeader.Host = "www.mayaww.com";
        loginHttpHeader.Origin="http://www.mayaww.com";
        loginHttpHeader.Referer = "http://www.mayaww.com/logging.php?action=login";
        loginHttpHeader.cookies.put("is_use_cookiex","yes");
        loginHttpHeader.cookies.put("cdb_cookietime","2592000");
        loginHttpHeader.cookies.put("cdb_oldtopics","D2112861D2112856D2112862D2112863D");
        loginHttpHeader.cookies.put("cdb_fid5","1499129739");
        loginHttpHeader.cookies.put("cdb_sid","7kuZiP");
        loginHttpHeader.cookies.put("is_use_cookied","yes");

        //HashMap params =new HashMap();
        loginHttpHeader.Content_Length=190;
        loginHttpHeader.cookies.put("Host","www.mayaww.com");
        //loginHttpHeader.params.put
        loginHttpHeader.params.put("username","zjlgdxfj");

        loginHttpHeader.params.put("password","123456fj");
        loginHttpHeader.params.put("formhash","c7fcc9af");
        loginHttpHeader.params.put("referer","http://www.mayaww.com/index.php");
        loginHttpHeader.params.put("loginfield","username");
        loginHttpHeader.params.put("questionid","0");
        loginHttpHeader.params.put("answer","");
        loginHttpHeader.params.put("loginmode","");
        loginHttpHeader.params.put("styleid","");
        loginHttpHeader.params.put("cookietime","2592000");
        loginHttpHeader.params.put("loginsubmit","%CC%E1+%26%23160%3B+%BD%BB");


        String body= HttpRequestUtil.doGet(loginHttpHeader, "http://www.mayaww.com/forumdisplay.php?fid=5", loginHttpHeader.params, "gbk");

        System.out.println(body);

    }
    public static void testLogin()throws Exception{
        HttpHeader loginHttpHeader=new  HttpHeader();
        loginHttpHeader.Host = "www.mayadisc.com";
        loginHttpHeader.Origin="http://www.mayadisc.com";
        loginHttpHeader.Referer = "http://www.mayadisc.com/logging.php?action=login";
        loginHttpHeader.cookies.put("is_use_cookiex","yes");
        loginHttpHeader.cookies.put("cdb_cookietime","2592000");
        loginHttpHeader.cookies.put("cdb_oldtopics","D2112861D2112856D2112862D2112863D");
        loginHttpHeader.cookies.put("cdb_fid5","1499129739");
        loginHttpHeader.cookies.put("cdb_sid","7kuZiP");
        loginHttpHeader.cookies.put("is_use_cookied","yes");

        //HashMap params =new HashMap();
        loginHttpHeader.Content_Length=190;
        loginHttpHeader.cookies.put("Host","www.mayadisc.com");
        //loginHttpHeader.params.put
        loginHttpHeader.params.put("username","zjlgdxfj");

        loginHttpHeader.params.put("password","123456fj");
        loginHttpHeader.params.put("formhash","afabcdb9");
        loginHttpHeader.params.put("referer","http://www.mayadisc.com/index.php");
        loginHttpHeader.params.put("loginfield","username");
        loginHttpHeader.params.put("questionid","0");
        loginHttpHeader.params.put("answer","");
        loginHttpHeader.params.put("loginmode","");
        loginHttpHeader.params.put("styleid","");
        loginHttpHeader.params.put("cookietime","2592000");
        loginHttpHeader.params.put("loginsubmit","%CC%E1+%26%23160%3B+%BD%BB");
        String url ="http://www.mayadisc.com/logging.php?action=login";

        // POST http://www.mayadisc.com/logging.php?action=login& HTTP/1.1
        String body= HttpRequestUtil.doPost(loginHttpHeader,url,loginHttpHeader.params,"gbk");


        loginHttpHeader.params.clear();
        body= HttpRequestUtil.doGet(loginHttpHeader,"http://www.mayadisc.com/forumdisplay.php?fid=5",loginHttpHeader.params,"gbk");

        FileUtil.writeFile(PathManager.getInstance().getTmpPath().resolve("page" + 1 + ".txt").toFile(), body);
        System.out.println("get请求结果\r\n"+body +"body length:"+body.length());

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = domFactory.newDocumentBuilder();



        Document doc = builder.parse(body);//"C:\\zzw\\calendar\\src\\main\\java\\pachong\\book.xml"

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr
                = xpath.compile("//table[class='row']/td/text()");

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

       /* String s ="http://123.jpg;http://55111.jpg";
        Pattern pattern = Pattern.compile("^http://.*\\.jpg");
        Matcher matcher = pattern.matcher(s);
        //StringBuffer sbr = new StringBuffer();
        while (matcher.find()) {
            System.out.println(matcher.group());
        }*/
        // matcher.appendTail(sbr);
        // System.out.println(sbr.toString());

        //HttpRequestUtil.loginPostWithSocket();
    }

    public static void test() throws Exception {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = domFactory.newDocumentBuilder();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        //HttpClient client = new HttpClient();

        HashMap map =new HashMap();
        map.put("username","zjlgdxfj");

        map.put("password","123456fj");
        map.put("formhash","afabcdb9");
        map.put("referer","http://www.mayadisc.com/index.php");
        map.put("loginfield","username");
        map.put("questionid","0");
        map.put("answer","");
        map.put("loginmode","");
        map.put("styleid","");
        map.put("cookietime",2592000);
        map.put("loginsubmit","%CC%E1+%26%23160%3B+%BD%BB");


        //]String loginConent = HttpRequestUtil.sendPost3("http://www.mayadisc.com/logging.php?action=login&", map);
        String loginConent = HttpRequestUtil.doPost(httpclient,"http://www.mayaww.com/logging.php?action=login", map, "gbk", true);

        //  System.out.println(ZipUtil.uncompress(loginConent));
        //String loginConent= HttpRequestUtil.sendPost("http://www.mayadisc.com/logging.php?action=login&","formhash=afabcdb9&referer=index.php&loginfield=username&username=zjlgdxfj&password=123456fj&questionid=0&answer=&cookietime=2592000&loginmode=&styleid=&loginsubmit=%CC%E1+%26%23160%3B+%BD%BB");
        System.out.println(loginConent);
        if(loginConent.indexOf("zjlgdxfj")>0){
            System.out.println("成功");
        }else{
            System.out.println("登录失败");
            // return;
        }

        //Socket socket =new Socket();


        String content = HttpRequestUtil.doGet(httpclient, "http://www.mayadisc.com/forumdisplay.php?fid=5", null, "GBK", true);
        System.out.println(content);
        int startIndex= content.indexOf("<form method=\"post\" name=\"moderate\"");

        int endIndex= content.indexOf("</form>",startIndex);
        System.out.print("start "+startIndex);

        String realContent = content.substring(startIndex,endIndex+6);
        System.out.println(""+realContent);
        URL url =new URL("http://www.mayadisc.com/forumdisplay.php?fid=5");

        Document doc = builder.parse("http://www.mayadisc.com/forumdisplay.php?fid=5");//"C:\\zzw\\calendar\\src\\main\\java\\pachong\\book.xml"

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr
                = xpath.compile("//table[class='row']/td/text()");

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

    }


}

