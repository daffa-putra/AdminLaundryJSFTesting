package DAO;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.LaundryUtil;
import pojo.Karyawan;

/**
 *
 * @author ASUS
 */
public class DAOLogin {
    
    public List<Karyawan> getBy(String uName, String uPass) {
        List<Karyawan> user = new ArrayList<>();
        Transaction trans = null;
        Session session = LaundryUtil.getSessionFactory().openSession();
        
        try {
            trans = session.beginTransaction();
            Query query = session.createQuery("from Karyawan where username = :uName AND password = :uPass");
            query.setString("uName", uName);
            query.setString("uPass", uPass);
            user = query.list(); // Menggunakan query.list() untuk mendapatkan hasil
            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return user;
    }
}
