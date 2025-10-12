<template>
  <app-header></app-header>
  <div class="service-container">
    <div class="service-box">
      <h2>Serviços Atribuídos - Posto 3 [Combustão]</h2>
      <div class="header-item-bottom">
        <div class="header-item">
          Ordem de Chegada
          <img :src="sortKey === 'arrival' && sortDesc ? '/down_arrow.png' : '/up_arrow.png'" class="img2" @click="toggleSort('arrival')"/>
        </div>
        <div class="header-item">
          Duração Média
          <img :src="sortKey === 'duration' && sortDesc ? '/down_arrow.png' : '/up_arrow.png'" class="img2" @click="toggleSort('duration')"/>
        </div>
        <div class="header-item">
          Data Limite
          <img :src="sortKey === 'date' && sortDesc ? '/down_arrow.png' : '/up_arrow.png'" class="img2" @click="toggleSort('date')"/>
        </div>
      </div>
      <ul>
        <li v-for="service in sortedServices" :key="service.id" class="service-details">
          <router-link :to="`/pagina-inicial/${service.id}`" class="service-item">
            <img :src="getImage(service)" :alt="getImageAlt(service)" />
            <a class="text-service-item">{{ getServiceDescription(service['service-definitionId']) }} - {{ service.vehicleId }}</a>
          </router-link>
          <span class="service-item2">
            <a class="text-service-item2">{{ getServiceDuration(service['service-definitionId']) }}</a>
          </span>
          <span class="service-item2">
            <a class="text-service-item2">{{ formatDate(service.data) }}</a>
          </span>
        </li>
      </ul>
    </div>
    <router-view/>
    <div class="legend-box">
      <h2 class="legend-header">Legenda:</h2>
      <ul class="legend-list">
        <li><img src="/xmark.png" alt="Não realizado" /> Não realizado</li>
        <li><img src="/suspended.png" alt="Suspenso" /> Suspenso</li>
        <li><img src="/exclamation.png" alt="Em atraso" /> Em atraso</li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      services: [],
      serviceDefinitions: [],
      sortKey: 'arrival',
      sortDesc: true
    };
  },
  computed: {
    sortedServices() {
      const filteredServices = this.services.filter(service => service.estado !== "realizado");
      if (!this.sortKey) return filteredServices;
      return filteredServices.sort((a, b) => {
        let valueA = this.getValue(a, this.sortKey);
        let valueB = this.getValue(b, this.sortKey);

        // Tratamento especial para nulls em ordenação de datas
        if (this.sortKey === 'date') {
          if (!valueA) return 1; // Empurra serviços sem data para o final se sortDesc é verdadeiro
          if (!valueB) return -1; // Empurra serviços sem data para o final se sortDesc é verdadeiro
        }

        if (typeof valueA === 'string') valueA = valueA.replace(' min', '');
        if (typeof valueB === 'string') valueB = valueB.replace(' min', '');

        return (valueA - valueB) * (this.sortDesc ? 1 : -1);
      });
    }
  },
  methods: {
    toggleSort(key) {
      if (this.sortKey === key && this.sortDesc) {
        return; // Prevent flipping the arrow if it's already down
      }
      this.sortKey = key;
      this.sortDesc = true; // Always sort descending when changing sort keys
    },
    getValue(service, key) {
      switch (key) {
        case 'duration':
          return this.getServiceDuration(service['service-definitionId']);
        case 'date':
          return service.data ? new Date(service.data.ano, service.data.mes - 1, service.data.dia, service.data.hora, service.data.minutos).getTime() : null;
        case 'arrival':
          return service.id;
        default:
          return 0;
      }
    },
    fetchServices() {
      axios.get('http://localhost:3000/service-definitions')
        .then(response => {
          this.serviceDefinitions = response.data;
          return axios.get('http://localhost:3000/services');
        })
        .then(response => {
          this.services = response.data;
        })
        .catch(error => {
          console.error('Error fetching services or definitions:', error);
        });
    },
    getServiceDescription(serviceDefinitionId) {
      const definition = this.serviceDefinitions.find(def => def.id === serviceDefinitionId);
      return definition ? definition.descr : 'Serviço não definido';
    },
    getServiceDuration(serviceDefinitionId) {
      const definition = this.serviceDefinitions.find(def => def.id === serviceDefinitionId);
      return definition ? `${definition.duração} min` : '';
    },
    formatDate(date) {
      if (!date || !date.dia || !date.mes || !date.ano || !date.hora) {
        return ''; // Retorna string vazia se qualquer propriedade necessária estiver faltando ou for undefined
      }
      // Formata a data assumindo que todas as propriedades estão presentes e são válidas
      return `${date.dia.toString().padStart(2, '0')}-${date.mes.toString().padStart(2, '0')}-${date.ano} ${date.hora.toString().padStart(2, '0')}:${date.minutos.toString().padStart(2, '0')}`;
    },
    getImage(service) {
      const now = new Date();
      const serviceDate = service.data ? new Date(service.data.ano, service.data.mes - 1, service.data.dia, service.data.hora, service.data.minutos) : null;
      if (serviceDate && serviceDate < now) {
        return '/exclamation.png';
      }
      switch (service.estado) {
        case 'parado':
          return '/suspended.png';
        case 'nafila':
        case 'programado':
          return '/xmark.png';
        default:
          return ''; // Sem imagem para outros casos
      }
    },
    getImageAlt(service) {
      if (service.estado === 'parado') {
        return 'Suspenso';
      } else if (service.estado === 'nafila' || service.estado === 'programado') {
        return 'Não realizado';
      } else if (this.getImage(service) === '../../public/exclamation.png') {
        return 'Em atraso';
      }
      return ''; // Caso não haja texto alternativo correspondente
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.fetchServices();
    });
  },
  created() {
    this.fetchServices();
  },
  watch: {
    '$route': 'fetchServices' // Chama fetchServices sempre que a rota muda
  }
}
</script>

