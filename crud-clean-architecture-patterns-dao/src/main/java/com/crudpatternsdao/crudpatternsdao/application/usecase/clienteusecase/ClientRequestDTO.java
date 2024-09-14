package com.crudpatternsdao.crudpatternsdao.application.usecase.clienteusecase;

public class ClientRequestDTO {

    private String name;

    private String cpf;

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
}
