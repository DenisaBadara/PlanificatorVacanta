/** Clasa folosita pentru a defini structura unui obiect de tip Vacanta, care are corelata
 * un tabel din baza de date, avand acelasi nume
 * @author Bădără Denisa
 * @version 23 Decembrie 2024
 */

package com.example.PlanificatorVacanta.model;
import com.example.PlanificatorVacanta.validare.DataSfarsitDupaDataInceput;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Vacanta")
@DataSfarsitDupaDataInceput
public class Vacanta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VacantaID")
    private Integer id;

    @Column(name = "NumeTara")
    @NotBlank(message = "Numele tarii nu poate fi gol.")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Numele tarii trebuie sa contina doar litere.")
    private String numeTara;

    @Column(name = "DataInceput")
    @NotNull(message = "Data de inceput este obligatorie.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInceput;

    @Column(name = "DataSfarsit")
    @NotNull(message = "Data de sfarsit este obligatorie.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataSfarsit;

    @Column(name = "BugetTotal")
    @NotNull(message = "Bugetul total este obligatoriu.")
    @Positive(message = "Bugetul total trebuie sa fie un numar pozitiv.")
    private Integer bugetTotal;

    @OneToMany(mappedBy = "vacanta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activitate> activitati;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setVacantaID(Integer vacantaID) {
        this.id = vacantaID;
    }

    public String getNumeTara() {
        return numeTara;
    }

    public void setNumeTara(String numeTara) {
        this.numeTara = numeTara;
    }

    public LocalDate getDataInceput() {
        return dataInceput;
    }

    public void setDataInceput(LocalDate dataInceput) {
        this.dataInceput = dataInceput;
    }

    public LocalDate getDataSfarsit() {
        return dataSfarsit;
    }

    public void setDataSfarsit(LocalDate dataSfarsit) {
        this.dataSfarsit = dataSfarsit;
    }

    public Integer getBugetTotal() {
        return bugetTotal;
    }

    public void setBugetTotal(Integer bugetTotal) {
        this.bugetTotal = bugetTotal;
    }

}
