<template>
  <div>
    <app-header></app-header>
    <div class="service-container">
      <div class="service-box">
        <h2>Serviços Concluídos - Posto 3 [Combustão]</h2>
        <!-- Iterar sobre serviços concluídos para listar as placas de veículos -->
        <div v-for="service in completedServices" :key="service.id" class="vehicle-id clickable">
          <!-- Usando router-link com query params -->
          <router-link :to="{ path: `/servicos-concluidos/${service.id}`, query: { title: getServiceDescription(service['service-definitionId']) + ' - ' + service.vehicleId }}" class="service-done">
            <img src="../../public/sure.png"/> <a class="text-service-done">{{ getServiceDescription(service['service-definitionId']) }} - {{ service.vehicleId }}</a>
          </router-link>
        </div>
      </div>
      <router-view/>
      <div class="legend-box">
        <h2>Data de Agendamento:</h2>
        <!-- Iterar sobre serviços concluídos para listar as datas de conclusão -->
        <div v-for="service in completedServices" :key="service.id">
          <!-- Verifica se todos os componentes da data estão presentes -->
          <div v-if="service.data && service.data.dia && service.data.mes && service.data.ano">
            <a class="text-service-done2">{{ service.data.dia }}/{{ service.data.mes }}/{{ service.data.ano }}</a>
          </div>
          <!-- Caso contrário, exibe uma entrada em branco -->
          <div v-else><a class="text-service-done2">Data não associada</a></div>
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
      services: [],
      serviceDefinitions: [] // Suponha que você também carrega isso de algum lugar
    };
  },
  methods: {
    getServiceDescription(serviceDefinitionId) {
      const definition = this.serviceDefinitions.find(def => def.id === serviceDefinitionId);
      return definition ? definition.descr : 'Serviço não definido';
    }
  },
  computed: {
    completedServices() {
      return this.services.filter(service => service.estado === "realizado");
    }
  },
  mounted() {
    axios.all([
      axios.get('http://localhost:3000/services'),
      axios.get('http://localhost:3000/service-definitions')
    ]).then(axios.spread((servicesResponse, definitionsResponse) => {
      this.services = servicesResponse.data;
      this.serviceDefinitions = definitionsResponse.data;
    })).catch(error => {
      console.error("Failed to load data", error);
    });
  }
}
</script>


<style scoped>
.service-container {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.service-box {
  background-color: #ccc; /* Cor cinza para os retângulos */
  border-radius: 4px;
  padding: 15px;
  width: 70%; /* Ajustado conforme o layout da página */
  margin-right: 2%; /* Adiciona um espaçamento à direita */
}

.legend-box {
  background-color: #ccc; /* Cor cinza para os retângulos */
  border-radius: 4px;
  padding: 15px;
  width: 28%; /* Ajustado para acomodar a margem */
  /* Se necessário, adicione uma margem à esquerda para alinhar com o serviço:
  margin-left: 2%; */
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

.clickable {
  cursor: pointer;
}

.dates-ligma {
  margin-top: 14px;
}

.text-service-done2 {
  display: flex; /* Usar flexbox para alinhar */
  align-items: center; /* Alinhar verticalmente */
  justify-content: space-between; /* Espaço igual entre itens para alinhamento horizontal */
  padding: 10px 0; /* Ajustar o padding para dar espaço vertical */
  height: 50px; /* Definir uma altura fixa para todos os itens para garantir consistência */
  border-bottom: 1px solid black;
}

.service-box, .legend-box {
  display: flex;
  flex-direction: column; /* Empilhar itens verticalmente */
  justify-content: flex-start; /* Alinhar itens ao topo */
}

.text-service-done2 {
  text-align: center; /* Centralizar texto se necessário */
  flex-grow: 1; /* Permitir que o item cresça se necessário */
}

.service-done {
  display: flex; /* Continua a usar flexbox para a disposição dos itens */
  align-items: center; /* Alinha verticalmente os itens dentro do container */
  padding: 10px 0; /* Ajustar o padding para dar espaço vertical */
  height: 50px; /* Definir uma altura fixa para todos os itens para garantir consistência */
  border-bottom: 1px solid black;
}
</style>