#! /bin/bash
export PATH=$PATH
#########################################################################
# << Java Jat Batch >>                                                  #
#                                                                       #
# create : 20181205 Nicholas_wang                                       #
# update : 20190130 Nicholas-wang                                       #
#########################################################################
# PORT
PORTS=(18020 18092 18093 18015 18094 38001 38002 18021 18023 18024 18022 18025)
# SYSTEM MODULES
MODULES=(fsmanager ccmager calloutserver fsline toagent tnas tts)
# SYSTEM_MODULE_NAMES
MODULE_NAMES=(wechat bill linemarket notice voip tnas tts fsmanager ccmager calloutserver fsline toagent)
# jar Group
JARS=(guiyu-wechat-web-1.0-SNAPSHOT.jar guiyu-billing-web-1.0-SNAPSHOT.jar guiyu-linemarket-web-1.0-SNAPSHOT.jar guiyu-notice-web-1.0-SNAPSHOT.jar guiyu-voipgateway-web-1.0-SNAPSHOT.jar guiyu-tts-nas-web-1.0-SNAPSHOT.jar guiyu-tts-2.0-web-2.0-SNAPSHOT.jar guiyu-callCenter-fsmanager-web-1.0-SNAPSHOT.jar guiyu-callCenter-ccmanager-web-1.0-SNAPSHOT.jar guiyu-callCenter-calloutserver-web-1.0-SNAPSHOT.jar guiyu-callCenter-fsline-web-1.0-SNAPSHOT.jar guiyu-callCenter-toagentserver-web-1.0-SNAPSHOT.jar)
YMLS=(wechat-web-application.yml billing-web-application.yml linemarket-web-application.yml notice-web-application.yml voipgateway-web-application.yml tts-nas-application.yml tts-application.yml callcenter-fsmanager.yml callcenter-ccmanager.yml callcenter-calloutserver.yml callcenter-fsline.yml callcenter-toagent.yml)
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
PORT=${PORTS[$i]}
if [ "$command" == "all" ] || [ "$command" == "$MODULE" ];then
  commandOk=1
  count=0
  PID=`ps -ef |grep $(echo $JAR_NAME | awk -F/ '{print $NF}') | grep -v grep | awk '{print $2}'`
  if [ -n "$PID" ];then
echo "$MODULE---$MODULE_NAME:RUNNING,PID=$PID"
    else if [ "$JAR_NAME" == "guiyu-callCenter-calloutserver-web-1.0-SNAPSHOT.jar" ];then
    exec nohup java -Xms2048m -Xmx4096m -XX:PermSize=2048m -XX:MaxPermSize=4096m -XX:MaxNewSize=4096m -Dspring.config.location=$YML_PATH/$YML_NAME -jar $JAR_PATH/$JAR_NAME >> $LOG_PATH/"$MODULE_NAME".out &
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
  exec nohup java -Xms512m -Xmx1024m -XX:PermSize=256m -XX:MaxPermSize=512m -XX:MaxNewSize=2048m -Dspring.config.location=$YML_PATH/$YML_NAME -jar $JAR_PATH/$JAR_NAME>> $LOG_PATH/"$MODULE_NAME".out &
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
echo "Please input second cmd:|wechat|bill|linemarket|notice|voip|tnasfsmanager|ccmager|calloutserver|fsline|toagent|"
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
echo "Please input systemName :|wechat|bill|linemarket|notice|voip|tnasfsmanager|ccmager|calloutserver|fsline|toagent|"
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
