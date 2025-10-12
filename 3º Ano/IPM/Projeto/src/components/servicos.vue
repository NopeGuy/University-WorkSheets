<template>
  <app-header></app-header>
  <div class="content">
    <!-- Seletor de Clientes -->
    <div class="input-container">
      <input list="client-list" v-model="selectedClientName" @input="filterClients" placeholder="Nome do Cliente" class="input-field">
      <datalist id="client-list">
        <option v-for="client in filteredClients" :key="client.id" :value="client.nome">{{ client.nome }}</option>
      </datalist>
    </div>

    <!-- Seletor de Veículos, visível apenas se um cliente está selecionado -->
    <div class="input-container" v-if="selectedClientId">
      <input list="vehicle-list" v-model="selectedVehicleId" @input="filterVehicles" placeholder="ID do Veículo" class="input-field">
      <datalist id="vehicle-list">
        <option v-for="vehicle in filteredVehicles" :key="vehicle.id" :value="vehicle.id">{{ vehicle.id }}</option>
      </datalist>
    </div>

    <!-- Área para descrição do serviço -->
    <div class="input-container">
      <textarea v-model="serviceDescription" placeholder="Descrição do Serviço" class="input-field textarea-field"></textarea>
    </div>

    <!-- Botão para criar serviços, habilitado apenas se um veículo foi selecionado -->
    <button class="btn-servicos" @click="goToNovoServico" :disabled="!selectedVehicleId">Serviços</button>
  </div>
  <router-view></router-view>
</template>


<script>
import axios from 'axios';

export default {
  data() {
    return {
      clients: [],
      vehicles: [],
      filteredClients: [],
      filteredVehicles: [],
      selectedClientId: null,
      selectedClientName: '',
      selectedVehicleId: null,
      serviceDescription: '',
    };
  },
  watch: {
    selectedClientName(newValue) {
      const client = this.clients.find(c => c.nome === newValue);
      if (client) {
        this.selectClient(client.id);
      }
    }
  },
  methods: {
    goToNovoServico(vehicleId) {
      this.$router.push({
        name: 'NovoServico',
        params: {
          vehicleId: this.selectedVehicleId,  // Certifique-se de que este ID está sendo corretamente capturado
          serviceDescription: this.serviceDescription  // Assegure-se de que esta é a variável correta
        }
      });
    },
    fetchClients() {
      axios.get('http://localhost:3000/clients').then(response => {
        this.clients = response.data;
        this.filteredClients = response.data;
      }).catch(error => {
        console.error('Error fetching clients:', error);
      });
    },
    fetchVehicles() {
      if (!this.selectedClientId) return;
      axios.get(`http://localhost:3000/vehicles?clientId=${this.selectedClientId}`).then(response => {
        this.vehicles = response.data;
        this.filteredVehicles = response.data;
      }).catch(error => {
        console.error('Error fetching vehicles:', error);
      });
    },
    filterVehicles() {
      // Assume IDs are typed directly, if invalid ID is typed, it won't match any vehicle
      const vehicle = this.vehicles.find(v => v.id === this.selectedVehicleId);
      this.filteredVehicles = vehicle ? [vehicle] : [];
    },
    selectClient(clientId) {
      this.selectedClientId = clientId;
      this.fetchVehicles();
    }
  },
  created() {
    this.fetchClients();
  },
  // Supondo que o método seja algo como this.navegarParaNovoServico():
  navegarParaNovoServico() {
    this.$router.push({
      name: 'NovoServico',
      params: {
        vehicleId: this.selectedVehicleId,  // Certifique-se de que este ID está sendo corretamente capturado
        serviceDescription: this.serviceDescription  // Este é o campo onde a descrição é armazenada
      }
    });
  }
}
</script>

<style scoped>
h2 {
  border-bottom: 1px solid black; /* Add black line under the name */
  font-size: 1.2rem;
  padding-bottom: 10px; /* Add some space under the name */
}

.content {
    max-width: 500px; /* Largura máxima para os campos */
    margin: 0 auto; /* Centraliza na página horizontalmente */
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center; /* Centraliza dentro do contêiner flexível verticalmente */
    height: 70vh; /* Faz o contêiner ter pelo menos a altura da tela */
    box-sizing: border-box; /* Inclui padding no cálculo da altura e largura */
  }
  
  
.input-container {
    width: 100%;
    margin: 10px 0;
}
  
.input-field {
    width: 100%;
    padding: 15px; /* Maior padding para tornar os campos maiores */
    margin-top: 5px;
    box-sizing: border-box; /* Para incluir padding na largura total */
    border: 1px solid #ccc;
    border-radius: 5px;
}
  
.textarea-field {
    height: 150px; /* Altura fixa para a área de texto */
    resize: none; /* Impede o redimensionamento */
}
  
.btn-servicos {
    width: 100%;
    padding: 15px 20px; /* Aumenta o padding para fazer um botão maior */
    font-size: 1rem; /* Tamanho da fonte do botão */
    margin-top: 20px; /* Espaçamento acima do botão */
    /* ... estilos existentes do botão ... */
}
</style>