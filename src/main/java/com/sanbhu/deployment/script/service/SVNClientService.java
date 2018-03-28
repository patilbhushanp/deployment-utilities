package com.sanbhu.deployment.script.service;

import java.util.List;

import com.sanbhu.deployment.script.bo.SVNFileDetail;
import com.sanbhu.deployment.script.bo.SVNLogDetail;

public interface SVNClientService {
	public List<SVNFileDetail> getSVNFileList();

	public List<SVNLogDetail> getSVNLogDetailList(final String showLogForURI, final Long startRevision,
			final Long endRevision);
	
	public List<SVNLogDetail> getSVNLogDetailList(final String showLogForURI, final String searchText,
			final Long startRevision, final Long endRevision);
	
	public Long getSVNLastRevisionNumber();
}
