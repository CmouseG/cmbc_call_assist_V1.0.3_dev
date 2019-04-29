package com.guiji.auth.service;

import com.alibaba.fastjson.util.IOUtils;
import com.guiji.auth.exception.GuiyuAuthExceptionEnum;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.Page;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.notice.api.INoticeSend;
import com.guiji.notice.enm.NoticeType;
import com.guiji.notice.entity.MessageSend;
import com.guiji.user.dao.SysAnnouncementMapper;
import com.guiji.user.dao.entity.SysAnnouncement;
import com.guiji.user.dao.entity.SysAnnouncementExample;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.NasUtil;
import com.guiji.utils.StrUtils;
import com.guiji.wechat.vo.SendMsgReqVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ty on 2019/1/14.
 */
@Service
public class AnnouncementService {
    private final Logger logger = LoggerFactory.getLogger(AnnouncementService.class);
    @Autowired
    private SysAnnouncementMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private INoticeSend noticeSend;
    @Value("${weixin.templateId}")
    String weixinTemplateId;
    @Value("${weixin.appid}")
    String weixinAppid;

    @Transactional
    public SysAnnouncement insert(SysAnnouncement sysAnnouncement, Long userId){
        logger.info("新增公告#start");
        check(sysAnnouncement);
        sysAnnouncement.setDelFlag(0);
        sysAnnouncement.setCreateTime(new Date());
        sysAnnouncement.setUpdateTime(new Date());
        sysAnnouncement.setCreateId(userId.intValue());
        sysAnnouncement.setUpdateId(userId.intValue());
        mapper.insert(sysAnnouncement);
        logger.info("新增公告成功#end");
        new Thread(() -> {
        	//发送站内信通知,当前发布人所在组织的所有账号都接收站内通知
            List<SysOrganization> sysOrganizationList = organizationService.getOrgByUserId(userId);
            if (sysOrganizationList != null) {
                for (SysOrganization sysOrganization : sysOrganizationList) {
                	try
					{
                		MessageSend messageSend = new MessageSend();
                        messageSend.setOrgCode(sysOrganization.getCode());
                        messageSend.setNoticeType(NoticeType.announcement);
                        String content = "";
                        if (sysAnnouncement != null && StringUtils.isNotEmpty(sysAnnouncement.getContent())) {
                            if (sysAnnouncement.getContent().length() >= 50) {
                                content = sysAnnouncement.getContent().substring(0,50) + ",登录系统查看详情";
                            } else {
                                content = sysAnnouncement.getContent() + ",登录系统查看详情";
                            }
                        }
                        //站内信
                        messageSend.setMailContent(content);
                        //邮箱
                        messageSend.setEmailSubject("公告");
                        messageSend.setEmailContent(content);
                        //短信
                        messageSend.setSmsContent(content);
                        //微信
                        messageSend.setWeixinTemplateId(weixinTemplateId);
                        messageSend.setWeixinAppId(weixinAppid);
                        HashMap<String, SendMsgReqVO.Item> map = new HashMap<>();
                        map.put("keyword2",new SendMsgReqVO.Item(NoticeType.announcement.getDesc(),null));
                        map.put("keyword3",new SendMsgReqVO.Item(content,null));
                        map.put("userName",new SendMsgReqVO.Item(content,null));
                        messageSend.setWeixinData(map);
                        noticeSend.sendMessage(messageSend);
					} catch (Exception e) {
						logger.error("发送站内信通知失败，组织编码为：" + sysOrganization.getCode() + "，错误信息：" + e.getMessage());
					}
                }
            }
        }).start(); 
        return sysAnnouncement;
    }

    @Transactional
    public SysAnnouncement update(SysAnnouncement sysAnnouncement,Long userId) {
        logger.info("更新公告#start");
        check(sysAnnouncement);
        sysAnnouncement.setUpdateTime(new Date());
        sysAnnouncement.setUpdateId(userId.intValue());
        mapper.updateByPrimaryKey(sysAnnouncement);
        logger.info("更新公告成功#end");
        return sysAnnouncement;
    }

