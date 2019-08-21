package Test01;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wang
 * @create 2019-08-11 18:31
 * @desc
 **/
public class Test01 {
    public static void main(String[] args) {
        //SAX解析
        //1、获取解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //2.获取解析器
        try {
            SAXParser parser = factory.newSAXParser();
            //3.编写处理器
            //4.加载Document注册处理器
            WebHandler handler = new WebHandler();
            //5.解析
            parser.parse(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("Test01/webtest.xml"),handler);


            //获取数据
            WebContext context = new WebContext(handler.getEntities(),handler.getMappings());


            String classname = context.getClz("/reg");
            //反射
            Class clz = Class.forName(classname);
            Servlet servlet = (Servlet) clz.getConstructor().newInstance();
            System.out.println(servlet);
            servlet.service();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class WebHandler extends DefaultHandler {
    private List<Entity> entities;
    private List<Mapping> mappings;
    private Mapping mapping;
    private Entity entity;
    private boolean isMapping;
    private String tag;//标签
    @Override
    public void startDocument() throws SAXException {
        entities = new ArrayList<Entity>();
        mappings = new ArrayList<Mapping>();
    }

    public List<Mapping> getMappings() {
        return mappings;
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tag=qName;//标签名
        if (qName.equals("servlet")){
            entity = new Entity();
            isMapping=false;
        }else if (qName.equals("servlet-mapping")){
            mapping = new Mapping();
            isMapping=true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("servlet")){
            entities.add(entity);
        }else if (qName.equals("servlet-mapping")){
            mappings.add(mapping);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch,start,length).trim();
        if (isMapping){
            if (content.length()>0){
                if (tag.equals("servlet-name")){
                    mapping.setName(content);
                }else if(tag.equals("url-pattern")){
                    mapping.addPattern(content);
                }
            }
        }else if (!isMapping){
            if (content.length()>0){
                if (tag.equals("servlet-name")){
                    entity.setName(content);
                }else if(tag.equals("servlet-class")){
                    entity.setClz(content);
                }
            }
        }
        tag=null;
    }

    public List<Entity> getEntities() {
        return entities;
    }
}

