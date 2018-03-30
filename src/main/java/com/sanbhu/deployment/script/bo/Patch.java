package com.sanbhu.deployment.script.bo;

import java.util.ArrayList;
import java.util.List;

public class Patch {
	private String patchXmlFileName = "";
	private final List<PatchDetail> patchDetailList = new ArrayList<PatchDetail>();
	private String patchXmlFileSourcePath;
	private String patchXmlFileDestinationPath;

	public String getPatchXmlFileName() {
		return patchXmlFileName;
	}

	public void setPatchXmlFileName(String patchXmlFileName) {
		this.patchXmlFileName = patchXmlFileName;
	}

	public List<PatchDetail> getPatchDetailList() {
		return patchDetailList;
	}

	public String getPatchXmlFileSourcePath() {
		return patchXmlFileSourcePath;
	}

	public void setPatchXmlFileSourcePath(String patchXmlFileSourcePath) {
		this.patchXmlFileSourcePath = patchXmlFileSourcePath;
	}

	public String getPatchXmlFileDestinationPath() {
		return patchXmlFileDestinationPath;
	}

	public void setPatchXmlFileDestinationPath(String patchXmlFileDestinationPath) {
		this.patchXmlFileDestinationPath = patchXmlFileDestinationPath;
	}

	@Override
	public String toString() {
		return "patchXmlFileName = " + this.getPatchXmlFileName() + ", patchXmlFileSourcePath = "
				+ this.getPatchXmlFileSourcePath() + ", patchXmlFileDestinationPath = "
				+ this.getPatchXmlFileDestinationPath() + ", " + this.getPatchDetailList();
	}
}
