<template>
  <div class="service-container">
    <div class="ha">
      <h1>Serviço a criar</h1>
      <div class="back-item" @click="goBack">
        <button class="close-button">&times;</button>
      </div>
    </div>
    <div class="date-choose">
      <h2>Data Limite:</h2>
      <input type="date" v-model="serviceDate">
      <input type="time" v-model="serviceTime" step="60"> <!-- Adicionando campo de tempo -->
    </div>
    <div v-if="loading">A Carregar serviços...</div>
    <div v-else>
      <div v-for="service in applicableServices" :key="service.id" class="checkbox-services">
        <input type="radio" :value="service.id" v-model="selectedServices">
        {{ service.descr }}
      </div>         
    </div>
    <button @click="submitServices" class="button-submit">Criar Serviço</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: {
    vehicleId: {
      type: String,
      required: true
    },
    serviceDescription: {
      type: String,
      required: false,  // 'required: false' porque a descrição pode não ser obrigatória
      default: ''       // Valor padrão como uma string vazia se nenhuma descrição for passada
    }
  },
  data() {
    return {
      serviceDate: '',
      serviceTime: '',
      applicableServices: [],
      selectedServices: null,  // Alterado para suportar a seleção de um único serviço
      loading: false
    };
  },
  mounted() {
    this.fetchVehicleAndApplicableServices();
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    async fetchVehicleAndApplicableServices() {
      try {
        this.loading = true;
        const vehicleResponse = await axios.get(`http://localhost:3000/vehicles/${this.vehicleId}`);
        const vehicleType = vehicleResponse.data['vehicle-typeId'];
        const vehicleTypeResponse = await axios.get(`http://localhost:3000/vehicle-types/${vehicleType}`);
        const serviceIds = vehicleTypeResponse.data['serviços'];
        const servicesPromises = serviceIds.map(id => axios.get(`http://localhost:3000/service-definitions/${id}`));
        const servicesResponses = await Promise.all(servicesPromises);
        this.applicableServices = servicesResponses.map(response => response.data);
        this.loading = false;
      } catch (error) {
        console.error('Erro ao carregar serviços:', error);
        this.loading = false;
      }
    },
    submitServices() {
      if (!this.selectedServices ||  this.selectedServices.length === 0) {
        alert('Por favor, selecione pelo menos um serviço.');
        return;
      }
      this.addNewServices();
    },
    async addNewServices() {
      try {
        const response = await axios.get('http://localhost:3000/services');
        const services = response.data;
        let nextServiceId = services.reduce((maxId, service) => parseInt(service.id.substring(1)), 0) + 1;
        const serviceDateTime = new Date(this.serviceDate + 'T' + this.serviceTime);

        const newService = {
          id: `s${nextServiceId}`,
          vehicleId: this.vehicleId,
          ['service-definitionId']: this.selectedServices,
          estado: 'nafila',
          agendamento: this.serviceDate ? 'programado' : 'filaDeEspera',
          data: this.serviceDate ? {
            dia: serviceDateTime.getDate(),
            mes: serviceDateTime.getMonth() + 1,
            ano: serviceDateTime.getFullYear(),
            hora: serviceDateTime.getHours(),
            minutos: serviceDateTime.getMinutes()
          } : null,
          descrição: this.serviceDescription || 'Sem descrição adicionada.'
        };

        await axios.post('http://localhost:3000/services', newService);
        alert('Serviço adicionado com sucesso!');
        this.goBack();
      } catch (error) {
        console.error('Erro ao adicionar serviço:', error);
        alert('Erro ao adicionar serviço. Por favor, tente novamente.\n\nDetalhes do erro: ' + JSON.stringify(newService, null, 2));
      }
    }
  }
}
</script>

<style scoped>
.service-container {
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

  .date-choose{
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 5px 10px;
    position: relative; 
  }

  .checkbox-services{
    display: flex;
    justify-content: center;
    align-items: center;
    padding-bottom: px;
    position: relative; 
  }

  .button-submit{
    width: 100%;
    padding: 15px 20px; /* Aumenta o padding para fazer um botão maior */
    font-size: 1rem; /* Tamanho da fonte do botão */
    margin-top: 20px; /* Espaçamento acima do botão */
  }
</style>