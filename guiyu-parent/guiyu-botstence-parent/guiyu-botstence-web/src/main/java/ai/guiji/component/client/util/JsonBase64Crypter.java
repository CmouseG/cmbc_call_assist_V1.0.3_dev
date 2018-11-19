package ai.guiji.component.client.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

public class JsonBase64Crypter {
	
	private static final String password = "toolwiz.com";
	
	private static final Base64 base64 = new Base64();

	public static String encode(String content){
		StringBuffer sb=new StringBuffer();
		char[] chars=content.toCharArray();
		for(int i=0;i<chars.length;i++){
			String item=new String(new char[]{chars[i]});
			//if(!StringUtils.isEmpty(item.trim())){
				if(isChineseByREG(chars[i])){
					sb.append(stringToUnicode(item));
				}else{
					sb.append(chars[i]);
				}
			//}
			
		}
		
		content=sb.toString();
		System.out.println(content);
		
		StringBuffer buffer=new StringBuffer();
		char[] keys=password.toCharArray();
		int keyLen=keys.length;

		char[] bytes=content.toCharArray();
		int byteLen=bytes.length;

		byte[] encodeByte=new byte[byteLen];
		for(int i=0;i<byteLen;i++){
			byte item=(byte) ((bytes[i] + keys[i%keyLen])%256);
			encodeByte[i]=item;
			buffer.append((char)(item & 0xff));
		}
		String encodedText = base64.encodeToString(buffer.toString().getBytes());
		return encodedText;
	}
	
	
	public static void encodeFile(File file){
		StringBuffer buffer=new StringBuffer();
		String line=null;
		BufferedReader reader=null;
		try {
			reader=new BufferedReader(new FileReader(file));
			while((line=reader.readLine())!=null){
				buffer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			IOUtil.close(reader);
		}
		
		String endcodeString=safeUrlBase64Encode(buffer.toString());
		OutputStream out=null;
		try {
			out=new FileOutputStream(file);
			out.write(endcodeString.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.close(out);
		}
	}

	public static String safeUrlBase64Encode(String content){
		String encodeBase64 = encode(content);
		String safeBase64Str = encodeBase64.replace('+','-');
		safeBase64Str = safeBase64Str.replace('/','_');
//		safeBase64Str = safeBase64Str.replaceAll("=","");
		return safeBase64Str;
	}
	
	
    public  static String stringToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        String returnStr = "";
        for (int i = 0; i < chars.length; i++) {
          returnStr += "\\u" + Integer.toString(chars[i], 16);
        }
        return returnStr;
    }
	
	
	public static boolean isChineseByREG(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
	       if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
	               || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
	               || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
	               || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
	               || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
	               || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
	           return true;  
	       }  
	       return false;
	}
	
}
