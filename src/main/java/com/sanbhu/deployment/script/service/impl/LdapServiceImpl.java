package com.sanbhu.deployment.script.service.impl;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;

import com.sanbhu.deployment.script.bo.LdapPerson;
import com.sanbhu.deployment.script.service.LdapService;

public class LdapServiceImpl implements LdapService {
	
	private static final Log logger = LogFactory.getLog("LdapServiceImpl");
	
	@Autowired
	LdapTemplate ldapTemplate;

	@Value("${ldap.base}")
	private String ldapBase = "dc=example,dc=org";
	
	@Override
	public List<LdapPerson> getAllPerson() {
		LdapQuery query = query().where("objectclass").is("person");
		return getAllPerson(query);
	}

	@Override
	public LdapPerson getPersonByUserName(String userName) {
		LdapPerson ldapPerson = null;
		LdapQuery query = query().where("objectclass").is("person").and("cn").whitespaceWildcardsLike(userName);
		List<LdapPerson> ldapPersonList = getAllPerson(query);
		if (!ldapPersonList.isEmpty()) {
			ldapPerson = ldapPersonList.get(0);
		}
		return ldapPerson;
	}

	@Override
	public LdapPerson getPersonByEmailAddress(String emailAddress) {
		LdapPerson ldapPerson = null;
		LdapQuery query = query().where("objectclass").is("person").and("mail").like(emailAddress);
		List<LdapPerson> ldapPersonList = getAllPerson(query);
		if (!ldapPersonList.isEmpty()) {
			ldapPerson = ldapPersonList.get(0);
		}
		return ldapPerson;
	}

	@Override
	public LdapPerson updatePersonByUserName(String userName) {
		LdapPerson ldapPerson = getPersonByUserName(userName);
		Name domainName = buildDn(ldapPerson);
		DirContextOperations context = ldapTemplate.lookupContext(domainName);
		mapToContext(ldapPerson, context);
		ldapTemplate.modifyAttributes(context);
		return null;
	}

	@Override
	public void resetPassword(String username, String newPassword) {
		try {
			String quotedNewPassword = "\"" + newPassword + "\"";
			char unicodeNewPasswordArray[] = quotedNewPassword.toCharArray();
			byte newPasswordArray[] = new byte[unicodeNewPasswordArray.length * 2];
			for (int i = 0; i < unicodeNewPasswordArray.length; i++) {
				newPasswordArray[i * 2 + 1] = (byte) (unicodeNewPasswordArray[i] >>> 8);
				newPasswordArray[i * 2 + 0] = (byte) (unicodeNewPasswordArray[i] & 0xff);
			}
			ModificationItem[] modificationItems = new ModificationItem[1];
			modificationItems[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("UnicodePwd", newPasswordArray));
			ldapTemplate.modifyAttributes("cn=" + username + ldapBase, modificationItems);
		} catch (Exception exception) {
			logger.error("Failed to reset password : " + exception.getLocalizedMessage(), exception);
		}
	}

	@Override
	public void changePassword(String username, String oldPassword, String newPassword) {
		try {
			String quotedOldPassword = "\"" + oldPassword + "\"";
			char unicodeOldPasswordArray[] = quotedOldPassword.toCharArray();
			byte oldPasswordArray[] = new byte[unicodeOldPasswordArray.length * 2];
			for (int i = 0; i < unicodeOldPasswordArray.length; i++) {
				oldPasswordArray[i * 2 + 1] = (byte) (unicodeOldPasswordArray[i] >>> 8);
				oldPasswordArray[i * 2 + 0] = (byte) (unicodeOldPasswordArray[i] & 0xff);
			}
			
			String quotedNewPassword = "\"" + newPassword + "\"";
			char unicodeNewPasswordArray[] = quotedNewPassword.toCharArray();
			byte newPasswordArray[] = new byte[unicodeNewPasswordArray.length * 2];
			for (int i = 0; i < unicodeNewPasswordArray.length; i++) {
				newPasswordArray[i * 2 + 1] = (byte) (unicodeNewPasswordArray[i] >>> 8);
				newPasswordArray[i * 2 + 0] = (byte) (unicodeNewPasswordArray[i] & 0xff);
			}
			ModificationItem[] modificationItems = new ModificationItem[2];
			modificationItems[0] = new   ModificationItem(DirContext.REMOVE_ATTRIBUTE,  new BasicAttribute("UnicodePwd", oldPasswordArray));
			modificationItems[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE,  new BasicAttribute("UnicodePwd", newPasswordArray));
			ldapTemplate.modifyAttributes("cn=" + username + ldapBase, modificationItems);
		} catch (Exception exception) {
			logger.error("Failed to change password : " + exception.getLocalizedMessage(), exception);
		}
	}
	
	private List<LdapPerson> getAllPerson(LdapQuery query) {
		return ldapTemplate.search(query, new AttributesMapper<LdapPerson>() {
			@Override
			public LdapPerson mapFromAttributes(Attributes attributes) throws NamingException {
				LdapPerson person = new LdapPerson();
				if (attributes.get("cn") != null) {
					person.setCommonName((String) attributes.get("cn").get());
					if (attributes.get("mail") != null) {
						person.setEmailAddress((String) attributes.get("mail").get());
					}
				}
				return person;
			}
		});
	}

	private void mapToContext(LdapPerson ldapPerson, DirContextOperations context) {
		context.setAttributeValues("objectclass", new String[] { "top", "person" });
		context.setAttributeValue("cn", ldapPerson.getCommonName());
		context.setAttributeValue("sn", ldapPerson.getLastName());
		context.setAttributeValue("description", ldapPerson.getDescription());
	}

	private Name buildDn(LdapPerson ldapPerson) {
		return buildDn(ldapPerson.getCommonName(), ldapPerson.getCompany(), ldapPerson.getCountry());
	}

	private Name buildDn(String fullname, String company, String country) {
		return LdapNameBuilder.newInstance().add("c", country).add("ou", company).add("cn", fullname).build();
	}

}
