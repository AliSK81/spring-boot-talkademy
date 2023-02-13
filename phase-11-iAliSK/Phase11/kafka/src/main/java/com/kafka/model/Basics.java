package com.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Basics {
    @Id
    private String nconst; // (string) - alphanumeric unique identifier of the name/person
    private String primaryName; // (string)– name by which the person is most often credited
    private String birthYear; //  – in YYYY format
    private String deathYear; // – in YYYY format if applicable, else '\N'
    @Transient
    private String[] primaryProfession; // (array of strings)– the top-3 professions of the person
    @Transient
    private String[] knownForTitles;  // (array of tconsts) – titles the person is known for
}
