<template>
  <div class="hello-container">
    <div class="ha">
      <h1>{{ title }}</h1>
      <div class="back-item" @click="goBack">
        <button class="close-button">&times;</button>
      </div>
    </div>
    <div v-if="!serviceSuspended">
      <div v-if="!showTimer && nothing">
        <ul class="list-fix">
          <br>
          <li><strong>Veículo:</strong> {{ vehicleDetails ? vehicleDetails.id : 'Veículo não definido' }}</li>
          <br>
          <li><strong>Tipo do Veículo:</strong> {{ vehicleType }}</li>
          <br>
          <li><strong>Cliente:</strong> {{ clientDetails ? clientDetails.nome : 'Cliente não definido' }}</li>
          <br>
          <li><strong>Contacto:</strong> {{ clientDetails ? clientDetails.telefone : 'Contacto não definido' }}</li>
          <br>
          <li><strong>Data e Hora de Agendamento:</strong> {{ service && service.data ? formatDate(service.data) : 'Data não definida' }}</li>
          <br>
          <li><strong>Informação Adicional:</strong> {{ service ? service.descrição : 'Serviço não possui descrição' }}</li>
        </ul>
        <button class="start-service-button" @click="startService">Começar Serviço</button>
      </div>
      <div v-if="!serviceSuspended && showTimer">
        <div class="header-title">Clique no botão, quando tiver suspendido/terminado o serviço</div>
        <br><br>
        <div class="timer">{{ formatTime(secondsElapsed) }}</div>
        <br><br>
        <div class="button-container">
          <button class="suspend-button" @click="pauseTimer">Suspender Serviço</button>
          <button class="finish-button" @click="stopTimer">Finalizar Serviço</button>
        </div>
      </div>
    </div>
    <div v-if="serviceSuspended">
      <textarea v-model="suspensionReason" placeholder="Escreva a justificação pela suspensão do serviço" rows="4"></textarea>
      <div class="button-container">
        <button class="resume-button" @click="submitSuspensionReason">Suspender Serviço</button>
        <button class="goback-button" @click="resumeService">Voltar</button>
      </div>
    </div>
    <div v-if="serviceCompleted && !showTimer && !serviceSuspended">
      <h1 class="header-title-done">O Veículo precisa de algum serviço extra? Se não clique em finalizar sem selecionar uma opção.</h1>
      <div v-for="service in filteredServices" :key="service" class="check-box-header">
        <label>
          <input type="checkbox" :value="service" v-model="selectedServices">
          {{ getServiceDescription(service) }}
        </label>
      </div>
      <div class="button-container">
        <button class="goback-button" @click="resumeService2">Voltar</button>
        <button class="resume-button" @click="finishService">Finalizar Serviço</button>
      </div>
    </div>

  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "ServicosPorFazerDetalhes",
  data() {
    return {
      service: null,
      serviceCompleted: false,
      vehicleType: '',
      vehicleTypes: [], // Armazenar todos os tipos de veículos e seus serviços
      serviceDefinitions: [],
      filteredServices: [], // Serviços filtrados com base no tipo do veículo
      selectedServices: [],
      nothing: true,
      timer: null,
      secondsElapsed: 0,
      showTimer: false,
      vehicleType: '',
      vehicleDetails: null,
      clientDetails: null,
      title: '',
      serviceSuspended: false,
      suspensionReason: ''
    };
  },
  created() {
    this.fetchServiceDetails();
    this.fetchVehicleTypes();
    this.fetchServiceDefinitions();
  },
  methods: {
    fetchVehicleTypes() {
      axios.get('http://localhost:3000/vehicle-types')
        .then(response => {
          this.vehicleTypes = response.data;
          this.updateFilteredServices(); // Atualiza a lista de serviços após carregar os tipos
        })
        .catch(error => {
          console.error('Error fetching vehicle types:', error);
        });
    },
    fetchVehicleDetails(vehicleId) {
      axios.get(`http://localhost:3000/vehicles/${vehicleId}`)
        .then(response => {
          this.vehicleDetails = response.data;
          this.vehicleType = response.data['vehicle-typeId']; // Armazena o tipo do veículo
          this.fetchClientDetails(this.vehicleDetails.clientId);
          this.updateTitle();
        })
        .catch(error => {
          console.error('Error fetching vehicle details:', error);
        });
    },
    fetchClientDetails(clientId) {
      axios.get(`http://localhost:3000/clients/${clientId}`)
        .then(response => {
          this.clientDetails = response.data;
        })
        .catch(error => {
          console.error('Error fetching client details:', error);
        });
    },
    fetchServiceDetails() {
      const serviceId = this.$route.params.id;
      axios.get(`http://localhost:3000/services/${serviceId}`)
        .then(response => {
          this.service = response.data;
          this.fetchVehicleDetails(this.service.vehicleId);
          this.fetchServiceDefinitions(); // Garantir que as definições de serviço são carregadas.
        })
        .catch(error => {
          console.error('Error fetching service details:', error);
        });
    },
    fetchServiceDefinitions() {
      axios.get('http://localhost:3000/service-definitions')
        .then(response => {
          this.serviceDefinitions = response.data;
          this.updateFilteredServices();
        })
        .catch(error => {
          console.error('Error fetching service definitions:', error);
        });
    },
    updateFilteredServices() {
      if (this.vehicleType && this.vehicleTypes.length > 0) {
        const type = this.vehicleTypes.find(type => type.id === this.vehicleType);
        if (type) {
          this.filteredServices = type.serviços;
        }
      }
    },
    getServiceDescription(serviceId) {
      const service = this.serviceDefinitions.find(s => s.id === serviceId);
      return service ? service.descr : 'Descrição não encontrada';
    },
    updateTitle() {
      if (this.service && this.serviceDefinitions.length > 0 && this.vehicleDetails) {
        // Encontra a descrição do serviço usando o ID do serviço.
        const definition = this.serviceDefinitions.find(def => def.id === this.service['service-definitionId']);
        if (definition) {
          this.title = `${definition.descr} - ${this.vehicleDetails.id}`;
        }
      }
    },
    goBack() {
      this.$router.go(-1);
    },
    formatDate(date) {
      console.log(date)
      if (!date || !date.ano || !date.mes || !date.dia || !date.hora) {
        console.log("erro " + date)
        return 'Data não definida';
      }
      return `${date.dia.toString().padStart(2, '0')}/${date.mes.toString().padStart(2, '0')}/${date.ano} ${date.hora.toString().padStart(2, '0')}:${date.minutos.toString().padStart(2, '0')}`;
    },
    startService() {
      this.showTimer = true;
      this.startTimer();
    },
    startTimer() {
      this.timer = setInterval(() => {
        this.secondsElapsed++;
      }, 1000);
    },
    pauseTimer() {
      clearInterval(this.timer);  // Interrompe o intervalo de timer
      this.timer = null;          // Limpa a referência do timer
      this.serviceSuspended = true; // Atualiza o estado para indicar que o serviço está suspenso
    },
    submitSuspensionReason() {
      if (confirm('Tem certeza que quer suspender o serviço com esta justificação?')) {
        const updatedDescription = this.service.descrição + ' | ' + this.suspensionReason;
        const serviceId = this.$route.params.id;
        const data = {
          descrição: updatedDescription,
          estado: 'parado'
        };
        axios.patch(`http://localhost:3000/services/${serviceId}`, data)
          .then(response => {
            console.log('Serviço atualizado com sucesso:', response);
            this.serviceSuspended = false; // Resetar o estado de suspensão após a confirmação
            this.$router.push('/pagina-inicial');
          })
          .catch(error => {
            console.error('Erro ao atualizar o serviço:', error);
            alert('Falha ao atualizar o serviço. Por favor, verifique os detalhes do erro no console.');
          });
      }
    },
    resumeService() {
      this.serviceSuspended = false;  // Desativa a suspensão
      this.startTimer();              // Reinicia o timer
      this.suspensionReason = '';     // Limpa a razão de suspensão
    },
    resumeService2() {
      this.serviceSuspended = false;
      this.serviceCompleted = false;
      this.nothing = true;
      this.showTimer = true;
      this.startTimer();
    },
    stopTimer() {
      clearInterval(this.timer);
      this.timer = null;
      this.secondsElapsed = 0;
      this.serviceCompleted = true;
      this.showTimer = false;
      this.nothing = false;
    },
    formatTime(seconds) {
      const hrs = Math.floor(seconds / 3600);
      const mins = Math.floor((seconds % 3600) / 60);
      const secs = seconds % 60;
      return `${hrs.toString().padStart(2, '0')}:${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
    },
    finishService() {
      // Atualizar o serviço atual para 'realizado'
      axios.patch(`http://localhost:3000/services/${this.service.id}`, {
        estado: 'realizado'
      }).then(() => {
        if (this.selectedServices.length > 0) {
          // Adicionar novos serviços se algum foi selecionado
          axios.get('http://localhost:3000/services').then(response => {
          const services = response.data;
          const highestId = services.reduce((max, srv) => Math.max(max, parseInt(srv.id.substring(1))), 0); // 's31' -> 31
          let nextId = highestId + 1;
          // Agora adicione os novos serviços com IDs incrementados
          this.selectedServices.forEach(serviceId => {
            this.addNewService(serviceId, `s${nextId++}`);
          });
        });
        } else {
          // Redirecionar para a página inicial se nenhum serviço extra foi selecionado
          this.$router.push('/pagina-inicial');
        }
      }).catch(error => {
        console.error('Erro ao atualizar o serviço:', error);
      });
    },
    addNewService(serviceId, newServiceId) {
      const newService = {
        id: newServiceId,
        vehicleId: this.vehicleDetails.id, // Assume que vehicleDetails contém 'id'
        ['service-definitionId']: serviceId,
        estado: 'nafila',
        agendamento: 'filaDeEspera',
        // Você pode adicionar outros campos necessários aqui
      };
      axios.post('http://localhost:3000/services', newService)
        .then(() => {
          // Se for o último serviço adicionado, redirecione para a página inicial
          if (serviceId === this.selectedServices[this.selectedServices.length - 1]) {
            this.$router.push('/pagina-inicial');
          }
        })
        .catch(error => {
          console.error('Erro ao adicionar novo serviço:', error);
        });
    },
  }
};
</script>
  
