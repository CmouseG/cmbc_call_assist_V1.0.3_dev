package com.guiji.ccmanager.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 黎阳
 * @Date: 2018/11/1 0001 18:54
 * @Description:
 */
@RestController
public class LineCountController {

    @Autowired
    private LineCountTask lineCountTask;

    @GetMapping(value="checkCount")
    public boolean checkCount(){
       return lineCountTask.checkCount();
    }

}
