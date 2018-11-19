# -*- coding:utf-8 -*-
import os
import sys
import json
import logging
import requests
from datetime import datetime
from os.path import exists

dt = datetime.now().strftime("%Y%m%d%H%M")


def move_cfgs(cfgs_name, dir_name):
    move("cfgs", cfgs_name, "_en", dir_name)


def move_wav(wav_name, dir_name):
    move("wav", wav_name, "_rec", dir_name)
    move("wav", wav_name, "_tts", dir_name)


def move(name, cfgs_name, postfix, dir_name):
    cfgs_log = create_logger()
    cfgs_log.info("/usr/local/gj_polaris_data/download/{}/{}{} /home/sellbot/dist/app/{}/{}{}".format(dir_name,
                                                                                                       cfgs_name,
                                                                                                       postfix, name,
                                                                                                       cfgs_name,
                                                                                                       postfix))
    print("/home/sellbot/dist/app/{}/{}{}".format(cfgs_name, cfgs_name, postfix))
    #if exists("/home/sellbot/dist/app/{}/{}{}".format(cfgs_name, cfgs_name, postfix)):
    if exists("/home/sellbot/dist/app/{}/{}{}".format(name, cfgs_name, postfix)):
        os.system(
            "mv /home/sellbot/dist/app/{}/{}{} /home/sellbot/dist/app/{}/{}{}_{}".format(name, cfgs_name, postfix,
                                                                                             name, cfgs_name, postfix,
                                                                                             dt))
    os.system(
        "cp -r  /usr/local/gj_polaris_data/download/{}/{}{} /home/sellbot/dist/app/{}/{}{}".format(dir_name,
                                                                                                       cfgs_name,
                                                                                                       postfix, name,
                                                                                                       cfgs_name,
                                                                                                       postfix))


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
    if exists("/home/update/tool/"):
        pass
    else:
        os.makedirs("/home/update/tool/")
    try:
        f = open("/home/update/tool/cfgs.txt", 'w')
        f.write(result)
        f.close()
    except Exception as e:
        print(e)


def create_logger(loglevel=logging.INFO):
    fmt = '%(asctime)s %(name)s %(levelname)s[%(filename)s:%(lineno)d] %(message)s'
    datefmt = '%y-%m-%d %H:%M:%S'

    logger = logging.getLogger("up_cfgs")

    logger.setLevel(loglevel)

    formatter = logging.Formatter(fmt, datefmt=datefmt)
    fh = logging.FileHandler("/tmp/up_cfgs.log", mode='w')
    fh.setLevel(loglevel)
    fh.setFormatter(formatter)
    logger.addHandler(fh)

    return logger


def main(name, dir_name):
    cfgs_log = create_logger()
    # 解压目标文件
    out = os.system("unzip -O UTF-8 /usr/local/gj_polaris_data/download/{}/{}.zip".format(dir_name, name))
    if out != 0:
        cfgs_log.info("解压目标文件夹失败，检查拉下来的文件是否和传的参数相同")
        write_result("1")
        return 1
    cfgs_log.info("开始执行上传模板过程")
    move_cfgs(name, dir_name)
    cfgs_log.info("cfgs里的模板已经放置完成")
    move_wav(name, dir_name)
    cfgs_log.info("wav里的模板已经放置完成")
    append_cfgs(name)
    cfgs_log.info("tts_need_config.json已经添加完成")
    cfgs_log.info("上传模板完成")
    os.system("rm -rf /home/sellbot/dist/app/{}".format(name))
    write_result("0")

    #sit
    github_url = "http://47.98.223.225:18081/botsentenceServer/autoDeployCallback"
    
    #prd
    #github_url = "http://47.98.254.81:18081/botsentenceServer/autoDeployCallback"
    
    data = json.dumps({'templateId': name})
    r = requests.post(github_url, data)
    cfgs_log.info("通知回调完成...")


if __name__ == "__main__":
    main(sys.argv[1], sys.argv[2])  # 传入的参数是模板的名字，不用加_en,_rec,.zip
