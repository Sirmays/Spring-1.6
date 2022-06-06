package org.hibernate.crud;

import org.hibernate.PrepareDataApp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CustomerDAO {
    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            init();
            showProductsByCustomerId(1L);



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    public static void showProductsByCustomerId(Long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            System.out.println(customer);
            System.out.println("Bought: " + customer.getProducts());
            session.getTransaction().commit();

        }
    }


    public static Customer saveOrUpdate(Long id, String name) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            if (customer == null) {
                customer = new Customer(id, name);
                session.save(customer);
            } else {
                customer.setName(name);
            }
            session.getTransaction().commit();
            return customer;
        }
    }


    public static Customer findById(Long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            //System.out.println(product);
            session.getTransaction().commit();
            return customer;
        }
    }

    public static List<Customer> showAll() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Customer> customers = session.createQuery("from Customer").getResultList();
//          System.out.println(products + "\n");
            session.getTransaction().commit();
            return customers;
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
