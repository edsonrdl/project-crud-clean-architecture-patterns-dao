package com.crudpatternsdao.crudpatternsdao.infrastructure.Model;
import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "client")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientModel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_client")
    private Long codeClient;

    @Column(name = "nome", length = 25, nullable = false, unique = false)
    @Size(min = 3, max = 25, message = "O Nome deve ter entre 3 e 25 caracteres")
    @NotBlank(message = "O Nome é obrigatório")
    private String name;
    

    @Column(name = "CPF", length = 11, nullable = false, unique = false)
    @Size(min = 11, max = 11, message = "O CPF deve conter 11 caracteres")
    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    @Column(name = "idade", nullable = false)
    @Min(value = 18, message = "A idade deve ser maior ou igual a 18")
    private int age;
}
