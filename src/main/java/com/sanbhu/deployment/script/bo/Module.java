package com.sanbhu.deployment.script.bo;

import java.util.ArrayList;
import java.util.List;

public class Module {
	private final List<Artifact> artifactList = new ArrayList<Artifact>();
	private  final List<Patch> patchList = new ArrayList<Patch>();
	private String name;
	
	public List<Artifact> getArtifactList() {
		return artifactList;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	
	public List<Patch> getPatches() {
		return patchList;
	}

	@Override
	public String toString() {
		return "ModuleName = " + this.name + ", " + this.getArtifactList() + ", " + this.getPatches();
	}
}
