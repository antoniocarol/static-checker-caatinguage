package static_checker;

import parser.Token;

import java.util.ArrayList;
import java.util.List;

public class Simbolo {
    private int indice;
    private int tamanhoAntesTrunc;
    private int tamanhoDepoisTrunc;

    private String lexema;
    private String codigoAtomo;
    private String tipo;

    private List<Integer> linhasOcorrencia;

    public Simbolo(int indice, Token tokenOriginal, String lexemaTruncado, String codigoAtomo) {
        this.indice = indice;
        this.lexema = lexemaTruncado;
        this.codigoAtomo = codigoAtomo;

        this.tamanhoAntesTrunc = tokenOriginal.image.length();
        this.tamanhoDepoisTrunc = lexemaTruncado.length();

        this.linhasOcorrencia = new ArrayList<>();
        this.linhasOcorrencia.add(tokenOriginal.beginLine);


        this.tipo = "-";
    }

    public void adicionarLinha(int linha) {
        if (this.linhasOcorrencia.size() < 5) {
            this.linhasOcorrencia.add(linha);
        }
    }

    public String getLexema() { return lexema; }
    public int getIndice() { return indice; }
    public String getCodigoAtomo() { return codigoAtomo; }
    public int getTamanhoAntes() { return tamanhoAntesTrunc; }
    public int getTamanhoDepois() { return tamanhoDepoisTrunc; }
    public String getTipo() { return tipo; }
    public List<Integer> getLinhas() { return linhasOcorrencia; }


}
