package com.tbc.elf.base.service.interceptor;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Date;

public class BaseModelInterceptor extends EmptyInterceptor {

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        return audit(entity, state, propertyNames);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
                                String[] propertyNames, Type[] types) throws CallbackException {
        for (int i = 0; i < propertyNames.length; i++) {
            if ("lastModifyTime".equals(propertyNames[i])) {
                currentState[i] = new Date();
            } else if ("lastModifyBy".equals(propertyNames[i])) {
                //currentState[i] = AuthenticationUtil.getCurrentUser();
                currentState[i] = "11111111222222223333333344444444";
            }
        }
        return true;
    }

    private boolean audit(Object entity, Object[] state, String[] propertyNames) {
        boolean changed = false;
        if (entity instanceof BaseModel) {
            for (int i = 0; i < propertyNames.length; i++) {
                String propertyName = propertyNames[i];
                if ("createTime".equals(propertyName)) {
                    Object currState = state[i];
                    if (currState == null) {
                        state[i] = new Date();
                        changed = true;
                    }
                } else if ("lastModifyTime".equals(propertyName)) {
                    Object currState = state[i];
                    if (currState == null) {
                        state[i] = new Date();
                        changed = true;
                    }
                } else if ("createBy".equals(propertyName)) {
                    Object currState = state[i];
                    if (currState == null) {
                        //state[i] = AuthenticationUtil.getCurrentUser();
                        state[i] = "11111111222222223333333344444444";
                        changed = true;
                    }
                } else if ("lastModifyBy".equals(propertyName)) {
                    Object currState = state[i];
                    if (currState == null) {
                        state[i] = "11111111222222223333333344444444";
                        changed = true;
                    }
                } else if ("corpCode".equals(propertyName)) {
                    Object currState = state[i];
                    if (currState == null) {
                        state[i] = "default";
                        changed = true;
                    }
                }
            }
        }

        return changed;
    }
}