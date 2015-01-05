package com.xinlukou.mmengine;

public class TransferLink {
    public int preTransferID = -1;
    public int linkID = -1;

    public TransferLink (int thePreTransferID, int theLinkID) {
        preTransferID = thePreTransferID;
        linkID = theLinkID;
    }

    public TransferLink (TransferLink transLink) {
    	preTransferID = transLink.preTransferID;
    	linkID = transLink.linkID;
    }
}
