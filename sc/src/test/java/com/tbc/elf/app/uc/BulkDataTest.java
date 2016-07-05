package com.tbc.elf.app.uc;

import com.tbc.elf.app.uc.model.Card;
import com.tbc.elf.app.uc.model.Person;
import com.tbc.elf.app.uc.service.PersonService;
import com.tbc.elf.base.BaseTests;
import com.tbc.elf.base.service.HibernateBaseService;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 大数据量测试
 */
public class BulkDataTest extends BaseTests {

    @Resource
    private HibernateBaseService hibernateBaseService;
    @Resource
    private PersonService personService;

    /*--------------------------------- 插入一百万调数据 ----------------------------------------------------*/

    /**
     * 不加@Rollback(false)  共用时：75秒
     *
     * 加上@Rollback(false)  共用时：77秒
     */
    @Test
    @Rollback(false)
    public void testJDBCInsert() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        PreparedStatement pst = connection.prepareStatement("insert into t_test_card(card_id,card_number) values (?,?);");
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            pst.setInt(1,i);
            pst.setInt(2,i);
            pst.addBatch();
            if (i % 1000 == 0) {
                pst.executeBatch();
                connection.commit();
                pst.clearBatch();
            }
        }
        pst.executeBatch();
        connection.commit();
        long endTime = System.currentTimeMillis();
        System.out.println("共用时：" + (endTime - beginTime)/1000 + "秒");
        pst.close();
        connection.close();
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://192.168.1.218:5432/elf_test", "postgres", "Eln4postgres123");
    }

    /**
     * 共用时：100秒
     */
    @Test
    @Rollback(false)
    public void testHibernateInsert() {
        List<Card> cardList = new ArrayList<Card>(1000000);
        for (int i = 0; i < 1000000; i++) {
            Card card = new Card();
            card.setCardId(String.valueOf(i));
            card.setCardNumber("100");
            cardList.add(card);
        }

        long beginTime = System.currentTimeMillis();
        hibernateBaseService.batchSave(cardList);
        long endTime = System.currentTimeMillis();
        System.out.println("共用时：" + (endTime - beginTime)/1000 + "秒");
    }

    /*--------------------------- 关联查询 ----------------------------------------------------------------*/

    @Test
    @Rollback(false)
    public void testCascadeInsert() {
        List<Person> personList = new ArrayList<Person>(100000);
        for (int i = 0; i < 100000; i++) {
            Card card = new Card();
            card.setCardNumber(String.valueOf(i));

            Person person = new Person();
            person.setPersonName(String.valueOf(i));
            person.setSex(i % 2 == 0 ? "Male" : "Female");
            person.setCard(card);
            personList.add(person);
        }

        long beginTime = System.currentTimeMillis();
        personService.batchSave(personList);
        long endTime = System.currentTimeMillis();
        System.out.println("共用时：" + (endTime - beginTime)/1000 + "秒"); //10w数据，共用时：154秒
    }

    @Test
    public void testJoinQuery() {
        long beginTime = System.currentTimeMillis();
        String hql = "select p from Person p left join p.card card";
        List<Person> persons = personService.listByHQL(hql, null);
        System.out.println(persons.get(0).getPersonName());
        System.out.println(persons.size());
        long endTime = System.currentTimeMillis();
        System.out.println("共用时：" + (endTime - beginTime)/1000 + "秒");
    }

    @Test
    public void testJDBCJoinQuery() throws SQLException, ClassNotFoundException {
        long beginTime = System.currentTimeMillis();
        Connection connection = getConnection();
        PreparedStatement pst = connection.prepareStatement("select p.* from t_test_person p left join t_test_card c on c.card_id = p.card_id");
        ResultSet resultSet = pst.executeQuery();
        List<Person> personList = new ArrayList<Person>(100000);
        while (resultSet.next()) {
            String personId = resultSet.getString("person_id");
            String personName = resultSet.getString("person_name");
            String sex = resultSet.getString("sex");

            Person person = new Person();
            person.setPersonId(personId);
            person.setPersonName(personName);
            person.setSex(sex);
            personList.add(person);
        }
        System.out.println(personList.size());
        long endTime = System.currentTimeMillis();
        System.out.println("共用时：" + (endTime - beginTime)/1000 + "秒");
    }

}
