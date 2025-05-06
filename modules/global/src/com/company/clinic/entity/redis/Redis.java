package com.company.clinic.entity.redis;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Entity;

@MetaClass(name = "clinic_Redis")
@NamePattern("%s|key")
public class Redis extends BaseUuidEntity {
    private static final long serialVersionUID = -7875867336641722660L;

    @MetaProperty
    private String key;

    /*@MetaProperty
    private String value;*/

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /*public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }*/
}