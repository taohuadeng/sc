package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Organization;
import com.tbc.elf.app.uc.model.Student;
import com.tbc.elf.base.service.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/2/24.
 */
@Service("studentService")
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {
    public void selfSave(){
    }
}
