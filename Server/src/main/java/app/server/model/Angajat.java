package app.server.model;

import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public class Angajat extends Entity {
    protected String nume;
    protected String prenume;
    protected String username;
    protected String password;

    public Angajat() {}

    public Angajat(String nume, String prenume, String username, String password) {
        this.nume = nume;
        this.prenume = prenume;
        this.username = username;
        this.password = password;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
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

    @Override
    public String toString() {
        return "Angajat{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Angajat that = (Angajat) o;
        return Objects.equals(username, that.username);
    }
}