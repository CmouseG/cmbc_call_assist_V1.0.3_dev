# -*- coding:utf-8 -*-
"""
硅语java版本，话术部署
20190325
"""
import json
import logging.handlers
import os
import subprocess
import sys
from datetime import datetime

dt = datetime.now().strftime("%Y%m%d%H%M%S%f")
log_dir = '/tmp/java_up_cfgs/'
log_name = 'up_cfgs.log'


def create_logger():
    logging_msg_format = '[%(asctime)s] [%(filename)s[line:%(lineno)d]] [%(levelname)s] [%(message)s]'
    logging_date_format = '%Y-%m-%d %H:%M:%S'
    logging.basicConfig(level=logging.INFO, format=logging_msg_format, datefmt=logging_date_format)
    if not os.path.exists(log_dir):
        os.makedirs(log_dir)
    log_path = os.path.join(log_dir, log_name)
    file_handler = logging.handlers.WatchedFileHandler(log_path)
    file_handler.setFormatter(logging.Formatter(logging_msg_format))
    logger_h = logging.getLogger()
    logger_h.addHandler(file_handler)
    return logger_h


up_log = create_logger()


def execute_cmd(shell_cmd):
    try:
        rst = subprocess.call(shell_cmd, shell=True)
        up_log.info('{} ~~~ {}'.format(shell_cmd, rst))
        return rst
    except Exception as e:
        up_log.warning('{} !!! {}'.format(shell_cmd, e.message))
        return -1


def move_cfgs(cfgs_name, dir_name):
    move("cfgs", cfgs_name, "_en", dir_name)


def move_wav(wav_name, dir_name):
    move("wav", wav_name, "_rec", dir_name)
    move("wav", wav_name, "_tts", dir_name)


def move(type_d, cfgs_name, postfix, dir_name):
    cmd_cp = "cp -r {}/{}{} /home/sellbot/dist/app/{}/{}{}" \
        .format(dir_name, cfgs_name, postfix, type_d, cfgs_name, postfix)
    up_log.info(cmd_cp)
    print("/home/sellbot/dist/app/{}/{}{}".format(cfgs_name, cfgs_name, postfix))
    if os.path.exists("/home/sellbot/dist/app/{}/{}{}".format(type_d, cfgs_name, postfix)):
        cmd_bak = "mv /home/sellbot/dist/app/{}/{}{} /home/sellbot/dist/app/{}/{}{}_{}" \
            .format(type_d, cfgs_name, postfix, type_d, cfgs_name, postfix, dt)
        execute_cmd(cmd_bak)
    execute_cmd(cmd_cp)


def append_cfgs(cfgs):
    append("cfgs", cfgs)
    append("tts", cfgs)


def append(type_d, cfgs):
    json_path = "/home/sellbot/dist/app/{}/tts_need_config.json".format(type_d)
    if os.path.exists(json_path):
        try:
            config_json = json.load(open(json_path))
            template_need_tts = config_json.get("template_need_tts", [])
            if cfgs not in template_need_tts:
                template_need_tts.append("{}".format(cfgs))
            if cfgs + "_en" not in template_need_tts:
                template_need_tts.append("{}_en".format(cfgs))
            json.dump(config_json, open(json_path, "w"), ensure_ascii=False)
            up_log.info('{} ~~~ {}'.format(json_path, 'update'))
        except Exception as e:
            up_log.warning('{} !!! {}'.format(json_path, e.message))
            return -1


def main(c_url, c_dir):
    # 准备工作
    if not os.path.isdir(c_dir):
        os.makedirs(c_dir)

    zip_name = os.path.basename(c_url)

    work_dir = os.path.join(c_dir, 'up_{}'.format(dt))
    if os.path.exists(work_dir):
        execute_cmd("rm -rf {}".format(work_dir))
    os.makedirs(work_dir)

    # 切换工作目录
    os.chdir(work_dir)
    # 下载文件
    target_path = os.path.join(work_dir, zip_name)

    execute_cmd("wget {}".format(c_url))
    if not os.path.isfile(target_path):
        up_log.info("下载失败")
        write_result("2")
        return
    # 解压目标文件
    out = execute_cmd("unzip -O UTF-8 {}".format(target_path))
    if out != 0:
        up_log.info("解压目标文件夹失败，检查拉下来的文件是否和传的参数相同")
        write_result("1")
        return
    # 部署文件
    rec_dir = ''
    for item in os.listdir(work_dir):
        if item.endswith('_rec'):
            rec_dir = item
            break
    if not rec_dir:
        up_log.info("模版包有问题")
        write_result("3")
        return
    name = rec_dir[:len(rec_dir) - 4]
    up_log.info("开始执行上传模板过程")
    move_cfgs(name, work_dir)
    up_log.info("cfgs里的模板已经放置完成")
    move_wav(name, work_dir)
    up_log.info("wav里的模板已经放置完成")
    append_cfgs(name)
    up_log.info("tts_need_config.json已经添加完成")
    up_log.info("上传模板完成")
    if os.path.exists(work_dir):
        execute_cmd("rm -rf {}".format(work_dir))
    write_result("0")


def write_result(result):
    print(result)


if __name__ == '__main__':

    download_dir = '/home/apps/config/cfgs_tmp'
    download_url = sys.argv[1]
    if len(sys.argv) >= 3:
        download_dir = sys.argv[2]
    main(download_url, download_dir)
