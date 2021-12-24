package com.peopleapps.dto.credit;

public class AdmPathDocsCredit {
    public String nameDocument;
    public String pathDownload;

    public AdmPathDocsCredit(String nameDocument, String pathDownload) {
        this.nameDocument = nameDocument;
        this.pathDownload = pathDownload;
    }

    public String getNameDocument() {
        return nameDocument;
    }

    public String getPathDownload() {
        return pathDownload;
    }
}
