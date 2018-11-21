package com.guiji.callcenter.fsmanager.service;

import com.guiji.fsmanager.entity.LineInfoVO;
import com.guiji.fsmanager.entity.LineXmlnfoVO;

import java.util.List;

public interface LineService {
     Boolean addLineinfos(LineInfoVO lineInfo);

     void deleteLineinfos(String lineId);

     void editLineinfos(String lineId,LineInfoVO lineInfo);

     List<LineXmlnfoVO> linexmlinfos(String lineId) ;

     List<LineXmlnfoVO> linexmlinfosAll() ;

}
