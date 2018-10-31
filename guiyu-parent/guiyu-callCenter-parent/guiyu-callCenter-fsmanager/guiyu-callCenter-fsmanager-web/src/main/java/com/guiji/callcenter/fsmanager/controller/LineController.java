package com.guiji.callcenter.fsmanager.controller;

import com.guiji.callcenter.fsmanager.config.FileDirConfig;
import com.guiji.callcenter.fsmanager.service.LineService;
import com.guiji.common.result.Result;
import com.guiji.fsmanager.api.LineOperApi;
import com.guiji.fsmanager.entity.LineInfo;
import com.guiji.fsmanager.entity.LineXmlnfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LineController implements LineOperApi{
    @Autowired
    LineService lineService;
    @Autowired
    FileDirConfig fileDirConfig;
    @Override
    public Result.ReturnData addLineinfos(LineInfo lineInfo) {
        try {
           if(!lineService.addLineinfos(fileDirConfig.getLineDir(),lineInfo)){
               return  Result.error("0000重名");
           }
        } catch (Exception e) {
            e.printStackTrace();
           // lineManager.
            //回滚，删除刚才创建的文件
          return  Result.error("0000异常");
        }

        //通知fsagent

        return Result.ok();
    }

    @Override
    public Result.ReturnData editLineinfos(String lineId, LineInfo lineInfo) {
        try {
           lineService.editLineinfos(fileDirConfig.getLineDir(),lineId,lineInfo);
        } catch (Exception e) {
            e.printStackTrace();
            // lineManager.
            //回滚，删除刚才创建的文件
            return  Result.error("0000异常");
        }
        return Result.ok();
    }

    @Override
    public Result.ReturnData deleteLineinfos(String lineId) {
        lineService.deleteLineinfos(fileDirConfig.getLineDir(),lineId);
        //通知fsagent
        return Result.ok();
    }

    @Override
    public Result.ReturnData linexmlinfos(String lineId) {
        List<LineXmlnfo> list =  lineService.linexmlinfos(fileDirConfig.getLineDir(),lineId);

//        List<LineXmlnfo> list = new ArrayList<LineXmlnfo>();
//        LineXmlnfo lineXmlnfo = new LineXmlnfo();
//        lineXmlnfo.setConfigType("dialplan");
//        lineXmlnfo.setFileName("01_jingdong.xml");
//        lineXmlnfo.setFileData("PGluY2x1ZGU+CiAgICAgICAgPGV4dGVuc2lvbiBuYW1lPSJqaW5nZG9uZ19FeHRlbnNpb24iPgogICAgICAgICAgICA8Y29uZGl0aW9uIGZpZWxkPSJjYWxsZXJfaWRfbmFtZSIgZXhwcmVzc2lvbj0iXmppbmdkb25nXGR7MCwyfSQiPgogICAgICAgICAgICAgICAgPGFjdGlvbiBhcHBsaWNhdGlvbj0ic2V0IiBkYXRhPSJyaW5nYmFjaz0ke3VzLXJpbmd9Ii8+CiAgICAgICAgICAgICAgICA8YWN0aW9uIGFwcGxpY2F0aW9uPSJleHBvcnQiIGRhdGE9InNpcF9jaWRfdHlwZT1ub25lIi8+CiAgICAgICAgICAgICAgICA8YWN0aW9uIGFwcGxpY2F0aW9uPSJicmlkZ2UiIGRhdGE9InNvZmlhL2dhdGV3YXkvZ3dfamluZ2RvbmcvJHtkZXN0aW5hdGlvbl9udW1iZXJ9Ii8+CiAgICAgICAgICAgIDwvY29uZGl0aW9uPgogICAgICAgIDwvZXh0ZW5zaW9uPgo8L2luY2x1ZGU+");
//        list.add(lineXmlnfo);
//        LineXmlnfo lineXmlnfoGw = new LineXmlnfo();
//        lineXmlnfoGw.setConfigType("gateway");
//        lineXmlnfoGw.setFileName("gw_jingdong.xml");
//        lineXmlnfoGw.setFileData("dGhpcyBpcyBhIGV4YW1wbGU8aW5jbHVkZT4KICAgICAgICA8Z2F0ZXdheSBuYW1lPSJnd19qaW5nZG9uZyI+CiAgICAgICAgICAgICAgICA8cGFyYW0gbmFtZT0idXNlcm5hbWUiIHZhbHVlPSJ1c2VyIi8+CiAgICAgICAgICAgICAgICA8cGFyYW0gbmFtZT0icGFzc3dvcmQiIHZhbHVlPSJwd2QiLz4KICAgICAgICAgICAgICAgIDxwYXJhbSBuYW1lPSJyZWFsbSIgdmFsdWU9IjM5LjEwOC4yNDcuMjQwOjY4NzEiLz4KICAgICAgICAgICAgICAgIDxwYXJhbSBuYW1lPSJmcm9tLWRvbWFpbiIgdmFsdWU9IjM5LjEwOC4yNDcuMjQwOjY4NzEiLz4KICAgICAgICAgICAgICAgIDxwYXJhbSBuYW1lPSJleHBpcmUtc2Vjb25kcyIgdmFsdWU9IjMwMCIvPgogICAgICAgICAgICAgICAgPHBhcmFtIG5hbWU9InJlZ2lzdGVyIiB2YWx1ZT0iZmFsc2UiLz4KICAgICAgICA8L2dhdGV3YXk+CjwvaW5jbHVkZT4=");
//        list.add(lineXmlnfoGw);
        return Result.ok(list);
    }

    @Override
    public Result.ReturnData linexmlinfosAll() {
        List<LineXmlnfo> list =  lineService.linexmlinfosAll(fileDirConfig.getLineDir());
//        List<LineXmlnfo> list = new ArrayList<LineXmlnfo>();
//        LineXmlnfo lineXmlnfo = new LineXmlnfo();
//        lineXmlnfo.setConfigType("dialplan");
//        lineXmlnfo.setFileName("01_jingdong.xml");
//        lineXmlnfo.setFileData("PGluY2x1ZGU+CiAgICAgICAgPGV4dGVuc2lvbiBuYW1lPSJqaW5nZG9uZ19FeHRlbnNpb24iPgogICAgICAgICAgICA8Y29uZGl0aW9uIGZpZWxkPSJjYWxsZXJfaWRfbmFtZSIgZXhwcmVzc2lvbj0iXmppbmdkb25nXGR7MCwyfSQiPgogICAgICAgICAgICAgICAgPGFjdGlvbiBhcHBsaWNhdGlvbj0ic2V0IiBkYXRhPSJyaW5nYmFjaz0ke3VzLXJpbmd9Ii8+CiAgICAgICAgICAgICAgICA8YWN0aW9uIGFwcGxpY2F0aW9uPSJleHBvcnQiIGRhdGE9InNpcF9jaWRfdHlwZT1ub25lIi8+CiAgICAgICAgICAgICAgICA8YWN0aW9uIGFwcGxpY2F0aW9uPSJicmlkZ2UiIGRhdGE9InNvZmlhL2dhdGV3YXkvZ3dfamluZ2RvbmcvJHtkZXN0aW5hdGlvbl9udW1iZXJ9Ii8+CiAgICAgICAgICAgIDwvY29uZGl0aW9uPgogICAgICAgIDwvZXh0ZW5zaW9uPgo8L2luY2x1ZGU+");
//        list.add(lineXmlnfo);
//        LineXmlnfo lineXmlnfoGw = new LineXmlnfo();
//        lineXmlnfoGw.setConfigType("gateway");
//        lineXmlnfoGw.setFileName("gw_jingdong.xml");
//        lineXmlnfoGw.setFileData("dGhpcyBpcyBhIGV4YW1wbGU8aW5jbHVkZT4KICAgICAgICA8Z2F0ZXdheSBuYW1lPSJnd19qaW5nZG9uZyI+CiAgICAgICAgICAgICAgICA8cGFyYW0gbmFtZT0idXNlcm5hbWUiIHZhbHVlPSJ1c2VyIi8+CiAgICAgICAgICAgICAgICA8cGFyYW0gbmFtZT0icGFzc3dvcmQiIHZhbHVlPSJwd2QiLz4KICAgICAgICAgICAgICAgIDxwYXJhbSBuYW1lPSJyZWFsbSIgdmFsdWU9IjM5LjEwOC4yNDcuMjQwOjY4NzEiLz4KICAgICAgICAgICAgICAgIDxwYXJhbSBuYW1lPSJmcm9tLWRvbWFpbiIgdmFsdWU9IjM5LjEwOC4yNDcuMjQwOjY4NzEiLz4KICAgICAgICAgICAgICAgIDxwYXJhbSBuYW1lPSJleHBpcmUtc2Vjb25kcyIgdmFsdWU9IjMwMCIvPgogICAgICAgICAgICAgICAgPHBhcmFtIG5hbWU9InJlZ2lzdGVyIiB2YWx1ZT0iZmFsc2UiLz4KICAgICAgICA8L2dhdGV3YXk+CjwvaW5jbHVkZT4=");
//        list.add(lineXmlnfoGw);
        return Result.ok(list);
    }
}
