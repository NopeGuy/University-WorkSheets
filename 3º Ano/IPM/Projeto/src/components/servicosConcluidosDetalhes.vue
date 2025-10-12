<template>
  <div class="pagina-detalhada">
    <div class="ha">
      <h1>{{ title }}</h1>
      <div class="back-item" @click="goBack">
        <button class="close-button">&times;</button>
      </div>
    </div>
    <ul class="list-fix">
      <br>
      <li><strong>Cliente:</strong> {{ clientDetails.nome || 'Cliente não disponível' }}</li>
      <br><br>
      <li><strong>Contacto:</strong> {{ clientDetails.telefone || 'Contacto não disponível' }}</li>
      <br><br>
      <li><strong>Data e Hora de Agendamento:</strong> {{ formatDate(serviceDetails.data) }}</li>
      <br><br>
      <li><strong>Descrição:</strong> {{ serviceDetails.descrição || 'Descrição não disponível' }}</li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'PaginaDetalhada',
  data() {
    return {
      title: '', // Inicializa o título como vazio
      serviceDetails: {}, // Inicializa os detalhes do serviço como um objeto vazio
      clientDetails: {}, // Detalhes do cliente
    };
  },
  mounted() {
    this.title = this.$route.query.title;
    this.fetchServiceDetails(this.$route.params.id); // Busca os detalhes do serviço
  },
  methods: {
    async fetchServiceDetails(serviceId) {
      try {
        const serviceResponse = await axios.get(`http://localhost:3000/services/${serviceId}`);
        this.serviceDetails = serviceResponse.data; // Assume que a resposta é um objeto com os detalhes
        this.fetchClientDetails(this.serviceDetails.vehicleId);
      } catch (error) {
        console.error('Error fetching service details:', error);
      }
    },
    async fetchClientDetails(vehicleId) {
      try {
        // Obtém a lista de veículos
        const vehiclesResponse = await axios.get('http://localhost:3000/vehicles');
        const vehicle = vehiclesResponse.data.find(v => v.id === vehicleId);

        if (vehicle) {
          // Obtém a lista de clientes
          const clientsResponse = await axios.get('http://localhost:3000/clients');
          this.clientDetails = clientsResponse.data.find(c => c.id === vehicle.clientId) || {};
        }
      } catch (error) {
        console.error('Error fetching client details:', error);
      }
    },
    formatDate(date) {
      if (!date || !date.dia || !date.mes || !date.ano || date.hora == null || date.minutos == null) {
        return 'Data não associada';
      }
      return `${date.dia.toString().padStart(2, '0')}-${date.mes.toString().padStart(2, '0')}-${date.ano} ${date.hora.toString().padStart(2, '0')}:${date.minutos.toString().padStart(2, '0')}`;
    },
    goBack() {
      this.$router.go(-1);
    }
  }
}
</script>

<style>
.pagina-detalhada {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 50%;
  height: auto;
  background-color: gray;
  color: white;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  z-index: 1000;
}

.ha {
  display: flex;
  justify-content: center; /* Mantém o título centralizado */
  align-items: center;
  padding: 5px 10px;
  border-bottom: 2px solid black;
  background-color: #333;
  position: relative; /* Necessário para posicionamento absoluto do botão */
}

.content {
  flex-grow: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}

.close-button {
  position: absolute; /* Posiciona o botão de forma absoluta */
  right: 10px; /* Distância da direita do cabeçalho */
  top: 50%; /* Centraliza verticalmente */
  transform: translateY(-50%); /* Ajuste para alinhar corretamente */
  background: none;
  border: none;
  font-size: 3rem;
  color: white;
  cursor: pointer;
}

.list-fix {
  list-style: none; 
  padding-top: 0px;
  margin-bottom: 40px; /* Espaço entre o botão e o fim do container */
  font-size: 20px; 
}
</style>