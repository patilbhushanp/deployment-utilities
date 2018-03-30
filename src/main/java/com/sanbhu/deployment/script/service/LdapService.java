package com.sanbhu.deployment.script.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sanbhu.deployment.script.bo.LdapPerson;

@Service
public interface LdapService {
	List<LdapPerson> getAllPerson();

	LdapPerson getPersonByUserName(String userName);

	LdapPerson getPersonByEmailAddress(String emailAddress);

	LdapPerson updatePersonByUserName(String userName);
	
	void resetPassword(String username, String newPassword);

	void changePassword(String username, String oldPassword, String newPassword);
}
