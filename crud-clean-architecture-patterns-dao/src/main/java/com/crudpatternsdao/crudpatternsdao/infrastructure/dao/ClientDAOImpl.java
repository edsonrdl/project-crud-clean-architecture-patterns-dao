package com.crudpatternsdao.crudpatternsdao.infrastructure.dao;

import com.crudpatternsdao.crudpatternsdao.infrastructure.Model.ClientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Primary
@Repository
public class ClientDAOImpl implements IClientDAO {

    @Autowired
    private DataSource dataSource;

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public ClientModel create(ClientModel clientModel) {
        String sql = "INSERT INTO CLIENT (NOME, CPF, IDADE) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, clientModel.getName());
            ps.setString(2, clientModel.getCpf());
            ps.setInt(3, clientModel.getAge());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Nenhuma linha foi afetada durante a criação do cliente.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    clientModel.setCodeClient(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Falha na criação do cliente: nenhuma chave gerada.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage(), e);
        }
        return clientModel;
    }

    @Override
    public ClientModel findById(Long id) {
        ClientModel cliente = null;
        String sql = "SELECT * FROM CLIENT WHERE CODE_CLIENT=?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new ClientModel();
                    cliente.setCodeClient(rs.getLong("CODE_CLIENT"));
                    cliente.setName(rs.getString("NOME"));
                    cliente.setCpf(rs.getString("CPF"));
                    cliente.setAge(rs.getInt("IDADE"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    @Override
    public List<ClientModel> findAll(int page, int size) {
        List<ClientModel> clientes = new ArrayList<>();
        int offset = page * size;
        String sql = "SELECT * FROM CLIENT LIMIT ? OFFSET ?";

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, size);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClientModel cliente = new ClientModel(
                            rs.getLong("CODE_CLIENT"),
                            rs.getString("NOME"),
                            rs.getString("CPF"),
                            rs.getInt("IDADE"));
                    clientes.add(cliente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

   
    @Override
    public long countTotal() {
        String sql = "SELECT COUNT(*) FROM CLIENT";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao contar clientes", e);
        }
        return 0;
    }
    

    @Override
    public ClientModel update(ClientModel clientModel,Long id) {
        String sql = "UPDATE CLIENT SET NOME=?, CPF=?, IDADE=? WHERE CODE_CLIENT=?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, clientModel.getName());
            ps.setString(2, clientModel.getCpf());
            ps.setInt(3, clientModel.getAge());
            ps.setLong(4, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Falha ao atualizar dados do Cliente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar a entidade cliente", e);
        }
        ClientModel clienteUpdate =findById(id);
        System.out.println(clienteUpdate);
        return  clienteUpdate;  
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM CLIENT WHERE CODE_CLIENT=?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
}
