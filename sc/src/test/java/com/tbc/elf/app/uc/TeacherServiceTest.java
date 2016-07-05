package com.tbc.elf.app.uc;

import com.tbc.elf.app.uc.model.Student;
import com.tbc.elf.app.uc.model.Teacher;
import com.tbc.elf.app.uc.service.StudentService;
import com.tbc.elf.app.uc.service.TeacherService;
import com.tbc.elf.base.BaseTests;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

/**
 * @author YangLiBo@HF
 * @since 2016年03月07日16:17:13
 */
public class TeacherServiceTest extends BaseTests {
    @Resource
    TeacherService teacherService;

    @Test
    @Rollback(false)
    public void test(){
        Teacher teacher = new Teacher();
        teacher.setName("1");
        teacher.setTeacherId("1");
        teacherService.save(teacher);
    }
}
