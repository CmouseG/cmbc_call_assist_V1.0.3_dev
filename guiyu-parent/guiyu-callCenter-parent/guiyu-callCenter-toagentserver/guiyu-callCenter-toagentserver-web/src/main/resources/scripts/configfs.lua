--http://xx.x.x.x.x/afafasf
local fileUrl = argv[1]
-- autoload_configs/callcenter.conf.xml
-- directory/default/1111.xml
local filePath = argv[2]
-- reloadxml
local cmd = argv[3]
-- number range, sunch as 30000-30010
local numrange = argv[4]
-- number login password, such as 123456
local numpwd = argv[5]

api = freeswitch.API()

function log(txt, level)
	level = level or "info"
	freeswitch.consoleLog(level, argv[0] .. ":" .. txt .. "\n");
end

function file_exists(name)
   local f=io.open(name,"r")
   if f~=nil then io.close(f) return true else return false end
end

function download_save(fileUrl, filePath)
	local downcmd = "wget -O " .. fulldir .. " " .. fileUrl
	log("start to download file by cmd1 " .. downcmd);
	os.execute(downcmd);
	if not file_exists(filePath) then
		downcmd = "curl -o " .. fulldir .. " " .. fileUrl
		log("start to download file by cmd2 " .. downcmd);
		os.execute(downcmd);
	end
end

function trim(s)
	return (s:gsub("\"", ""))
end

function mysplit(inputstr, sep)
        if sep == nil then
                sep = "%s"
        end
        local t={} ; i=1
        for str in string.gmatch(inputstr, "([^"..sep.."]+)") do
                t[i] = str
                i = i + 1
        end
        return t
end

--download file and save to path
log("start to get file: " .. fileUrl);

confdir = api:executeString("global_getvar conf_dir");

fulldir = confdir .. "/" .. filePath;
download_save(fileUrl, fulldir);

-- execute command
api:executeString("reloadxml");
if cmd and cmd ~="null" then
	cmd = (string.gsub(cmd, "+", " "));
	tb = mysplit(cmd, "|")
	for k,v in pairs(tb) do
		log("start to execute command:" .. v);
		log(api:executeString(v));
	end
end



