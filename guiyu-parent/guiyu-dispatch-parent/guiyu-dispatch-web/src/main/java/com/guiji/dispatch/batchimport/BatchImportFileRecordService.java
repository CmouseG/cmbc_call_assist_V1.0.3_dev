package com.guiji.dispatch.batchimport;

import com.guiji.common.model.Page;
import com.guiji.dispatch.dao.FileRecordsMapper;
import com.guiji.dispatch.dao.entity.FileRecords;
import com.guiji.dispatch.dao.entity.FileRecordsExample;
import com.guiji.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BatchImportFileRecordService implements IBatchImportFileRecordService{
    @Autowired
    private FileRecordsMapper fileRecordsMapper;

    @Override
    public boolean save(FileRecords fileRecords) {
        if (fileRecords == null) {
            return false;
        }
        int result = fileRecordsMapper.insert(fileRecords);
        return result > 0 ? true : false;
    }

    @Override
    public boolean batchSave(List<FileRecords> fileRecords) {
        if (fileRecords == null || fileRecords.size() <=0) {
            return false;
        }

        for (FileRecords fileRecord : fileRecords) {
            fileRecordsMapper.insert(fileRecord);
        }

        return true;
    }

    @Override
    public boolean update(FileRecords fileRecords) {
        if (fileRecords != null) {
            int result = fileRecordsMapper.updateByPrimaryKeySelective(fileRecords);
            return result >0 ? true:false;
        } else {
            return false;
        }
    }

    @Override
    public List<FileRecords> list(FileRecords fileRecords) {
        FileRecordsExample example = this.getExampleByCondition(fileRecords);
        if(example == null) example = new FileRecordsExample();
        List<FileRecords> list = fileRecordsMapper.selectByExample(example);
        return list;
    }

    @Override
    public Page<FileRecords> queryFileRecordsPage(int pageNo, int pageSize, FileRecords fileRecords) {
        Page<FileRecords> page = new Page<FileRecords>();
        FileRecordsExample example = this.getExampleByCondition(fileRecords);
        if(example == null) example = new FileRecordsExample();
        int totalRecord = fileRecordsMapper.countByExample(example); //总数
        example.setLimitStart((pageNo-1)*pageSize);	//起始条数
        example.setLimitEnd(pageSize);	//结束条数
        example.setOrderByClause("create_time desc");//按时间倒序显示
        //分页查询
        List<FileRecords> list = fileRecordsMapper.selectByExample(example);
        if(list != null && !list.isEmpty()) {
            List<FileRecords> rspFileRecordsList = new ArrayList<FileRecords>();
            for(FileRecords fileRecord : list) {
                rspFileRecordsList.add(fileRecord);
            }
            page.setRecords(rspFileRecordsList);
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(totalRecord);
        return page;
    }

    @Override
    public boolean delete(Long id) {
        int result = fileRecordsMapper.deleteByPrimaryKey(id);
        return result >0 ? true:false;
    }

    private FileRecordsExample getExampleByCondition(FileRecords fileRecords) {
        if(fileRecords != null) {
            Long id = fileRecords.getId();
            Integer batchid = fileRecords.getBatchid();
            String batchName = fileRecords.getBatchName();
            String fileName = fileRecords.getFileName();
            Integer successCount = fileRecords.getSuccessCount();
            Integer failureCount = fileRecords.getFailureCount();
            Date createTime = fileRecords.getCreateTime();
            Integer userId = fileRecords.getUserId();
            String orgCode = fileRecords.getOrgCode();
            String robot = fileRecords.getRobot();
            String lineId = fileRecords.getLineId();
            String callData = fileRecords.getCallData();
            String status = fileRecords.getStatus();
            String callHour = fileRecords.getCallHour();
            Integer isClean = fileRecords.getIsClean();
            String url = fileRecords.getUrl();
            String filePath = fileRecords.getFilePath();
            FileRecordsExample example = new FileRecordsExample();
            FileRecordsExample.Criteria criteria = example.createCriteria();
            if(id != null) {
                criteria.andIdEqualTo(id);
            }
            if(batchid != null) {
                criteria.andBatchidEqualTo(batchid);
            }
            if(StrUtils.isNotEmpty(batchName)) {
                criteria.andBatchNameEqualTo(batchName);
            }
            if(StrUtils.isNotEmpty(fileName)) {
                criteria.andFileNameEqualTo(fileName);
            }
            if(successCount != null) {
                criteria.andSuccessCountEqualTo(successCount);
            }
            if(failureCount != null) {
                criteria.andFailureCountEqualTo(failureCount);
            }
            if(createTime != null) {
                criteria.andCreateTimeEqualTo(createTime);
            }
            if(userId != null) {
                criteria.andUserIdEqualTo(userId);
            }
            if(StrUtils.isNotEmpty(orgCode)) {
                criteria.andOrgCodeEqualTo(orgCode);
            }
            if(StrUtils.isNotEmpty(robot)) {
                criteria.andRobotEqualTo(robot);
            }
            if(StrUtils.isNotEmpty(lineId)) {
                criteria.andLineIdEqualTo(lineId);
            }

            if(StrUtils.isNotEmpty(callData)) {
                criteria.andCallDataEqualTo(callData);
            }

            if(StrUtils.isNotEmpty(status)) {
                criteria.andStatusEqualTo(status);
            }

            if(StrUtils.isNotEmpty(callHour)) {
                criteria.andCallHourEqualTo(callHour);
            }

            if(isClean != null) {
                criteria.andIsCleanEqualTo(isClean);
            }

            if(StrUtils.isNotEmpty(url)) {
                criteria.andUrlEqualTo(url);
            }

            if(StrUtils.isNotEmpty(filePath)) {
                criteria.andFilePathEqualTo(filePath);
            }

            return example;
        }
        return null;
    }
}