<style scoped>
  .hello-container {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 65%;
    height: auto;
    background-color: gray;
    color: white;
    display: flex;
    flex-direction: column;
    align-items: stretch;
    z-index: 1000;
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

  .ha {
    display: flex;
    justify-content: center; /* Mantém o título centralizado */
    align-items: center;
    padding: 5px 10px;
    border-bottom: 2px solid black;
    background-color: #333;
    position: relative; /* Necessário para posicionamento absoluto do botão */
  }

  .start-service-button {
    display: block;
    background-color: green;
    color: white;
    padding: 10px 20px;
    margin-top: 20px;
    margin-bottom: 20px; /* Espaço entre o botão e o fim do container */
    margin-left: auto; /* Margem automática à esquerda */
    margin-right: auto; /* Margem automática à direita */
    border: 2px solid black;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    width: 200px;
  }  

  .start-service-button:hover {
    background-color: darkgreen;
  }

  .timer {
    font-size: 50px; /* Aumenta o tamanho da fonte para tornar o timer mais visível */
    text-align: center; /* Centraliza o texto horizontalmente */
    width: 100%; /* Faz o elemento do timer ocupar toda a largura do container */
    margin: 20px 0; /* Adiciona espaço acima e abaixo para desacoplar do conteúdo adjacente */
    color: #FFF; /* Define a cor do texto, ajuste conforme necessário */
  }

  .header-title {
    font-size: 25px; 
    text-align: center; /* Centraliza o texto horizontalmente */
    width: 100%; /* Faz o elemento do timer ocupar toda a largura do container */
    margin: 20px 0; /* Adiciona espaço acima e abaixo para desacoplar do conteúdo adjacente */
    color: #FFF; /* Define a cor do texto, ajuste conforme necessário */
  }
  
  .button-container {
    display: flex;
    flex-direction: row; /* Botões alinhados horizontalmente */
    align-items: center; /* Centraliza verticalmente os botões dentro do container */
    justify-content: center; /* Centraliza horizontalmente no container */
    gap: 20px; /* Espaço entre os botões */
    margin-top: 20px; /* Espaçamento entre o timer e os botões */
    margin-bottom: 20px; /* Espaçamento no final do container */
  }
  
  .suspend-button, .finish-button {
    background-color: green;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    width: 200px; /* Largura fixa para ambos os botões */
  }
  
  .suspend-button {
    background-color: #FF5B37; /* Cor específica para o botão Suspender */
  }
  
  .finish-button {
    background-color: green; /* Cor específica para o botão Finalizar */
  }
  
  .suspend-button:hover, .finish-button:hover {
    opacity: 0.8; /* Efeito ao passar o mouse para indicar interatividade */
  }

  .list-fix{
    font-size: 20px; 
  }

  .resume-button {
    background-color: green;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    margin-top: 20px;
  }

  .goback-button {
    background-color: #FF5B37;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    margin-top: 20px;
  }
  
  textarea {
    width: 90%; /* Ajusta a largura da caixa de texto */
    margin-top: 20px; /* Espaço acima da caixa de texto */
    padding: 15px; /* Espaço interno para texto */
    font-size: 16px;
    resize: none; /* Impede o redimensionamento */
    margin-left: 3.15%;
  }

  .header-title-done {
    font-size: 20px; 
    text-align: center; /* Centraliza o texto horizontalmente */
    width: 100%; /* Faz o elemento do timer ocupar toda a largura do container */
    margin: 20px 0; /* Adiciona espaço acima e abaixo para desacoplar do conteúdo adjacente */
    color: #FFF; /* Define a cor do texto, ajuste conforme necessário */
  }

  .check-box{
    padding-left: 38%;
  }

  .check-box-header{
    text-align: center;
  }
</style> 