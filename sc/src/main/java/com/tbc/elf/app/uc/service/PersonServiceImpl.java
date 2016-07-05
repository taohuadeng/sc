package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Person;
import com.tbc.elf.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service("personService")
public class PersonServiceImpl extends BaseServiceImpl<Person> implements PersonService {
}
