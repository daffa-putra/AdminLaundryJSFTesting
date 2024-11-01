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
import pojo.Layanan;

/**
 *
 * @author ASUS
 */
public class DAOLayanan {
    
 private boolean exceptionCaught = false;
 
    public void addLayanan(Layanan lyn) {
        Transaction trans = null;
        Session session = LaundryUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.save(lyn);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            exceptionCaught = true;
        }
    }
    
    public boolean isExceptionCaught() {
        return exceptionCaught;
    }
   

    public List<Layanan> retrieveTblLayanan() {
        List<Layanan> stud = new ArrayList<>();
        Transaction trans = null;
        Session session = LaundryUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            Query query = session.createQuery("from Layanan");
            stud = query.list();
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stud;
    }

    public void deleteLayanan(Integer idP) {
        Transaction trans = null;
        Session session = LaundryUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            Layanan plg = (Layanan) session.get(Layanan.class, idP);
            session.delete(plg);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            exceptionCaught = true;
        }
    }

    public Layanan getById(Integer idLayanan) {
        Layanan layanan = null;
        Transaction trans = null;
        Session session = LaundryUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            layanan = (Layanan) session.get(Layanan.class, idLayanan);
            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            e.printStackTrace();
            exceptionCaught = true;
        } finally {
            session.close();
        }
        return layanan;
    }

    public boolean updateLayanan(Layanan lyn) {
    boolean success = false;
    Session session = LaundryUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();
    try {
        session.update(lyn);
        tx.commit();
        success = true; // Set nilai success menjadi true jika update berhasil
    } catch (Exception e) {
        if (tx != null) {
            tx.rollback();
        }
        System.out.println("[ERROR] DAOLayanan.update: " + e.getMessage());
        e.printStackTrace();
        success = false;
    } finally {
        session.close(); // Pastikan untuk menutup sesi
    }
    return success;
}

    
}
