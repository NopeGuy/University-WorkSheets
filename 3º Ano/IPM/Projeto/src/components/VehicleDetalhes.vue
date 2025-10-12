<template>
  <app-header></app-header>
    <div class="client-details-wrapper">
      <div class="client-details">
            <div class="client-header">
                <h3>{{ vehicleDetails.id }}</h3>
                <div class="back-item" @click="goBack">
                    <img class="back-arrow" src="../../public/left_arrow.svg" alt="Voltar" />
                </div>
            </div>
            <div>
                <p>Tipo: {{ vehicleDetails.vehicleTypeId }}</p>
                <p v-if="vehicleDetails.vehicleTypeId === 'eletrico' || vehicleDetails.vehicleTypeId === 'hibrido'">Potência: {{ vehicleDetails.potencia }} kW</p>
                <p v-if="vehicleDetails.cilindrada">Cilindrada: {{ vehicleDetails.cilindrada }} cc</p>
                <p>Kms: {{ vehicleDetails.kms }} km</p>
            </div>
        </div>
    </div>
</template>
  
<script>
  import axios from 'axios';

  export default {
    data() {
      return {
        vehicleDetails: {}
      };
    },
    mounted() {
      this.fetchVehicleDetails();
    },
    methods: {
        goBack() {
            this.$router.go(-1);
        },
        fetchVehicleDetails() {
            // Suponha uma API ou método para buscar os detalhes
            axios.get(`http://localhost:3000/vehicles/${this.$route.params.vehicleId}`)
            .then(response => {
                this.vehicleDetails = {
                    ...response.data,
                    vehicleTypeId: response.data['vehicle-typeId']
                };
            })
            .catch(error => {
                console.error('Erro ao buscar detalhes do veículo:', error);
            });
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

.client-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 2px solid #000;
    padding-bottom: 10px;
}

h4 {
    padding-top: 15px; /* Espaço acima da seção de veículos */
    padding-bottom: 5px; /* Espaço abaixo do subtítulo */
    margin-bottom: 10px; /* Espaço antes da lista de veículos */
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