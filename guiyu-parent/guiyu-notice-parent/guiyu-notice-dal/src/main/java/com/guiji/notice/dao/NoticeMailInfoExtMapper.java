package com.guiji.notice.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NoticeMailInfoExtMapper {

    List<Map> queryMailList(@Param("userId") Long userId,@Param("isRead") Integer read,@Param("noticeType")  Integer noticeType,
                            @Param("limitStart") int limitStart, @Param("pageSize") Integer pageSize);

    int countMailList(@Param("userId") Long userId,@Param("isRead")  Integer isRead,@Param("noticeType")  Integer noticeType);

    List<Map>  selectMailInfo(@Param("id") Integer id);
}