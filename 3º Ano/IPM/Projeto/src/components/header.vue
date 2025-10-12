<template>
    <div class="header">
        <div class="gear-background">
            <h1>E.S. Ideal</h1>
        </div>
        <div @click="toggleSlidePage" class="config-icon">
            <img src="../../public/config.svg" alt="Configurações" />
        </div>
    </div>
    <!-- Slide Page -->
    <div :class="{ 'slide-page-active': isSlidePageOpen }" class="slide-page">
        <div class="slide-content">
            <div class="slide-header">
            <h2>Navegador de Páginas</h2>
            <button @click="toggleSlidePage" class="close-slide-button">&times;</button>
            </div>
            <router-link to="/perfil" class="menu-item"><img src="../../public/profile.png"/><a class="text-menu-item">Perfil</a></router-link>

            <router-link to="/pagina-inicial" class="menu-item"><img src="../../public/suspended.png"/><a class="text-menu-item">Serviços Por Realizar</a></router-link>

            <router-link to="/servicos-concluidos" class="menu-item"><img src="../../public/finish.png"/><a class="text-menu-item">Serviços Concluídos</a></router-link>

            <router-link to="/servicos" class="menu-item"><img src="../../public/new.png"/><a class="text-menu-item">Criar Serviço</a></router-link>

            <router-link to="/clientes" class="menu-item"><img src="../../public/clients.png"/><a class="text-menu-item">Clientes</a></router-link>

            <router-link to="/faq" class="menu-item"><img src="../../public/faq.png"/><a class="text-menu-item">F.A.Q</a></router-link>

            <router-link to="/login" class="menu-item"><img src="../../public/exit.png"/><a class="text-menu-item">Sair</a></router-link>
        </div>
    </div>
</template>
  
<script>
  import axios from 'axios';

  export default {
    data() {
      return {
        isSlidePageOpen: false,
        vehicleDetails: {}
      };
    },
    methods: {
        toggleSlidePage() {
            this.isSlidePageOpen = !this.isSlidePageOpen; // Alterna a visibilidade da slide page
        },
        goBack() {
            this.$router.go(-1);
        }
    }
}
</script>

<style scoped>
.close-slide-button {
  background: none;
  border: none;
  font-size: 3rem; /* Ajuste o tamanho conforme necessário */
  color: #333; /* Cor que combine com seu layout */
  cursor: pointer;
}


.slide-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.slide-page {
  position: fixed;
  top: 0;
  right: -100%; /* Começa fora da tela */
  width: 250px;
  height: 100%;
  background-color: #85E2FF;
  transition: right 0.3s;
  overflow-y: auto; /* Para rolagem se os itens excederem a altura da tela */
  z-index: 1000; /* Garante que a slide page fique acima de outros elementos */
}

/* Quando 'slide-page-active' é aplicado, a slide page desliza para a tela */
.slide-page-active {
  right: 0;
}

/* Estilos para os itens do menu na slide page */
.menu-item {
  display: block;
  padding: 10px;
  border-bottom: 1px solid #ccc; /* Separação visual dos itens */
  color: #333; /* Cor do texto */
  text-decoration: none; /* Remove sublinhado dos links */
}

.text-menu-item{
  position: fixed;
  text-align: center; /* Centraliza o texto dos elementos com esta classe */
  padding-top: 13px;
}

.menu-item:hover {
  background-color: #f5f5f5; /* Efeito de hover para os itens do menu */
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 97.4%;
  background-color: #85E2FF;
  padding: 0 20px; /* Espaçamento em ambos os lados */
  height: 80px; /* Uma altura fixa para o cabeçalho, ajuste conforme necessário */
}

.gear-background {
  background: url('../../public/gear-1@2x.png') left center no-repeat;
  background-size: 28%;
  display: flex;
  align-items: center;
  height: 100%;
}

h2 {
  border-bottom: 1px solid black; /* Add black line under the name */
  font-size: 1.2rem;
  padding-bottom: 10px; /* Add some space under the name */
}

h1 {
  color: black;
  margin-left: 70px;
}

.config-icon img {
  height: auto;
  width: auto;
  max-height: 50px;
  max-width: 50px;
  cursor: pointer;
}

img {
  margin-right: 10px;
  max-height: 40px;
  max-width: 40px;
}
</style>