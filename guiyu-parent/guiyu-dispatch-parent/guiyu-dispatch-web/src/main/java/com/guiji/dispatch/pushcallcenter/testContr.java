package com.guiji.dispatch.pushcallcenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.dispatch.bean.MQSuccPhoneDto;
import com.guiji.dispatch.dao.PushRecordsMapper;
import com.guiji.dispatch.dao.entity.PushRecords;
import com.guiji.dispatch.dao.entity.PushRecordsExample;
import com.guiji.dispatch.state.IphonesThread;
import com.guiji.dispatch.util.Constant;
import com.guiji.utils.JsonUtils;

@RestController
public class testContr {

	@Autowired
	private IphonesThread  thread;
	@Autowired
	private IPushPhonesHandler handeler;
	@Autowired
	private PushRecordsMapper recordMapper;
	
	@PostMapping("start")
	public void a() { 
		thread.execute();
	}

	@PostMapping("IPushPhonesHandler")
	public void b() {
		handeler.pushHandler();
	} 
	@PostMapping("test")
	public void c(){
		PushRecordsExample ex = new PushRecordsExample();
		ex.createCriteria().andPlanuuidEqualTo("e0821939ad5b4f6eb9296eda6eb752ce")
				.andCallbackStatusEqualTo(Constant.NOCALLBACK);
		PushRecords re = new PushRecords();
		//设置已经回调的状态
		re.setCallbackStatus(Constant.CALLBACKED);
		recordMapper.updateByExampleSelective(re, ex);
	}
}
