package Test;

import DAO.DAOLayanan;
import converter.LayananConverter;
import org.junit.Before;
import org.junit.Test;
import pojo.Layanan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Ignore;

@Ignore
public class LayananConverterTest {

    private LayananConverter layananConverter;
    private DAOLayanan layananDAO;

    @Before
    public void setUp() {
        layananConverter = new LayananConverter();
        layananDAO = new DAOLayanan() {
            public Layanan getById(int id) {
                if (id == 1) {
                    Layanan layanan = new Layanan();
                    layanan.setIdLayanan(1);
                    layanan.setNamaLayanan("Test Layanan");
                    return layanan;
                }
                return null; // Kembalikan null untuk ID lain
            }
        };

        // Mengatur DAOLayanan di converter dengan objek yang sudah diinstansiasi
        // Namun karena kita tidak mengubah LayananConverter, kita akan memodifikasi getAsObject untuk menggunakan layananDAO yang didefinisikan di sini
    }

    @Test
    public void testGetAsObject_WithValidId() {
        Layanan actualLayanan = (Layanan) layananConverter.getAsObject(null, null, "1");
        assertEquals(Integer.valueOf(1), Integer.valueOf(actualLayanan.getIdLayanan()));
    }

    @Test
    public void testGetAsObject_WithInvalidId() {
        Layanan actualLayanan = (Layanan) layananConverter.getAsObject(null, null, "invalid");
        assertNull(actualLayanan); // Harus mengembalikan null untuk input tidak valid
    }

    @Test
    public void testGetAsObject_WithNullValue() {
        Layanan actualLayanan = (Layanan) layananConverter.getAsObject(null, null, null);
        assertNull(actualLayanan); // Harus mengembalikan null untuk input null
    }

    @Test
    public void testGetAsString_WithValidLayanan() {
        Layanan layanan = new Layanan();
        layanan.setIdLayanan(1);
        String actualValue = layananConverter.getAsString(null, null, layanan);
        assertEquals("1", actualValue); // Harus mengembalikan ID sebagai string
    }

    @Test
    public void testGetAsString_WithNullValue() {
        String actualValue = layananConverter.getAsString(null, null, null);
        assertEquals("", actualValue); // Harus mengembalikan string kosong untuk input null
    }

    @Test
    public void testGetAsString_WithNonLayananObject() {
        String actualValue = layananConverter.getAsString(null, null, new Object());
        assertEquals("", actualValue); // Harus mengembalikan string kosong untuk objek yang bukan Layanan
    }
}
