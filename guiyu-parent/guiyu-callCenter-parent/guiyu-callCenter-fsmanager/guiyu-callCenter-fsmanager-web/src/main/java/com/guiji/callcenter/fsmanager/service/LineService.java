package com.guiji.callcenter.fsmanager.service;

import com.guiji.fsmanager.entity.LineInfoVO;
import com.guiji.fsmanager.entity.LineXmlnfoVO;

import java.util.List;

public interface LineService {
    public Boolean addLineinfos(LineInfoVO lineInfo) throws Exception;

    public void deleteLineinfos(String lineId);

    public void editLineinfos(String lineId,LineInfoVO lineInfo) throws Exception;

    public List<LineXmlnfoVO> linexmlinfos(String lineId) ;

    public List<LineXmlnfoVO> linexmlinfosAll() ;

}
