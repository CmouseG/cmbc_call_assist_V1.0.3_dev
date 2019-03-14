package com.guiji.botsentence.service;

import java.util.Map;
import java.util.Set;

public interface ITradeService {

    String getIndustryFullName(String industryId);

    Map<String,String> getIndustryIdToFullNameMap(Set<String> industryIds);
}
