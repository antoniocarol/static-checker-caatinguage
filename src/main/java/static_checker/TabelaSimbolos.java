package static_checker;

import parser.ParserConstants;
import parser.Token;

import java.util.ArrayList;
import java.util.List;

public class TabelaSimbolos {

    private List<Simbolo> tabela;

    public TabelaSimbolos() {
        this.tabela = new ArrayList<>();
    }

    public void inserir(Token token) {
        String lexemaOriginal = token.image;
        String lexemaTruncado = lexemaOriginal;

        if (lexemaOriginal.length() > 35) {
            lexemaTruncado = lexemaOriginal.substring(0, 35);
        }

        Simbolo simboloExistente = buscar(lexemaTruncado);


        //verificando a existencia do simbolo para evitar mais que uma entrada
        if (simboloExistente != null) {
            simboloExistente.adicionarLinha(token.beginLine);
        } else {
            String codigo = determinarCodigoAtomo(token.kind);
            Simbolo novoSimbolo = new Simbolo(tabela.size() + 1, token, lexemaTruncado, codigo);
            tabela.add(novoSimbolo);
        }
    }

    private String determinarCodigoAtomo(int kind) {
        switch (kind) {
            case ParserConstants.IDENTIFIER:
                return "IDN02";
            case ParserConstants.INT_CONST:
                return "IDN04";
            case ParserConstants.REAL_CONST:
                return "IDN05";
            case ParserConstants.STRING_CONST:
                return "IDN06";
            case ParserConstants.CHAR_CONST:
                return "IDN07";
            default:
                return "IDN02";
        }
    }

    public Simbolo buscar(String lexema) {
        for (Simbolo s : tabela) {
            if (s.getLexema().equalsIgnoreCase(lexema)) {
                return s;
            }
        }
        return null;
    }

    public List<Simbolo> getTodos() {
        return tabela;
    }

    public int getIndice(String lexema) {
        String lexemaTruncado = lexema;
        if (lexema.length() > 35) {
            lexemaTruncado = lexema.substring(0, 35);
        }
        Simbolo s = buscar(lexemaTruncado);
        return (s != null) ? s.getIndice() : -1;
    }
}
