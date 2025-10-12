<template>
  <app-header></app-header>
  <div class="service-container">
    <div class="service-box">
      <h2>Clientes - Posto 3 [Combustão]</h2>
      <div v-for="client in clients" :key="client.id" class="client-box">
        <button @click="redirectToClientDetails(client.id)">
          {{ client.nome }}
        </button>
      </div>
    </div>
  </div>
</template>
  
<script>
import axios from 'axios';

export default {
  data() {
    return {
      clients: [], // Lista de clientes
    }
  },
  mounted() {
    this.fetchClients();
  },
  methods: {
    async fetchClients() {
      try {
        const response = await axios.get('http://localhost:3000/clients');
        this.clients = response.data;
      } catch (error) {
        console.error('There was an error fetching the client data:', error);
      }
    },
    redirectToClientDetails(clientId) {
      this.$router.push(`/clientes/${clientId}`);
    }
  }
}
</script>
  
<style scoped>
.client-box button {
  margin: 10px;
  padding: 10px;
}

.client-details {
  background-color: lightgray;
  padding: 20px;
  margin-top: 10px;
}

.service-container {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.service-box {
  background-color: #ccc; /* Cor cinza para os retângulos */
  border-radius: 4px;
  padding: 15px;
  width: 100%; /* Ajustado conforme o layout da página */
  margin-right: 2%; /* Adiciona um espaçamento à direita */
}

h2 {
  border-bottom: 1px solid black; /* Add black line under the name */
  font-size: 1.2rem;
  padding-bottom: 10px; /* Add some space under the name */
}

img {
  margin-right: 10px;
  max-height: 40px;
  max-width: 40px;
}
</style>