package com.guiji.botsentence.service.impl;

import com.google.common.collect.Lists;
import com.guiji.botsentence.dao.BotSentenceTtsBackupMapper;
import com.guiji.botsentence.dao.BotSentenceTtsParamMapper;
import com.guiji.botsentence.dao.BotSentenceTtsTaskMapper;
import com.guiji.botsentence.dao.VoliceInfoMapper;
import com.guiji.botsentence.dao.entity.*;
import com.guiji.botsentence.service.ITtsService;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.util.TtsUtil;
import com.guiji.botsentence.util.enums.TtsParamTypeEnum;
import com.guiji.botsentence.util.enums.TtsTaskParamEnum;
import com.guiji.botsentence.util.enums.TtsTaskTypeEnum;
import com.guiji.botsentence.vo.*;
import com.guiji.common.exception.CommonException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class TtsServiceImpl implements ITtsService {

    private static final Logger logger = LoggerFactory.getLogger(TtsServiceImpl.class);

    @Resource
    private VoliceInfoMapper voliceInfoMapper;

    @Resource
    private BotSentenceTtsBackupMapper botSentenceTtsBackupMapper;

    @Resource
    private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;

    @Resource
    private BotSentenceTtsParamMapper botSentenceTtsParamMapper;

    @Override
    public List<TtsVoiceVO> queryTtsVoiceInfoVOList(String processId) {

        VoliceInfoExample voiceExample = new VoliceInfoExample();
        voiceExample.setOrderByClause("crt_time desc");
        voiceExample.createCriteria()
                .andProcessIdEqualTo(processId)
                .andNeedTtsEqualTo(true);

        List<VoliceInfo> voiceInfoList = voliceInfoMapper.selectByExample(voiceExample);
        if(CollectionUtils.isEmpty(voiceInfoList)){
            return Lists.newArrayList();
        }

        List<Long> voiceIds = Lists.newArrayList();
        voiceInfoList.forEach(voiceInfo -> voiceIds.add(voiceInfo.getVoliceId()));

        BotSentenceTtsBackupExample ttsBackupExample = new BotSentenceTtsBackupExample();
        ttsBackupExample.createCriteria().andVoliceIdIn(voiceIds);

        List<BotSentenceTtsBackup> ttsBackupList = botSentenceTtsBackupMapper.selectByExample(ttsBackupExample);

        List<Long> ttsBackupVoiceIds = Lists.newArrayList();
        ttsBackupList.forEach(ttsBackup -> ttsBackupVoiceIds.add(ttsBackup.getVoliceId()));


        List<TtsVoiceVO> ttsVoiceVOList = Lists.newArrayList();
        voiceInfoList.forEach(voiceInfo -> {
            TtsVoiceVO ttsVoiceVO = new TtsVoiceVO();
            BeanUtils.copyProperties(voiceInfo, ttsVoiceVO);
            if(ttsBackupVoiceIds.contains(voiceInfo.getVoliceId())){
                ttsVoiceVO.setHasBackup(true);
            }else {
                ttsVoiceVO.setHasBackup(false);
            }

            ttsVoiceVOList.add(ttsVoiceVO);
        });

        return ttsVoiceVOList;
    }

    @Override
    public TtsVoiceDetailVO queryTtsVoiceDetailByVoiceId(Long voiceId) {
        VoliceInfo voiceInfo = voliceInfoMapper.selectByPrimaryKey(voiceId);
        if(null == voiceInfo){
            throw new CommonException("未找到对应话术！");
        }

        TtsVoiceDetailVO voiceDetail = new TtsVoiceDetailVO();
        voiceDetail.setVoliceId(voiceInfo.getVoliceId());
        voiceDetail.setTtsCompositeType(voiceInfo.getTtsCompositeType());
        voiceDetail.setContent(voiceInfo.getContent());

        //search backup
        BotSentenceTtsBackupExample ttsBackupExample = new BotSentenceTtsBackupExample();
        ttsBackupExample.createCriteria().andVoliceIdEqualTo(voiceId);

        List<BotSentenceTtsBackup> ttsBackupList = botSentenceTtsBackupMapper.selectByExample(ttsBackupExample);
        if(!CollectionUtils.isEmpty(ttsBackupList)){
            voiceDetail.setBackupId(ttsBackupList.get(0).getBackupId());
            voiceDetail.setBackupContent(ttsBackupList.get(0).getContent());
        }

        // tts task
        BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
        ttsTaskExample.createCriteria()
                .andBusiTypeEqualTo(TtsTaskTypeEnum.CALL_RECORD.getKey())// 通话录音
                .andBusiIdEqualTo(String.valueOf(voiceId));

        List<BotSentenceTtsTask> ttsTasks = botSentenceTtsTaskMapper.selectByExample(ttsTaskExample);
        if(CollectionUtils.isEmpty(ttsTasks)){
            throw new CommonException("未找到对应tts合成任务！");
        }

        List<TtsTaskVO> ttsTaskVOS = Lists.newArrayList();
        ttsTasks.forEach(ttsTask -> {
            TtsTaskVO ttsTaskVO = new TtsTaskVO();
            ttsTaskVO.setTaskId(ttsTask.getId());
            ttsTaskVO.setContent(ttsTask.getContent());
            ttsTaskVO.setIndex(TtsUtil.getIndexFromSeq(ttsTask.getSeq()));
            ttsTaskVOS.add(ttsTaskVO);
        });

        ttsTaskVOS.sort(Comparator.comparing(TtsTaskVO::getIndex)); // sort by index
//        ttsTaskVOS.sort((o1, o2) -> o1.getIndex()- o2.getIndex()); // sort by index

        voiceDetail.setTaskVOList(ttsTaskVOS);

        return voiceDetail;
    }

    @Override
    @Transactional
    public void updateTtsVoice(TtsVoiceReqVO ttsVoiceReqVO, String userId) {
        VoliceInfo voiceInfo = voliceInfoMapper.selectByPrimaryKey(ttsVoiceReqVO.getVoliceId());
        if(null == voiceInfo){
            throw new CommonException("参数不完整,音频不存在！");
        }

        voiceInfo.setTtsCompositeType(ttsVoiceReqVO.getTtsCompositeType());
        voiceInfo.setLstUpdateTime(new Date());
        voiceInfo.setLstUpdateUser(userId);
        voliceInfoMapper.updateByPrimaryKey(voiceInfo);

        //保存备用话术
        TtsBackupReqVO backupReqVO = new TtsBackupReqVO();
        backupReqVO.setVoliceId(voiceInfo.getVoliceId());
        backupReqVO.setBackupId(ttsVoiceReqVO.getBackupId());
        backupReqVO.setContent(ttsVoiceReqVO.getBackupContent());
        backupReqVO.setProcessId(voiceInfo.getProcessId());
        backupReqVO.setTemplateId(voiceInfo.getTemplateId());
        saveSingleTtsBackup(backupReqVO, userId);

        //更新tts task
        BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
        ttsTaskExample.createCriteria()
                .andBusiIdEqualTo(String.valueOf(voiceInfo.getVoliceId()))
                .andBusiTypeEqualTo(TtsTaskTypeEnum.CALL_RECORD.getKey());
        botSentenceTtsTaskMapper.deleteByExample(ttsTaskExample);

        ttsVoiceReqVO.getTaskVOList().forEach(ttsTaskVO -> {

            if(StringUtils.isBlank(ttsTaskVO.getContent())){
                throw new CommonException("分段内容不能为空！");
            }

            BotSentenceTtsTask ttsTask = new BotSentenceTtsTask();
            ttsTask.setProcessId(voiceInfo.getProcessId());
            ttsTask.setBusiId(String.valueOf(voiceInfo.getVoliceId()));
            ttsTask.setBusiType(TtsTaskTypeEnum.CALL_RECORD.getKey());
            ttsTask.setContent(ttsTaskVO.getContent());
            ttsTask.setCrtTime(new Date());
            ttsTask.setCrtUser(userId);
            ttsTask.setSeq(voiceInfo.getVoliceId() + "_" + ttsTaskVO.getIndex());
            if(BotSentenceUtil.validateContainParam(ttsTaskVO.getContent())){
                ttsTask.setIsParam(TtsTaskParamEnum.IS_PARAM.getKey());
            }else {
                ttsTask.setIsParam(TtsTaskParamEnum.NOT_PARAM.getKey());
            }

            botSentenceTtsTaskMapper.insert(ttsTask);
        });
    }

    @Override
    @Transactional
    public void saveSingleTtsBackup(TtsBackupReqVO ttsBackupReqVO, String userId) {

        if(StringUtils.isBlank(ttsBackupReqVO.getContent())){
            throw new CommonException("备用话术不能为空！");
        }

        if(BotSentenceUtil.validateContainParam(ttsBackupReqVO.getContent())) {
            throw new CommonException("备用话术不允许存在变量");
        }

        String backupId = ttsBackupReqVO.getBackupId();
        if (StringUtils.isBlank(backupId)){
            BotSentenceTtsBackup backup = new BotSentenceTtsBackup();
            backup.setProcessId(ttsBackupReqVO.getProcessId());
            backup.setTemplateId(ttsBackupReqVO.getTemplateId());
            backup.setContent(ttsBackupReqVO.getContent().replace("\n", "").trim().replace(",", "，"));//把英文逗号改成中文逗号，为了后面json格式化
            backup.setVoliceId(ttsBackupReqVO.getVoliceId());
            backup.setCrtTime(new Date());
            backup.setCrtUser(userId);
            botSentenceTtsBackupMapper.insertSelective(backup);
        }else {
            BotSentenceTtsBackup backup = botSentenceTtsBackupMapper.selectByPrimaryKey(backupId);
            if(null == backup){
                throw new CommonException("备用话术为找到！");
            }
            backup.setContent(ttsBackupReqVO.getContent().replace("\n", "").trim().replace(",", "，"));//把英文逗号改成中文逗号，为了后面json格式化
            backup.setUrl(null);
            backup.setLstUpdateTime(new Date());
            backup.setLstUpdateUser(userId);
            botSentenceTtsBackupMapper.updateByPrimaryKey(backup);

            BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
            ttsTaskExample.createCriteria().andBusiIdEqualTo(backup.getBackupId());
            botSentenceTtsTaskMapper.deleteByExample(ttsTaskExample);
        }
    }

    @Override
    public void saveTtsParam(String ttsParamKey, TtsParamTypeEnum ttsParamTypeEnum, String processId, String userId) {
        if(!BotSentenceUtil.validateContainParam(ttsParamKey) || ttsParamKey.length() != 5){
            logger.error("illegal tts param :{}", ttsParamKey);
            return;
        }

        BotSentenceTtsParamExample ttsParamExample = new BotSentenceTtsParamExample();
        ttsParamExample.createCriteria()
                .andProcessIdEqualTo(processId)
                .andParamKeyEqualTo(ttsParamKey);

        List<BotSentenceTtsParam> ttsParamList = botSentenceTtsParamMapper.selectByExample(ttsParamExample);
        if(!CollectionUtils.isEmpty(ttsParamList)){
            return;
        }

        BotSentenceTtsParam ttsParam = new BotSentenceTtsParam();
        ttsParam.setParamKey(ttsParamKey);
        ttsParam.setParamType(ttsParamTypeEnum.getKey());
        ttsParam.setProcessId(processId);
        ttsParam.setCrtUser(userId);
        ttsParam.setCrtTime(new Date());

        botSentenceTtsParamMapper.insert(ttsParam);
    }

    @Override
    public List<BotSentenceTtsParam> getSortedParams(String processId) {
        BotSentenceTtsParamExample ttsParamExample = new BotSentenceTtsParamExample();
        ttsParamExample.createCriteria()
                .andProcessIdEqualTo(processId);
        ttsParamExample.setOrderByClause("param_key asc");

        return botSentenceTtsParamMapper.selectByExample(ttsParamExample);
    }
}
