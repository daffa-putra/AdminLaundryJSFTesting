/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import DAO.DAOTransaksi;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Ignore;

@Ignore
public class TransaksiTest {

    private Transaksi transaksi;
    private Layanan layanan;
    private DAOTransaksi daoTransaksi;

    @Before
    public void setUp() {
        // Inisialisasi objek Transaksi dengan konstruktor
        transaksi = new Transaksi(layanan, "John Doe", new Date(), BigDecimal.valueOf(2.5), BigDecimal.valueOf(25000), "Pending", "Belum Dibayar");
        daoTransaksi = new DAOTransaksi();
    }

    @Test
    public void testTransaksiConstructor() {
        // Arrange
        String expectedNamaPelanggan = "John Doe";
        Date expectedTanggalTransaksi = new Date(System.currentTimeMillis());
        BigDecimal expectedTotalBerat = new BigDecimal("5.5");
        BigDecimal expectedTotalHarga = new BigDecimal("100000");

        // Act
        Transaksi transaksi = new Transaksi(expectedNamaPelanggan, expectedTanggalTransaksi, expectedTotalBerat, expectedTotalHarga);

        // Assert
        assertEquals(expectedNamaPelanggan, transaksi.getNamaPelanggan());
        assertEquals(expectedTanggalTransaksi, transaksi.getTanggalTransaksi());
        assertEquals(expectedTotalBerat, transaksi.getTotalBerat());
        assertEquals(expectedTotalHarga, transaksi.getTotalHarga());
    }

    @Test
    public void testSave() {
        // Arrange: Inisialisasi transaksi dengan data yang tepat
        transaksi.setNamaPelanggan("Test Customer");
        transaksi.setTanggalTransaksi(new Date());
        transaksi.setTotalBerat(new BigDecimal("5.0"));
        transaksi.setTotalHarga(new BigDecimal("50000"));
        transaksi.setStatus("Pending");
        transaksi.setStatusPembayaran("Unpaid");

        // Act: Simpan transaksi
        transaksi.save();

        // Ambil transaksi untuk memverifikasi apakah disimpan
        List<Transaksi> transaksiList = transaksi.getAllRecords();

        // Verifikasi bahwa daftar transaksi tidak kosong setelah menyimpan
        assertNotNull(transaksiList);
        assertFalse(transaksiList.isEmpty()); // Pastikan ada transaksi setelah menyimpan

        // Verifikasi bahwa transaksi yang disimpan adalah yang terbaru
        Transaksi lastTransaksi = transaksiList.get(transaksiList.size() - 1);
        assertEquals("Test Customer", lastTransaksi.getNamaPelanggan());
        assertEquals(0, lastTransaksi.getTotalBerat().compareTo(new BigDecimal("5.0")));
        assertEquals(0, lastTransaksi.getTotalHarga().compareTo(new BigDecimal("50000")));
        assertEquals("Pending", lastTransaksi.getStatus());
        assertEquals("Unpaid", lastTransaksi.getStatusPembayaran());
    }

    @Test
    public void testDelete() {
        transaksi.save();
        Integer id = transaksi.getIdTransaksi();

        transaksi.delete();

        // Pastikan transaksi sudah dihapus
        List<Transaksi> deletedTransaksiList = daoTransaksi.getbyID(id); // Pastikan ini mengembalikan List<Transaksi>
        assertTrue(deletedTransaksiList.isEmpty()); // Pastikan daftar kosong
    }

    @Test
    public void testUpdate() {
        // Simpan transaksi pertama kali
        transaksi.save();

        // Ambil transaksi yang baru saja disimpan
        List<Transaksi> transaksiList = transaksi.getAllRecords();
        Transaksi lastTransaksi = transaksiList.get(transaksiList.size() - 1);

        // Ubah data pada transaksi
        lastTransaksi.setNamaPelanggan("Updated Customer");
        lastTransaksi.setTotalBerat(new BigDecimal("10.0"));
        lastTransaksi.setTotalHarga(new BigDecimal("100000"));
        lastTransaksi.update();

        // Ambil transaksi yang telah diperbarui
        List<Transaksi> updatedTransaksiList = transaksi.getAllRecords();
        Transaksi updatedTransaksi = updatedTransaksiList.get(updatedTransaksiList.size() - 1);

        // Verifikasi bahwa transaksi telah diperbarui
        assertEquals("Updated Customer", updatedTransaksi.getNamaPelanggan());
        assertEquals(0, updatedTransaksi.getTotalBerat().compareTo(new BigDecimal("10.0")));
        assertEquals(0, updatedTransaksi.getTotalHarga().compareTo(new BigDecimal("100000")));
    }

    @Test
    public void testGetAllRecords() {
        transaksi.save(); // Pastikan ada setidaknya satu transaksi
        List<Transaksi> transaksiList = transaksi.getAllRecords();
        assertNotNull(transaksiList);
        assertTrue(transaksiList.size() > 0);
    }

    @Test
    public void testGetById() {
        transaksi.save(); // Pastikan transaksi sudah disimpan
        Integer id = transaksi.getIdTransaksi();

        // Ambil berdasarkan ID
        transaksi.setIdTransaksi(id);
        transaksi.getbyid();

        assertEquals(transaksi.getNamaPelanggan(), transaksi.getNamaPelanggan());
        assertEquals(transaksi.getTotalBerat(), transaksi.getTotalBerat());
        assertEquals(transaksi.getTotalHarga(), transaksi.getTotalHarga());
    }
}
