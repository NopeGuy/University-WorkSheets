<template>
  <div class="login-container">
    <div class="header">
      <div class="gear-background">
        <h1>E.S. Ideal</h1>
      </div>
    </div>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="workerId">Funcionário:</label>
        <input id="workerId" type="text" v-model="username">
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input id="password" type="password" v-model="password">
      </div>
      <div class="button-container">
        <!-- Removed @click event -->
        <button type="submit">Login</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      password: '',
      workers: [],
    };
  },
  created() {
    this.fetchWorkers();
  },
  methods: {
    async fetchWorkers() {
      try {
        const response = await axios.get('http://localhost:3000/worker');
        this.workers = response.data; // Modificação aqui, removendo .worker
        console.log(this.workers); // Adicione isto para depurar e ver a estrutura dos dados
      } catch (error) {
        console.error('Error fetching workers', error);
      }
    },
    handleLogin() {
      const worker = this.workers.find(worker => 
        worker.name === this.username && worker.password === this.password
      );
      if (worker) {
        console.log('Login successful', worker);
        this.$router.push('/pagina-inicial');
      } else {
        console.error('Login failed');
        alert('Login failed: incorrect username or password.');
      }
    },
  },
};
</script>

<style scoped>
body, html {
  margin: 0;
  height: 100%;
  background-color: #f0f0f0; /* Assuming a light grey background for the whole page */
}

.login-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.header {
  width: 100%;
  background-color: #85E2FF;
  position: relative;
  padding-left: 20px; /* Add padding to align the text from the left side */
}

.gear-background {
  background: url('../../public/gear-1@2x.png') left center no-repeat;
  background-size: 4%; /* Reduced size of the image */
  display: flex;
  align-items: center;
  height: 100%; /* Adjust if needed */
}

h1 {
  color: black;
  margin-left: 90px; /* Adjust the margin to position the text appropriately */
}

.form-group {
  display: flex; /* Add this to make form-group a flex container */
  flex-direction: column; /* Stack flex items vertically */
  align-items: flex-start; /* Align flex items to the start of the flex container */
  margin: 20px 0;
  width: 300px; /* Adjust as needed for proper spacing */
}

label, input {
  width: 100%; /* Make label and input take up the full width of .form-group */
  margin-bottom: 5px; /* Add some space below each label */
}

input {
  padding: 8px; /* Add padding to input for better aesthetics */
  border: 1px solid #ccc; /* Add a border to the input */
  border-radius: 4px; /* Optional: round the corners of the input field */
}

.button-container {
  display: flex;
  justify-content: flex-end;
  width: 300px; /* Match this width to the width of your form groups */
}

button {
  background-color: #21bfef;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  color: white;
  cursor: pointer;
  outline: none;
}
</style>