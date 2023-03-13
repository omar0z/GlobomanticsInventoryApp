package com.pluralsight.globomantics.services;

public interface Service {
    public Object fetch(Object... args);
    public Object findBy(String field);
    public void emit(Object... args);
    public void update(String arg);
}
