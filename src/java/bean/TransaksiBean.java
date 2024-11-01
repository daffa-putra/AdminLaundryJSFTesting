package bean;
import DAO.DAOLayanan;
import DAO.DAOTransaksi;
import java.math.BigDecimal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import pojo.Layanan;
import pojo.Transaksi;

@ManagedBean
@ViewScoped
public class TransaksiBean {

    private DAOLayanan layananDAO;
    private List<Layanan> layananList;
    private BigDecimal totalPendapatan;
    private DAOTransaksi daoTransaksi; // DAO untuk transaksi
    private DAOTransaksi transaksiService; // Ini seharusnya DAOTransaksi
     private Transaksi transaksi;

    public TransaksiBean() {
        this.layananDAO = new DAOLayanan();
        this.layananList = layananDAO.retrieveTblLayanan();
        this.daoTransaksi = new DAOTransaksi(); // Menginisialisasi daoTransaksi
        this.transaksiService = new DAOTransaksi(); // Menginisialisasi transaksiService
        this.totalPendapatan = daoTransaksi.getTotalPendapatan();
        this.transaksi = new Transaksi(); // Inisialisasi objek transaksi di sini
    }

    // Tambahkan getter untuk totalPendapatan
    public BigDecimal getTotalPendapatan() {
        return totalPendapatan;
    }

    public List<Layanan> getLayananList() {
        return layananList;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public String saveTransaksi() {
        daoTransaksi.addTransaksi(transaksi);
        transaksi = new Transaksi(); // Reset form
        // Update totalPendapatan setelah menambahkan transaksi baru
        this.totalPendapatan = daoTransaksi.getTotalPendapatan();
        return "Transaksi?faces-redirect=true"; // Redirect ke halaman Transaksi
    }

    public int getTotalPesanan() {
        return transaksiService.countTotalPesanan(); // Memanggil metode countTotalPesanan
    }

}
