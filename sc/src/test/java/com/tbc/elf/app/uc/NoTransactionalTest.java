package com.tbc.elf.app.uc;

import com.tbc.elf.app.uc.model.Authority;
import com.tbc.elf.app.uc.model.Card;
import com.tbc.elf.app.uc.model.Person;
import com.tbc.elf.app.uc.model.Role;
import com.tbc.elf.app.uc.service.AuthorityService;
import com.tbc.elf.app.uc.service.CardService;
import com.tbc.elf.app.uc.service.PersonService;
import com.tbc.elf.app.uc.service.RoleService;
import com.tbc.elf.base.service.HibernateBaseService;
import com.tbc.elf.base.util.HqlBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class NoTransactionalTest {
    private static AuthorityService authorityService;
    private static RoleService roleService;
    private static PersonService personService;
    private static CardService cardService;

    @BeforeClass
    public static void init() {
        ApplicationContext
                context = new ClassPathXmlApplicationContext("classpath:/spring-config/spring-servlet.xml",
                "classpath:/spring-config/spring-context.xml");
        authorityService = (AuthorityService) context.getBean("authorityService");
        roleService = (RoleService) context.getBean("roleService");
        personService = (PersonService) context.getBean("personService");
        cardService = (CardService) context.getBean("cardService");
    }

    /**
     * Role 主控方 Authority 被控方
     * 情况一
     * 主控方不配置 @Cascade 属性 不调用【authorityService.batchSaveOrUpdate(authorities);】
     * 结论 保存都不成功 异常信息
     * <p>
     * org.springframework.dao.InvalidDataAccessApiUsageException:
     * object references an unsaved transient instance - save the transient instance before flushing
     * <p/>
     * This happens because you have a collection in your entity, and that collection has one or more items
     * which are not present in the database. By specifying the above options you tell hibernate to save them
     * to the database when saving their parent.
     * </p>
     * <p/>
     * 情况二
     * 主控方不配置 @Cascade 属性 调用【authorityService.batchSaveOrUpdate(authorities);】
     * 结论 role成功保存 authority成功保存 级联关系成功保存 不报错 维护关系表
     * <p/>
     * 情况三
     * 主控方配置 @Cascade(value = {SAVE_UPDATE}) 属性 不调用【authorityService.batchSaveOrUpdate(authorities);】
     * 结论 role成功保存 authority成功保存 级联关系成功保存 不报错 维护关系表
     * <p/>
     * 情况四
     * 主控方配置 @Cascade(value = {SAVE_UPDATE}) 属性 调用【authorityService.batchSaveOrUpdate(authorities);】
     * 结论 role成功保存 authority成功保存 级联关系成功保存 不报错 维护关系表
     */
    @Test
    public void test级联保存主控() {
        List<Authority> authorities = new ArrayList<Authority>();
        for (int i = 0; i < 10; i++) {
            Authority authority = new Authority();
            authority.setSourceUrl("url-" + i);
            authority.setParentId(".");
            authority.setShowOrder(i + 1);
            authority.setSourceName("name-" + i);
            authority.setAuthorityType(Authority.AuthorityType.SYSTEM);
            authorities.add(authority);
        }

        authorityService.batchSaveOrUpdate(authorities);

        Role role = new Role();
        role.setRoleName("roleName");
        role.setRoleType(Role.RoleType.SYSTEM);
        role.setAuthorities(authorities);
        role.setComments("角色描述");
        roleService.save(role);
    }

    /**
     * Authority 被控方 Role 主控方
     * 情况一
     * 条件 被控方不配置 @Cascade 属性 不调用【roleService.batchSaveOrUpdate(roles);】
     * 结论 authority成功保存  其他都不保存 不报错
     * <p/>
     * 情况二
     * 条件 被控方不配置 @Cascade 属性 调用【roleService.batchSaveOrUpdate(roles);】
     * 结论 role成功保存 authority成功保存 级联关系不保存 不报错
     * <p/>
     * 情况三
     * 条件 被控方配置 @Cascade(value = {SAVE_UPDATE})属性 不调用【roleService.batchSaveOrUpdate(roles);】
     * 结论 role成功保存 authority成功保存 级联关系不保存 不报错 不维护关系表
     * <p/>
     * 情况四
     * 条件 被控方配置 @Cascade(value = {SAVE_UPDATE})属性 调用【roleService.batchSaveOrUpdate(roles);】
     * 结论 role成功保存 authority成功保存 级联关系不保存 不报错 不维护关系表
     */
    @Test
    public void test级联保存被控() {
        List<Role> roles = new ArrayList<Role>();
        for (int i = 0; i < 10; i++) {
            Role role = new Role();
            role.setRoleName("roleName" + i);
            role.setRoleType(Role.RoleType.SYSTEM);
            role.setComments("角色描述" + i);


            roles.add(role);
        }

        roleService.batchSaveOrUpdate(roles);

        Authority authority = new Authority();
        authority.setSourceUrl("url-");
        authority.setParentId(".");
        authority.setShowOrder(1);
        authority.setSourceName("name-");
        authority.setRoles(roles);
        authority.setAuthorityType(Authority.AuthorityType.SYSTEM);
        authorityService.save(authority);
    }

    /**
     * Role 主控方 Authority 被控方
     * 情况一
     * 主控方不配置fetch属性
     * 结论 不会级联查询
     * <p/>
     * 情况二
     * 主控方配置 @ManyToMany(fetch = FetchType.EAGER) 属性
     * 结论 会级联查询
     * <p/>
     * 情况三
     * 主控方配置 @ManyToMany(fetch = FetchType.LAZY) 属性  默认属性
     * 结论 不会级联查询
     */
    @Test
    public void test级联查询主控() {
        String hql = "FROM Role WHERE corpCode = ?";
        List<Role> roles = roleService.listByHQL(hql, new Object[]{"default"});
        for (Role role : roles) {
            System.out.println(role.getRoleName());
        }
    }

    /**
     * Role 主控方 Authority 被控方
     * 情况一
     * 被控方不配置fetch属性
     * 结论 不会级联查询
     * 情况二
     * 被控方配置fetch属性 @ManyToMany(mappedBy = "authorities", fetch = FetchType.EAGER) 属性
     * 结论 会级联查询
     * 情况三
     * 被控方配置fetch属性 @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY) 属性 默认属性
     * 结论 不会级联查询
     */
    @Test
    public void test级联查询被控() {
        String hql = "FROM Authority WHERE corpCode = ?";
        List<Authority> authorities = authorityService.listByHQL(hql, new Object[]{"default"});
        for (Authority authority : authorities) {
            System.out.println(authority.getSourceName());
        }
    }

    @Test
    public void test级联更新() {
        String hql = "FROM Authority WHERE corpCode = ?";
        List<Authority> authorities = authorityService.listByHQL(hql, new Object[]{"default"});
        for (Authority authority : authorities) {
            authority.setSourceName(authority.getSourceName() + "hello");
            List<Role> roles = authority.getRoles();
            for (int i = 0; i < roles.size(); i++) {
                Role role = roles.get(i);
                role.setRoleName("hello234" + i);
            }
        }

        authorityService.saveOrUpdate(authorities);
    }

    @Test
    public void test删除1() {
        String hql = "FROM Authority WHERE corpCode = ?";
        List<Authority> authorities = authorityService.listByHQL(hql, new Object[]{"default"});
        List<String> authorityIds = new ArrayList<String>(authorities.size());
        for (Authority authority : authorities) {
            authorityIds.add(authority.getAuthorityId());
        }

        authorityService.delete(authorityIds);
    }

    @Test
    public void test删除2() {
        String hql = "FROM Role WHERE corpCode = ?";
        List<Role> authorities = roleService.listByHQL(hql, new Object[]{"default"});
        List<String> authorityIds = new ArrayList<String>(authorities.size());
        for (Role authority : authorities) {
            authorityIds.add(authority.getRoleId());
        }

        roleService.delete(authorityIds);
    }

    /**
     * Person主控 Card被控
     * 情况一
     * 不配置级联关系时
     * 结果 不会级联保存，报错
     * 情况二
     * 主控放配置级联关系，被控方不配置
     * 结果 会级联保存
     * 情况三
     * 主控方不配置级联关系，被控方配置
     * 结果 不会级联保存，报错
     */
    @Test
    public void testOtOSave() {
        Card card = new Card();
        card.setCardNumber("001");
        cardService.save(card);

        Person person = new Person();
        person.setPersonName("sdf");
        person.setSex("male");
        person.setCard(card);
        personService.save(person);
    }

    /**
     * Person主控 Card被控
     * 情况一
     * 不配置级联关系
     * 结果 不会级联删除，如果是主控方则仅删除主控方的数据，如果是被控方则因违反外键约束抛异常
     * 情况二
     * 主控方配置级联关系，被控方不配置
     * 结果 会级联删除
     * 情况三
     * 主控方不配置级联关系，被控方配置
     * 结果 如果是删除主控方数据，则报错 deleted object would be re-saved by cascade
     *      如果是删除被控方数据，则会级联删除
     */
    @Test
    public void testOtODelete() {
        String hql = "from Person";
        List<Person> persons = personService.listByHQL(hql,null);
        personService.delete(persons.get(0).getPersonId());

        /*String hql2 = "from Card";
        List<Card> cards = cardService.listByHQL(hql2, null);
        cardService.delete(cards.get(0).getCardId());*/
    }

    /**
     * Person主控 Card被控
     * 情况一
     * 不配置级联关系
     * 结果 不会级联更新，仅更新各自的数据
     * 情况二
     * 主控方配置级联关系，被控方不配置
     * 结果 更新主控方时会级联更新，更新被控方时只更新被控方
     * 情况三
     * 主控方不配置级联关系，被控方配置
     * 结果 更新主控方时不会级联更新被控方，更新被控方时会级联更新主控方
     */
    @Test
    public void testOtOUpdate() {
        String hql = "from Person";
        List<Person> persons = personService.listByHQL(hql,null);
        Person person = persons.get(0);
        person.setPersonName(person.getPersonName() + "_sdf");
        Card card = person.getCard();
        card.setCardNumber(card.getCardNumber() + "_001");
        //personService.update(person);

        card.setPerson(person);
        cardService.update(card);
    }

    /**
     * Person主控 Card被控
     * 情况一
     * 都不配置fetchType
     * 结果 都能查询到关联对象的信息，说明 默认fetchType是 EAGER
     * 情况二
     * 主控方配置fetch = FetchType.LAZY，被控方不配置
     * 结果 查询主控方不会把关系对象查出来，查询被控方会把关联对象查询出来
     * 情况三
     * 主控方不配置fetch = FetchType.LAZY，被控方配置
     * 结果 都能查询到关联对象的信息
     * 情况四
     * 都配置fetch = FetchType.LAZY
     * 结果 查询主控方不会把关系对象查出来，查询被控方会把关联对象查询出来
     */
    @Test
    public void testOtOQuery() {
        String hql = "from Person";
        List<Person> persons = personService.listByHQL(hql,null);
        Person person = persons.get(0);
        System.out.println(person.getPersonName());

        /*String hql2 = "from Card";
        List<Card> cards = cardService.listByHQL(hql2, null);
        Card card = cards.get(0);
        System.out.println(card.getCardNumber());*/
    }
}
