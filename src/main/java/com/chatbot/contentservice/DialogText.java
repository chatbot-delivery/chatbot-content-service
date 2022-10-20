package com.chatbot.contentservice;

public class DialogText {
	
	private int dialogSeqNumber;

	private String dialogTextId;
	
	private String dialogText;

	private String dialogTextDescription;

	public int getDialogSeqNumber() {
		return dialogSeqNumber;
	}

	public void setDialogSeqNumber(int dialogSeqNumber) {
		this.dialogSeqNumber = dialogSeqNumber;
	}
	
	

	public String getDialogTextId() {
		return dialogTextId;
	}

	public void setDialogTextId(String dialogTextId) {
		this.dialogTextId = dialogTextId;
	}

	public String getDialogText() {
		return dialogText;
	}

	public void setDialogText(String dialogText) {
		this.dialogText = dialogText;
	}
	
	

	public String getDialogTextDescription() {
		return dialogTextDescription;
	}

	public void setDialogTextDescription(String dialogTextDescription) {
		this.dialogTextDescription = dialogTextDescription;
	}

	@Override
	public String toString() {
		return "DialogText [dialogSeqNumber=" + dialogSeqNumber + ", dialogTextId=" + dialogTextId + ", dialogText="
				+ dialogText + ", dialogTextDescription=" + dialogTextDescription + "]";
	}
	
}
