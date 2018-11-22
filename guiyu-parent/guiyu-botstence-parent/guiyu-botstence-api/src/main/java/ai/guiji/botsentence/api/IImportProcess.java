package ai.guiji.botsentence.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ai.guiji.botsentence.api.entity.ServerResult;

@FeignClient("guiyu-botstence-web")
public interface IImportProcess {

	@RequestMapping(value="importProcess")
	public ServerResult importProcess(@RequestParam("multipartFile")MultipartFile multipartFile,@RequestParam("templateType") String templateType,@RequestParam("userId") Long userId);
}
