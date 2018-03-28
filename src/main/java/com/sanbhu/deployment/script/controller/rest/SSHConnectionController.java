package com.sanbhu.deployment.script.controller.rest;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jcraft.jsch.Session;
import com.sanbhu.deployment.script.service.SSHConnectorService;

@RestController
public class SSHConnectionController {

	@Autowired
	SSHConnectorService sshConnectorService;

	@RequestMapping("/connectToServer")
	public Map<String, String> connectToServer(@RequestParam("serverIPAddress") String serverIPAddress,
			@RequestParam("port") int port, @RequestParam("userName") String userName, @RequestParam("password") String password, HttpServletRequest request) {
		Map<String, String> resultMap = new ConcurrentHashMap<String, String>();
		Session session = sshConnectorService.connectToServer(serverIPAddress, port, userName, password);
		request.getSession().setAttribute("sshSession", session);
		resultMap.put("result", "SUCCESS");
		return resultMap;
	}
	
	@RequestMapping("/performLoginToServerUser")
	public Map<String, Boolean> performLoginToServerUser(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpServletRequest request) {
		Map<String, Boolean> resultMap = new ConcurrentHashMap<String, Boolean>();
		boolean result = false;
		Object sessionObject = request.getSession().getAttribute("sshSession");
		if(sessionObject instanceof Session) {
			Session session = (Session)sessionObject;
			result = sshConnectorService.performLoginToServerUser(session, userName, password);
		}
		resultMap.put("result", result);	
		return resultMap;
	}
	
	@RequestMapping("/executeCommands")
	public Map<String, Boolean> executeCommand(@RequestParam String[] commandArray, HttpServletRequest request) {
		Map<String, Boolean> resultMap = new ConcurrentHashMap<String, Boolean>();
		boolean result = false;
		Object sessionObject = request.getSession().getAttribute("sshSession");
		if(sessionObject instanceof Session) {
			Session session = (Session)sessionObject;
			
			result = sshConnectorService.executeCommands(session, Arrays.asList(commandArray));
		}
		resultMap.put("result", result);	
		return resultMap;
	}
	
	@RequestMapping("/executeCommand")
	public Map<String, Boolean> executeCommand(@RequestParam String command, HttpServletRequest request) {
		Map<String, Boolean> resultMap = new ConcurrentHashMap<String, Boolean>();
		boolean result = false;
		Object sessionObject = request.getSession().getAttribute("sshSession");
		if(sessionObject instanceof Session) {
			Session session = (Session)sessionObject;
			
			result = sshConnectorService.executeCommands(session, Arrays.asList(new String[] {command}));
		}
		resultMap.put("result", result);	
		return resultMap;
	}
}
