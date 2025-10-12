<template>
  <app-header></app-header>
  <div class="profile-card" v-if="worker">
    <div v-if="!showQuote">
      <div class="profile-header">
        <h2>{{ worker.name }}</h2>
        <div class="arrow" @click="toggleQuote">
          <img src="/right_arrow.svg" alt="Show More" />
        </div>
      </div>
      <div class="title-bar"></div>
      <ul>
        <li><strong>Idade</strong> - {{ worker.age }} anos</li>
        <li><strong>Profissão</strong> - {{ worker.job }}</li>
        <li><strong>Localização</strong> - {{ worker.location }}</li>
        <li><strong>Educação</strong> - {{ worker.education }}</li>
        <li><strong>Especialização</strong> - {{ worker.specialty }}</li>
        <li><strong>Interesses</strong> - {{ worker.interests.join(', ') }}</li>
        <li><strong>Objetivos</strong> - {{ worker.objectives.join(', ') }}</li>
        <li><strong>Desafios</strong> - {{ worker.challenges.join(', ') }}</li>
        <li><strong>Soluções</strong> - {{ worker.solutions }}</li>
      </ul>
    </div>
    <div v-else>
      <div class="profile-header">
        <h2>{{ worker.name }}</h2>
        <div class="arrow" @click="toggleQuote">
          <img src="/left_arrow.svg" alt="Show Less" />
        </div>
      </div>
      <div class="title-bar"></div>
      <strong>Citação</strong> - {{ worker.quote }}
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      worker: null,
      showQuote: false
    };
  },
  mounted() {
    this.fetchWorkerData();
  },
  methods: {
    async fetchWorkerData() {
      try {
        const response = await axios.get('http://localhost:3000/worker');
        // Atribui o primeiro trabalhador da lista ao objeto 'worker'
        // Assumindo que 'response.data' é uma array com um único objeto trabalhador
        this.worker = response.data[0]; 
      } catch (error) {
        console.error('There was an error fetching the worker data:', error);
      }
    },
    toggleQuote() {
      this.showQuote = !this.showQuote;
    }
  }
}
</script>

<style scoped>
.profile-card {
  background-color: rgb(223, 213, 213);
  max-width: 800px;
  margin: auto;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
  transition: 0.3s;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

h2 {
  font-size: 1.5rem; /* Ajustado para ser um pouco maior */
  margin-bottom: 20px; /* Adiciona espaço abaixo do título */
}

.arrow {
  cursor: pointer;
}

.arrow img {
  width: 25px;
  height: auto;
}

ul {
  list-style-type: none;
  padding: 0;
  margin: 0 0 20px 0; /* Adiciona margem abaixo da lista */
}

ul li {
  margin-bottom: 10px; /* Espaço entre os itens da lista */
}

ul li strong {
  font-weight: bold;
}

blockquote {
  font-style: italic;
  margin: 20px 0;
}

img {
  margin-right: 10px;
  max-height: 40px;
  max-width: 40px;
}

.profile-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-bottom: 10px; /* Adiciona padding abaixo do header para mais espaço */
}

.title-bar {
  height: 4px; /* Aumenta a altura para ser mais visível */
  background-color: black;
  width: 100%;
  margin-bottom: 20px; /* Adiciona espaço abaixo da barra */
}

.arrow img {
  cursor: pointer;
  margin-left: 10px;
}
</style>
