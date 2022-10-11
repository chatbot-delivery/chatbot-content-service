package com.chatbot.contentservice;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;

@Repository
public interface DialogRepository extends CosmosRepository<Dialog, String> {
	List<Dialog> findByLanguage(String language);
	
	List<Dialog> findByLanguageAndDialogId(String language, String dialogId);
}