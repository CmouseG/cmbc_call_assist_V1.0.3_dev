package com.guiji.callcenter.fsmanager.service;

import com.guiji.common.result.Result;
import com.guiji.fsmanager.entity.LineInfo;
import com.guiji.fsmanager.entity.LineXmlnfo;

import java.util.List;

public interface LineService {
    public Boolean addLineinfos(String filePath,LineInfo lineInfo) throws Exception;

    public void deleteLineinfos(String filePath,String lineId);

    public void editLineinfos(String filePath,String lineId,LineInfo lineInfo) throws Exception;

    public List<LineXmlnfo> linexmlinfos(String filepath,String lineId) ;

    public List<LineXmlnfo> linexmlinfosAll(String filepath) ;

}
