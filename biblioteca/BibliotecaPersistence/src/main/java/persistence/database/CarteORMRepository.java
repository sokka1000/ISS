package persistence.database;

import bibliotecamodel.Carte;
import persistence.RepositoryCarte;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CarteORMRepository implements RepositoryCarte {

    static SessionFactory sessionFactory;

    public CarteORMRepository(){
        sessionFactory = HibernateUtility.getSessionFactory();
        System.out.println("CarteHibernateRepo" + sessionFactory);
    }

    @Override
    public void add(Carte carte) throws Exception {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                session.save(carte);
                tx.commit();
            }catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(Integer id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Carte carteSTERS = session.createQuery("from Carte where id = ?", Carte.class)
                        .setParameter(0, id)
                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println("Se va sterge " + carteSTERS);
                session.delete(carteSTERS);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(Carte elem) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Carte carteUpdated = session.createQuery("from Carte where id = ?", Carte.class)
                        .setParameter(0, elem.getId())
                        .setMaxResults(1)
                        .uniqueResult();

                System.out.println("Inainte de modificare " + carteUpdated);

                carteUpdated.setAutor(elem.getAutor());
                carteUpdated.setTitlu(elem.getTitlu());
                carteUpdated.setDisponibila(elem.getDisponibila());

                System.out.println("Dupa modificare " + carteUpdated);

                session.update(carteUpdated);

                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Carte findById(Integer id) {
        Carte result = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Carte where id = ? ", Carte.class)
                        .setParameter(0,id).uniqueResult();

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
    public List<Carte> findAll() {
        List<Carte> result = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Carte as c order by c.titlu asc ", Carte.class).list();

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
    public List<Carte> toateCartileDisponibile() {
        List<Carte> result = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Carte as c where c.disponibila is true order by c.titlu asc ", Carte.class).list();

                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if(transaction !=null)
                    transaction.rollback();
            }
        }
        return result;
    }


}