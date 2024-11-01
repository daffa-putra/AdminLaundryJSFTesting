package Test;

import DAO.DAOLayanan;
import java.math.BigDecimal;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.LaundryUtil;
import pojo.Layanan;
import java.util.List;

import static org.junit.Assert.*;

public class DAOLayananTest {

    private static DAOLayanan daoLayanan;
    private static Session session;
    private Layanan testLayanan;

    @Before
    public void setUp() {
        daoLayanan = new DAOLayanan();
        session = LaundryUtil.getSessionFactory().openSession();

        // Bersihkan tabel sebelum pengujian
        Transaction tx = session.beginTransaction();
        session.createQuery("DELETE FROM Layanan").executeUpdate();
        tx.commit();

        // Buat sample data
        testLayanan = new Layanan();
        testLayanan.setNamaLayanan("Test Layanan");
        testLayanan.setHargaPerKilo(new BigDecimal("30000"));
        daoLayanan.addLayanan(testLayanan);
    }

    @After
    public void tearDown() {
        // Hapus data setelah setiap pengujian
        if (testLayanan.getIdLayanan() != null) {
            daoLayanan.deleteLayanan(testLayanan.getIdLayanan());
        }

        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    @Test
    public void testAddLayanan() {
        Layanan layanan = new Layanan();
        layanan.setNamaLayanan("Laundry Baju");
        layanan.setHargaPerKilo(new BigDecimal("30000"));
        daoLayanan.addLayanan(layanan);

        List<Layanan> layananList = daoLayanan.retrieveTblLayanan();
        assertTrue(layananList.stream().anyMatch(l -> l.getNamaLayanan().equals("Laundry Baju")));
    }

    @Test
    public void testUpdateLayanan1() {
        Layanan layanan = new Layanan("Cuci", new BigDecimal("15000"));
        session.save(layanan);

        layanan.setNamaLayanan("Cuci Express");
        layanan.setHargaPerKilo(new BigDecimal("18000"));
        daoLayanan.updateLayanan(layanan);

        Layanan updatedLayanan = (Layanan) session.get(Layanan.class, layanan.getIdLayanan());
        assertEquals("Cuci Express", updatedLayanan.getNamaLayanan());
        assertEquals(new BigDecimal("18000"), updatedLayanan.getHargaPerKilo());
    }

    @Test
    public void testDeleteLayanan() {
        Layanan layanan = new Layanan();
        layanan.setNamaLayanan("Layanan untuk Dihapus");
        layanan.setHargaPerKilo(new BigDecimal("30000"));
        daoLayanan.addLayanan(layanan);

        daoLayanan.deleteLayanan(layanan.getIdLayanan());

        List<Layanan> layananListAfterDelete = daoLayanan.retrieveTblLayanan();
        assertTrue(layananListAfterDelete.stream().noneMatch(l -> l.getIdLayanan().equals(layanan.getIdLayanan())));
    }

    @Test
    public void testDeleteLayanan_NegativeCase() {
        // Membuat layanan baru untuk pengujian
        Layanan layanan = new Layanan();
        layanan.setNamaLayanan("Layanan untuk Dihapus");
        layanan.setHargaPerKilo(new BigDecimal("30000"));
        daoLayanan.addLayanan(layanan);

        // Mengambil ID layanan untuk penghapusan
        int idLayanan = layanan.getIdLayanan();

        // Menghapus layanan yang baru ditambahkan
        daoLayanan.deleteLayanan(idLayanan);

        // Menggunakan ID yang tidak valid untuk pengujian negatif
        int invalidIdLayanan = idLayanan + 1; // ID yang pasti tidak ada di database

        // Mencoba menghapus layanan dengan ID yang tidak valid
        daoLayanan.deleteLayanan(invalidIdLayanan);

        // Pastikan bahwa layanan yang asli sudah terhapus
        List<Layanan> remainingLayanan = daoLayanan.retrieveTblLayanan();
        assertTrue("Layanan harus terhapus setelah penghapusan ID yang valid.",
                remainingLayanan.stream().noneMatch(l -> l.getIdLayanan() == idLayanan));
    }

    @Test
    public void testGetById() {
        // Membuat layanan baru untuk pengujian
        Layanan newLayanan = new Layanan();
        newLayanan.setNamaLayanan("Layanan untuk Diuji");
        newLayanan.setHargaPerKilo(new BigDecimal("25000.00"));

        // Menambahkan layanan baru ke database
        daoLayanan.addLayanan(newLayanan);

        // Mendapatkan ID layanan setelah disimpan
        int idLayanan = newLayanan.getIdLayanan(); // ID yang dihasilkan setelah penambahan

        // Mengambil layanan berdasarkan ID
        Layanan retrievedLayanan = daoLayanan.getById(idLayanan);

        // Memastikan layanan berhasil diambil
        assertNotNull(retrievedLayanan);

        // Memverifikasi bahwa layanan yang diambil sama dengan layanan yang ditambahkan
        assertEquals(newLayanan.getNamaLayanan(), retrievedLayanan.getNamaLayanan());
        assertEquals(newLayanan.getHargaPerKilo(), retrievedLayanan.getHargaPerKilo());
    }

    @Test
    public void testGetById_NegativeCase() {
        // Membuat layanan baru untuk pengujian
        Layanan newLayanan = new Layanan();
        newLayanan.setNamaLayanan("Layanan untuk Diuji");
        newLayanan.setHargaPerKilo(new BigDecimal("25000.00"));

        // Menambahkan layanan baru ke database
        daoLayanan.addLayanan(newLayanan);

        // Mendapatkan ID layanan setelah disimpan
        int validIdLayanan = newLayanan.getIdLayanan(); // ID yang dihasilkan setelah penambahan

        // Menghapus layanan untuk memastikan ID tidak lagi valid
        daoLayanan.deleteLayanan(validIdLayanan);

        // Mencoba mengambil layanan dengan ID yang tidak valid
        int invalidIdLayanan = validIdLayanan; // ID yang telah dihapus
        Layanan retrievedLayanan = daoLayanan.getById(invalidIdLayanan);

        // Memastikan layanan tidak ditemukan menggunakan assertTrue
        assertTrue("Layanan dengan ID yang tidak valid harus mengembalikan null.", retrievedLayanan == null);
    }

    @Test
    public void testAddLayanan_ExceptionCaught() {
        // Buat layanan dengan nama valid tetapi dengan harga tidak valid
        Layanan layanan = new Layanan();
        layanan.setNamaLayanan(null);
        layanan.setHargaPerKilo(new BigDecimal("10000")); // Harga negatif untuk memicu exception
        daoLayanan.addLayanan(layanan);
        assertTrue("Exception pasti gagal", daoLayanan.isExceptionCaught());
    }

    @Test
    public void testUpdateLayanan_NegativeCase() {
        // Buat objek Layanan dengan ID yang tidak valid (misalnya ID yang tidak ada di database)
        Layanan invalidLayanan = new Layanan();
        invalidLayanan.setIdLayanan(-1); // Asumsikan ID -1 tidak ada di database
        invalidLayanan.setNamaLayanan("Layanan Test"); // Nama lain yang valid

        // Coba melakukan pembaruan
        boolean result = daoLayanan.updateLayanan(invalidLayanan);

        // Verifikasi bahwa update tidak berhasil
        assertFalse("Update seharusnya gagal untuk ID yang tidak valid.", result);
    }

}