<style scoped>
.vehicle-id {
  display: flex;
  align-items: center;
  list-style: none; /* Remove estilos de lista para limpar a visualização */
  margin: 15px 0; /* Margem reduzida para aproximar os textos verticalmente */
  padding: 0;
  margin-bottom: 5px;
}

.service-container {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.service-box {
  display: flex;
  flex-direction: column;
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
  /* Se necessário, adicione uma margem à esquerda para alinhar com o serviço: */
  margin-left: 2%;
}

.legend-list {
  list-style: none; /* Remove estilos padrão de lista */
  padding: 0;
}

.legend-list li {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
  margin: 15px 0;
}

.legend-header{
  border-bottom: 1px solid black;
  padding-bottom: 63px;
}

h2 {
  font-size: 1.2rem;
  padding-bottom: 10px; /* Add some space under the name */
}

img {
  margin-right: 10px;
  max-height: 40px;
  max-width: 40px;
}

img[src="/exclamation.png"] {
  margin-right: 30px; /* Ajuste o valor conforme necessário */
}

.service-details {
  display: flex;
  justify-content: space-between; /* Separa o conteúdo em lados opostos */
  padding: 0 10px; /* Espaço interno entre bordas e conteúdo */
  border-right: 1px solid black;
  border-left: 1px solid black;
}

.service-item2 {
  display: flex; /* Maintains as a flex container */
  justify-content: flex-end; /* Aligns content to the right */
  padding: 0 20px; /* Adjusts padding as needed */
  text-align: right; /* Ensures text is aligned to the right */
  flex: 1; /* Allows the item to grow if needed */
  width: 50px;
}

.service-item {
  display: flex; /* Ensure that this is a flex container */
  align-items: center; /* Vertically center the items within the container */
  padding: 5px 10px; /* Adjust padding as needed */
  width: 350px; /* Adjust width as needed, or remove if it's not necessary */
}

.service-item:hover {
  cursor: pointer;
  /* Restante do seu CSS */
}


.text-service-item2{
  text-align: center;
  padding-top: 17px;
}

.service-item2:not(:last-child) {
  border-right: 1px solid black; /* Adiciona borda à direita, exceto no último item */
  border-left: 1px solid black;
}

.service-item2 {
  display: flex;
  justify-content: flex-end;
  padding: 0 20px;
  text-align: right;
  flex: 1;
  margin-right: 10px; /* Ajuste conforme necessário */
}

.header-item-bottom {
  display: flex; /* Define o display como flex para alinhar os itens horizontalmente */
  justify-content: space-between; /* Espaça igualmente os itens ao longo da linha */
  align-items: center; /* Centraliza os itens verticalmente */
  border-bottom: 1px solid black;
}

.header-item {
  flex: 1; /* Permite que cada item cresça igualmente para preencher o espaço disponível */
  text-align: center;
  padding: 10px 20px; /* Adicione um pouco de padding para melhor visualização */
}

.header-item-bottom, .service-details {
  justify-content: space-between; /* Isso garantirá que os itens sejam distribuídos igualmente */
}

.header-item, .service-item, .service-item2 {
  flex: 2; /* Isso garantirá que cada coluna tenha a mesma largura */
}

.img2 {
  margin-right: 10px;
  max-height: 15px;
  max-width: 15px;
}

.img2:hover {
  cursor: pointer;
}
</style>