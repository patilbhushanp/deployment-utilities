package com.sanbhu.deployment.script.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.sanbhu.deployment.script.bo.SVNFileDetail;
import com.sanbhu.deployment.script.bo.SVNLogDetail;
import com.sanbhu.deployment.script.service.SVNClientService;

@Service("svnClientService")
public class SVNClientServiceImpl implements SVNClientService {
	private static final Log logger = LogFactory.getLog("SVNClientServiceImpl");

	private SVNRepository repository = null;

	private static final String repoURL = "";
	private static final String userName = "";
	private static final String password = "";
	
	static {
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();
	}

	public SVNClientServiceImpl() {
		this(repoURL, userName, password);
	}
	
	public SVNClientServiceImpl(final String repoURL, final String userName, final String password) {
		try {
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(repoURL));
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, password);
			repository.setAuthenticationManager(authManager);
		} catch (SVNException exception) {
			logger.error("SVN Exception - " + exception.getLocalizedMessage());
		}
	}

	public List<SVNFileDetail> getSVNFileList() {
		final List<SVNFileDetail> svnFileDetailList = new ArrayList<SVNFileDetail>();
		try {
			Collection<?> entries = repository.getDir("", -1, null, (Collection<?>) null);
			Iterator<?> iterator = entries.iterator();
			while (iterator.hasNext()) {
				SVNDirEntry svnDirEntry = (SVNDirEntry) iterator.next();
				SVNFileDetail svnFileDetail = new SVNFileDetail();
				svnFileDetail.setAuthorName(svnDirEntry.getAuthor());
				svnFileDetail.setName(svnDirEntry.getName());
				svnFileDetail.setRelativePath(svnDirEntry.getRelativePath());
				svnFileDetail.setRevisionNumber(Long.valueOf(svnDirEntry.getRevision()));
				svnFileDetailList.add(svnFileDetail);
			}
		} catch (SVNException exception) {
			logger.error("SVN Exception - " + exception.getLocalizedMessage());
		}
		return svnFileDetailList;
	}

	public Long getSVNLastRevisionNumber() {
		Long revisionNumber = -1L;
		try {
			SVNDirEntry svnDirEntry = repository.info(".", -1L);
			revisionNumber = svnDirEntry.getRevision();
		} catch (SVNException exception) {
			logger.error("SVN Exception - " + exception.getLocalizedMessage());
		}
		return revisionNumber;
	}

	public List<SVNLogDetail> getSVNLogDetailList(final String showLogForURI, final Long startRevision,
			final Long endRevision) {
		List<SVNLogDetail> svnLogDetailList = new ArrayList<SVNLogDetail>();
		try {
			Collection<?> logEntries = repository.log(new String[] { showLogForURI }, null, startRevision, endRevision,
					true, true);
			Iterator<?> iterator = logEntries.iterator();
			while (iterator.hasNext()) {
				SVNLogEntry svnLogEntry = (SVNLogEntry) iterator.next();
				SVNLogDetail svnLogDetail = new SVNLogDetail();
				svnLogDetail.setMessage(svnLogEntry.getMessage());
				svnLogDetail.setRevisionNumber(svnLogEntry.getRevision());
				svnLogDetail.setAuthor(svnLogEntry.getAuthor());
				svnLogDetail.setDate(svnLogEntry.getDate().toString());
				Iterator<String> changedPathsIterator = svnLogEntry.getChangedPaths().keySet().iterator();
				while (changedPathsIterator.hasNext()) {
					String changedPath = changedPathsIterator.next();
					svnLogDetail.getChangeListPaths().add(changedPath);
				}
				svnLogDetailList.add(svnLogDetail);
			}
		} catch (SVNException exception) {
			logger.error("SVN Exception - " + exception.getLocalizedMessage());
		}
		return svnLogDetailList;
	}

	public List<SVNLogDetail> getSVNLogDetailList(final String showLogForURI, final String searchText,
			final Long startRevision, final Long endRevision) {
		List<SVNLogDetail> svnLogDetailList = new ArrayList<SVNLogDetail>();
		try {
			Collection<?> logEntries = repository.log(new String[] { showLogForURI }, null, startRevision, endRevision,
					true, true);
			Iterator<?> iterator = logEntries.iterator();
			while (iterator.hasNext()) {
				SVNLogEntry svnLogEntry = (SVNLogEntry) iterator.next();
				if (svnLogEntry.getMessage().trim().toUpperCase().contains(searchText.toUpperCase())) {
					SVNLogDetail svnLogDetail = new SVNLogDetail();
					svnLogDetail.setMessage(svnLogEntry.getMessage());
					svnLogDetail.setRevisionNumber(svnLogEntry.getRevision());
					svnLogDetail.setAuthor(svnLogEntry.getAuthor());
					svnLogDetail.setDate(svnLogEntry.getDate().toString());
					Iterator<String> changedPathsIterator = svnLogEntry.getChangedPaths().keySet().iterator();
					while (changedPathsIterator.hasNext()) {
						String changedPath = changedPathsIterator.next();
						svnLogDetail.getChangeListPaths().add(changedPath);
					}
					svnLogDetailList.add(svnLogDetail);
				}
			}
		} catch (SVNException exception) {
			logger.error("SVN Exception - " + exception.getLocalizedMessage());
		}
		return svnLogDetailList;
	}
}
