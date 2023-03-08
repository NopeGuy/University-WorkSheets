import java.time.LocalDate;

public class Encomenda {
    private String nome;
    private int NIF;
    private String morada;
    private int numero;
    private LocalDate data;
    private LinhaEncomenda[] encomendas;
    private int capacity = 10;
    private int index = 0;

    public Encomenda(String name, int nif, String mo, int num) {
        this.setNome(name);
        this.setNIF(nif);
        this.setMorada(mo);
        this.setNumero(num);
        LinhaEncomenda[] e = new LinhaEncomenda[capacity];
        index = 0;
        this.setEncomendas(e);
    }

    public Encomenda() {
        this("Nao determinada", 1, "Nao determinada", 1);
    }


    public void adicionaLinha (Linha Encomenda linha) {
        if (ocupacao >= capacidade) {
            capacidade *= 2;
            LinhaEncomenda[] newArr = new LinhaEncomenda[capacidade];
            for (int i = 0; i < ocupacao; i++) {
                newArr[i] = linhas[i];
            }
=
            linhas = newArr;
        }
        linhas = newArr;
        1 inhas[ocupacao++] = linha.clone();
        public void remove Produto(String codProd) {
            int iCod = this.indexOf(codProd);
            if (iCod >= 0) {
                for (int i = iCod; i < ocupacao; i++) {
                    1 inhas[1] = linhas[1 + 1];
                    linhas[--ocupacao] = null;
                }
            }
        }
    }



                    public void adicionaLinha(LinhaEncomenda linha) {
                        LinhaEncomenda[] array = this.getEncomendas();
                        LinhaEncomenda[] novo = new LinhaEncomenda[array.length+1];
                        System.arraycopy(array, 0, novo, 0, array.length);
                        novo[array.length] = new LinhaEncomenda(linha);
                        this.setEncomendas(novo);
                    }



    public double calculaValorTotal() {
        LinhaEncomenda[] array = this.getEncomendas();
        double soma = 0;
        for (int i = 0; i<index; i++){
            LinhaEncomenda linhaEncomenda;
            soma += linhaEncomenda.calculaValorLinhaEnc();
        }
        return soma;
    }

    public double calculaValorDesconto(){
        LinhaEncomenda[] array = this.getEncomendas();
        double desconto = 0;
        for (LinhaEncomenda linhaEncomenda : array) desconto += linhaEncomenda.calculaValorDesconto();
        return desconto;
    }

    public int numeroTotalProdutos(){
        LinhaEncomenda[] array = this.getEncomendas();
        int quantidade = 0;
        for (LinhaEncomenda linhaEncomenda : array) quantidade += linhaEncomenda.getQuantidade();
        return quantidade;
    }









    // sets e gets
    public String getNome() {
        return this.nome;
    }
    public int getNIF() {
        return this.NIF;
    }
    public String getMorada() {
        return this.morada;
    }
    public int getNumero() {
        return this.numero;
    }
    public LocalDate getData() {
        return this.data;
    }
    public LinhaEncomenda[] getEncomendas() {
        LinhaEncomenda[] array = new LinhaEncomenda[this.encomendas.length];
        for (int i = 0; i < this.encomendas.length; i++) array[i] = this.encomendas[i].clone();
        return array;
    }

    public void setNome(String name) {
        this.nome = name;
    }
    public void setNIF(int nif) {
        this.NIF = nif;
    }
    public void setMorada(String mo) {
        this.morada = mo;
    }
    public void setNumero(int num) {
        this.numero = num;
    }
    public void setData(LocalDate date) {
        this.data = date;
    }
    public void setEncomendas(LinhaEncomenda[] e) {
        this.encomendas = new LinhaEncomenda[e.length];
        for (int i = 0; i < e.length; i++) this.encomendas[i] = new LinhaEncomenda(e[i]);
    }

}