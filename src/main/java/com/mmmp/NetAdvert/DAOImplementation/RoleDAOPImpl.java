package com.mmmp.NetAdvert.DAOImplementation;

import com.mmmp.NetAdvert.DAO.RoleDAO;
import com.mmmp.NetAdvert.model.Comment;
import com.mmmp.NetAdvert.model.Role;
import com.mmmp.NetAdvert.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by milosandric on 19/11/2016.
 */
@Repository
public class RoleDAOPImpl implements RoleDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public Role findRole(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Role r where r.id=:id");
        query.setParameter("id", id);
        List<Role> roleList = query.list();
        Role role = null;
        for(Role r: roleList){
            role = r;
        }
        return role;
    }
}
