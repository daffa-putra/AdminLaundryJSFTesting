package Test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import DAO.DAOTransaksi;
import pojo.LaundryUtil;
import pojo.Transaksi;

public class DAOTransaksiTest {

    private static DAOTransaksi daoTransaksi;

    @BeforeClass
    public static void setUpBeforeClass() {
        daoTransaksi = new DAOTransaksi();
    }

    @Before
    public void setUp() {
        clearDatabase(); // Hapus semua data sebelum setiap pengujian
    }

    @After
    public void tearDown() {
        clearDatabase(); // Hapus semua data setelah setiap pengujian
    }

    private void clearDatabase() {
        Session session = LaundryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.createQuery("DELETE FROM Transaksi").executeUpdate();
        tx.commit();
        session.close();
    }

    @Test
    public void testAddTransaksi() {
        Transaksi transaksi = new Transaksi();
        transaksi.setNamaPelanggan("John Doe");
        transaksi.setTanggalTransaksi(new Date());
        transaksi.setTotalBerat(new BigDecimal(5.0));
        transaksi.setTotalHarga(new BigDecimal(100000));
        transaksi.setStatus("Pending");
        transaksi.setStatusPembayaran("Belum Lunas");

        daoTransaksi.addTransaksi(transaksi);

        List<Transaksi> result = daoTransaksi.retrieveTblTransaksi();
        assertNotNull(result);
        assertTrue(result.stream().anyMatch(t -> "John Doe".equals(t.getNamaPelanggan())));
    }

    @Test
    public void testDeleteTransaksi() {
        Transaksi transaksi = new Transaksi();
        transaksi.setNamaPelanggan("Jane Doe");
        transaksi.setTanggalTransaksi(new Date());
        transaksi.setTotalBerat(new BigDecimal(4.0));
        transaksi.setTotalHarga(new BigDecimal(80000));
        transaksi.setStatus("Selesai");
        transaksi.setStatusPembayaran("Lunas");

        daoTransaksi.addTransaksi(transaksi);
        int idTransaksi = transaksi.getIdTransaksi();

        daoTransaksi.deleteTransaksi(idTransaksi);

        List<Transaksi> resultAfterDelete = daoTransaksi.getbyID(idTransaksi);
        assertTrue(resultAfterDelete.isEmpty());
    }

    @Test
    public void testGetbyID() {
        Transaksi transaksi = new Transaksi();
        transaksi.setNamaPelanggan("Mark Smith");
        transaksi.setTanggalTransaksi(new Date());
        transaksi.setTotalBerat(new BigDecimal(3.0));
        transaksi.setTotalHarga(new BigDecimal(60000));
        transaksi.setStatus("Pending");
        transaksi.setStatusPembayaran("Belum Lunas");

        daoTransaksi.addTransaksi(transaksi);
        int idTransaksi = transaksi.getIdTransaksi();

        List<Transaksi> foundTransaksi = daoTransaksi.getbyID(idTransaksi);
        assertNotNull(foundTransaksi);
        assertFalse(foundTransaksi.isEmpty());
    }

    @Test
    public void testUpdateTransaksi() {
        Transaksi transaksi = new Transaksi();
        transaksi.setNamaPelanggan("Sam Lee");
        transaksi.setTanggalTransaksi(new Date());
        transaksi.setTotalBerat(new BigDecimal(2.5));
        transaksi.setTotalHarga(new BigDecimal(50000));
        transaksi.setStatus("Pending");
        transaksi.setStatusPembayaran("Belum Lunas");

        daoTransaksi.addTransaksi(transaksi);

        transaksi.setNamaPelanggan("Sam Lee Updated");
        transaksi.setStatus("Selesai");
        boolean isUpdated = daoTransaksi.updateTransaksi(transaksi);

        assertTrue(isUpdated);
        List<Transaksi> updatedTransaksi = daoTransaksi.getbyID(transaksi.getIdTransaksi());
        assertEquals("Sam Lee Updated", updatedTransaksi.get(0).getNamaPelanggan());
    }

    @Test
    public void testGetTotalPendapatan() {
        Transaksi transaksi1 = new Transaksi();
        transaksi1.setNamaPelanggan("Customer 1");
        transaksi1.setTanggalTransaksi(new Date());
        transaksi1.setTotalHarga(new BigDecimal(50000));
        daoTransaksi.addTransaksi(transaksi1);

        Transaksi transaksi2 = new Transaksi();
        transaksi2.setNamaPelanggan("Customer 2");
        transaksi2.setTanggalTransaksi(new Date());
        transaksi2.setTotalHarga(new BigDecimal(75000));
        daoTransaksi.addTransaksi(transaksi2);

        BigDecimal totalPendapatan = daoTransaksi.getTotalPendapatan();
        assertEquals(new BigDecimal(0), totalPendapatan);
    }

    @Test
    public void testCountTotalPesanan() {
        Transaksi transaksi1 = new Transaksi();
        transaksi1.setNamaPelanggan("Customer 1");
        transaksi1.setTanggalTransaksi(new Date());
        daoTransaksi.addTransaksi(transaksi1);

        Transaksi transaksi2 = new Transaksi();
        transaksi2.setNamaPelanggan("Customer 2");
        transaksi2.setTanggalTransaksi(new Date());
        daoTransaksi.addTransaksi(transaksi2);

        int totalPesanan = daoTransaksi.countTotalPesanan();
        assertEquals(0, totalPesanan);
    }
}
