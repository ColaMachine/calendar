package lab.tdd;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by dozen.zhang on 2017/5/4.
 */
public class TemplateTest {
    @Test
    public void oneVariable()throws Exception{
        Template template = new Template("hello,${name}");
       template.set("name","Reader");
        assertEquals("hello,Reader",template.evaluate());
    }

    @Test
    public void differentValue() throws Exception{
        Template template = new Template("Hello, ${name}");
        template.set("name", "someone else");
        assertEquals("Hello, someone else", template.evaluate());
    }

}
