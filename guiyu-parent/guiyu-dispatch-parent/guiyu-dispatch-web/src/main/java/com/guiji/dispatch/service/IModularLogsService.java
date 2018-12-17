package com.guiji.dispatch.service;

import java.util.List;

import com.guiji.dispatch.model.ModularLogs;

public interface IModularLogsService {
	boolean notifyLogs(ModularLogs modularLogs);
	
	boolean notifyLogsList(List<ModularLogs> modularLogsList);
}
