package com.sanbhu.deployment.script.service;

import java.util.List;

import com.jcraft.jsch.Session;

public interface SSHConnectorService {
	public Session connectToServer(final String serverIPAddress, final Integer port, final String userName,
			final String password);

	public boolean performLoginToServerUser(final Session session, final String userName, final String password);

	public boolean executeCommands(final Session session, final List<String> commands);

	public void disconnectToServer(final Session session);
}
