
package campus.u2.entrysystem.Utilities;

import java.util.Date;


public class RegisterUser {
    private String name;
    private String cedula;
    private String telefono;
    private Date employmentDate;
    private String userName;
    private String password;

    public RegisterUser() {
    }

    public RegisterUser(String name, String cedula, String telefono, Date employmentDate, String userName, String password) {
        this.name = name;
        this.cedula = cedula;
        this.telefono = telefono;
        this.employmentDate = employmentDate;
        this.userName = userName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
