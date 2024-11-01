/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import bean.PelangganBean;
import DAO.DAOPelanggan;
import bean.PelangganBean;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

@Ignore
public class PelangganBeanTest {

    private PelangganBean pelangganBean;
    private DAOPelanggan daoPelanggan;

    @Before
    public void setUp() {
        // Inisialisasi objek DAOPelanggan dan PelangganBean
        daoPelanggan = new DAOPelanggan();
        pelangganBean = new PelangganBean();
    }

    @Test
    public void testGetTotalPelanggan() {
        // Mengambil total pelanggan dari DAO secara langsung untuk memastikan pengujian sesuai data nyata
        long expectedTotal = daoPelanggan.getTotalPelanggan();

        // Memanggil metode getTotalPelanggan() dari PelangganBean
        long actualTotal = pelangganBean.getTotalPelanggan();

        // Verifikasi apakah total pelanggan yang didapatkan dari bean sesuai dengan yang diharapkan
        assertEquals(expectedTotal, actualTotal);
    }
}
