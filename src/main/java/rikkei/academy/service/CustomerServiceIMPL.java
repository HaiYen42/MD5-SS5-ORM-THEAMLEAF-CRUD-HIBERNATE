package rikkei.academy.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import rikkei.academy.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerServiceIMPL implements ICustomerService {

    //SessionFactory : Thiết kế cho Hibernate-->Để tạo ra các phiên thay đổi
    // EntityManager : Quản lý thực thể
    public static SessionFactory sessionFactory;
    public static EntityManager entityManager;

    static {
        //Xây dựng 1 cấu hình với cấu trúc  lấy từ file "hibernate.conf.xml" --> & bắt đầu xây ựng
        sessionFactory = new Configuration().configure("hibernate.conf.xml").buildSessionFactory();
        // Từ nhà máy vừa xây dựng sessionFactory---> tạo ra quản lý thực the
        entityManager = sessionFactory.createEntityManager();
    }

    @Override
    public List<Customer> findALl() {
        String qrFindAll = "SELECT c FROM Customer AS c";
        // TypeQuery<Customer> : Bắt
        //entityManager.createQuery(qrFindAll, Customer.class);--> Quản lý thực thể tạo ra mệnh lệnh tác động đến đối tượng Customer
        TypedQuery<Customer> query = entityManager.createQuery(qrFindAll, Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        String qrFindById = "SELECT c FROM Customer as c WHERE c.id=:id";
        TypedQuery<Customer> query = entityManager.createQuery(qrFindById, Customer.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void save(Customer customer) {
        // session
//        Session session = null;
        Transaction transaction = null;
        // sessionFactory.openSession();-->mở cửa nh máy
        Session session = sessionFactory.openSession();
        // beginTransaction : Quản đốc bắt đầu cv giám sát
        transaction = session.beginTransaction();
        if (customer.getId() != null) {
            Customer customer1 = findById(customer.getId());
            customer1.setName(customer.getName());
            session.saveOrUpdate(customer1);
        } else {
            session.saveOrUpdate(customer);
        }
        //    transaction.commit(): quản đốc xác nhận công việc hoàn tất
        transaction.commit();
        //   session.close(): Đóng cửa nhà máy
        session.close();
    }

    @Override
    public void deleteById(Long id) {
        Session session = null;
        Transaction transaction = null;
        session= sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.close();
    }
}
