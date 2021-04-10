package com.yc;

/**
 * @program: reflectionAndAnnotation
 * @description:
 * @author: Joe
 * @create: 2021-03-29 19:44
 */
public class Person implements Showable{
    public int age;
    public String name;

    public Person(){}
    public Person(int age,String name){
        this.age=age;
        this.name=name;
    }

    public void setAge(int age){
        this.age=age;
    }

    public void setName(String name){
        this.name=name;
    }

    public int getAge(){
        return this.age;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public void show() {
        System.out.println("show");
    }
}
