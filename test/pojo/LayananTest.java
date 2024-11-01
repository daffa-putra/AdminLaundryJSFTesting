package pojo;

import DAO.DAOLayanan;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Ignore;

@Ignore
public class LayananTest {

    private Layanan layanan;
    private DAOLayanan daoLayanan;

    private Layanan layanan1;
    private Layanan layanan2;
    private Layanan layanan3;

    @Before
    public void setUp() {
       // Membuat objek Layanan untuk pengujian
        Set<Object> transaksis = new HashSet<>(); // Gunakan objek yang sesuai jika perlu
        layanan1 = new Layanan("Cuci Kering", "Layanan mencuci dan mengeringkan", BigDecimal.valueOf(10000), transaksis);
        layanan2 = new Layanan("Cuci Basah", "Layanan mencuci dengan air", BigDecimal.valueOf(8000), transaksis);
        
        layanan3 = new Layanan();
        layanan3.setIdLayanan(2); // Set idLayanan yang berbeda dari layanan1

        // Inisialisasi objek layanan untuk pengujian lainnya
        layanan = new Layanan("Layanan Uji", BigDecimal.valueOf(10000));
        daoLayanan = new DAOLayanan();

    }

    @Test
    public void testLayananConstructor() {
        assertEquals("Cuci Kering", layanan1.getNamaLayanan());
        assertEquals("Layanan mencuci dan mengeringkan", layanan1.getDeskripsi());
        assertEquals(BigDecimal.valueOf(10000), layanan1.getHargaPerKilo());
        assertEquals(0, layanan1.getTransaksis().size()); // Pastikan set transaksis kosong
    }

    @Test
    public void testEquals_SameObject() {
        // Test jika kedua referensi objek sama
        assertTrue(layanan1.equals(layanan1));
    }

    @Test
    public void testEquals_NullObject() {
        // Test jika objek yang dibandingkan adalah null
        assertFalse(layanan1.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        // Test jika objek yang dibandingkan bukan dari kelas yang sama
        assertFalse(layanan1.equals("string")); // Bandingkan dengan objek String
    }

    @Test
    public void testEquals_SameIdLayanan() {
        // Test jika kedua objek memiliki idLayanan yang sama
        assertTrue(layanan1.equals(layanan2));
    }

    @Test
    public void testEquals_DifferentIdLayanan() {
        // Test jika kedua objek memiliki idLayanan yang berbeda
        assertFalse(layanan1.equals(layanan3));
    }

    @Test
    public void testHashCode() {
        layanan1.setIdLayanan(1);
        layanan2.setIdLayanan(1);
        assertEquals(layanan1.hashCode(), layanan2.hashCode()); // Harus sama jika equals sama

        layanan2.setIdLayanan(2);
        assertNotEquals(layanan1.hashCode(), layanan2.hashCode()); // Harus berbeda jika equals berbeda
    }

    @Test
    public void testSave() {
        layanan.save();
        Layanan retrievedLayanan = daoLayanan.getById(layanan.getIdLayanan());
        assertNotNull(retrievedLayanan);
        assertEquals(layanan.getNamaLayanan(), retrievedLayanan.getNamaLayanan());
    }

    @Test
    public void testGetAllRecords() {
        List<Layanan> layananList = layanan.getAllRecords();
        assertNotNull(layananList);
        assertTrue(layananList.size() > 0);
    }

    @Test
    public void testDelete() {
        layanan.save();
        Integer id = layanan.getIdLayanan();
        layanan.delete();
        Layanan deletedLayanan = daoLayanan.getById(id);
        assertNull(deletedLayanan);
    }

@Test
public void testUpdate() {
    // Ambil layanan yang sudah ada di database dengan ID 5
    layanan = daoLayanan.getById(5);
    
    // Pastikan layanan ditemukan
    assertNotNull("Layanan dengan ID 5 harus ditemukan", layanan);

    // Ubah data pada layanan
    layanan.setNamaLayanan("express");
    layanan.setDeskripsi("Layanan cuci cepat");
    layanan.setHargaPerKilo(new BigDecimal("6000"));

    // Update layanan di database
    layanan.update();

    // Ambil kembali layanan yang sudah diupdate berdasarkan ID 5
    Layanan updatedLayanan = daoLayanan.getById(5);
    
    // Verifikasi bahwa layanan telah diperbarui
    assertNotNull("Layanan dengan ID 5 harus ada setelah update", updatedLayanan);
    assertEquals("Nama layanan harus diperbarui", "express", updatedLayanan.getNamaLayanan());
    assertEquals("Deskripsi layanan harus diperbarui", "Layanan cuci cepat", updatedLayanan.getDeskripsi());
    assertEquals("Harga per kilo harus diperbarui", 0, updatedLayanan.getHargaPerKilo().compareTo(new BigDecimal("6000")));
}






@Test
public void testGetbyid_LayananFound() {
    // Simulasikan ID layanan yang ada
    Integer validId = 1; // Ganti dengan ID yang valid sesuai data Anda
    layanan.setIdLayanan(validId); // Set ID layanan untuk pengujian

    // Panggil metode getbyid()
    layanan.getbyid();

    // Verifikasi nilai-nilai atribut layanan
    assertNotNull("Layanan harus ditemukan", layanan);
    assertEquals("Nama layanan harus sesuai", "express", layanan.getNamaLayanan()); // Ganti dengan nama yang sesuai
    assertEquals("Deskripsi layanan harus sesuai", "celana", layanan.getDeskripsi()); // Ganti dengan deskripsi yang sesuai
    assertEquals("Harga per kilo layanan harus sesuai", new BigDecimal("5000.00"), layanan.getHargaPerKilo()); // Ganti dengan harga yang sesuai
}

@Test
public void testGetbyid_LayananNotFound() {
    // Simulasi ID layanan yang tidak ada
    layanan.setIdLayanan(999); // Set ID yang tidak valid
    layanan.getbyid();

    assertEquals("", layanan.getNamaLayanan());
    assertEquals("", layanan.getDeskripsi());
    assertEquals(BigDecimal.ZERO, layanan.getHargaPerKilo());
}


}
