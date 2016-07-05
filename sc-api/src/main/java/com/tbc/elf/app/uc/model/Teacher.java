package com.tbc.elf.app.uc.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author YangLiBo@HF
 * @since 2016年03月07日16:09:37
 */
@Entity
@Table(name = "t_uc_teacher")
public class Teacher {
    @Id
    @Column(name = "teacher_id", length = 32)
    private String teacherId;

    @Column(name = "name", length = 32)
    private String name;

    //若配置为单向多对一时或者双向一对多，使用1下面的注解，否则使用2下面的注解
    //并将另外一个编号的注解注释掉。
    //1
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "teacher")//mappedBy,双向多对一时需要配置，将维护权交给多方，建议使用
    //fetch表示何时抓取关联的另一方的数据，lazy表示为非立即，eager:立即加载，
    //当使用双向一对多时，可以使用mappedBy将维护关系的责任交给"many"方
    @JoinColumn(name = "teacher_id")//关联"many"方表中的字段，当使用双向一对多，不要配置该选项
    @Cascade({CascadeType.SAVE_UPDATE})//使用hibernate提供的级联注解，配置级联时,建议除特殊情况下不要使用DELETE，否则老师一删除，学生就会被删除
    @Fetch(FetchMode.JOIN)
    //2
    @Transient
    private Set<Student> students;

    public Teacher() {
        students = new HashSet<Student>(0);
    }

    public String getTeacherId() {
        return teacherId;
    }


    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
