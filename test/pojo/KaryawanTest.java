package pojo;

import DAO.DAOLogin;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Ignore;

@Ignore
public class KaryawanTest {

    private Karyawan karyawan;

    @Before
    public void setUp() {
        karyawan = new Karyawan();
    }
    @Test
    public void testKaryawanConstructor() {
        // Menginisialisasi data untuk pengujian
        String username = "user1";
        String password = "pass1";
        String nama = "Karyawan 1";

        // Membuat objek Karyawan menggunakan konstruktor
        Karyawan karyawan = new Karyawan(username, password, nama);

        // Menguji apakah nilai yang di-set benar
        assertEquals(username, karyawan.getUsername());
        assertEquals(password, karyawan.getPassword());
        assertEquals(nama, karyawan.getNama());
    }

   @Test
    public void testValidasiLoginSuccess() {
        karyawan.setUsername("admin");
        karyawan.setPassword("123");
        String result = karyawan.validasiLogin();
        assertEquals("index", result); // Harus mengarah ke halaman index
    }


    @Test
    public void testValidasiLoginFail() {
        karyawan.setUsername("user1");
        karyawan.setPassword("wrongpass");
        String result = karyawan.validasiLogin();
        assertEquals("error", result); // Harus mengarah ke halaman error
    }

    @Test
    public void testValidasiLoginUserNotFound() {
        karyawan.setUsername("nonexistent");
        karyawan.setPassword("pass");
        String result = karyawan.validasiLogin();
        assertEquals("error", result); // Harus mengarah ke halaman error
    }
}


