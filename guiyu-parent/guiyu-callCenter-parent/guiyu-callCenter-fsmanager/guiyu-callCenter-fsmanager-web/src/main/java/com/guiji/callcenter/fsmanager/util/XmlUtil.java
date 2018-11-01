package com.guiji.callcenter.fsmanager.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class XmlUtil {
    /**
     * 生成
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String buildxml(T obj) {
        try {
            StringWriter write = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(new Class[] { obj.getClass() });
            Marshaller jaxbMarshaller = context.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            jaxbMarshaller.marshal(obj, write);
            return write.getBuffer().toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return "";
    }

    //    public void buildxml(DialplanEntity include, File file) throws Exception {
//        JAXBContext jaxbContext = JAXBContext.newInstance(DialplanEntity.class);
//        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//        // output pretty printed
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
//        jaxbMarshaller.marshal(include, file);
//        jaxbMarshaller.marshal(include, System.out);
//    }
}
