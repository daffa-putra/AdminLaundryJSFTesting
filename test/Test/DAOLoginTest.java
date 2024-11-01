package Test;

import DAO.DAOLogin;
import pojo.Karyawan;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.*;
import pojo.LaundryUtil;

import java.util.List;
import org.hibernate.Query;

import static org.junit.Assert.*;
public class DAOLoginTest {

    private Session session;
    private Transaction transaction;
    private DAOLogin daoLogin;

    @Before
    public void setUp() {
        daoLogin = new DAOLogin();
        session = LaundryUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        // Simpan data pengguna ke database sebagai data uji
        Karyawan karyawan = new Karyawan();
        karyawan.setUsername("testuser");
        karyawan.setPassword("testpass");
        karyawan.setNama("Test Nama"); // Menambahkan nama
        session.save(karyawan);
        transaction.commit(); //mengubah di database
    }

    @After
    public void tearDown() {
        // Hapus data uji setelah pengujian selesai
        transaction = session.beginTransaction();
        Query query = session.createQuery("delete from Karyawan where username = :uName");
        query.setString("uName", "testuser");
        query.executeUpdate();
        transaction.commit();
        
        if (session != null) {
            session.close();
        }
    }

    @Test
    public void testGetByValidUser() {
        // Menguji login dengan username dan password yang benar
        List<Karyawan> result = daoLogin.getBy("testuser", "testpass");
        assertNotNull(result);               // Pastikan hasilnya tidak null
        assertFalse(result.isEmpty());       // Pastikan hasilnya tidak kosong
        assertEquals("testuser", result.get(0).getUsername());  // Pastikan username sesuai
        assertEquals("testpass", result.get(0).getPassword());  // Pastikan password sesuai
        assertEquals("Test Nama", result.get(0).getNama());     // Pastikan nama sesuai
    }
    

    @Test
    public void testGetByInvalidUser() {
        // Menguji login dengan username dan password yang salah
        List<Karyawan> result = daoLogin.getBy("invaliduser", "invalidpass");
        assertTrue(result.isEmpty());  // Pastikan hasilnya kosong karena tidak ada pengguna dengan kredensial ini
    }

}
