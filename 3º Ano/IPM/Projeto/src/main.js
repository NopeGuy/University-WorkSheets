import { createApp } from "vue";
import { createRouter, createWebHistory } from "vue-router";
import App from "./App.vue";

import Login from "./components/Login.vue";
import PaginaInicial from "./components/PaginaInicial.vue";
import ServicosPorFazerDetalhes from "./components/PaginaInicialDetalhes1.vue";
import Perfil from "./components/Perfil.vue";
import Clientes from "./components/Clientes.vue";
import ClienteDetalhes from "./components/ClienteDetalhes.vue";
import Faq from "./components/faq.vue";
import ServicosConcluidos from "./components/servicosConcluidos.vue";
import ServicosConcluidosDetalhes from "./components/servicosConcluidosDetalhes";
import VehicleDetalhes from "./components/VehicleDetalhes.vue";
import Servicos from "./components/servicos.vue";
import NovoServico from "./components/NovoServico.vue"

import AppHeader from './components/header.vue';

import "./global.css";

const routes = [
  { path: "/", component: Login },
  { path: "/pagina-inicial", component: PaginaInicial, children:[
    {path: ":id", component: ServicosPorFazerDetalhes}
  ]},
  { path: "/login", component: Login },
  { path: "/perfil", component: Perfil },
  { path: "/clientes", component: Clientes },
  { path: "/clientes/:clientId", component: ClienteDetalhes },
  { path: "/faq", component: Faq },
  { path: "/servicos-concluidos", component: ServicosConcluidos, children:[
    {path: ":id", component: ServicosConcluidosDetalhes}
  ]},
  {
    path: "/servicos",
    component: Servicos,
    children: [
      {
        path: "novoServico/:vehicleId/:serviceDescription?",
        name: "NovoServico",
        component: NovoServico,
        props: route => ({ vehicleId: route.params.vehicleId, serviceDescription: route.params.serviceDescription || '' })
      }
    ]
  },
  { path: "/clientes/:clientId/:vehicleId", component: VehicleDetalhes }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

const app = createApp(App);
app.component('app-header', AppHeader); // Registrar o componente de cabeçalho globalmente
app.use(router);
app.mount("#app");

// Configuração para adicionar meta tags e gerenciar títulos (ajustar conforme sua necessidade)
router.beforeEach((to, from, next) => {
  document.title = to.meta.title || "E.S-Ideal";
  let metaDescription = document.querySelector('meta[name="description"]');
  if (!metaDescription) {
    metaDescription = document.createElement('meta');
    metaDescription.setAttribute('name', 'description');
    document.head.appendChild(metaDescription);
  }
  metaDescription.setAttribute('content', to.meta.description || '');
  next();
});
