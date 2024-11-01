package Test;

import DAO.DAOPelanggan;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.LaundryUtil;
import pojo.Pelanggan;

import java.util.List;

import static org.junit.Assert.*;

public class DAOPelangganTest {

    private static DAOPelanggan daoPelanggan;
    private static Session session;
    private Pelanggan testPelanggan;

    @Before
    public void setUp() {
        daoPelanggan = new DAOPelanggan();
        session = LaundryUtil.getSessionFactory().openSession();

        // Bersihkan tabel sebelum pengujian
        Transaction tx = session.beginTransaction();
        session.createQuery("DELETE FROM Pelanggan").executeUpdate();
        tx.commit();

        // Buat sample data
        testPelanggan = new Pelanggan();
        testPelanggan.setNamaPelanggan("Test Pelanggan");
        testPelanggan.setNoTelepon("08123456789");
        daoPelanggan.addPelanggan(testPelanggan);
    }

    @After
    public void tearDown() {
        // Hapus data setelah setiap pengujian
        if (testPelanggan.getIdPelanggan() != null) {
            daoPelanggan.deletePelanggan(testPelanggan.getIdPelanggan());
        }

        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    @Test
    public void testAddPelanggan() {
        Pelanggan pelanggan = new Pelanggan();
        pelanggan.setNamaPelanggan("Daffa");
        pelanggan.setNoTelepon("08234567890");
        daoPelanggan.addPelanggan(pelanggan);

        List<Pelanggan> pelangganList = daoPelanggan.getbyID(pelanggan.getIdPelanggan());
        assertTrue(pelangganList.stream().anyMatch(l -> l.getNamaPelanggan().equals("Daffa")));
    }

    @Test
    public void testUpdatePelanggan() {
        Pelanggan pelanggan = new Pelanggan("Zaky", "082169147267", new Date());
        session.save(pelanggan);

        pelanggan.setNamaPelanggan("Daffa");
        pelanggan.setNoTelepon(("0812345678"));
        daoPelanggan.updatePelanggan(pelanggan);

        Pelanggan updatedPelanggan = (Pelanggan) session.get(Pelanggan.class, pelanggan.getIdPelanggan());
        assertEquals("Daffa", updatedPelanggan.getNamaPelanggan());
        assertEquals("0812345678", updatedPelanggan.getNoTelepon());
    }

    @Test
    public void testDeletePelanggan() {
        Pelanggan pelanggan = new Pelanggan();
        pelanggan.setNamaPelanggan("Pelanggan untuk Dihapus");
        pelanggan.setNoTelepon("08765432109");
        daoPelanggan.addPelanggan(pelanggan);

        daoPelanggan.deletePelanggan(pelanggan.getIdPelanggan());

        List<Pelanggan> deletedPelanggan = daoPelanggan.getbyID(pelanggan.getIdPelanggan());
        assertTrue(deletedPelanggan.isEmpty());
    }
    @Test
    public void testDeletePelanggan_NegativeCase() {
        // Membuat pelanggan baru untuk pengujian
        Pelanggan pelanggan = new Pelanggan();
        pelanggan.setNamaPelanggan("Pelanggan untuk Dihapus");
        pelanggan.setNoTelepon("08765432109");
        daoPelanggan.addPelanggan(pelanggan);

        // Mengambil ID pelanggan untuk penghapusan
        int idPelanggan = pelanggan.getIdPelanggan();

        // Menghapus pelanggan yang baru ditambahkan
        daoPelanggan.deletePelanggan(idPelanggan);

        // Menggunakan ID yang tidak valid untuk pengujian negatif
        int invalidIdPelanggan = idPelanggan + 1; // ID yang pasti tidak ada di database

        // Mencoba menghapus pelanggan dengan ID yang tidak valid
        daoPelanggan.deletePelanggan(invalidIdPelanggan);

        // Pastikan bahwa pelanggan yang asli sudah terhapus
        List<Pelanggan> remainingPelanggan = daoPelanggan.getbyID(idPelanggan);
        assertTrue("Pelanggan harus terhapus setelah penghapusan ID yang valid.", remainingPelanggan.isEmpty());
    }

@Test
    public void testGetById() {
        // Membuat pelanggan baru untuk pengujian
        Pelanggan newPelanggan = new Pelanggan();
        newPelanggan.setNamaPelanggan("John Doe");
        newPelanggan.setNoTelepon("123456789");
        newPelanggan.setTanggalDaftar(new Date());
        
        // Menambahkan pelanggan baru ke database
        daoPelanggan.addPelanggan(newPelanggan);

        // Mendapatkan ID pelanggan setelah disimpan
        int idPelanggan = newPelanggan.getIdPelanggan(); // ID yang dihasilkan setelah penambahan

        // Mengambil pelanggan berdasarkan ID
        List<Pelanggan> retrievedPelangganList = daoPelanggan.getbyID(idPelanggan);
        
        // Memastikan pelanggan berhasil diambil
        assertNotNull(retrievedPelangganList);
        assertFalse("Daftar pelanggan harus tidak kosong.", retrievedPelangganList.isEmpty());
        
        // Memverifikasi bahwa pelanggan yang diambil sama dengan pelanggan yang ditambahkan
        Pelanggan retrievedPelanggan = retrievedPelangganList.get(0);
        assertEquals(newPelanggan.getNamaPelanggan(), retrievedPelanggan.getNamaPelanggan());
        assertEquals(newPelanggan.getNoTelepon(), retrievedPelanggan.getNoTelepon());
    }
     @Test
    public void testGetById_NegativeCase() {
        // Membuat pelanggan baru untuk pengujian
        Pelanggan newPelanggan = new Pelanggan();
        newPelanggan.setNamaPelanggan("Alice Johnson");
        newPelanggan.setNoTelepon("987654321");
        newPelanggan.setTanggalDaftar(new Date());

        // Menambahkan pelanggan baru ke database
        daoPelanggan.addPelanggan(newPelanggan);

        // Mendapatkan ID pelanggan setelah disimpan
        int validIdPelanggan = newPelanggan.getIdPelanggan(); // ID yang dihasilkan setelah penambahan

        // ID yang tidak valid (asumsikan ID -1 tidak ada di database)
        int invalidIdPelanggan = validIdPelanggan + 1; // ID yang berbeda dari yang ada

        // Mengambil pelanggan berdasarkan ID yang tidak valid
        List<Pelanggan> retrievedPelangganList = daoPelanggan.getbyID(invalidIdPelanggan);

        assertTrue("Daftar pelanggan harus kosong untuk ID yang tidak valid.", retrievedPelangganList.isEmpty());
    }


    @Test
    public void testAddPelanggan_ExceptionCaught() {
        // Buat pelanggan dengan nama valid tetapi dengan nomor telepon tidak valid
        Pelanggan pelanggan = new Pelanggan();
        pelanggan.setNamaPelanggan(null); // Nama tidak boleh null untuk memicu exception
        pelanggan.setNoTelepon("08123456789"); // Nomor telepon valid
        daoPelanggan.addPelanggan(pelanggan);
        assertTrue("Exception seharusnya tertangkap.", daoPelanggan.isExceptionCaught());
    }

    @Test
    public void testUpdatePelanggan_NegativeCase() {
        // Buat objek Pelanggan dengan ID yang tidak valid (misalnya ID yang tidak ada di database)
        Pelanggan invalidPelanggan = new Pelanggan();
        invalidPelanggan.setIdPelanggan(-1); // Asumsikan ID -1 tidak ada di database
        invalidPelanggan.setNamaPelanggan("Pelanggan Test"); // Nama lain yang valid

        // Coba melakukan pembaruan
        boolean result = daoPelanggan.updatePelanggan(invalidPelanggan);

        // Verifikasi bahwa update tidak berhasil
        assertFalse("Update seharusnya gagal untuk ID yang tidak valid.", result);
    }

    @Test
    public void testGetTotalPelanggan() {
        long totalPelanggan = daoPelanggan.getTotalPelanggan();
        assertEquals(1, totalPelanggan); // Harus ada 1 pelanggan
    }
}
