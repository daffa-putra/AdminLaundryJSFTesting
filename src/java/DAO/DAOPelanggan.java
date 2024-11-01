/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.LaundryUtil;
import pojo.Pelanggan;

/**
 *
 * @author ASUS
 */
public class DAOPelanggan {
    
private boolean exceptionCaught = false;
    public void addPelanggan(Pelanggan plg) {
        Transaction trans = null;
        Session session = LaundryUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.save(plg);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            exceptionCaught = true;
        }
    }
    
        public boolean isExceptionCaught() {
        return exceptionCaught;
    }

    public List<Pelanggan> getbyID(Integer idPelanggan) {
        Pelanggan plg = new Pelanggan();
        List<Pelanggan> lPlg = new ArrayList<>();
        Transaction trans = null;
        Session session = LaundryUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            Query query = session.createQuery("from Pelanggan where idPelanggan = :id");
            query.setInteger("id", idPelanggan);
            lPlg = query.list();
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            exceptionCaught = true;
        }
        return lPlg;
    }

    public List<Pelanggan> retrieveTblPelanggan() {
        List<Pelanggan> stud = new ArrayList<>();
        Transaction trans = null;
        Session session = LaundryUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            Query query = session.createQuery("from Pelanggan");
            stud = query.list();
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stud;
    }

    public long getTotalPelanggan() {
        Session session = LaundryUtil.getSessionFactory().openSession();
        long count = 0;
        try {
            // Tidak menggunakan parameter kedua (tipe kelas)
            Query query = session.createQuery("SELECT COUNT(p.idPelanggan) FROM Pelanggan p");
            count = (Long) query.uniqueResult(); // Menggunakan uniqueResult() untuk hasil tunggal
        } finally {
            session.close();
        }
        return count;
    }

    public void deletePelanggan(Integer idP) {
        Transaction trans = null;
        Session session = LaundryUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            Pelanggan plg = (Pelanggan) session.get(Pelanggan.class, idP);
            session.delete(plg);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updatePelanggan(Pelanggan lyn) {
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
            System.out.println("[ERROR] DAOPelanggan.update: " + e.getMessage());
            e.printStackTrace();
            success = false;
        }
        return success;
    }
}
