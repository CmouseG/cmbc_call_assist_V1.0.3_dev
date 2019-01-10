package com.guiji.dispatch.pushcallcenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.dispatch.state.IphonesThread;

@RestController
public class testContr {

	@Autowired
	private IphonesThread  thread;
	@Autowired
	private IPushPhonesHandler handeler;
	@PostMapping("start")
	public void a() { 
		thread.execute();
	}

	@PostMapping("IPushPhonesHandler")
	public void b() {
		handeler.pushHandler();
	}
}
