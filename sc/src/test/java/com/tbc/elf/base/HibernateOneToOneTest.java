package com.tbc.elf.base;

import com.tbc.elf.app.uc.model.Card;
import com.tbc.elf.app.uc.model.Person;
import com.tbc.elf.base.service.HibernateBaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

/**
 * Created by zhaoyue on 2016-03-07
 */
public class HibernateOneToOneTest extends BaseTests {

    Log log = LogFactory.getLog(this.getClass());

    //@Resource
    private HibernateBaseService hibernateBaseService;

    @Before
    public void before() {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @After
    public void after() {
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    /*------------------------------------------ 单向一对一 -------------------------------------------------------*/

    @Test
    @Rollback(false)
    public void testSave() {
        Person person = new Person();
        person.setPersonName("sdf");
        person.setSex("male");
        String personId = hibernateBaseService.save(person);

        Card card = new Card();
        card.setCardNumber("001");
        Person temp = new Person();
        temp.setPersonId(personId);
        card.setPerson(person);
        hibernateBaseService.save(card);
    }

    @Test
    public void testQuery() {
        Card card = hibernateBaseService.get(Card.class, "402881d2535a630301535a6307aa0001");
        log.info(card.getCardNumber());
        Person person = card.getPerson();
        log.info(person.getPersonName());
    }

    @Test
    @Rollback(false)
    public void testDelete() {
        hibernateBaseService.delete(Card.class,"402881d25354ce6a015354ce6e830001");
    }

    @Test
    @Rollback(false)
    public void testUpdate() {
        Card card = hibernateBaseService.load(Card.class, "402881d25354da42015354da46ec0001");
        Person person = card.getPerson();
        log.info(person.getPersonName());
        person.setPersonName("a3");
        hibernateBaseService.getSession().evict(person);
    }


    /*----------------------------------------------- 双向一对一 -----------------------------------------------------------*/

    @Test
    @Rollback(false)
    public void testSaves() {
        Person person = new Person();
        person.setPersonName("sdf");
        person.setSex("male");
        String personId = hibernateBaseService.save(person);
        person.setPersonId(personId);

        Card card = new Card();
        card.setCardNumber("001");
        card.setPerson(person);
        hibernateBaseService.save(card);

        person.setCard(card);


    }

    @Test
    public void testSelect() {
        Person person = hibernateBaseService.get(Person.class, "402881d2535ab06c01535ab072e00000");
        log.info(person.getPersonName());
        //log.info(person.getCard().getCardNumber());
    }

    @Test
    public void testSelect2() {
        Card card = hibernateBaseService.get(Card.class, "402881d253558515015355851a710001");
        log.info(card.getCardNumber());
        log.info(card.getPerson().getPersonName());
    }

    @Test
    @Rollback(false)
    public void testDelete2() {
        hibernateBaseService.delete(Card.class,"402881d2535aaa2b01535aaa30160001");
        //hibernateBaseService.delete(Person.class,"402881d2535aa8ba01535aa8bfbd0000");
    }
}
