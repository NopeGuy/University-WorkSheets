<template>
  <app-header></app-header>
    <div class="client-details-wrapper">
        <div class="client-details">
            <div class="client-header">
                <h3>{{ clientDetails.nome }}</h3>
                <div class="back-item" @click="goBack">
                  <img class="back-arrow" src="../../public/left_arrow.svg" alt="Voltar" />
                </div>
            </div>
            <p><strong>Email:</strong> {{ clientDetails.email }}</p>
            <p><strong>Telefone:</strong> {{ clientDetails.telefone }}</p>
            <h4>Veículos Associados:</h4>
            <div class="vehicles-list">
                <div v-if="clientDetails.veiculos && clientDetails.veiculos.length > 0">
                  <div v-for="veiculo in clientDetails.veiculos" :key="veiculo.id" class="vehicle" @click="redirectToVehicleDetails(clientDetails.id, veiculo.id)">
                       {{ veiculo.id }}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        clientDetails: {}
      }
    },
    beforeRouteEnter(to, from, next) {
      axios.get(`http://localhost:3000/clients/${to.params.clientId}`)
      .then(response => {
        const clientData = response.data;
        // Agora, fazemos outra chamada para buscar os veículos
        axios.get('http://localhost:3000/vehicles')
          .then(vehiclesResponse => {
            const vehicles = vehiclesResponse.data;
            const clientVehicles = vehicles.filter(vehicle => vehicle.clientId === to.params.clientId);
            // Combinar os dados do cliente com seus veículos
            clientData.veiculos = clientVehicles;
            next(vm => vm.setClientDetails(clientData));
          })
          .catch(error => {
            console.log('Erro ao buscar veículos:', error);
            next(false);
          });
      })
      .catch(error => {
        console.log('Erro ao buscar detalhes do cliente:', error);
        next(false);
      });
    },
    methods: {
      goBack() {
        this.$router.go(-1);
      },
      setClientDetails(data) {
          this.clientDetails = data;
      },
      fetchVehicles(clientId) {
        axios.get('http://localhost:3000/vehicles')
        .then(response => {
                // Filtrar os veículos para encontrar aqueles associados ao clientId fornecido
                const clientVehicles = response.data.filter(v => v.clientId === clientId);
                this.$set(this.clientDetails, 'veiculos', clientVehicles);  // Adicionando veículos ao clientDetails
            })
            .catch(error => console.log('Erro ao buscar veículos:', error));
      },
      redirectToVehicleDetails(clientId, vehicleId) {
        this.$router.push({ path: `/clientes/${clientId}/${vehicleId}` });
      }
    }
  }
</script>
  
<style scoped>
img {
  margin-right: 10px;
  max-height: 40px;
  max-width: 40px;
}

.client-details-wrapper {
    padding: 20px;
    display: flex;
    justify-content: center; /* Centraliza o .client-details horizontalmente */
}

.client-details {
    border: 1px solid #000; /* borda preta como no print */
    padding: 20px;
    width: 1400px; /* Ajusta a largura ao conteúdo */
    padding-bottom: auto;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Sombra suave */
    margin-top: 20px; /* Espaçamento acima do detalhe */
    background-color: rgb(223, 213, 213);
}

h4 {
    padding-top: 15px; /* Espaço acima da seção de veículos */
    padding-bottom: 5px; /* Espaço abaixo do subtítulo */
    margin-bottom: 10px; /* Espaço antes da lista de veículos */
}

.vehicles-list {
    border-top: 1px solid #000; /* Linha acima da lista de veículos */
}

.vehicle {
    background-color: #e8e8e8; /* Cor de fundo para cada veículo */
    padding: 10px; /* Espaçamento interno para cada veículo */
    margin-bottom: 5px; /* Espaçamento entre veículos */
    margin-top: 5px;
    width: 72px;
    border: 1px solid #000;
    text-align: center;
    cursor: pointer;
}

.vehicle:hover {
    opacity: 0.7;
}

.vehicle:last-child {
    margin-bottom: 0; /* Remove a margem do último veículo */
}

.client-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 2px solid #000;
    padding-bottom: 10px;
}
  
.back-item {
    margin-left: auto; /* Move o back-item para a direita */
    cursor: pointer;
}
  
.back-arrow {
    transition: opacity 0.2s;
}
  
.back-arrow:hover {
    opacity: 0.7;
}
</style>