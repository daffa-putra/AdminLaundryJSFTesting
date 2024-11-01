package bean;

import DAO.DAOPelanggan;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PelangganBean {
    
    private DAOPelanggan daoPelanggan;
    private long totalPelanggan;

    public PelangganBean() {
        daoPelanggan = new DAOPelanggan();
        totalPelanggan = daoPelanggan.getTotalPelanggan(); // Memanggil DAO untuk mendapatkan jumlah pelanggan
    }

    public long getTotalPelanggan() {
        return totalPelanggan;
    }
}
