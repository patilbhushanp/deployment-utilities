package com.sanbhu.deployment.script.service;

import java.util.List;

import com.sanbhu.deployment.script.bo.Artifact;
import com.sanbhu.deployment.script.bo.Patch;

public interface PatchAnalyzerService {
	List<Artifact> analyzePatchArtifact(List<String> modules);
	
	List<Patch> analyzeDBPatch(List<String> modules);
}
