package com.guiji.dispatch.util;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.regex.Matcher;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

import com.guiji.utils.HttpClientUtil;
import com.guiji.utils.StrUtils;
/** 
* @ClassName: MobileFromUtil 
* @Description: 手机号码归属地查询
* @author: weiyunbo
* @date 2018年7月27日 下午4:30:32 
* @version V1.0  
*/
public class MobileFromUtil {
	private static final Logger logger = LoggerFactory.getLogger(MobileFromUtil.class);
	
	/**
	 * 查询手机号
	 * @param mobile
	 * @return
	 */
    public MobileOwnerRsp getPhoneFrom(String mobile) {
    	return this.getPhoneFrom(mobile,1);
    }
	
	private MobileOwnerRsp getPhoneFrom(String mobile,int i){
		//webXml提供的手机号归属服务
		WebXmlMobileService webXmlMobileService = new WebXmlMobileService();
		//淘宝提供的手机号发送服务
		TaobaoMobileService taobaoMobileService = new TaobaoMobileService();	
		//搜收录网手机号归属地查询服务
		SoshouluMobileService soshouluMobileService = new SoshouluMobileService();
		Random r = new Random();
		int num = r.nextInt(100);
		MobileOwnerRsp rsp = null;
		if(num < 40){ //40个数字的区间，40%的几率 (用淘宝发)
			try {
				rsp = taobaoMobileService.getMobileFrom(mobile);
			} catch (Exception e) {
				logger.error("调用淘宝获取手机号归属地异常!",e);
			}
		}else if(num < 80){//[40,40)，40个数字的区间，40%的几率 (用搜收录网发)
			try {
				rsp = soshouluMobileService.getMobileFrom(mobile);
			} catch (Exception e) {
				logger.error("调用搜收录获取手机号归属地异常!",e);
			}
		}else{
		    //剩余20%概率 (用webXml网发)
			try {
				rsp = webXmlMobileService.getMobileFrom(mobile);
			} catch (Exception e) {
				logger.error("调用webXml获取手机号归属地异常!",e);
			}
		}
		i++; //查询次数，防止死循环
		if(i<5 && rsp == null || "00".equals(rsp.getStatus())) {
			//失败了，重新查询
			return this.getPhoneFrom(mobile,i);
		}else {
			return rsp;
		}
	}
	
	
	/**
	* @ClassName: TaobaoMobileService 
	* @Description: 淘宝提供的手机号归属地查询
	* @author: weiyunbo
	* @date 2018年8月30日 上午10:59:34 
	* @version V1.0
	 */
	class TaobaoMobileService{
		public MobileOwnerRsp getMobileFrom(String Mobile){
			MobileOwnerRsp rsp = new MobileOwnerRsp();
			if(StrUtils.isNotEmpty(Mobile)) {
				String url ="http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel="+Mobile+"";
				String str = HttpClientUtil.doGet(url);
				logger.info("调用淘宝接口查询手机号归属地："+str);
				if(StrUtils.isNotEmpty(str)) {
					int begin = str.indexOf("province:'");
					int end = StrUtils.getFromIndex(str, "',", 2);
					if(begin >0 && end >0) {
						//查到了
						String owner = str.substring(begin+10, end);
						rsp.setOwner(owner); //归属地
						rsp.setStatus("01"); //成功
						logger.info("OK--淘宝接口查询手机号{}归属地{}：",Mobile,owner);
					}else {
						rsp.setStatus("00"); //失败
						rsp.setErrorMsg("没有查到");
					}
				}else {
					rsp.setStatus("00"); //失败
					rsp.setErrorMsg("没有查到");
				}
				
			}else {
				rsp.setStatus("00");
				rsp.setErrorMsg("手机号不能为空");
			}
			return rsp;
		}
	}
	
	/**
	* @ClassName: SoshouluMobileService 
	* @Description: 搜收录网手机号归属地查询
	* @author: weiyunbo
	* @date 2018年8月30日 上午11:08:08 
	* @version V1.0
	 */
	class SoshouluMobileService{
		public MobileOwnerRsp getMobileFrom(String Mobile){
			MobileOwnerRsp rsp = new MobileOwnerRsp();
			if(StrUtils.isNotEmpty(Mobile)) {
				String url ="http://sj.soshoulu.com/ajax/lifecha.ashx?_type=sfzsearch&ip="+Mobile+"&px=1";
				String str = HttpClientUtil.doGet(url);
				logger.info("调用搜收录网接口查询手机号归属地："+str);
				if(StrUtils.isNotEmpty(str)) {
					str = str.replaceAll(Matcher.quoteReplacement("$fg$"),",");
			    	String[] array = str.split(",");
			    	if(array != null && array.length>1) {
			    		//查到了
			    		rsp.setOwner(array[1]); //归属地
						rsp.setStatus("01"); //成功
						logger.info("OK--搜收录接口查询手机号{}归属地{}：",Mobile,array[1]);
			    	}
				}else {
					rsp.setStatus("00"); //失败
					rsp.setErrorMsg("没有查到");
				}
				
			}else {
				rsp.setStatus("00");
				rsp.setErrorMsg("手机号不能为空");
			}
			return rsp;
		}
	}
	
	
	/**
	 * 
	* @ClassName: MobileOwnerRsp 
	* @Description: 不同渠道查询手机号归属地返回信息
	* @author: weiyunbo
	* @date 2018年8月30日 上午10:16:57 
	* @version V1.0
	 */
	public class MobileOwnerRsp{
		//状态(00-失败,01-成功)
		private String status;
		//归属地
		private String owner;
		private String errorMsg;
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getOwner() {
			return owner;
		}
		public void setOwner(String owner) {
			this.owner = owner;
		}
		public String getErrorMsg() {
			return errorMsg;
		}
		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
	}
	
