package com.guiji.auth.model;

import java.io.Serializable;
import java.util.List;

public class ProductTemplatesVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	List<String> templateIds;

	public List<String> getTemplateIds()
	{
		return templateIds;
	}

	public void setTemplateIds(List<String> templateIds)
	{
		this.templateIds = templateIds;
	}
	
}
