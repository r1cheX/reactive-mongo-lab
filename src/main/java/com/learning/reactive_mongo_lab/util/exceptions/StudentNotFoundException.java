package com.learning.reactive_mongo_lab.util.exceptions;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String id){
        super("Estudiante " + id + " no existe!");
    }
}
