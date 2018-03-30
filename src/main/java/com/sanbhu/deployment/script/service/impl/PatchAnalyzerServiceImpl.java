package com.sanbhu.deployment.script.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sanbhu.deployment.script.bo.Artifact;
import com.sanbhu.deployment.script.bo.Module;
import com.sanbhu.deployment.script.bo.Patch;
import com.sanbhu.deployment.script.bo.PatchDetail;
import com.sanbhu.deployment.script.service.PatchAnalyzerService;

@Service("patchAnalyzerService")
public class PatchAnalyzerServiceImpl implements PatchAnalyzerService {
	private static final Log logger = LogFactory.getLog("PatchAnalyzerServiceImpl");
	private static final String PATCH_MAPPER_FILE = "PatchMapperFile.xml";
	private static final Map<String, Module> moduleMapper = new ConcurrentHashMap<String, Module>();

	public PatchAnalyzerServiceImpl() {
		Document document = null;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File xmlFile = new File(classLoader.getResource(PATCH_MAPPER_FILE).getFile());
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(xmlFile);
			document.getDocumentElement().normalize();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		if (document != null) {
			fillPatchAnalyzerObject(document);
			logger.info("Patch Mapper Object Populated.");
			logger.info(moduleMapper);
		}
	}

	private void fillPatchAnalyzerObject(Document document) {
		NodeList moduleNodeList = document.getElementsByTagName("Module");
		for (int i = 0; i < moduleNodeList.getLength(); i++) {
			Module moduleObject = new Module();
			Node moduleNode = moduleNodeList.item(i);
			if (moduleNode.getNodeType() == Node.ELEMENT_NODE) {
				Element moduleElement = (Element) moduleNode;
				String moduleName = moduleElement.getAttribute("name");
				moduleObject.setName(moduleName);

				getArtifactDetails(moduleElement, moduleObject);

				getDBPatchDetails(moduleElement, moduleObject);

				moduleMapper.put(moduleName, moduleObject);
			}
		}
	}

	private void getArtifactDetails(final Element moduleElement, final Module moduleObject) {
		NodeList artifactNodeList = moduleElement.getElementsByTagName("artifact");
		for (int j = 0; j < artifactNodeList.getLength(); j++) {
			Node artifactNode = artifactNodeList.item(j);
			if (artifactNode.getNodeType() == Node.ELEMENT_NODE) {
				Artifact artifactObject = new Artifact();
				Element artifactElement = (Element) artifactNode;
				String required = artifactElement.getAttribute("required");
				if (required != null && !required.trim().equals("")) {
					artifactObject.setRequired(false);
				} else {
					artifactObject.setRequired(true);
				}
				NodeList sourceNodeList = artifactElement.getElementsByTagName("source");
				NodeList destinationNodeList = artifactElement.getElementsByTagName("destination");
				if (sourceNodeList.getLength() > 0 && destinationNodeList.getLength() > 0) {
					String source = sourceNodeList.item(0).getTextContent();
					String destination = destinationNodeList.item(0).getTextContent();
					artifactObject.setSourcePath(source);
					artifactObject.setDestinationPath(destination);
				}
				moduleObject.getArtifactList().add(artifactObject);
			}
		}
	}

	private void getDBPatchDetails(final Element moduleElement, final Module moduleObject) {
		NodeList dbPatchNodeList = moduleElement.getElementsByTagName("patch");
		for (int j = 0; j < dbPatchNodeList.getLength(); j++) {
			Node dbPatchNode = dbPatchNodeList.item(j);
			if (dbPatchNode.getNodeType() == Node.ELEMENT_NODE) {
				Patch patchObject = new Patch();
				Element patchElement = (Element) dbPatchNode;
				NodeList scriptNodeList = patchElement.getElementsByTagName("script");
				for (int k = 0; k < scriptNodeList.getLength(); k++) {
					Node scriptNode = scriptNodeList.item(k);
					PatchDetail patchDetail = new PatchDetail();
					String scriptName = scriptNode.getTextContent().trim();
					if (scriptName.toUpperCase().contains(".SH")) {
						patchDetail.setDbScript(false);
					} else {
						patchDetail.setDbScript(true);
					}
					patchDetail.setSource("Oversight/db/patches/" + scriptName);
					patchDetail.setDestination("Oversight/patches/" + scriptName);
					patchObject.getPatchDetailList().add(patchDetail);
				}
				NodeList patchXmlFileList = patchElement.getElementsByTagName("patchXmlFile");
				if (patchXmlFileList.getLength() > 0) {
					String patchXmlFile = patchXmlFileList.item(0).getTextContent().trim();
					patchObject.setPatchXmlFileName(patchXmlFile);
					patchObject.setPatchXmlFileSourcePath("Oversight/db/patches/" + patchXmlFile);
					patchObject.setPatchXmlFileDestinationPath("Oversight/patches/" + patchXmlFile);
				}
				moduleObject.getPatches().add(patchObject);
			}
		}
	}

	@Override
	public List<Artifact> analyzePatchArtifact(List<String> modules) {
		Set<Artifact> artifactSet = new HashSet<Artifact>();
		for (String moduleName : modules) {
			if (moduleName.toUpperCase() != "DB" && moduleMapper.containsKey(moduleName)) {
				artifactSet.addAll(moduleMapper.get(moduleName).getArtifactList());
			}
			if (moduleName.toUpperCase() != "DB" && moduleMapper.containsKey(moduleName)) {
				artifactSet.addAll(moduleMapper.get(moduleName).getArtifactList());
			}
		}
		return new ArrayList<Artifact>(artifactSet);
	}
	
	@Override
	public List<Patch> analyzeDBPatch(List<String> modules) {
		List<Patch> patchList = new ArrayList<Patch>();
		for (String moduleName : modules) {
			if (moduleMapper.containsKey(moduleName)) {
				patchList.addAll(moduleMapper.get(moduleName).getPatches());
			}
		}
		return patchList;
	}
}