    public SysAnnouncement getSysAnnounceMentById(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    public int delete(int id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public Page<SysAnnouncement> getSysAnnounceMentByPage(int pageNo, int pageSize, SysAnnouncement sysAnnouncement) {
        Page<SysAnnouncement> page = new Page<SysAnnouncement>();
        SysAnnouncementExample example = this.getExampleByCondition(sysAnnouncement);
        if(example == null) example = new SysAnnouncementExample();
        int totalRecord = mapper.countByExample(example); //总数
        example.setLimitStart((pageNo-1)*pageSize);	//起始条数
        example.setLimitEnd(pageSize);	//结束条数
        example.setOrderByClause("create_time desc");
        //分页查询
        List<SysAnnouncement> list = mapper.selectByExample(example);
        if(list != null && !list.isEmpty()) {
            List<SysAnnouncement> rspSysAnnouncementList = new ArrayList<SysAnnouncement>();
            for(SysAnnouncement announcement : list) {
                rspSysAnnouncementList.add(announcement);
            }
            page.setRecords(rspSysAnnouncementList);
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(totalRecord);
        return page;
    }

    private SysAnnouncementExample getExampleByCondition(SysAnnouncement sysAnnouncement) {
        logger.info("查询公告，查询条件="+sysAnnouncement);
        if(sysAnnouncement != null) {
            Integer id = sysAnnouncement.getId();	//主键ID
            String title = sysAnnouncement.getTitle();
            Integer type = sysAnnouncement.getType();
            String content = sysAnnouncement.getContent();
            String contentStyle = sysAnnouncement.getContentStyle();
            String filePaths = sysAnnouncement.getFilePaths();
            Integer readCount = sysAnnouncement.getReadCount();
            Integer createId = sysAnnouncement.getCreateId();
            Integer updateId = sysAnnouncement.getUpdateId();
            Integer delFlag = sysAnnouncement.getDelFlag();
            SysAnnouncementExample example = new SysAnnouncementExample();
            SysAnnouncementExample.Criteria criteria = example.createCriteria();
            if(id != null) {
                criteria.andIdEqualTo(id);
            }
            if(StrUtils.isNotEmpty(title)) {
                criteria.andTitleLike("%"+title+"%");
            }
            if (type != null) {
                criteria.andTypeEqualTo(type);
            }
            if(StrUtils.isNotEmpty(content)) {
                criteria.andContentEqualTo(content);
            }
            if(StrUtils.isNotEmpty(contentStyle)) {
                criteria.andContentStyleEqualTo(contentStyle);
            }
            if(StrUtils.isNotEmpty(filePaths)) {
                criteria.andFilePathsEqualTo(filePaths);
            }
            if (readCount != null) {
                criteria.andReadCountEqualTo(readCount);
            }
            if (createId != null) {
                criteria.andCreateIdEqualTo(createId);
            }
            if (updateId != null) {
                criteria.andUpdateIdEqualTo(updateId);
            }
            if (updateId != delFlag) {
                criteria.andUpdateIdEqualTo(delFlag);
            }
            return example;
        }else {
            logger.info("查询全部公告列表");
        }
        return null;
    }

    private void check(SysAnnouncement sysAnnouncement) {
        if (sysAnnouncement == null) {
            throw new GuiyuException(GuiyuAuthExceptionEnum.AUTH00010015.getErrorCode(),GuiyuAuthExceptionEnum.AUTH00010015.getMsg());
        }

        if (StringUtils.isEmpty(sysAnnouncement.getTitle())){
            throw new GuiyuException(GuiyuAuthExceptionEnum.AUTH00010011.getErrorCode(),GuiyuAuthExceptionEnum.AUTH00010011.getMsg());
        }

        if (sysAnnouncement.getTitle().length() > 150) {
            throw new GuiyuException(GuiyuAuthExceptionEnum.AUTH00010012.getErrorCode(),GuiyuAuthExceptionEnum.AUTH00010012.getMsg());
        }

        if (sysAnnouncement.getContent() != null && sysAnnouncement.getContent().length() > 200) {
            throw new GuiyuException(GuiyuAuthExceptionEnum.AUTH00010013.getErrorCode(),GuiyuAuthExceptionEnum.AUTH00010013.getMsg());
        }

        if (sysAnnouncement.getContentStyle() != null && sysAnnouncement.getContentStyle().length() > 3000) {
            throw new GuiyuException(GuiyuAuthExceptionEnum.AUTH00010014.getErrorCode(),GuiyuAuthExceptionEnum.AUTH00010014.getMsg());
        }
    }

    private File getFile(MultipartFile file) {
        File f = null;
        try {

            InputStream ins = file.getInputStream();
            f = new File(file.getOriginalFilename());
            inputStreamToFile(ins, f);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return f;
    }

    private void inputStreamToFile(InputStream ins, File file) {
    	OutputStream os = null;
        try {
        	os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
        	logger.error(e.getMessage());
        } finally {
        	IOUtils.close(os);
        	IOUtils.close(ins);
		}
    }

}
