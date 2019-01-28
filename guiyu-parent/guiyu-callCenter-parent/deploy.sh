#!/usr/bin/env bash
scp /Users/toolwiz.com/Documents/code/git/si-talk/guiyu-parent/guiyu-callCenter-parent/guiyu-callCenter-toagentserver/guiyu-callCenter-toagentserver-web/target/guiyu-callCenter-toagentserver-web-1.0-SNAPSHOT.jar root@192.168.1.78:/home/apps/callcenter/toagentserver/

echo "start to chmod"
ssh bs1 "chmod a+x /home/apps/callcenter/toagentserver/*.jar"
echo "over"
