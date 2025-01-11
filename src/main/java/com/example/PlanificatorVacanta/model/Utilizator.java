/** Clasa folosita pentru a defini structura unui obiect de tip Utilizator, care are corelata
 * un tabel din baza de date, avand numele Users si care permite realizarea conexiunii cu pagina
 * principala, prin pagina de login
 * @author Bădără Denisa
 * @version 23 Decembrie 2024
 */

package com.example.PlanificatorVacanta.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class Utilizator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")

    private int userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}