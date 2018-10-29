package com.guiji.callcenter.fsmanager.service;

import com.guiji.common.model.ServerResult;
import com.guiji.fsmanager.api.LineOperApi;
import com.guiji.fsmanager.entity.LineInfo;
import com.guiji.fsmanager.entity.LineXmlnfo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LineService implements LineOperApi{

    @Override
    public ServerResult addLineinfos(LineInfo lineInfo) {
        return null;
    }

    @Override
    public ServerResult editLineinfos(String lineId, LineInfo lineInfo) {
        return null;
    }

    @Override
    public ServerResult deleteLineinfos(String lineId) {
        return null;
    }

    @Override
    public ServerResult<LineXmlnfo> linexmlinfos(String lineId) {
        return null;
    }

    @Override
    public ServerResult<LineXmlnfo> linexmlinfosAll() {
        return null;
    }
}
