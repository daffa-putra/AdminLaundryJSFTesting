package pojo;
// Generated Sep 30, 2024 2:28:57 AM by Hibernate Tools 4.3.1

import DAO.DAOLogin;
import java.util.List;
import javax.faces.bean.ManagedBean;




/**
 * Karyawan generated by hbm2java
 */
@ManagedBean
public class Karyawan  implements java.io.Serializable {


     private Integer id;
     private String username;
     private String password;
     private String nama;

    public Karyawan() {
    }

    public Karyawan(String username, String password, String nama) {
       this.username = username;
       this.password = password;
       this.nama = nama;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNama() {
        return this.nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
public String validasiLogin() {
    DAOLogin uDao = new DAOLogin();
    List<Karyawan> us = uDao.getBy(username, password);
    if (us != null && !us.isEmpty()) {  // Cek apakah us tidak kosong
        username = us.get(0).getUsername();
        password = us.get(0).getPassword();
        nama = us.get(0).getNama();
        return "index"; // Halaman login berhasil
    }
    return "error";  // Halaman error login gagal
}




}

