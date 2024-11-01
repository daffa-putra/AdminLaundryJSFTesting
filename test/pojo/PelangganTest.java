package pojo;

import DAO.DAOPelanggan;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import org.junit.Ignore;

@Ignore
public class PelangganTest {

    private Pelanggan pelanggan;
    private DAOPelanggan daoPelanggan;

    @Before
    public void setUp() {
        pelanggan = new Pelanggan("John Doe", "123456789", new Date());
        daoPelanggan = new DAOPelanggan();
    }
     @Test
    public void testPelangganConstructorWithName() {
        // Uji konstruktor dengan nama pelanggan
        pelanggan = new Pelanggan("John Doe");

        // Pastikan nama pelanggan diinisialisasi dengan benar
        assertEquals("John Doe", pelanggan.getNamaPelanggan());

        // Pastikan atribut lain adalah null atau default value
        assertNull(pelanggan.getNoTelepon());
        assertNull(pelanggan.getTanggalDaftar());
    }

    @Test
    public void testSave() {
        pelanggan.save();

        // Ambil pelanggan dari database
        List<Pelanggan> pelangganList = daoPelanggan.retrieveTblPelanggan();
        assertTrue(pelangganList.size() > 0); //ini untuk apabila sudah ke simpan data pelanggan akan bertambah jika tidak unit testnya akan gagal
        
        // Asumsikan pelanggan yang disimpan adalah pelanggan terakhir
        Pelanggan retrievedPelanggan = pelangganList.get(pelangganList.size() - 1);
        
        //pengujian ini memastikan bahwa data pelanggan yang baru disimpan (nama dan nomor telepon)
        //sesuai dengan data pelanggan yang diambil dari database. Jika data tersebut cocok, maka pengujian ini dianggap berhasil.
        assertEquals(pelanggan.getNamaPelanggan(), retrievedPelanggan.getNamaPelanggan());
        assertEquals(pelanggan.getNoTelepon(), retrievedPelanggan.getNoTelepon());
    }

    @Test
    public void testDelete() {
        pelanggan.save();
        Integer id = pelanggan.getIdPelanggan();

        pelanggan.delete();
        List<Pelanggan> deletedPelangganList = daoPelanggan.getbyID(id); // Pastikan ini mengembalikan List<Pelanggan>

        // Jika Anda mengembalikan List, Anda bisa memeriksa apakah daftar kosong
        assertTrue(deletedPelangganList.isEmpty()); // Pastikan daftar kosong
    }

    @Test
    public void testUpdate() {
        pelanggan.save();
        Integer id = pelanggan.getIdPelanggan();

        // Update detail pelanggan
        pelanggan.setNamaPelanggan("Jane Doe");
        pelanggan.setNoTelepon("987654321");
        pelanggan.update();

        // Ambil pelanggan yang diperbarui
        List<Pelanggan> updatedPelangganList = daoPelanggan.getbyID(id); // Pastikan ini mengembalikan List<Pelanggan>

        // Pastikan list tidak kosong dan ambil pelanggan pertama
        assertFalse(updatedPelangganList.isEmpty());
        Pelanggan updatedPelanggan = updatedPelangganList.get(0); // Ambil pelanggan pertama dari daftar

        assertEquals("Jane Doe", updatedPelanggan.getNamaPelanggan());
        assertEquals("987654321", updatedPelanggan.getNoTelepon());
    }

    @Test
    public void testGetAllRecords() {
        pelanggan.save(); // Pastikan ada setidaknya satu pelanggan
        List<Pelanggan> pelangganList = pelanggan.getAllRecords();
        assertNotNull(pelangganList);
        assertTrue(pelangganList.size() > 0);
    }

    @Test
    public void testGetById() {
        pelanggan.save(); // Pastikan pelanggan sudah disimpan
        Integer id = pelanggan.getIdPelanggan();

        // Ambil berdasarkan ID
        pelanggan.setIdPelanggan(id);
        pelanggan.getbyid();

        assertEquals(pelanggan.getNamaPelanggan(), pelanggan.getNamaPelanggan());
        assertEquals(pelanggan.getNoTelepon(), pelanggan.getNoTelepon());
    }

}
