package com.chatbot.contentservice;

public class DialogText {
	
	private int dialogSeqNumber;

	private String dialogText;
	
	public int getDialogSeqNumber() {
		return dialogSeqNumber;
	}

	public void setDialogSeqNumber(int dialogSeqNumber) {
		this.dialogSeqNumber = dialogSeqNumber;
	}

	public String getDialogText() {
		return dialogText;
	}

	public void setDialogText(String dialogText) {
		this.dialogText = dialogText;
	}

	@Override
	public String toString() {
		return "DialogText [dialogSeqNumber=" + dialogSeqNumber + ", dialogText=" + dialogText + "]";
	}
	
}
