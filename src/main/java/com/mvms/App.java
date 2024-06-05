package com.mvms;

import com.mvms.entity.Address;
import com.mvms.entity.Customer;
import com.mvms.entity.Gender;
import com.mvms.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        System.out.println(sessionFactory);

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();

        Address address = new Address();
        address.setAddress1("17 Duy Tan");
        address.setAddress2("19 Duy Tan");
        address.setDistrict(1L);
        address.setPhone("123");
        address.setPostalCode("100");

        String input = "06/03/2024 18:29:09";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        java.util.Date dt = null;
        try {
            dt = sdf.parse(input);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        java.sql.Date dtSql = new java.sql.Date(dt.getTime());

        address.setLastUpdate(dtSql);

        List<Customer> customers = new ArrayList<>();
        Customer biden = new Customer("Biden", "Obama", Gender.MALE, "biden@gmail.com"
                , true);
        Customer obama = new Customer("Obama", "Cruso", Gender.FEMALE,
                "obama@gmail.com"
                , true);
        biden.setAddress(address);
        obama.setAddress(address);
        customers.add(biden);
        customers.add(obama);
        address.setCustomers(customers);

//        Address removedAddr = session.get(Address.class, 1L);
        //session.remove(removedAddr);

        session.persist(address);

        tx.commit();

        session.close();


    }
}
