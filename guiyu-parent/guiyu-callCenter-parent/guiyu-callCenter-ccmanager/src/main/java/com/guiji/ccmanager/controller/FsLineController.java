package com.guiji.ccmanager.controller;

import com.guiji.ccmanager.feign.LineOperApiFeign;
import com.guiji.ccmanager.vo.LinePort;
import com.guiji.common.model.ServerResult;
import com.guiji.fsmanager.entity.LineXmlnfo;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.Target;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/25 0025 15:54
 * @Description:
 */
@RestController
public class FsLineController {

    @ApiOperation(value = "获取所有的fsline对应的freeswitch对外端口列表")
    @GetMapping(value="fsoutlines")
    public ServerResult getLinePorts(){

        LinePort linePort1 = new LinePort("xx","192.168.1.1:8081");
        LinePort linePort2 = new LinePort("yy","192.168.1.2:8082");
        LinePort linePort3 = new LinePort("zz","192.168.1.3:8083");

        List list = new ArrayList();
        list.add(linePort1);
        list.add(linePort2);
        list.add(linePort3);

        return ServerResult.create("0300000","success",list);
    }

    @GetMapping(value="test")
    public ServerResult test() throws Exception {
        /*LineOperApiFeign client = Feign.builder().target(Target.EmptyTarget.create(LineOperApiFeign.class));
        client.linexmlinfosAll(new URI("http://localhost:19084"));*/
        LineOperApiFeign service = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Request.Options(1000, 3500))
                .retryer(new Retryer.Default(5000, 5000, 3))
                .target(LineOperApiFeign.class, "http://127.0.0.1:19084");
        ServerResult s = service.linexmlinfosAll();
        return s;
    }

}
