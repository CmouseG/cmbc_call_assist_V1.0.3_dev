package com.guiji.ccmanager.controller;


import com.guiji.auth.api.IAuth;
import com.guiji.ccmanager.api.ILabel;
import com.guiji.ccmanager.service.LabelService;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysUser;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class LabelController implements ILabel {

    @Autowired
    LabelService labelService;
    @Autowired
    IAuth iAuth;

    private final Logger logger = LoggerFactory.getLogger(LabelController.class);

    @ApiOperation(value = "获取最近1个月所有的通话记录标签")
    @GetMapping("getAllLabelOneMonth")
    public Result.ReturnData<List<String>> getAllLabelOneMonth(String orgCode,Long userId,Integer authLevel) {

        List<String> list = labelService.getAllLabelOneMonth(orgCode,userId,authLevel);

        List<String> authLabel = new ArrayList<>();
        try{
            Result.ReturnData<SysUser> result =  iAuth.getUserById(userId);
            String intent = result.getBody().getIntenLabel();
            if(StringUtils.isNotBlank(intent)){
                String[] arr = intent.split(",");
                authLabel = Arrays.asList(arr);
            }
        }catch (Exception e){
            logger.error("iAuth.getUserById userId[{}] has error :"+e,userId);
        }

        if(list!=null && list.size()>0){
            if(authLabel!=null && authLabel.size()>0){
                for(String label:authLabel){
                    if(!list.contains(label)){
                        list.add(label);
                    }
                }
            }
            Collections.sort(list);
            return Result.ok(list);
        }else{
            Collections.sort(authLabel);
            return Result.ok(authLabel);
        }

    }

}
