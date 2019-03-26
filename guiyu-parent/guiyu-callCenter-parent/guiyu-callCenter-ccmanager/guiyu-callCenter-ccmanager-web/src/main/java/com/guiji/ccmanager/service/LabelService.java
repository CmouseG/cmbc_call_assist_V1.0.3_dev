package com.guiji.ccmanager.service;

import java.util.List;

public interface LabelService {
    List<String> getAllLabelOneMonth(String orgCode,Long userId,Integer authLevel);
}
