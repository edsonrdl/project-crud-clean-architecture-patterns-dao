package com.crudpatternsdao.crudpatternsdao.domain.entities;

public class Client  {

    private String name;
    
    private String cpf;

    private Long codeClient;
    
    private int age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long  getCodeClient() {

     return codeClient ;

     }

     public void setCodeClient(Long codeClient) {
        this.codeClient = codeClient;
    }

}
