package Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.List;
import pojo.Layanan;
import pojo.Transaksi;
import DAO.DAOLayanan;
import DAO.DAOTransaksi;
import bean.TransaksiBean;
import org.junit.Ignore;

@Ignore
public class TransaksiBeanTest {

    private TransaksiBean transaksiBean;
    private DAOTransaksi daoTransaksi;
    private DAOLayanan layananDAO;

    @Before
    public void setUp() {
        // Inisialisasi objek DAO
        daoTransaksi = new DAOTransaksi(); 
        layananDAO = new DAOLayanan(); 
        
        // Inisialisasi TransaksiBean dengan DAO
        transaksiBean = new TransaksiBean();

    }

    @Test
    public void testSaveTransaksi() {
        // Arrange: Siapkan transaksi untuk disimpan
        Transaksi transaksi = new Transaksi();
        transaksi.setNamaPelanggan("Customer A");
        transaksi.setTotalBerat(new BigDecimal("5.0"));
        transaksi.setTotalHarga(new BigDecimal("50000"));
        transaksiBean.setTransaksi(transaksi);

        // Act: Simpan transaksi
        String result = transaksiBean.saveTransaksi();

        // Assert: Verifikasi bahwa hasilnya sesuai dan transaksi direset
        assertEquals("Transaksi?faces-redirect=true", result);
        assertNotNull(transaksiBean.getTransaksi()); // Transaksi baru harus direset

        // Ambil total pendapatan untuk memverifikasi
        BigDecimal totalPendapatan = daoTransaksi.getTotalPendapatan();
        assertNotNull(totalPendapatan);
        assertTrue(totalPendapatan.compareTo(BigDecimal.ZERO) > 0); // Pastikan total pendapatan lebih dari 0
    }

    @Test
    public void testGetTotalPesanan() {
        // Act: Ambil total pesanan
        int totalPesanan = transaksiBean.getTotalPesanan();

        // Assert: Verifikasi total pesanan
        assertTrue(totalPesanan >= 0); // Pastikan total pesanan tidak negatif
    }

 @Test
    public void testGetLayananList() {
        // Mendapatkan list layanan dari DAOLayanan
        List<Layanan> expectedLayananList = layananDAO.retrieveTblLayanan();

        // Memanggil metode getLayananList() dari TransaksiBean
        List<Layanan> actualLayananList = transaksiBean.getLayananList();

        // Verifikasi apakah daftar layanan yang didapatkan sesuai
        assertEquals(expectedLayananList.size(), actualLayananList.size());
        assertNotNull(actualLayananList); // Verifikasi bahwa hasil tidak null
        assertFalse(actualLayananList.isEmpty()); // Pastikan bahwa daftar tidak kosong

        // Verifikasi apakah objek Layanan pertama sama
        assertEquals(expectedLayananList.get(0).getNamaLayanan(), actualLayananList.get(0).getNamaLayanan());
    }


    @Test
    public void testTotalPendapatan() {
        // Act: Ambil total pendapatan
        BigDecimal totalPendapatan = transaksiBean.getTotalPendapatan();

        // Assert: Verifikasi total pendapatan
        assertNotNull(totalPendapatan);
        assertTrue(totalPendapatan.compareTo(BigDecimal.ZERO) >= 0); // Pastikan total pendapatan tidak negatif
    }
}
