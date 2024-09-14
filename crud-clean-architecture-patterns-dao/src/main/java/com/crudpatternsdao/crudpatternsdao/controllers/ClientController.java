package com.crudpatternsdao.crudpatternsdao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.crudpatternsdao.crudpatternsdao.application.usecase.clienteusecase.ClientRequestDTO;
import com.crudpatternsdao.crudpatternsdao.application.usecase.clienteusecase.ClientResponseDTO;
import com.crudpatternsdao.crudpatternsdao.application.usecase.clienteusecase.IClientMapperDTO;
import com.crudpatternsdao.crudpatternsdao.application.usecase.clienteusecase.IClientUseCase;
import com.crudpatternsdao.crudpatternsdao.domain.entities.Client;
import com.crudpatternsdao.crudpatternsdao.infrastructure.Model.ClientModel;
import com.crudpatternsdao.crudpatternsdao.infrastructure.mapper.IClientMapperModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
public class ClientController {

    private IClientMapperDTO iClientMapperDTO;

    private IClientMapperModel iClientMapperModel;

    private IClientUseCase iClientUseCase;

    @Autowired
    public ClientController(IClientMapperDTO iClientMapperDTO, IClientMapperModel iClientMapperModel,
            IClientUseCase iClientUseCase) {

        this.iClientMapperDTO = iClientMapperDTO;

        this.iClientMapperModel = iClientMapperModel;

        this.iClientUseCase = iClientUseCase;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        Client client = iClientMapperDTO.toClient(clientRequestDTO);
        ClientModel clientModel = iClientUseCase.create(client);
        Client clientValidation = iClientMapperModel.toClient(clientModel);
        ClientResponseDTO clientResponseDTO = iClientMapperDTO.toClientResponseDto(clientValidation);
        return new ResponseEntity<>(clientResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        try {
            ClientModel clientModel = iClientUseCase.findById(id);
            Client client = iClientMapperModel.toClient(clientModel);
            ClientResponseDTO clientResponseDTO = iClientMapperDTO.toClientResponseDto(client);
            return new ResponseEntity<>(clientResponseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/clients")
    public ResponseEntity<Map<String, Object>> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> result = iClientUseCase.findAll(page, size);
    
            List<ClientModel> clientModelList = (List<ClientModel>) result.get("clients");
            long totalClients = (long) result.get("total");
    
            List<ClientResponseDTO> clientResponseDTOList = clientModelList.stream()
                    .map(iClientMapperModel::toClient)
                    .map(iClientMapperDTO::toClientResponseDto)
                    .collect(Collectors.toList());
    
            Map<String, Object> response = new HashMap<>();
            response.put("clients", clientResponseDTOList);
            response.put("currentPage", page);
            response.put("totalPages", (int) Math.ceil((double) totalClients / size));
            response.put("totalElements", totalClients);
    
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
    


    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id,
            @RequestBody ClientRequestDTO clientRequestDTO) {
        try {
            Client client = iClientMapperDTO.toClient(clientRequestDTO);
            ClientModel updatedClientModel = iClientUseCase.update(client, id);
            Client clientUpdate = this.iClientMapperModel.toClient(updatedClientModel);
            ClientResponseDTO responseDTO = iClientMapperDTO.toClientResponseDto(clientUpdate);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            iClientUseCase.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