	/**
	 * 
	* @ClassName: WebXmlMobileService 
	* @Description: WebXml对外暴露的获取手机号服务
	*                免费用户按说明每天可以免费调用100次，但是实测可以调用3000多次吧
	* @author: weiyunbo
	* @date 2018年8月30日 上午9:47:19 
	* @version V1.0
	 */
	public class WebXmlMobileService{     //内部类
    	private String getSoapRequest(String mobileCode) {
            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n"
            + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + " "
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"" + " "
            + "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" + "\n"
            + "<soap:Body>" + "\n"
            + "<getMobileCodeInfo" + " " + "xmlns=\"http://WebXml.com.cn/\">" + "\n"
            + "<mobileCode>" + mobileCode + "</mobileCode>" + "\n"
            + "<userID></userID>" + "\n"
            + "</getMobileCodeInfo>" + "\n"
            + "</soap:Body>" + "\n"
            + "</soap:Envelope>"
            );
            return sb.toString();
        }
        private InputStream getSoapInputStream(String mobileCode) {
            try {
                String soap = getSoapRequest(mobileCode);
                if (soap == null)
                    return null;
                URL url = new URL("http://www.webxml.com.cn/WebServices/MobileCodeWS.asmx");
                URLConnection conn = url.openConnection();
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                conn.setRequestProperty("Content-Length", Integer.toString(soap.length()));
                conn.setRequestProperty("SOAPAction", "http://WebXml.com.cn/getMobileCodeInfo");
                OutputStream os = conn.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                osw.write(soap);
                osw.flush();
                osw.close();
                osw.close();
                InputStream is = conn.getInputStream();
                return is;
            } catch (Exception e) {
            	logger.error("调用第三方手机归属地查询服务异常!",e);
                return null;
            }
     
        }
     
        /**
         * 根据手机号查询相对全的一些信息(号码归属地、手机号类型之类)
         * @param mobileCode
         * @return
         */
        public String getMobileNoFull(String mobileCode) {
            try {
                org.w3c.dom.Document document = null;
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true);
                InputStream is = getSoapInputStream(mobileCode);
                DocumentBuilder db = dbf.newDocumentBuilder();
                document = db.parse(is);
                NodeList nl = document.getElementsByTagName("getMobileCodeInfoResult");
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < nl.getLength(); i++) {
                    org.w3c.dom.Node n = nl.item(i);
                    if (n.getFirstChild().getNodeValue().equals("手机号码错误")) {
                        sb = new StringBuffer("#");
                        logger.error("手机号码输入有误!");
                        break;
                    }
                    sb.append(n.getFirstChild().getNodeValue() + "\n");
                }
                is.close();
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        /**
         * 查询手机号归属地
         * @param Mobile
         * @return
         */
        public MobileOwnerRsp getMobileFrom(String Mobile){
            String str = "";
            str = this.getMobileNoFull(Mobile);
            if(str != null && !"".equals(str)){
                str = str.substring(str.indexOf("：")+1);
                String strArry [] = new String[]{};
                strArry = str.split(" ");
                str = strArry[0];
            }
            MobileOwnerRsp rsp = new MobileOwnerRsp();
            if(StrUtils.isEmpty(str)) {
            	rsp.setErrorMsg("返回空！");
            	rsp.setStatus("00"); //失败
            }else {
            	if(str.contains("没有") || str.contains("http")) {
            		//调用失败，没有查到或者返回http://www.webxml.com.cn/zh_cn/web_services_user.aspx 表示免费用户每天用量完了
            		rsp.setErrorMsg(str);
                	rsp.setStatus("00"); //失败
            	}else {
            		rsp.setStatus("01"); //成功
            		rsp.setOwner(str);
            		logger.info("OK--WebXml接口查询手机号{}归属地{}：",Mobile,str);
            	}
            }
            return rsp;
        }
    }
	
	public static void main(String[] args) {
		MobileFromUtil util = new MobileFromUtil();
		System.out.println(util.getPhoneFrom("15221738675").getOwner());
	}
}
