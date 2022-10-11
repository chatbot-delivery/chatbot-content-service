package com.chatbot.contentservice;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;


@Container(containerName = "dialogs")
public class Dialog {
    
    @Id
    @GeneratedValue
    private String id;
    
    private String language;

    private String dialogId;
    
	private String dialogName;
	
    private List<DialogText> dialogTexts;

    public Dialog() {

    }

    public Dialog(String language, String dialogId, String dialogName, List<DialogText> dialogTexts) {
        this.language = language;
        this.dialogId = dialogId;
        this.dialogName = dialogName;
        this.dialogTexts = dialogTexts;
    }

    public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDialogName() {
		return dialogName;
	}

	public void setDialogName(String dialogName) {
		this.dialogName = dialogName;
	}

	public List<DialogText> getDialogTexts() {
		return dialogTexts;
	}

	public void setDialogTexts(List<DialogText> dialogTexts) {
		this.dialogTexts = dialogTexts;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getDialogId() {
		return dialogId;
	}

	public void setDialogId(String dialogId) {
		this.dialogId = dialogId;
	}

	@Override
	public String toString() {
		return "Dialog [id=" + id + ", language=" + language + ", dialogId=" + dialogId + ", dialogName=" + dialogName
				+ ", dialogTexts=" + dialogTexts + "]";
	}

	
   

}