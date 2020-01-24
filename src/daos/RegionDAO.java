/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import models.Region;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import idaos.IRegionDAO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author aqira
 */
public class RegionDAO implements IRegionDAO {

    private Session session;
    private Transaction transaction;
    private SessionFactory sessionFactory;

    public RegionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Region> getAll() {
        List<Region> regions = new ArrayList<>();
        session = this.sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            String query = "FROM Region";
            regions = session.createQuery(query).list();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return regions;
    }

    @Override
    public List<Region> getById(BigDecimal id) {
        Region region = null;
        session = this.sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            String hql = "FROM Region WHERE regionId = :a";
            Query query = session.createQuery(hql);
            query.setParameter("a", id);
            region = (Region) query.uniqueResult();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return (List<Region>) region;
    }

    @Override
    public List<Region> search(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        session.delete(id);
        return result;
    }

    @Override
    public boolean insert(Region r) {
        boolean result = false;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (getById(r.getRegionId()).isEmpty()) {
                session.save(r); //insert
            } else {
                session.update(r); // update
            }
            transaction.commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean update(Region r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
