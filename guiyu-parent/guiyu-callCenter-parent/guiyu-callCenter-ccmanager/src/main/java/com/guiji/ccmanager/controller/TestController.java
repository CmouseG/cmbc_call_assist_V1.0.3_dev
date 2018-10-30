package com.guiji.ccmanager.controller;

import com.guiji.ccmanager.feign.LineOperApiFeign;
import com.guiji.common.result.Result;
import com.guiji.fsmanager.api.LineOperApi;
import com.guiji.fsmanager.entity.LineInfo;
import com.guiji.fsmanager.entity.LineXmlnfo;
import com.guiji.utils.FeignBuildUtil;
import feign.RequestLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 09:15
 * @Description:
 */
@RestController
public class TestController{

    @Autowired
    private Registration registration;

//    @Override
    @GetMapping(value="/linexmlinfos")
    public Result.ReturnData<String> linexmlinfosAll() {

        Result s = new Result();
        return Result.ok(registration.getHost() + ":" + registration.getPort());
    }

/*    @GetMapping(value="/test")
    public Result.ReturnData<String> test() {

        LineOperApiFeign c = FeignBuildUtil.feignBuilderTarget(LineOperApiFeign.class,"http://127.0.0.1:19084");

        Result.ReturnData<Integer> r =  c.linexmlinfosAll();
        int xx = r.getBody();
        return null;
    }*/
}
