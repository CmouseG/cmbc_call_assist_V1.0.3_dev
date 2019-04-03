package com.guiji.clm.service.sip;

import com.guiji.clm.constant.ClmConstants;
import com.guiji.clm.dao.SipLineApplyMapper;
import com.guiji.clm.dao.entity.SipLineApply;
import com.guiji.clm.dao.entity.SipLineApplyExample;
import com.guiji.clm.dao.entity.SipLineApplyExample.Criteria;
import com.guiji.clm.vo.SipLineApplyQueryCondition;
import com.guiji.common.model.Page;
import com.guiji.utils.DateUtil;
import com.guiji.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V1.0
 * @Description: SIP线路申请、审批服务
 * @Author: weiyunbo
 * @date 2019年1月23日 上午10:31:31
 */
@Slf4j
@Service
public class SipLineApplyService {
    @Autowired
    SipLineApplyMapper sipLineApplyMapper;


    /**
     * 新增/更新申请审批记录
     *
     * @param sipLineApply
     * @return
     */
    @Transactional
    public SipLineApply save(SipLineApply sipLineApply) {
        if (sipLineApply != null) {
            if (sipLineApply.getId() != null) {
                //更新
                sipLineApply.setUpdateTime(DateUtil.getCurrent4Time());
                sipLineApplyMapper.updateByPrimaryKey(sipLineApply);
            } else {
                //新增
                sipLineApply.setCrtTime(DateUtil.getCurrent4Time());
                sipLineApply.setUpdateTime(DateUtil.getCurrent4Time());
                sipLineApplyMapper.insert(sipLineApply);
            }
        }
        return sipLineApply;
    }


    /**
     * 根据主键ID查询sip线路申请信息
     *
     * @param id
     * @return
     */
    public SipLineApply queryById(Integer id) {
        if (id != null) {
            return sipLineApplyMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    /**
     * 根据条件获取SIP线路申请、审批历史数据
     *
     * @param condition
     * @return
     */
    public List<SipLineApply> querySipLineApplyListByCondition(SipLineApplyQueryCondition condition) {
        SipLineApplyExample example = this.queryExample(condition);
        return sipLineApplyMapper.selectByExample(example);
    }


    /**
     * 根据条件获取SIP线路申请、审批历史数据(分页)
     *
     * @param pageNo
     * @param pageSize
     * @param condition
     * @return
     */
    public Page<SipLineApply> querySipLineApplyForPageByCondition(SipLineApplyQueryCondition condition) {
        int pageNo = condition.getPageNo();
        int pageSize = condition.getPageSize();
        Page<SipLineApply> page = new Page<SipLineApply>();
        int totalRecord = 0;
        int limitStart = (pageNo - 1) * pageSize;    //起始条数
        int limitEnd = pageSize;    //查询条数
        SipLineApplyExample example = this.queryExample(condition);
        //查询总数
        totalRecord = sipLineApplyMapper.countByExample(example);
        if (totalRecord > 0) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
            List<SipLineApply> list = sipLineApplyMapper.selectByExample(example);
            page.setRecords(list);
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(totalRecord);
        return page;
    }


    /**
     * 根据条件获取查询example
     *
     * @param condition
     * @return
     */
    private SipLineApplyExample queryExample(SipLineApplyQueryCondition condition) {
        SipLineApplyExample example = new SipLineApplyExample();
        if (condition != null) {
            Criteria criteria = example.createCriteria();
            if (condition.getAgentLineId() != null) {
                criteria.andAgentLineIdEqualTo(condition.getAgentLineId());
            }
            if (condition.getUpSipLineId() != null) {
                criteria.andUpSipLineIdEqualTo(condition.getUpSipLineId());
            }
            if (StrUtils.isNotEmpty(condition.getLineName())) {
                criteria.andLineNameLike("%" + condition.getLineName() + "%");
            }
            if (StrUtils.isNotEmpty(condition.getTemplate())) {
                criteria.andTemplatesLike("%" + condition.getTemplate() + "%");
            }
            if (condition.getOrgCode() != null) {
                criteria.andOrgCodeEqualTo(condition.getOrgCode());
            }

            if (condition.getAuthLevel() == ClmConstants.USER_DATA_AUTH_ME) {
                criteria.andApplyUserEqualTo(condition.getApplyUserId());
            } else if (condition.getAuthLevel() == ClmConstants.USER_DATA_AUTH_ORG) {
                criteria.andApplyOrgCodeEqualTo(condition.getApplyOrgCode());
            } else if (condition.getAuthLevel() == ClmConstants.USER_DATA_AUTH_ORGTREE) {
                criteria.andApplyOrgCodeLike(condition.getApplyOrgCode() + "%");
            }

            if (StrUtils.isNotEmpty(condition.getApplyDateBegin()) && StrUtils.isNotEmpty(condition.getApplyDateEnd())
                    && condition.getApplyDateBegin().equals(condition.getApplyDateEnd())) {
                //申请日期
                criteria.andApplyDateEqualTo(condition.getApplyDateBegin());
            } else {
                if (StrUtils.isNotEmpty(condition.getApplyDateBegin())) {
                    criteria.andApplyDateGreaterThanOrEqualTo(condition.getApplyDateBegin());
                }
                if (StrUtils.isNotEmpty(condition.getApplyDateEnd())) {
                    criteria.andApplyDateLessThanOrEqualTo(condition.getApplyDateEnd());
                }
            }
            if (condition.getApplyType() != null) {
                criteria.andApplyTypeEqualTo(condition.getApplyType());
            }
            if (condition.getApplyStatus() != null) {
                criteria.andApplyStatusEqualTo(condition.getApplyStatus());
            }
            if (StrUtils.isNotEmpty(condition.getApproveUser())) {
                criteria.andApproveUserEqualTo(condition.getApproveUser());
            }
            if (StrUtils.isNotEmpty(condition.getApproveDateBegin()) && StrUtils.isNotEmpty(condition.getApproveDateEnd())
                    && condition.getApproveDateBegin().equals(condition.getApproveDateEnd())) {
                //申请日期
                criteria.andApproveDateEqualTo(condition.getApproveDateBegin());
            } else {
                if (StrUtils.isNotEmpty(condition.getApproveDateBegin())) {
                    criteria.andApproveDateGreaterThanOrEqualTo(condition.getApproveDateBegin());
                }
                if (StrUtils.isNotEmpty(condition.getApproveDateEnd())) {
                    criteria.andApproveDateLessThanOrEqualTo(condition.getApproveDateEnd());
                }
            }
        }
        example.setOrderByClause(" id desc");
        return example;
    }
}
