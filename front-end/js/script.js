const sideMenu = document.querySelector("aside");
const menuBtn = document.querySelector("#menu-btn");
const closeBtn = document.querySelector("#close-btn");
const themeToggler = document.querySelector(".theme-toggler");
const tbodyAllClients =  document.querySelector("#tbody-clients-all");
const createBtn = document.querySelector(".btn-create-client");
const pageNumbersContainer = document.querySelector("#page-numbers");
const filterClientNumber = document.querySelector(".button-filter-client-number");
let modal = document.getElementById("my-modal");
let btnModal = document.querySelectorAll(".button-modal");
let spanClose = document.getElementsByClassName("close-modal")[0];

const containerCreateDeleteUpdateclientsCrud = document.querySelector(
  ".container-create-delete-update-clients-crud"
);
const titleCreateDeleteUpdate = document.querySelector(
  ".title-create-delete-update-client"
);

const tbodyclientsRecentToday = document.querySelector("#tbody-clients-today");
const url = "http://localhost:8080/client";

async function getClient(id) {
  try {
    const response = await fetch(`${url}/${id}`, {
      method: "GET",
    });
    if (!response.ok) {
      throw new Error("Erro ao buscar Clientes");
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Erro:", error);
    throw error;
  }
}

document.addEventListener("DOMContentLoaded", () => {
  const pageNumbersContainer = document.querySelector("#page-numbers");

  let currentPage = 0;
  const pageSize = 10;
  let totalPages = 0;

  const getAllClients = async (currentPage, pageSize) => {
    try {
      const response = await fetch(`${url}/clients?page=${currentPage}&size=${pageSize}`, {
        method: "GET",
      });
      if (!response.ok) {
        throw new Error(`Erro HTTP! Status: ${response.status}`);
      }
      const result = await response.json();
      console.log(result);
      if (result.clients && Array.isArray(result.clients)) {
        totalPages = result.totalPages;
        return result.clients;
      } else {
        console.error("Resposta do servidor não contém a estrutura esperada.");
        return [];
      }
    } catch (error) {
      console.error("Erro ao buscar Clientes:", error);
      return [];
    }
  };

  const loadClients = async () => {
    let clients = await getAllClients(currentPage, pageSize);
    tbodyAllClients.innerHTML = "";

    if (clients && clients.length > 0) {
      clients.forEach((client) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${client.codeClient}</td>
          <td>${client.name}</td>
          <td>${client.cpf}</td>
          <td>${client.age}</td>
          <td>
            <span id="${client.codeClient}"   class="material-symbols-outlined btn-edit-client button-modal">
              edit
            </span>
            <span id="${client.codeClient}" class="material-symbols-outlined btn-delete-client button-modal">
              delete
            </span>
          </td>
        `;
        tbodyAllClients.appendChild(tr);
      });
    } else {
      console.error(
        "Nenhum cliente encontrado ou estrutura de dados inválida."
      );
    }
    attachModalButtonListeners();
    updatePagination();
  };
 

  
  const updatePagination = () => {
    pageNumbersContainer.innerHTML = "";
  
    const prevButton = document.getElementById("prev-page");
    prevButton.style.display = currentPage > 0 ? "inline-block" : "none"; 
  
    const nextButton = document.getElementById("next-page");
    nextButton.style.display = currentPage < totalPages - 1 ? "inline-block" : "none"; 
  
    let startPage = Math.max(1, currentPage - 1 + 1);
    let endPage = Math.min(totalPages, currentPage + 3);
  
    if (currentPage <= 2) {
      endPage = Math.min(totalPages, 5);
    }
    if (currentPage >= totalPages - 2) {
      startPage = Math.max(1, totalPages - 4);
    }
  
    for (let i = startPage; i <= endPage; i++) {
      const pageNumber = document.createElement("span");
      pageNumber.textContent = i; 
      pageNumber.className = "page-number" + (i === currentPage + 1 ? " active" : ""); 
      pageNumber.onclick = () => changePage(i);
      pageNumbersContainer.appendChild(pageNumber);
    }
  };
 

  const changePage = async (page) => {
    const backendPage = page - 1;
    
    if (backendPage < 0 || backendPage >= totalPages) return;
  
    currentPage = backendPage;
    await loadClients();
  };

  document.getElementById("prev-page").onclick = () => {
    if (currentPage > 1) {
      console.log("prev-page - 1")
      changePage(currentPage - 1);
    }
  };

  document.getElementById("next-page").onclick = () => {
    if (currentPage < totalPages) {
      console.log("next-page + 1");
      changePage(currentPage + 1);
    }
  };

  loadClients();
});

const searchNumber = async () => {
  const input = document.getElementById("input-filter-client");
  const id = input.value;
  try {
    const client = await getClient(id);
    if (client != null) {
      tbodyAllClients.innerHTML = "";
      const tr = document.createElement("tr");
      const trContent = `
        <td>${client.codeClient}</td>
        <td>${client.name}</td>
        <td>${client.cpf}</td>
        <td>${client.age}</td>
        <td><span id="${client.codeClient}" class="material-symbols-outlined btn-edit-client button-modal">
        edit</span>
        <span id="${client.codeClient}" class="material-symbols-outlined btn-delete-client button-modal">
        delete</span></td>
      `;
      tr.innerHTML = trContent;
      tbodyAllClients.appendChild(tr);
    }
    attachModalButtonListeners();
  } catch (error) {
    console.error("Erro ao buscar produto:", error);
  }
};

filterClientNumber.addEventListener("click",searchNumber );

filterClientNumber.addEventListener("keydown", searchNumber );

const attachModalButtonListeners = () => {
  document.querySelectorAll(".button-modal").forEach((btnModal) => {
    btnModal.addEventListener("click", function (event) {
      modal.style.display = "block";
      const classList = event.target.classList;

      let action = null;
      if (classList.contains("btn-create-client")) {
        action = "create";
      } else if (classList.contains("btn-edit-client")) {
        action = "edit";
      } else if (classList.contains("btn-delete-client")) {
        action = "delete";
      }

      switch (action) {
        case "create":
          createClient();
          break;

        case "edit":
          const idEdit = event.target.closest(".btn-edit-client").id;
          editClient(idEdit);
          break;

        case "delete":
          const idDelete = event.target.closest(".btn-delete-client").id;
          deleteClient(idDelete);
          break;

        default:
          modal.style.display = "none";
          break;
      }
    });
  });
};

const createClient = function () {
  containerCreateDeleteUpdateclientsCrud.innerHTML = "";

  titleCreateDeleteUpdate.innerHTML = "Novo  Cliente";

  const containerCreateForm = `<div class="create-update-delete-form-client">
      <form action="">
              <label for="nome">Nome</label>
              <input type="text" id="nome" placeholder="Nome Cliente" required>
              <label for="cpf">CPF</label>
              <input type="text" id="cpf" placeholder="XXX.XXX.XXX-XX" required>
              <div class="form-content-number">
                  <label for="age">Idade</label>
                  <input type="number" id="age" min="0" step="0" required>
              </div>
          <button  id="btn-create-client-confirm" class="btn-add-confirm-or-delete" type="button">CONFIRMAR</button>
      </form>
  </div>
  `;
  containerCreateDeleteUpdateclientsCrud.innerHTML = containerCreateForm;
  const btnCreateConfirm = document.querySelector("#btn-create-client-confirm");
  btnCreateConfirm.addEventListener("click", confirmFormCreater);
};

createBtn.addEventListener("click", createClient);

const confirmFormCreater = async (event) => {
  event.preventDefault();

  const name = document.getElementById("nome").value;
  const cpf = document.getElementById("cpf").value;
  const age = parseFloat(document.getElementById("age").value);

  const data = {
    name,
    cpf,
    age,
  };
  try {
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });
    if (!response.ok) {
      throw new Error("Erro ao enviar dados para a API");
    }else{
      cleanInputCreate();
      alert("Cliente criado com sucesso!");
    }
    const responseData = await response.json();
    console.log("Resposta da API:", responseData);
  } catch (error) {
    console.error("Erro ao enviar dados para a API:", error);
  }
};

function cleanInputCreate() {
  let name = document.getElementById("nome");
  if (name) {
    name.value = "";
  }

  let cpf = document.getElementById("cpf");
  if (cpf) {
    cpf.value = ""; 
  }

  let age = document.getElementById("age");
  if (age) {
    age.value = ""; 
  }
}


const editClient = async function (id) {
  const client = await getClient(id);
  containerCreateDeleteUpdateclientsCrud.innerHTML = "";
  titleCreateDeleteUpdate.innerHTML = "Editar Cliente";

  const containerEdit = `
    <div class="create-update-delete-form-client">
      <div class="container-edit">
           <label for="nome">Nome</label>
          <input type="text" id="${client.codeClient}" class="name-edit" value="${client.name}">
          <label for="cpf">CPF</label>
          <input type="text" id="cpf-edit" value="${client.cpf}">
          <div class="edit-content-number">
           <label for="age">Idade</label>
            <input type="number" id="age-edit" value="${client.age}">
          </div>
        <button id="btn-edit-client-confirm" class="btn-add-confirm-or-delete" type="button">CONFIRMAR</button>
      </div>
    </div>
  `;
  containerCreateDeleteUpdateclientsCrud.innerHTML = containerEdit;

  const btnEditConfirm = document.querySelector("#btn-edit-client-confirm");
  btnEditConfirm.addEventListener("click", confirmFormEdit);
};

const confirmFormEdit = async (event) => {
  event.preventDefault();
  const id = document.querySelector(".name-edit").id;
  const name = document.querySelector(".name-edit").value;
  const cpf = document.getElementById("cpf-edit").value;
  const age = parseInt(document.getElementById("age-edit").value);

  const data = {
    name,
    cpf,
    age,
  };
  try {
    const response = await fetch(url + "/" + id, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      const errorMessage = await response.text();
      throw new Error(`Erro ao Enviar para API: ${errorMessage}`);
    }else{
      const clientUpdate = await response.json();
      alert(`Cliente  ${clientUpdate.name} Atualizado`);
      window.location.reload(); 
    }
    console.log("Respota da API:", responseData);

  } catch (error) {
    console.error("Error al enviar datos a la API:", error);
  }
  modal.style.display = "none";
  titleCreateDeleteUpdate.innerHTML = "";
  containerCreateDeleteUpdateclientsCrud.innerHTML = "";
};

const deleteClient = async function (idDelete) {
      const client = await getClient(idDelete);
      containerCreateDeleteUpdateclientsCrud.innerHTML = "";
      titleCreateDeleteUpdate.innerHTML = "Deletar o Cliente?";

      const containerDelete = `
          <div class="create-update-delete-form-client">
          <div class="container-delete">
                 <div class="delete-content-number">
                  <h3>Código Cliente</h3>
                   <p class="id-delete" id="${client.codeClient}">${client.codeClient}</p>
                  </div>
                  <h3>Nome</h3>
                  <p class="name-delete">${client.name}</p>
                  <h3>CFP</h3>
                        <p class="name-delete">${client.cpf}</p>
              <div class="delete-group-number">
                  <div class="delete-content-number">
                      <h3>Idade</h3>
                      <p class="age-delete">${client.age}</p>
                  </div>
              </div>
              <div class="delete-content-select">
              <button id="btn-delete-client-confirm" class="btn-add-confirm-or-delete"
                  type="button">CONFIRMAR</button>
          </div>
      </div>
          `;
      containerCreateDeleteUpdateclientsCrud.innerHTML = containerDelete;
      const btnDeleteConfirm = document.querySelector("#btn-delete-client-confirm");
      btnDeleteConfirm.addEventListener("click", confirmDelete);
 };

const confirmDelete = async (event) => {
  event.preventDefault();
  const idDelete = document.querySelector(".id-delete");
  try {
    const response = await fetch(url + "/" + idDelete.id, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    });
    if (!response.ok) {
      throw new Error("Erro ao Deletar dados da API");
    }
    else{
      modal.style.display = "none";
      alert("Cliente Deletado");
      window.location.reload(); 
    }
  } catch (error) {
    console.error("Erro ao Deletar dados da API:", error);
  }
};

btnModal.onclick = function () {
  modal.style.display = "block";
};

spanClose.onclick = function () {
  modal.style.display = "none";
};

window.onclick = function (event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
};
