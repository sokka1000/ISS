package persistence.database;

import bibliotecamodel.Bibliotecar;
import persistence.RepositoryBibliotecar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class BibliotecarORMRepository implements RepositoryBibliotecar {


    static SessionFactory sessionFactory;

    public BibliotecarORMRepository(){
        sessionFactory = HibernateUtility.getSessionFactory();
        System.out.println("BibliotecarHibernateRepo" + sessionFactory);
    }


    @Override
    public Bibliotecar findBibliotecarByUsernameParola(String username, String parola) {
        Bibliotecar result = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Bibliotecar where username = ? and parola = ?", Bibliotecar.class)
                        .setParameter(0,username).setParameter(1,parola).uniqueResult();

                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if(transaction !=null)
                    transaction.rollback();
            }
        }
        return result;
    }

    @Override
    public void add(Bibliotecar elem) throws Exception {

    }

    @Override
    public void delete(Integer integer) {

    }


    @Override
    public void update(Bibliotecar elem) {

    }

    @Override
    public Bibliotecar findById(Integer integer) {
        return null;
    }

    @Override
    public List<Bibliotecar> findAll() {
        return null;
    }


}
