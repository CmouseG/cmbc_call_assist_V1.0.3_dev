function log(txt, level)
        level = level or "debug"
        freeswitch.consoleLog(level, argv[0] .. ":" .. txt .. "\n");
end

function file_exists(name)
   local f=io.open(name,"r")
   if f~=nil then io.close(f) return true else return false end
end

function generatenum(uspath,num)
     local fullFilePath = uspath .. "/" .. num .. ".xml";
     if file_exists(fullFilePath) then
	log("directory file:" .. fullFilePath .. " already exist, skip");
	return;
     end
     local wc=io.open(fullFilePath,"w");
     wc:write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
     wc:write("<include>\n");
     wc:write(" <user id=\"" .. num .. "\" mailbox=\"" .. num .. "\">\n");
     wc:write("   <params>\n");
     wc:write("      <param name=\"password\" value=\"" .. num .. "\"/>\n");
     wc:write("      <param name=\"vm-password\" value=\"" .. num .. "\"/>\n");
     wc:write("   </params>\n");
     wc:write("   <variables>\n");
     wc:write("      <variable name=\"toll_allow\" value=\"domestic,international,local\"/>\n");
     wc:write("      <variable name=\"accountcode\" value=\"" .. num .. "\"/>\n");
     wc:write("      <variable name=\"user_context\" value=\"default\"/>\n");
     wc:write("      <variable name=\"effective_caller_id_name\" value=\"Extension " .. num .. "\"/>\n");
     wc:write("      <variable name=\"effective_caller_id_number\" value=\"" .. num .. "\"/>\n");
     wc:write("      <variable name=\"outbound_caller_id_name\" value=\"$${outbound_caller_name}\"/>\n");
     wc:write("      <variable name=\"outbound_caller_id_number\" value=\"$${outbound_caller_id}\"/>\n");
     wc:write("      <variable name=\"callgroup\" value=\"" .. num .. "\"/>\n");
     wc:write("   </variables>\n");
     wc:write(" </user>\n");
     wc:write("</include>\n");
     wc:close();
end


function trim(s)
  return (string.gsub(s, "^%s*(.-)%s*$", "%1"))
end

function create(startIndex, endIndex, filePath)
	for i=startIndex,endIndex do
		local str = tostring(i)
		if str:sub(-2) == ".0" then
			str = str:sub(1,-3)
		end
		generatenum(filePath, str)
	end
end

startIndex = argv[1];
endIndex = argv[2];

api = freeswitch.API()
confdir = api:executeString("global_getvar conf_dir");
local fsPath = confdir .."/directory/default";         
create(startIndex, endIndex, fsPath);

api:executeString("reloadxml");

log("create number end")
