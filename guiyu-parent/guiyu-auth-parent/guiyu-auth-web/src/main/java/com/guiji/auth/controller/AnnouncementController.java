package com.guiji.auth.controller;

import com.guiji.auth.service.AnnouncementService;
import com.guiji.common.model.Page;
import com.guiji.component.aspect.SysOperaLog;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.user.dao.entity.SysAnnouncement;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by ty on 2019/1/14.
 */
@RestController
public class AnnouncementController {
    @Autowired
    AnnouncementService announcementService;

    @Jurisdiction("system_noticeList_add")
    @SysOperaLog(operaTarget = "公告", operaType = "新增")
    @ApiOperation(value="新增公告", notes="新增公告")
    @RequestMapping(value = "/announcement/add", method = RequestMethod.POST)
    public SysAnnouncement insert(SysAnnouncement sysAnnouncement, @RequestHeader Long userId) {
        return announcementService.insert(sysAnnouncement,userId);
    }

    @SysOperaLog(operaTarget = "公告", operaType = "更新")
    @ApiOperation(value="更新公告", notes="更新公告")
    @RequestMapping(value = "/announcement/update", method = RequestMethod.POST)
    public SysAnnouncement update(SysAnnouncement sysAnnouncement,  @RequestHeader Long userId) {
        return announcementService.update(sysAnnouncement,userId);
    }

    @SysOperaLog(operaTarget = "公告", operaType = "分页查询")
    @ApiOperation(value="分页公告信息", notes="根据查询条件分页查询公告信息")
    @RequestMapping(value = "/announcement/queryByPage", method = RequestMethod.POST)
    public Page<SysAnnouncement> queryByPage(int pageNo, int pageSize, SysAnnouncement sysAnnouncement) {
        return announcementService.getSysAnnounceMentByPage(pageNo, pageSize, sysAnnouncement);
    }

    @Jurisdiction("system_noticeList_delete")
    @SysOperaLog(operaTarget = "公告", operaType = "删除")
    @ApiOperation(value="删除公告信息", notes="根据ID删除公告信息")
    @RequestMapping(value = "/announcement/delete", method = RequestMethod.POST)
    public int delete(int id) {
        return announcementService.delete(id);
    }

    @SysOperaLog(operaTarget = "公告", operaType = "根据ID查询公告信息")
    @ApiOperation(value="获取公告信息", notes="根据ID查询公告信息")
    @RequestMapping(value = "/announcement/get", method = RequestMethod.POST)
    public SysAnnouncement getById(int id) {
        return announcementService.getSysAnnounceMentById(id);
    }
}
