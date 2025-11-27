package static_checker;


import parser.Parser;
import parser.ParserConstants;
import parser.Token;
import parser.TokenMgrError;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StaticChecker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insira o nome do arquivo (Sem a extensão .252)");
        String nomeArquivo = scanner.nextLine();


        String caminhoArquivo;
        if (nomeArquivo.endsWith(".252")) {
            caminhoArquivo = nomeArquivo;
        } else {
            caminhoArquivo = nomeArquivo + ".252";
        }

        String nomeBase;
        if (caminhoArquivo.contains(".")) {
            nomeBase = caminhoArquivo.substring(0, caminhoArquivo.lastIndexOf('.'));
        } else {
            nomeBase = caminhoArquivo;
        }

        try {
            // Inicia análise
            Parser parser = new Parser(new FileReader(caminhoArquivo));
            TabelaSimbolos tabelaSimbolos = new TabelaSimbolos();
            List<Token> sequenciaTokens = new ArrayList<>();
            GeradorRelatorios gerador = new GeradorRelatorios();

            System.out.println("Iniciando análise de: " + caminhoArquivo);

            // Análise Léxica
            Token token = null;
            do {
                token = parser.getNextToken();

                if (token.kind == ParserConstants.EOF) {
                    break;
                }

                // Adiciona todos os tokens na lista do relatório .LEX
                sequenciaTokens.add(token);

                // Lógica da Tabela de Símbolos: Apenas IDENTIFICADORES
                if (token.kind == ParserConstants.IDENTIFIER) {
                    tabelaSimbolos.inserir(token);
                }

            } while (token.kind != ParserConstants.EOF);

            // Geração dos Relatórios
            gerador.gerarRelatorioLEX(sequenciaTokens, nomeBase, tabelaSimbolos);
            gerador.gerarRelatorioTAB(tabelaSimbolos, nomeBase);

            System.out.println("Sucesso! Relatórios gerados.");

        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo '" + caminhoArquivo + "' não encontrado.");
        } catch (TokenMgrError e) {
            System.err.println("Erro Léxico: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }

    }
}