<template>
  <app-header></app-header>
    <div class="main-content">
        <!-- FAQ Section -->
        <div class="faq-section">
          <h2>FAQ (Perguntas Frequentes) - Oficina E.S.Ideal</h2>
          <!-- Adicionar conteúdo do FAQ aqui -->
            <div class="classification-container">
                <div class="classification-section">
                    <h2>Informações sobre Clientes e Veículos</h2>
                    <a>Quais informações são registadas acerca dos clientes e veículos na oficina?</a>
                    <p></p>
                    <a>Sobre os clientes, registamos nome, morada, número de contacto, NIF e uma lista de veículos que possuem. Sobre os veículos, registamos marca, modelo, tipo de motor, cilindrada (se aplicável), potência do carregador (se aplicável), medida das jantes e histórico de serviços.</a>
                </div>
                <div class="classification-section">
                    <h2>Classificação dos Postos de Serviço</h2>
                    <a>Como são classificados os postos de serviço na oficina?Os postos são classificados de acordo com a sua especialidade: para motores a combustão, para motores elétricos ou de especialização geral, que engloba motores a combustão, elétricos e híbridos.Cenários de Utilização:</a>
                </div>
            </div>
            <div class="classification-container">
                <div class="classification-section">
                    <h2>Tipos de Serviços</h2>
                    <a>Quais são os tipos de serviços disponíveis na oficina e quem pode realizá-los?</a>
                    <p></p>
                    <a>Os tipos de serviços incluem universais, para carros com motor a combustão, para carros com motor elétrico e para carros híbridos. Os mecânicos com especialização correspondente podem realizá-los.</a>
                </div>
                <div class="classification-section">
                    <h2>Tratamento de Serviços Suspensos</h2>
                    <a>Qual é a abordagem para lidar com serviços suspensos ou com a necessidade de encomenda de peças? Os mecânicos podem indicar no sistema quando um serviço não pode ser realizado imediatamente e a razão para isso. O serviço é então atualizado para o estado suspenso até que as peças necessárias estejam disponíveis para retomar o serviço.</a>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
h2 {
    border-bottom: 1px solid black; /* Add black line under the name */
    font-size: 1.2rem;
    padding-bottom: 10px; /* Add some space under the name */
}

.main-content {
    padding: 20px; /* Espaçamento ao redor do conteúdo principal */
}
  
.faq-section, .classification-section {
    background-color: #f0f0f0; /* Ajuste a cor de fundo conforme a imagem */
    border-radius: 8px; /* Cantos arredondados */
    padding: 20px; /* Espaçamento interno */
    margin-bottom: 20px; /* Espaçamento entre seções */
}

.classification-section {
    background-color: #ffffff; /* Cor de fundo branca para a seção */
    border: 1px solid #cccccc; /* Borda cinza claro para delimitar a seção */
    border-radius: 4px; /* Cantos ligeiramente arredondados para a seção */
    padding: 20px; /* Espaçamento interno para não tocar diretamente nas bordas */
    margin-top: 20px; /* Espaço acima da seção */
    margin-bottom: 20px; /* Espaço abaixo da seção */
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Sombra sutil para dar profundidade */
    width: 20%;
}
  
.classification-section h2 {
    margin-top: 0;
    margin-bottom: 16px; /* Espaço abaixo do título */
    color: #333333; /* Cor do texto mais escura para o título */
    padding-bottom: 10px; /* Espaçamento abaixo do título */
    border-bottom: 2px solid #333333; /* Linha abaixo do título para destaque */
    font-size: 18px; /* Tamanho da fonte do título */
    font-weight: bold; /* Fonte do título em negrito */
}

.classification-container {
    display: flex;
    justify-content: space-between; /* Isso irá distribuir espaço igualmente entre as seções */
}
  
.classification-section {
    width: 48%; /* Cada seção vai ocupar quase metade da largura do container */
    /* Restante dos estilos da classification-section mantidos */
    margin: 10px;
}
</style>