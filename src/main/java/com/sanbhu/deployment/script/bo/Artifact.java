package com.sanbhu.deployment.script.bo;

public class Artifact {
	private String sourcePath;
	private String destinationPath;
	private boolean isRequired;

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getDestinationPath() {
		return destinationPath;
	}

	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	@Override
	public int hashCode() {
		return this.sourcePath.length();
	}

	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object instanceof Artifact) {
			Artifact otherArtifact = (Artifact) object;
			if (this.getSourcePath().equalsIgnoreCase(otherArtifact.getSourcePath())) {
				result = true;
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		return "isRequired=" + this.isRequired + ",SourcePath=" + this.getSourcePath() + ",DestinationPath=" + this.getDestinationPath();
	}
}
