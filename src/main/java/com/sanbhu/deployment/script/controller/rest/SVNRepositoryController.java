package com.sanbhu.deployment.script.controller.rest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanbhu.deployment.script.bo.SVNFileDetail;
import com.sanbhu.deployment.script.bo.SVNLogDetail;
import com.sanbhu.deployment.script.controller.BaseRestController;
import com.sanbhu.deployment.script.service.SVNClientService;

@RestController
public class SVNRepositoryController extends BaseRestController {

	@Autowired
	SVNClientService svnClientService;

	@RequestMapping("/getSVNRepositoryDetails")
	public Map<String, List<SVNFileDetail>> getSVNRepositoryDetails() {
		Map<String, List<SVNFileDetail>> resultMap = new ConcurrentHashMap<String, List<SVNFileDetail>>();
		List<SVNFileDetail> svnFileDetailList = svnClientService.getSVNFileList();
		resultMap.put("data", svnFileDetailList);
		return resultMap;
	}

	@RequestMapping("/getSVNLogDetail")
	public Map<String, List<SVNLogDetail>> getSVNLogDetail(@RequestParam("showLogForURI") String showLogForURI,
			@RequestParam("startRevision") Long startRevision, @RequestParam("endRevision") Long endRevision) {
		Map<String, List<SVNLogDetail>> resultMap = new ConcurrentHashMap<String, List<SVNLogDetail>>();
		List<SVNLogDetail> svnLogDetailList = svnClientService.getSVNLogDetailList(showLogForURI, startRevision,
				endRevision);
		resultMap.put("data", svnLogDetailList);
		return resultMap;
	}

	@RequestMapping("/getSVNLogDetailWithSearchText")
	public Map<String, List<SVNLogDetail>> getSVNLogDetailWithSearchText(
			@RequestParam("showLogForURI") String showLogForURI, @RequestParam("searchText") String searchText,
			@RequestParam("startRevision") Long startRevision, @RequestParam("endRevision") Long endRevision) {
		Map<String, List<SVNLogDetail>> resultMap = new ConcurrentHashMap<String, List<SVNLogDetail>>();
		List<SVNLogDetail> svnLogDetailList = svnClientService.getSVNLogDetailList(showLogForURI, searchText,
				startRevision, endRevision);
		resultMap.put("data", svnLogDetailList);
		return resultMap;
	}

	@RequestMapping("/getSVNRepositoryLastRevision")
	public Long getSVNRepositoryLastRevision() {
		Long lastRevisionNumber = svnClientService.getSVNLastRevisionNumber();
		return lastRevisionNumber;
	}
}