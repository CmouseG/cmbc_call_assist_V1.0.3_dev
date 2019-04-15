#! /bin/bash
export PATH=$PATH
#########################################################################
# <<B02 Java Jat Batch >>                                                  #
#                                                                       #
# create : 20181205 Nicholas_wang                                       #
# update : 20190228 Nicholas-wang                                       #
#########################################################################
# PORT
PORTS=(18020 18092 18093 18015 18094 38001 38002 18021 18023 18024 18022 18025 18026)
# SYSTEM MODULES
MODULES=(wechat bill linemarket notice voip tnas tts fsmanager ccmager calloutserver fsline toagent sim)
# SYSTEM_MODULE_NAMES
MODULE_NAMES=(wechat bill linemarket notice voip  tnas tts fsmanager ccmager calloutserver fsline toagent sim)
# jar Group
JARS=(guiyu-wechat-web-1.0-SNAPSHOT.jar guiyu-billing-web-1.0-SNAPSHOT.jar guiyu-linemarket-web-1.0-SNAPSHOT.jar guiyu-notice-web-1.0-SNAPSHOT.jar guiyu-voipgateway-web-1.0-SNAPSHOT.jar guiyu-tts-nas-web-1.0-SNAPSHOT.jar guiyu-tts-2.0-web-2.0-SNAPSHOT.jar guiyu-callCenter-fsmanager-web-1.0-SNAPSHOT.jar guiyu-callCenter-ccmanager-web-1.0-SNAPSHOT.jar guiyu-callCenter-calloutserver-web-1.0-SNAPSHOT.jar guiyu-callCenter-fsline-web-1.0-SNAPSHOT.jar guiyu-callCenter-toagentserver-web-1.0-SNAPSHOT.jar guiyu-callCenter-simagent-web-1.0-SNAPSHOT.jar)

YMLS=(wechat-web-application.yml billing-web-application.yml linemarket-web-application.yml notice-web-application.yml voipgateway-web-application.yml tts-nas-application.yml tts-application.yml callcenter-fsmanager.yml callcenter-ccmanager.yml callcenter-calloutserver.yml callcenter-fsline.yml callcenter-toagent.yml callcenter-simagent.yml)

