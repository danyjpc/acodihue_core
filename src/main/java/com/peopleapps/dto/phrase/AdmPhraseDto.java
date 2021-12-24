package com.peopleapps.dto.phrase;

public class AdmPhraseDto {

    public final Long phrase_id;
    public final String author;
    public final String phrase;

    public AdmPhraseDto(Long phrase_id, String author, String phrase) {
        this.phrase_id = phrase_id;
        this.author = author;
        this.phrase = phrase;
    }


}
