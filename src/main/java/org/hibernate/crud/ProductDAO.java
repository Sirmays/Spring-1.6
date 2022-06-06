package org.hibernate.crud;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.PrepareDataApp;

import java.util.List;


public class ProductDAO {
    private static SessionFactory factory;


    public static void main(String[] args) {
        try {
            init();
            showCustomersByProductId(2L);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    public static void showCustomersByProductId(Long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            System.out.println(product);
            System.out.println("Was bought by: " + product.getCustomers());
            session.getTransaction().commit();

        }
    }



    public static Product saveOrUpdate(Long id, String title, int price) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product == null) {
                product = new Product(title, price);
                session.save(product);
            } else {
                product.setPrice(price);
                product.setTitle(title);
            }
            session.getTransaction().commit();
            return product;
        }
    }


    public static Product findById(Long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            //System.out.println(product);
            session.getTransaction().commit();
            return product;
        }
    }

    public static List<Product> showAll() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Product> products = session.createQuery("from Product").getResultList();
//          System.out.println(products + "\n");
            session.getTransaction().commit();
            return products;
        }
    }

    public static void deleteById(Long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Product simpleItem = session.get(Product.class, id);
            session.delete(simpleItem);
            session.getTransaction().commit();
        }
    }


    public static void shutdown() {

        factory.close();
    }

    public static void init() {
        PrepareDataApp.forcePrepareData();
        factory = new Configuration().configure("configs/crud/hibernate.cfg.xml").buildSessionFactory();
    }
}



