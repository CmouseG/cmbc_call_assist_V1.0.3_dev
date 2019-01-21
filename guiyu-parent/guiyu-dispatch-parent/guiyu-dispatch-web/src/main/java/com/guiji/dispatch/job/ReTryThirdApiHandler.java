package com.guiji.dispatch.job;

import java.util.List;

import org.springframework.stereotype.Component;

import com.guiji.dispatch.dao.ThirdInterfaceRecordsMapper;
import com.guiji.dispatch.dao.entity.ThirdInterfaceRecords;
import com.guiji.dispatch.dao.entity.ThirdInterfaceRecordsExample;
import com.guiji.dispatch.util.Constant;
import com.guiji.utils.HttpClientUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

@JobHandler(value = "ReTryThirdApiHandler")
@Component
public class ReTryThirdApiHandler extends IJobHandler {

	private ThirdInterfaceRecordsMapper thirdInterfaceRecordsMapper;

	@Override
	public ReturnT<String> execute(String arg0) throws Exception {
		ThirdInterfaceRecordsExample ex = new ThirdInterfaceRecordsExample();
		// 查询失败的大于0
		ex.createCriteria().andTimesGreaterThan(0).andTypeEqualTo(Constant.THIRDAPIFAILURE);
		List<ThirdInterfaceRecords> selectByExample = thirdInterfaceRecordsMapper.selectByExample(ex);
		for (ThirdInterfaceRecords record : selectByExample) {
			try {
				String result = HttpClientUtil.doPostJson(record.getUrl(), record.getParams());
				// 删除数据
				thirdInterfaceRecordsMapper.deleteByPrimaryKey(record.getId());
			} catch (Exception e) {
				// 如果有问题那么把times次数减1
				record.setTimes(record.getTimes() - 1);
				thirdInterfaceRecordsMapper.updateByPrimaryKeySelective(record);
			}
		}
		return SUCCESS;
	}

}
