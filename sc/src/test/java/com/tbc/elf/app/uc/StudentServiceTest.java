package com.tbc.elf.app.uc;

import com.tbc.elf.app.uc.model.Student;
import com.tbc.elf.app.uc.model.Teacher;
import com.tbc.elf.app.uc.service.StudentService;
import com.tbc.elf.app.uc.service.TeacherService;
import com.tbc.elf.base.BaseTests;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.openxmlformats.schemas.drawingml.x2006.main.STAdjAngle;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author YangLiBo@HF
 * @since 2016年03月07日16:17:13
 */
public class StudentServiceTest extends BaseTests {
    @Resource
    StudentService studentService;
    @Resource
    TeacherService teacherService;

    private Log LOG = LogFactory.getLog(StudentServiceTest.class);

    /**
     * ****************************************************************************************
     * <p/>
     * 单向一对多使用 @OneToMany,使用@JoinColumn(name="表中的字段名")表示关联"many"方的字段
     * 使用 @Casecade注解表示使用的级联操作类型，例如CasecadeType.SAVE_UPDATE表示更新级联操作
     * 使用 @Fetch表示查询多方使用的抓取方式，例如FetchModel.SELECT(发出另外一条查询语句)
     * 注：单向一对多不建议使用，因为会在建立两者关系的时候产生多条update语句
     * <p/>
     * *****************************************************************************************
     */

    @Test
    /**
     * 测试单向一对多方法。
     * 找到 Student类，并找到teacher变量，按照提示操作之后，再运行此测试方法
     */
    @Rollback(false)//事务不回滚
    public void testOSaveOrUpdate() {
        List<Student> students = new ArrayList<Student>(3);
        for (int i = 0; i < 3; i++) {
            Student student = new Student();
            student.setId("i" + i);
            student.setName("i" + i);
            student.setName1("i" + i);
            student.setName2("i" + i);
            student.setName3("i" + i);
            student.setName4("i" + i);
            students.add(student);
        }
        studentService.batchSave(students);
        Teacher teacher = new Teacher();
        teacher.setName("t1");
        teacher.setTeacherId("t1");
        teacher.setStudents(new HashSet<Student>(students));
        teacherService.save(teacher);
    }

    @Test
    /**
     * 测试单向一对多删除方法。
     * 删除时，如果te
     */
    @Rollback(false)//事务不回滚
    public void testODelete() {
      /*  //单独删除学生
        studentService.delete("i0");*/
        //删除老师
        teacherService.delete("t1");
    }


    /**
     * ****************************************************************************************
     * <p/>
     * 单向多对一使用 @ManyToOne,使用@JoinColumn(name="表中的字段名")表示关联"one"方的字段
     * 使用 @Casecade注解表示使用的级联操作类型，例如CasecadeType.SAVE_UPDATE表示更新级联操作
     * 使用 @Fetch表示查询多方使用的抓取方式，例如FetchModel.SELECT(发出另外一条查询语句)
     * <p/>
     * <p/>
     * *****************************************************************************************
     */

    @Test
    /**
     * 测试单向一对多方法。
     * 找到 Student类，并找到teacher变量，按照提示操作之后，再运行此测试方法
     */
    @Rollback(false)
    public void testMSaveOrUpdate() {
        Teacher teacher = new Teacher();
        teacher.setName("t2");
        teacher.setTeacherId("t2");
        ;
        teacherService.save(teacher);
        List<Student> students = new ArrayList<Student>(3);
        for (int i = 6; i < 9; i++) {
            Student student = new Student();
            student.setId("i" + i);
            student.setName("i" + i);
            student.setName1("i" + i);
            student.setName2("i" + i);
            student.setName3("i" + i);
            student.setName4("i" + i);
            student.setTeacher(teacher);
            students.add(student);
        }
        studentService.batchSave(students);

    }

    @Test
    /**
     * 测试单向一对多删除方法。
     * 删除时，如果te
     */
    @Rollback(false)//事务不回滚
    public void testMDelete() {
        //单独删除学生
        studentService.delete("i7");
    /*    //删除老师
        teacherService.delete("t1");*/
    }


    /*******************************************************************************************
     *
     * 双向一对多使用@OneToMany(一方) @ManyToOne(多方)
     *
     * 更详细信息可以在Student(many)类和teacher(one)类中查找
     *
     *
     * *****************************************************************************************
     */


     @Test
    /**
     * 测试双向一对多方法。
     * 找到 Student类，并找到teacher变量，按照提示操作之后，再运行此测试方法
     */
    @Rollback(false)
    public void testDSaveOrUpdate() {
        Teacher teacher = new Teacher();
        teacher.setName("t2");
        teacher.setTeacherId("t2");
        ;
        teacherService.save(teacher);
        List<Student> students = new ArrayList<Student>(3);
        for (int i = 6; i < 9; i++) {
            Student student = new Student();
            student.setId("i" + i);
            student.setName("i" + i);
            student.setName1("i" + i);
            student.setName2("i" + i);
            student.setName3("i" + i);
            student.setName4("i" + i);
            student.setTeacher(teacher);
            students.add(student);
        }
        studentService.batchSave(students);

    }

    @Test
    /**
     * 测试双向一对多删除方法。
     * 删除时，如果te
     */
    @Rollback(false)//事务不回滚
    public void testDDelete() {
        //单独删除学生
        studentService.delete("i7");
    /*    //删除老师
        teacherService.delete("t1");*/
    }

}
