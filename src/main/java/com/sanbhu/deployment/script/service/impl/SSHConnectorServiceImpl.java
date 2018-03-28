package com.sanbhu.deployment.script.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.sanbhu.deployment.script.service.SSHConnectorService;

@Service("sshConnectorService")
public class SSHConnectorServiceImpl implements SSHConnectorService {
	private static final Log logger = LogFactory.getLog("SSHConnectorServiceImpl");

	public Session connectToServer(String serverIPAddress, Integer port, String userName, String password) {
		Session session = null;
		JSch jsch = new JSch();
		try {

			session = jsch.getSession(userName, serverIPAddress, port);
			logger.info("Connection Created with Server - " + session.getHost());
			session.setPassword(password);
			session.connect();
		} catch (JSchException exception) {
			logger.error("failed to connect to server - " + serverIPAddress + " with reason "
					+ exception.getLocalizedMessage(), exception);
		}
		return session;
	}

	public boolean performLoginToServerUser(final Session session, final String userName, final String password) {
		boolean result = false;
		try {
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand("sudo l - web ");
			result = true;
		} catch (JSchException exception) {
			logger.error("failed to do sudo su on server - " + session.getHost() + " with reason "
					+ exception.getLocalizedMessage(), exception);
		}
		return result;
	}
	
	public boolean executeCommands(final Session session, final List<String> commands) {
		boolean result = false;
		try {
			Channel channel = session.openChannel("exec");
			for(String command : commands) {
				((ChannelExec) channel).setCommand(command);
			}
			result = true;
		} catch (JSchException exception) {
			logger.error("failed to execute commands on server - " + session.getHost() + " with reason "
					+ exception.getLocalizedMessage(), exception);
		}
		return result;
	}
	
	public void disconnectToServer(final Session session) {
		session.disconnect();
	}

}
