package com.guiji.nas.constants;

/**
 * Created by ty on 2018/11/1.
 */
public class NasErrorConstant {
    public static final String NAS_QUERY_NULL = "0700001";//文件查询请求信息附件ID、业务ID不能都为空
    public static final String NAS_UPLOAD_ERROR = "0700002";//文件上传失败
    public static final String NAS_DOWNLOAD_ERROR = "0700003";//文件下载失败
    public static final String NAS_UPLOAD_FILE_NULL = "0700004";//文件下载失败
    public static final String NAS_UPLOAD_FILESIZE_NOTNULL = "0700005";//文件大小不能为0
}
