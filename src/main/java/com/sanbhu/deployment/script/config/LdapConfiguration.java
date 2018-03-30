package com.sanbhu.deployment.script.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import com.sanbhu.deployment.script.service.LdapService;
import com.sanbhu.deployment.script.service.impl.LdapServiceImpl;

@Configuration
public class LdapConfiguration {
	@Value("${ldap.url}")
	private String ldapUrl = "ldap://54.154.65.69:389";
	
	@Value("${ldap.base}")
	private String ldapBase = "dc=example,dc=org";
	
	
	@Value("${ldap.userDn}")
	private String ldapUserDb = "daniel@example";
	
	
	@Value("${ldap.userPassword}")
	private String ldapUserPassword = "Test1234";
	
	
	@Bean
	public LdapContextSource getContextSource() throws Exception {
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl(ldapUrl);
		ldapContextSource.setBase(ldapBase);
		ldapContextSource.setUserDn(ldapUserDb);
		ldapContextSource.setPassword(ldapUserPassword);
		ldapContextSource.setPooled(true);
		return ldapContextSource;
	}

	@Bean
	public LdapTemplate getLdapTemplate() throws Exception {
		LdapTemplate ldapTemplate = new LdapTemplate(getContextSource());
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setContextSource(getContextSource());
		return ldapTemplate;
	}
	
	@Bean 
	public LdapService getLdapService() {
		LdapService ldapService = new LdapServiceImpl();
		return ldapService;
	}
}
