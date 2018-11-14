package com.guiji.callcenter.fsmanager.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

public class XmlUtil {
    private final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    /**
     * 生成xml
     * @param obj
     * @param <T>
     * @return
     */
    public <T> String buildxml(T obj) {
        try {
            StringWriter write = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(new Class[] { obj.getClass() });
            Marshaller jaxbMarshaller = context.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(obj, write);
            return write.getBuffer().toString();
        } catch (JAXBException e) {
            logger.info("操作线路，生成xml失败",e);
        }
        return "";
    }

    /**
     * String转base64
     *
     * @param str
     * @return
     */
    public  String getBase64(String str) {
        Base64 base64 = new Base64();
        byte[] textByte = new byte[0];
        try {
            textByte = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.info("将线路String转base64异常",e);
        }
        //编码
        return base64.encodeToString(textByte);
    }
}
