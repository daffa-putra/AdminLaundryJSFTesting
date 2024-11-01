/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.LaundryUtil;
import pojo.Transaksi;





/**
 *
 * @author ASUS
 */

public class DAOTransaksi {

    private Session session;
     @PersistenceContext
    private EntityManager em;
private boolean exceptionCaught = false;
    public void addTransaksi(Transaksi trs) {
        Transaction trans = null;
        Session session = LaundryUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.save(trs);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
              exceptionCaught = true;
        }
    }
    
    public boolean isExceptionCaught() {
        return exceptionCaught;
    }

    public List<Transaksi> retrieveTblTransaksi() {
        List<Transaksi> stud = new ArrayList<>();
        Transaction trans = null;
        Session session = LaundryUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            Query query = session.createQuery("from Transaksi");
            stud = query.list();
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stud;
    }

    public void deleteTransaksi(Integer idT) {
        Transaction trans = null;
        Session session = LaundryUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            Transaksi plg = (Transaksi) session.get(Transaksi.class, idT);
            session.delete(plg);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Transaksi> getbyID(Integer idTransaksi) {
        Transaksi plg = new Transaksi();
        List<Transaksi> lPlg = new ArrayList<>();
        Transaction trans = null;
        Session session = LaundryUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            Query query = session.createQuery("from Transaksi where idTransaksi = :id");
            query.setInteger("id", idTransaksi);
            lPlg = query.list();
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lPlg;
    }

    public boolean updateTransaksi(Transaksi lyn) {
        boolean success = true;
        Session session = LaundryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(lyn);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("[ERROR] DAOTransaksi.update: " + e.getMessage());
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public DAOTransaksi() {
        // Inisialisasi Session dari Hibernate
        session = LaundryUtil.getSessionFactory().openSession();
    }
// Metode untuk menghitung total pendapatan dari transaksi

    public BigDecimal getTotalPendapatan() {
        BigDecimal totalPendapatan = BigDecimal.ZERO; // Inisialisasi total pendapatan

        try {
            // Membuat query HQL untuk menghitung total pendapatan
            String queryStr = "SELECT SUM(t.totalHarga) FROM Transaksi t";
            Query query = session.createQuery(queryStr); // Hapus parameter generik

            // Mengambil hasil dari query
            totalPendapatan = (BigDecimal) query.uniqueResult(); // Casting hasil ke BigDecimal

            // Menggunakan BigDecimal.ZERO jika totalPendapatan null
            if (totalPendapatan == null) {
                totalPendapatan = BigDecimal.ZERO;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Menangani exception
        } finally {
            if (session.isOpen()) {
                session.close(); // Pastikan session ditutup
            }
        }

        return totalPendapatan; // Mengembalikan total pendapatan
    }

  public int countTotalPesanan() {
    int totalPesanan = 0; // Inisialisasi total pesanan

    try {
        // Membuat query HQL untuk menghitung jumlah total pesanan
        String queryStr = "SELECT COUNT(t) FROM Transaksi t"; // Query untuk menghitung total pesanan
        Query query = session.createQuery(queryStr); // Membuat query menggunakan Hibernate

        // Mengambil hasil dari query
        totalPesanan = ((Long) query.uniqueResult()).intValue(); // Menghitung total pesanan sebagai integer

    } catch (Exception e) {
        e.printStackTrace(); // Menangani exception
    } finally {
        if (session.isOpen()) {
            session.close(); // Pastikan session ditutup
        }
    }

    return totalPesanan; // Mengembalikan total pesanan
}

}
