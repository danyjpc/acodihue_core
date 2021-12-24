package com.peopleapps.model;


import com.peopleapps.dto.phrase.AdmPhraseDto;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "adm_phrase")
@SequenceGenerator(
        name = "admPhraseSequence",
        sequenceName = "adm_phrase_sequence",
        initialValue = 3,
        allocationSize = 1
)

@SqlResultSetMapping(
        name = "admPhraseDto",
        classes = @ConstructorResult(
                targetClass = AdmPhraseDto.class,
                columns = {
                        @ColumnResult(name = "phrase_id", type = Long.class),
                        @ColumnResult(name = "author", type = String.class),
                        @ColumnResult(name = "phrase", type = String.class)
                }
        )
)

@JsonbPropertyOrder({"phraseId", "author", "phrase"})
@Schema(name = "AdmPhrase")
public class AdmPhrase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admPhraseSequence") //JPA
    @Column(name = "phrase_id") //JPA
    @Schema(required = true, example = "1")
    private Long phraseId;

    @Column
    @Schema(required = true, example = "100")
    private String author;

    @Column
    @Schema(required = true, example = "100")
    private String phrase;


    public AdmPhrase() {
        this.author = "";
        this.phrase = "";
    }

    public AdmPhrase(Long phraseId, String author, String phrase) {
        this.phraseId = phraseId;
        this.author = author;
        this.phrase = phrase;
    }

    public Long getPhraseId() {
        return phraseId;
    }

    public void setPhraseId(Long phraseId) {
        this.phraseId = phraseId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    @Override
    public String toString() {
        return "AdmPhrase{" +
                "phraseId=" + phraseId +
                ", author='" + author + '\'' +
                ", phrase='" + phrase + '\'' +
                '}';
    }

}
