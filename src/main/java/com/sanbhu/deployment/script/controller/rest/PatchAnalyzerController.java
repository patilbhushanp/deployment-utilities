package com.sanbhu.deployment.script.controller.rest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanbhu.deployment.script.bo.Artifact;
import com.sanbhu.deployment.script.bo.Patch;
import com.sanbhu.deployment.script.service.PatchAnalyzerService;

@RestController
public class PatchAnalyzerController {
	@Autowired
	PatchAnalyzerService patchAnalyzerService;

	//http://localhost:8080/analyzePatchArtifact?module=workbench&module=util&module=db
	@RequestMapping("/analyzePatchArtifact")
	public Map<String, List<Artifact>> analyzePatchArtifact(@RequestParam("module") List<String> moduleList) {
		Map<String, List<Artifact>> resultMap = new ConcurrentHashMap<String, List<Artifact>>();
		List<Artifact> artifactList = patchAnalyzerService.analyzePatchArtifact(moduleList);
		resultMap.put("result", artifactList);
		return resultMap;
	}
	
	//http://localhost:8080/analyzePatchDBChanges?module=workbench&module=util&module=db
	@RequestMapping("/analyzePatchDBChanges")
	public Map<String, List<Patch>> analyzePatchDBChanges(@RequestParam("module") List<String> moduleList) {
		Map<String, List<Patch>> resultMap = new ConcurrentHashMap<String, List<Patch>>();
		List<Patch> patchList = patchAnalyzerService.analyzeDBPatch(moduleList);
		resultMap.put("result", patchList);
		return resultMap;
	}
}
