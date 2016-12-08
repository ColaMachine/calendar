package pachong;

/**
 * Created by dozen.zhang on 2016/12/2.
 */
import java.io.IOException;
import java.net.URL;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

public class XPathExample {

    public static void main(String[] args)
            throws ParserConfigurationException, SAXException,
            IOException, XPathExpressionException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        URL url =new URL("http://www.mayadisc.com/forumdisplay.php?fid=5");
        Document doc = builder.parse(url.getFile());//"C:\\zzw\\calendar\\src\\main\\java\\pachong\\book.xml"

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr
                = xpath.compile("//book[author='Neal Stephenson']/title/text()");

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

    }

}
