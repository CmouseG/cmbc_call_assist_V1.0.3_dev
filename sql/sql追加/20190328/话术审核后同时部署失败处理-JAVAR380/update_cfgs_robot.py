# -*- coding:utf-8 -*-
import json
import logging.handlers
import os
import subprocess
import sys
from datetime import datetime
from os.path import exists

dt = datetime.now().strftime("%Y%m%d%H%M%S%f")
log_dir = '/tmp/java_up_cfgs/'
log_name = 'up_cfgs_robot.log'


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


def move(name, cfgs_name, postfix, dir_name):
    if not os.path.isdir('/home/botstence_robot_tmpl/' + cfgs_name + '_en'):
        os.makedirs('/home/botstence_robot_tmpl/' + cfgs_name + '_en')

    cmd_cp = "cp -r {}/{}{} /home/botstence_robot_tmpl/{}/{}{}" \
        .format(dir_name, cfgs_name, postfix, cfgs_name + '_en', cfgs_name, postfix)
    if exists("/home/botstence_robot_tmpl/{}/{}{}".format(cfgs_name + '_en', cfgs_name, postfix)):
        cmd_bak = "mv /home/botstence_robot_tmpl/{}/{}{} /home/botstence_robot_tmpl/{}/{}{}_{}" \
            .format(cfgs_name + '_en', cfgs_name, postfix, cfgs_name + '_en', cfgs_name, postfix, dt)
        execute_cmd(cmd_bak)
    execute_cmd(cmd_cp)


def append_cfgs(cfgs):
    append("cfgs", cfgs)
    append("tts", cfgs)


def append(name, cfgs):
    if exists("/home/sellbot/dist/app/{}/tts_need_config.json".format(name)):
        config_json = json.load(open("/home/sellbot/dist/app/{}/tts_need_config.json".format(name)))
        template_need_tts = config_json.get("template_need_tts", [])
        if cfgs not in template_need_tts:
            template_need_tts.append("{}".format(cfgs))
        if cfgs + "_en" not in template_need_tts:
            template_need_tts.append("{}_en".format(cfgs))
        json.dump(config_json, open("/home/sellbot/dist/app/{}/tts_need_config.json".format(name), "w"),
                  ensure_ascii=False)


def write_result(result):
    print (result)


def main(c_url, c_dir):
    # 准备工作
    if not os.path.isdir(c_dir):
        os.makedirs(c_dir)
    if not os.path.isdir('/home/botstence_robot_tmpl/'):
        os.makedirs('/home/botstence_robot_tmpl/')

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
    up_log.info("上传模板完成")
    if os.path.exists(work_dir):
        execute_cmd("rm -rf {}".format(work_dir))
    write_result("0")


if __name__ == "__main__":
    download_dir = '/home/apps/config/cfgs_tmp'
    if len(sys.argv) >= 3:
        download_dir = sys.argv[2]
    download_url = sys.argv[1]
    main(download_url, download_dir)
