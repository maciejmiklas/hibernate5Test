package org.miklas.test.hib.t1;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String test;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
