package TP2;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class  Gestor_Artigos implements Serializable   {

    // Variáveis de classe
    private static double taxaNovo = 0.5;
    private static double taxaUsado = 0.25;


    

    // Métodos de classe
    public static double getTaxaNovo() {
        return taxaNovo;
    }

    public static double getTaxaUsado() {
        return taxaUsado;
    }

    public static void setTaxaNovo(double novaTaxa) {
        taxaNovo = novaTaxa;
    }

    public static void setTaxaUsado(double novaTaxa) {
        taxaUsado = novaTaxa;
    }

    private  Map <String ,Artigo>  artigos; 


    public Gestor_Artigos(){
        this.artigos = new HashMap<>();
    }
    public Gestor_Artigos(Map <Integer ,Artigo>  artigos){
        this.artigos = new HashMap<>();
    }
    public Gestor_Artigos(Gestor_Artigos g){
        this.getArtigos();
    }
    public Map<String, Artigo> getArtigos() {
        Map<String, Artigo> copia = new HashMap<>();
        for (String i : this.artigos.keySet()) {
            copia.put(i, this.artigos.get(i).clone());
        }
        return copia;
    }
    public void setArtigos(Map<String, Artigo> artigos) {
        this.artigos = artigos;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String i : this.artigos.keySet()) {
            if(this.artigos.get(i).getDisponivel() == true){     
                sb.append(this.artigos.get(i).toString()).append("\n");}
        }
        return sb.toString();
    }
       public String mostrar_gestor(LocalDate data) {
        StringBuilder sb = new StringBuilder();
        for (String i : this.artigos.keySet()) {
            if(this.artigos.get(i).getDisponivel() == true){     
                sb.append(this.artigos.get(i).mostrar(data)).append("\n");}
        }
        return sb.toString();
    }


    
    public boolean pesquisar_artigo_id(String id){
        Artigo artigo = artigos.get(id);
        if(artigo == null){
            return false;
        }
        else{
            return true;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        Gestor_Artigos that = (Gestor_Artigos) o;
        return this.artigos.equals(that.getArtigos());
    }
    @Override
    public Gestor_Artigos clone() {
        return (new Gestor_Artigos(this));
    }
// ---------------------------------------------------- Descrição Marca

    public String pesquisar_artigos_marca_sapatilha(String marca){
         StringBuilder sb = new StringBuilder();
        for (String i : this.artigos.keySet()) {
            if (this.artigos.get(i) instanceof Sapatilha){
                Sapatilha sapatilhas = (Sapatilha) this.artigos.get(i);
                if(sapatilhas.getDisponivel() == true  && sapatilhas.getMarca().equals(marca)){     
                    sb.append(this.artigos.get(i).toString()).append("\n");}
            }
        }
        
     return (sb.toString());
    }

    public String pesquisar_artigos_marca_tshirt(String marca){
         StringBuilder sb = new StringBuilder();
        for (String i : this.artigos.keySet()) {
            if (this.artigos.get(i) instanceof Tshirt){
                Tshirt tshirt = (Tshirt) this.artigos.get(i);
                if(tshirt.getDisponivel() == true  && tshirt.getMarca().equals(marca)){     
                    sb.append(this.artigos.get(i).toString()).append("\n");}
            }
        }
     return (sb.toString());
    }

    public String pesquisar_artigos_marca_mala(String marca){
            StringBuilder sb = new StringBuilder();
            for (String i : this.artigos.keySet()) {
                if (this.artigos.get(i) instanceof Mala){
                    Mala mala = (Mala) this.artigos.get(i);
                    if(mala.getDisponivel() == true  && mala.getMarca().equals(marca)){     
                        sb.append(this.artigos.get(i).toString()).append("\n");}
                }
            }
        return (sb.toString());
    }

// ----------------------------------------------------  Novo

    public String pesquisar_artigos_novo_sapatilhas(Boolean novo){
         StringBuilder sb = new StringBuilder();
        for (String i : this.artigos.keySet()) {
            if (this.artigos.get(i) instanceof Sapatilha){
                Sapatilha sapatilhas = (Sapatilha) this.artigos.get(i);
                if(sapatilhas.getDisponivel() == true  && sapatilhas.getNovo() == novo){     
                    sb.append(this.artigos.get(i).toString()).append("\n");}
            }
        }
     return (sb.toString());
    }

    public String pesquisar_artigos_novo_tshirt(Boolean novo){
         StringBuilder sb = new StringBuilder();
        for (String i : this.artigos.keySet()) {
            if (this.artigos.get(i) instanceof Tshirt){
                Tshirt tshirt = (Tshirt) this.artigos.get(i);
                if(tshirt.getDisponivel() == true  && tshirt.getNovo() == novo){     
                    sb.append(this.artigos.get(i).toString()).append("\n");}
            }
        }
     return (sb.toString());
    }

    public String pesquisar_artigos_novo_mala(Boolean novo){
            StringBuilder sb = new StringBuilder();
            for (String i : this.artigos.keySet()) {
                if (this.artigos.get(i) instanceof Mala){
                    Mala mala = (Mala) this.artigos.get(i);
                    if(mala.getDisponivel() == true  && mala.getNovo() == novo){     
                        sb.append(this.artigos.get(i).toString()).append("\n");}
                }
            }
        return (sb.toString());
    }
//---------------------------------------------------- Tamanho   
    public String pesquipesquisar_artigos_tamanho_sapatilhas(int tamanho){
         StringBuilder sb = new StringBuilder();
        for (String i : this.artigos.keySet()) {
            if (this.artigos.get(i) instanceof Sapatilha){
                Sapatilha sapatilhas = (Sapatilha) this.artigos.get(i);
                if(sapatilhas.getDisponivel() == true  &&  sapatilhas.getTamanho() == tamanho){     
                    sb.append(this.artigos.get(i).toString()).append("\n");}
            }
        }
     return (sb.toString());
    } 
    public String pesquipesquisar_artigos_tamanho__tshit(Tamanho tamanho){
            StringBuilder sb = new StringBuilder();
            for (String i : this.artigos.keySet()) {
                if (this.artigos.get(i) instanceof Tshirt){
                    Tshirt tshirt = (Tshirt) this.artigos.get(i);
                    if(tshirt.getDisponivel() == true  &&  tshirt.getTamanho().equals(tamanho)){     
                        sb.append(this.artigos.get(i).toString()).append("\n");}
                }
            }
        return (sb.toString());
    } 
    public String pesquisar_artigos_dimensão_mala(int dimensao){
            StringBuilder sb = new StringBuilder();
            for (String i : this.artigos.keySet()) {
                if (this.artigos.get(i) instanceof Mala){
                    Mala mala = (Mala) this.artigos.get(i);
                    if(mala.getDisponivel() == true  &&  mala.getDimensao() == dimensao){     
                        sb.append(this.artigos.get(i).toString()).append("\n");}
                }
            }
        return (sb.toString());
    }     


//---------------------------------------------------- Cor
    public String pesquisar_artigos_cor(String cor){
         StringBuilder sb = new StringBuilder();
        for (String i : this.artigos.keySet()) {
            if (this.artigos.get(i) instanceof Sapatilha){
                Sapatilha sapatilhas = (Sapatilha) this.artigos.get(i);
            if(this.artigos.get(i).getDisponivel() == true  && sapatilhas.getCor().equals(cor)){      
                sb.append(this.artigos.get(i).toString()).append("\n");}
            }
        }
        return (sb.toString());

    }
//----------------------------------------------------  Padrão das t-shits
    public String pesquisar_artigos_padrao(Padrao padrao){
         StringBuilder sb = new StringBuilder();
        for (String i : this.artigos.keySet()) {
            if (this.artigos.get(i) instanceof Tshirt){
                Tshirt tshirt = (Tshirt) this.artigos.get(i);
            if(this.artigos.get(i).getDisponivel() == true  && tshirt.getPadrao().equals(padrao)) {    
                sb.append(this.artigos.get(i).toString()).append("\n");}
            }
        }
     return (sb.toString());
    }
    

// ---------------------------------------------------- Material
     public String pesquisar_artigos_material(String material){
        StringBuilder sb = new StringBuilder();
        for (String i : this.artigos.keySet()) {
            if(this.artigos.get(i) instanceof Mala){
                Mala mala = (Mala) this.artigos.get(i);
                if( mala.getDisponivel() == true  && mala.getMaterial().equals(material)){     
                    sb.append(this.artigos.get(i).toString()).append("\n");}
                }
            }
            return (sb.toString());
        }


// ---------------------------------------------------- Ano 
  public String pesquisar_artigos_ano_colecao(int ano){
    StringBuilder sb = new StringBuilder();
    for (String i : this.artigos.keySet()) {
        if(this.artigos.get(i) instanceof Mala){
            Mala mala = (Mala) this.artigos.get(i);
            if(mala.getDisponivel() == true  && mala.getAno_colecao() == ano){     
                sb.append(this.artigos.get(i).toString()).append("\n");}
            }
        }
        return (sb.toString());
  }

  // Método que adiciona um artigo
  private String adicionaArtigo(Artigo a){
      this.artigos.put(a.getCodigo_barras(), a);
      return a.getCodigo_barras();
  }

  // ---------------------------------------------------- Adicionar artigo
  public String criar_artigo_sapatilhas(String descricao, String marca, double preco_base, boolean uso, int estado_uso, int n_utilizadores, Long transportadora, long vendedor, int tamanho, boolean atacadores, String cor, int anoColecao, boolean premium, double desconto){
        Artigo sapatilhas = new Sapatilha( descricao,  marca,  preco_base,  uso,  estado_uso,  n_utilizadores,  transportadora,  vendedor,  tamanho, atacadores,  cor,  anoColecao,  premium,  desconto);
        return (this.adicionaArtigo(sapatilhas));
    }

    public String criar_artigo_tshirt (String descricao, String marca, double preco_base, boolean uso, int estado_uso, int n_utilizadores, long transportadora, Long vendedor, Tamanho tamanho, Padrao padrao){
        Artigo tshirt = new Tshirt (descricao,  marca,  preco_base,  uso,  estado_uso,  n_utilizadores,  transportadora,  vendedor,  tamanho,  padrao);
        return (this.adicionaArtigo(tshirt));

    }
    public String criar_artigo_mala(String descricao, String marca, double preco_base, boolean uso, int estado_uso, int n_utilizadores, long transportadora, long vendedor, int dimensao, String material, int ano_colecao, Boolean premium, float valorizacao){
        Artigo mala = new Mala ( descricao,  marca,  preco_base,  uso,  estado_uso,  n_utilizadores,  transportadora,  vendedor,  dimensao,  material,  ano_colecao,  premium,  valorizacao);
        return (this.adicionaArtigo(mala));
    }



// ---------------------------------------------------- Tirar de disponivel o artigo
    public long tirar_disponivel_artigo(String codigo_barras) throws ArtigoNaoEncontradoException{
        if(this.artigos.containsKey(codigo_barras) == false){
            throw new ArtigoNaoEncontradoException("O artigo "+codigo_barras+" não existe!"); 
        }
        else{
        this.artigos.get(codigo_barras).setDisponivel(false);
        return this.artigos.get(codigo_barras).getIdVendedor();
        }
    }
// ---------------------------------------------------- Colocar disponivel o artigo
    public long colocar_disponivel_artigo(String codigo_barras) throws ArtigoNaoEncontradoException{
        if(this.artigos.containsKey(codigo_barras) == false){
            throw new ArtigoNaoEncontradoException("O artigo "+codigo_barras+" não existe!");
        }

        else{
        this.artigos.get(codigo_barras).setDisponivel(true);
        return this.artigos.get(codigo_barras).getIdVendedor();
        }
    }
// --------------------------------------------------- Id de artigo para id de vendedor
    public Long id_artigo_para_id_vendedor(String codigo_barras){
        return this.artigos.get(codigo_barras).getIdVendedor();
    }
// --------------------------------------------------- Id de artigo para id de transportadora
    public Long id_artigo_para_id_transportadora(String codigo_barras){
        return this.artigos.get(codigo_barras).getIdTransportadora();
    }

    public String artigoToString (String idArtigo){
        return this.artigos.get(idArtigo).toString();
    }

    // Método que recebe uma Encomenda finalizada 
    // e altera a data de venda dos artigos
    public void alteraData(Encomenda e){
        Set<String> idsArtigos = e.getEncomenda();

        for(String s : idsArtigos){
            if(this.artigos.get(s).getDisponivel() == false){
                this.artigos.get(s).setData_venda(e.getDataEntrega());
            }
            else{
                return;
                //Temos que adicionar uma exceção
            }
        }
    }

    // Método que recebe um idArtigo e passa a data de venda para predefinido
    // Usar quando se devolver os artigos
    public void alteraDataToInicio(String idArtigo){
        this.artigos.get(idArtigo).setData_venda(LocalDate.MIN);
    }

    // Método que calcula a valor total das vendas desde sempre de um
    // vendedor
    // Recebe um set de ids -- Query 1
    public double vendaTotal(Set<String> idsArtigos, LocalDate data){
        double preco =0;

        for(String s : idsArtigos){
            preco += this.artigos.get(s).preco_atual(data);
        }

        return preco;
    }

    public double precoFaturou(Set <String> idsArtigos, LocalDate data){
        double preco =0;
        for(String s : idsArtigos){
            if(this.artigos.get(s).getNovo() == true) preco -= taxaNovo;
            else preco -= taxaUsado;
            preco += this.artigos.get(s).preco_atual(data);
        }
        return preco;
    }

    public double precoFaturouTempo(Set <String> idsArtigos, LocalDate inicio, LocalDate fim){
        double preco =0;
        for(String s : idsArtigos){
            if(this.artigos.get(s).getData_venda().isAfter(inicio) && this.artigos.get(s).getData_venda().isBefore(fim)){
                if(this.artigos.get(s).getNovo() == true) preco -= taxaNovo;
                else preco -= taxaUsado;
                preco += this.artigos.get(s).preco_atual(this.artigos.get(s).getData_venda());
            }
        }
        return preco;
        
    } 
    public double vintageFaturou(){
        double preco =0;
        for(Artigo a : this.artigos.values()){
            if(a.getData_venda().equals(LocalDate.MIN) == false ){
                if(a.getNovo() == false) preco +=  taxaUsado;
                else preco += taxaNovo;
                }
            }
        return preco;
    }

    // Método que conta o nº de artigos que estão entre duas datas
    public int entreDatas(Set <String> idsArtigos ,LocalDate inicio, LocalDate fim){
        int quantidade =0;
        for(String s : idsArtigos){
            if(this.artigos.get(s).getData_venda().isAfter(inicio) && this.artigos.get(s).getData_venda().isBefore(fim)){
                quantidade += 1;
            }
        }
        return quantidade;
    }



}