LOGXMLS=(log-wechat-web-application.xml log-billing-web-application.xml log-linemarket-web-application.xml log-notice-web-application.xml log-voipgateway-web-application.xml log-tts-nas-application.xml log-tts-application.xml log-callcenter-fsmanager.xml log-callcenter-ccmanager.xml log-callcenter-calloutserver.xml log-callcenter-fsline.xml log-callcenter-toagent.xml log-callcenter-simagent.xml)
# jar PATH
JAR_PATH='/home/apps/jars'
# YML_NAME PATH
YML_PATH='/home/apps/jars/config'
# log PATH
LOG_PATH='/home/apps/jars/logs'
start() {
  local MODULE=
  local MODULE_NAME=
  local JAR_NAME=
  local YML_NAME=
  local LOGXML_NAME=
  local command="$1"
  local commandOk=0
  local count=0
  local okCount=0
  local port=0
  for((i=0;i<${#MODULES[@]};i++))
  do
MODULE=${MODULES[$i]}
MODULE_NAME=${MODULE_NAMES[$i]}
JAR_NAME=${JARS[$i]}
YML_NAME=${YMLS[$i]}
LOGXML_NAME=${LOGXMLS[$i]}
PORT=${PORTS[$i]}
if [ "$command" == "all" ] || [ "$command" == "$MODULE" ];then
  commandOk=1
  count=0
  PID=`ps -ef |grep $(echo $JAR_NAME | awk -F/ '{print $NF}') | grep -v grep | awk '{print $2}'`
  if [ -n "$PID" ];then
echo "$MODULE---$MODULE_NAME:RUNNING,PID=$PID"
    else if [ "$JAR_NAME" == "guiyu-callCenter-calloutserver-web-1.0-SNAPSHOT.jar" ] || [ "$JAR_NAME" == "guiyu-billing-web-1.0-SNAPSHOT.jar" ];then
    exec nohup java -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m -Xms2048m -Xmx4096m -Xmn1024m -Xss512k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -Dspring.config.location=$YML_PATH/$YML_NAME -Dlogging.config=$YML_PATH/$LOGXML_NAME -jar $JAR_PATH/$JAR_NAME & #>> $LOG_PATH/"$MODULE_NAME".out &
    PID=`netstat -apn | grep $PORT | awk '{print $7}' | cut -d/ -f 1`
    while [ -z "$PID" ]
      do
        if (($count == 30));then
        echo "$MODULE---$MODULE_NAME:$(expr $count \* 10)  FAILED, PLEASE CHECK YOU SYSTEM!!!"
        break
        fi
      count=$(($count+1))
      echo "$MODULE_NAME RUNNING.................."
      sleep 10s
      PID=`netstat -apn | grep $PORT | awk '{print $7}' | cut -d/ -f 1`
     done
  okCount=$(($okCount+1))
  echo "$MODULE---$MODULE_NAME:RUNING SUCCESSFUL,PID=$PID"

  else
  exec nohup java -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m -Xms1024m -Xmx2048m -Xmn1024m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -Dspring.config.location=$YML_PATH/$YML_NAME -Dlogging.config=$YML_PATH/$LOGXML_NAME -jar $JAR_PATH/$JAR_NAME & #>> $LOG_PATH/"$MODULE_NAME".out &
  PID=`netstat -apn | grep $PORT | awk '{print $7}' | cut -d/ -f 1`
  while [ -z "$PID" ]
  do
    if (($count == 30));then
  echo "$MODULE---$MODULE_NAME:$(expr $count \* 10)  FAILED, PLEASE CHECK YOU SYSTEM!!!"
  break
    fi
    count=$(($count+1))
    echo "$MODULE_NAME RUNNING.................."
    sleep 10s
    PID=`netstat -apn | grep $PORT | awk '{print $7}' | cut -d/ -f 1`
  done
  okCount=$(($okCount+1))
  echo "$MODULE---$MODULE_NAME:RUNING SUCCESSFUL,PID=$PID"
    fi
  fi
fi
  done
  if(($commandOk == 0));then
echo "Please input second cmd:|wechat|bill|linemarket|notice|voip|ccmp|tnas|tts|fsmanager|ccmager|calloutserver|fsline|toagent|sim|"
  else
echo "............RUNNING SUCCESSFUL :$okCount  services..........."
  fi
}
 
stop() {
  local MODULE=
  local MODULE_NAME=
  local JAR_NAME=
  local command="$1"
  local commandOk=0
  local okCount=0
  for((i=0;i<${#MODULES[@]};i++))
  do
MODULE=${MODULES[$i]}
MODULE_NAME=${MODULE_NAMES[$i]}
JAR_NAME=${JARS[$i]}
if [ "$command" = "all" ] || [ "$command" = "$MODULE" ];then
  commandOk=1
  PID=`ps -ef |grep $(echo $JAR_NAME | awk -F/ '{print $NF}') | grep -v grep | awk '{print $2}'`
  if [ -n "$PID" ];then
echo "$MODULE---$MODULE_NAME:Ready Finished,PID=$PID"
kill -9 $PID
PID=`ps -ef |grep $(echo $JAR_NAME | awk -F/ '{print $NF}') | grep -v grep | awk '{print $2}'`
while [ -n "$PID" ]
do
  sleep 3s
  PID=`ps -ef |grep $(echo $JAR_NAME | awk -F/ '{print $NF}') | grep -v grep | awk '{print $2}'`
done
echo "$MODULE---$MODULE_NAME: STOP SUCCESSFUL"
okCount=$(($okCount+1))
  else
echo "$MODULE---$MODULE_NAME: STOP FAILED"
  fi
fi
  done
  if (($commandOk == 0));then
echo "Please input systemName :|wechat|bill|linemarket|notice|voip|ccmp|tnas|tts|fsmanager|ccmager|calloutserver|fsline|toagent|sim|"
  else
echo "............Stopped :$okCount services..........."
  fi
}


case "$1" in
  start)
start "$2"
  ;;
  stop)
stop "$2"
  ;;
  restart)
stop "$2"

sleep 3s
start "$2"
  ;;
  *)
echo "Please input cmd :|start|stop|restart|"
exit 1
  ;;
esac

