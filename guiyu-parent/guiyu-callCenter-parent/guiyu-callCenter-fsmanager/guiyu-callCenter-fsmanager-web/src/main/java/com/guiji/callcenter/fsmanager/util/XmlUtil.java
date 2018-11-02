package com.guiji.callcenter.fsmanager.util;

import sun.misc.BASE64Encoder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

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

    /**
     * String转base64
     * @param str
     * @return
     */
    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }
}
