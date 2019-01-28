--used to send msg by verto

api=freeswitch.API();
domain=api:executeString("global_getvar domain");

local recvNum = argv[1]
local msg = argv[2]
local cmd = "chat verto|Server@FS.local|" .. recvNum .. "@" .. domain .. "|" .. msg
--freeswitch.consoleLog("debug",  argv[0] ..": start to send msg by verto, command:" .. cmd .. "\n");
api:executeString(cmd)
