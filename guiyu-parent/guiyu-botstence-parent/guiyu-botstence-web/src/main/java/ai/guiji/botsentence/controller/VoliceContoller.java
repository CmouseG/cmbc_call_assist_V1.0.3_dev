package ai.guiji.botsentence.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ai.guiji.botsentence.dao.entity.VoliceInfo;
import ai.guiji.botsentence.dao.entity.VoliceInfoExt;
import ai.guiji.botsentence.service.IVoliceService;
import ai.guiji.botsentence.vo.RefuseVoliceVO;
import ai.guiji.component.client.config.JsonParam;
import ai.guiji.component.client.util.ExcelUtil;
import ai.guiji.component.exception.CommonException;
import ai.guiji.component.model.ServerResult;

@Controller
@RequestMapping("volice")
public class VoliceContoller {

    @Autowired
    private IVoliceService service;

//    @Autowired
//    private QiuniuUploadUtil qiuniuUploadUtil;
//
//    @Autowired
//    private BotSentenceProcessServiceImpl botSentenceProcessService;

    @RequestMapping("queryVoliceInfoList")
    @ResponseBody
    public ServerResult<List<VoliceInfoExt>> queryVoliceInfoList(@JsonParam String processId) {
        List<VoliceInfoExt> list = service.queryVoliceInfoList(processId);
        return ServerResult.createBySuccess(list);
    }

    /**
     * 批量导出文案
     *
     * @param processId
     * @param voliceIds
     * @param resp
     */
    @RequestMapping("downloadContentXml")
    public void downloadContentXml(@JsonParam String processId, @JsonParam String[] voliceIds, HttpServletResponse resp) {
        List<VoliceInfoExt> list = service.queryVoliceInfoList(processId);
        if (null != list & list.size() > 0) {
            for (VoliceInfoExt temp : list) {
                if (StringUtils.isNotBlank(temp.getVoliceUrl())) {
                    temp.setHasVolice("是");
                } else {
                    temp.setHasVolice("否");
                }
            }
        }
        String[] header = new String[]{"编号", "标题", "是否已有录音文件", "文案"};
        String[] columns = new String[]{"voliceId", "title", "hasVolice", "content"};
        ExcelUtil.export(header, columns, list, resp);
    }

    /**
     * 上传解析音频文件
     *
     * @throws FileNotFoundException
     */
    @RequestMapping("uploadVoliceZip")
    @ResponseBody
    public ServerResult<Object> uploadVoliceZip(MultipartFile multipartFile, @RequestParam("processId") String processId, @RequestHeader Long userId) {
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!"zip".equals(suffix)) {
            return ServerResult.createByErrorMessage("请上传zip格式压缩文件!");
        }
        if (null != multipartFile && StringUtils.isNotBlank(processId)) {

            long size = multipartFile.getSize();
            if (size > 80 * 1024 * 1024) {
                throw new CommonException("压缩文件大小超过80M,请您压缩后重新上传");
            }

            List<String> list;
			try {
				InputStream inStream = multipartFile.getInputStream();
				list = service.uploadVoliceZip(processId, inStream, userId);
				return ServerResult.createBySuccess(list);
			} catch (IOException e) {
				e.printStackTrace();
			}
            
        }
        
        return ServerResult.createByError();
    }


    /**
     * 上传单个音频文件
     *
     * @throws FileNotFoundException
     */
    @RequestMapping("uploadOneVolice")
    @ResponseBody
    public ServerResult<String> uploadOneVolice(MultipartFile multipartFile, @RequestParam("processId") String processId, @RequestParam("voliceId") String voliceId, @RequestParam("type") String type, @RequestHeader Long userId) {
        try {
            if (null != multipartFile && StringUtils.isNotBlank(voliceId)) {
                String fileName = multipartFile.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

                long size = multipartFile.getSize();
                if (size > 1 * 1024 * 1024) {
                    return ServerResult.createByErrorMessage("文件大小超过1M,请重新上传");
                }

                if (!"wav".equals(suffix)) {
                    return ServerResult.createByErrorMessage("请上传wav格式音频文件!");
                }
                String voliceUrl = service.uploadOneVolice(processId, voliceId, multipartFile.getInputStream(), type, userId);
                return ServerResult.createBySuccess(voliceUrl);
            } else {
                return ServerResult.createByErrorMessage("请求参数不完整!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResult.createByError();
    }

    public ServerResult saveVolice(List<String> contents) {

        return ServerResult.createBySuccess();
    }

    @RequestMapping(value = "queryRefuseVoliceList")
    @ResponseBody
    public ServerResult<List<VoliceInfo>> queryRefuseVoliceList(@JsonParam String processId) {
        List<VoliceInfo> list = service.queryRefuseVoliceList(processId);
        return ServerResult.createBySuccess(list);
    }


    @RequestMapping(value = "saveRefuseVolice")
    @ResponseBody
    public ServerResult<List<VoliceInfo>> saveRefuseVolice(@JsonParam RefuseVoliceVO refuseVolice,@RequestHeader Long userId) {
        service.saveRefuseVolice(refuseVolice.getProcessId(), refuseVolice.getList(),userId);
        List<VoliceInfo> list = service.queryRefuseVoliceList(refuseVolice.getProcessId());
        return ServerResult.createBySuccess(list);
    }

    /**
     * 删除挽回话术池
     */
    @RequestMapping(value = "deleteRefuseVolice")
    @ResponseBody
    public ServerResult<List<VoliceInfo>> deleteRefuseVolice(@JsonParam String processId, @JsonParam String domainName, @JsonParam String voliceId) {
        service.deleteRefuseVolice(processId, voliceId, domainName);
        List<VoliceInfo> list = service.queryRefuseVoliceList(processId);
        //FlowInfoVO flow = botSentenceProcessService.initFlowInfo(processId);
        return ServerResult.createBySuccess(list);
    }

}
