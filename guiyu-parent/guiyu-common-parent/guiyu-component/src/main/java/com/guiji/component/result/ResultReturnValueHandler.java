package result;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.util.List;


public class ResultReturnValueHandler extends  RequestResponseBodyMethodProcessor{
	
	
	public ResultReturnValueHandler(List<HttpMessageConverter<?>> converters) {
		super(converters);
	}


	@Override
	protected <T> void writeWithMessageConverters(T value, MethodParameter returnType, NativeWebRequest webRequest)
			throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {
		Object result=value;
		if(!(value instanceof Result.ReturnData)){
			result= Result.ok(value);
		}
		super.writeWithMessageConverters(result, returnType, webRequest);
	}

	@Override
	protected <T> void writeWithMessageConverters(T value, MethodParameter returnType,
			ServletServerHttpRequest inputMessage, ServletServerHttpResponse outputMessage)
			throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {
		Object result=value;
		if(!(value instanceof Result.ReturnData)){
			result= Result.ok(value);
		}
		super.writeWithMessageConverters(result, returnType, inputMessage, outputMessage);
	}
	
	

	
}
