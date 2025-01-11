/** Clasa folosita pentru a defini structura unui obiect de tip Activitate, care are corelata
 * un tabel din baza de date, avand acelasi nume
 * @author Bădără Denisa
 * @version 23 Decembrie 2024
 */

package com.example.PlanificatorVacanta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "Activitate")
public class Activitate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int activitateID;

    @ManyToOne
    @JoinColumn(name = "VacantaID", nullable = false)
    private Vacanta vacanta;

    @Column(name = "Nume")
    @NotBlank(message = "Numele activitatii nu poate fi gol.")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Numele activitatii trebuie sa conțină doar litere.")
    private String nume;

    @Column(name = "Stare")
    private String stare;

    @Column(name = "Cost")
    @NotNull(message = "Costul este obligatoriu.")
    @Positive(message = "Costul trebuie sa fie un numar pozitiv.")
    private double cost;

    @Column(name = "Descriere")
    private String descriere;

    public int getActivitateID() {
        return activitateID;
    }

    public void setActivitateID(int activitateID) {
        this.activitateID = activitateID;
    }

    public Vacanta getVacanta() {
        return vacanta;
    }

    public void setVacanta(Vacanta vacanta) {
        this.vacanta = vacanta;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getStare() {
        return stare;
    }

    public void setStare(String stare) {
        this.stare = stare;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
}
