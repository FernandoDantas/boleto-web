package com.mie.boleto;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

public class exemploBoleto {
  public static void main(String[] args) {
	// Cedente
			Cedente cedente = new Cedente("Millennium Impresos Eletrônicos Ltda", "10.687.566/0001-97");
			
			// Sacado
			Sacado sacado = new Sacado("Fernando Junior");
			
			// Endereço do sacado
			Endereco endereco = new Endereco();
			endereco.setUF(UnidadeFederativa.PB);
			endereco.setLocalidade("Campina Grande");
			endereco.setCep(new CEP("10100-000"));
			endereco.setBairro("Santo Antônio");
			endereco.setLogradouro("Rua Fco. Antonio Nascimento");
			endereco.setNumero("318");
			
			sacado.addEndereco(endereco);
			
			// Criando o título
			ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_BRADESCO.create());
			contaBancaria.setAgencia(new Agencia(1010, "0"));
			contaBancaria.setNumeroDaConta(new NumeroDaConta(2020, "0"));
			contaBancaria.setCarteira(new Carteira(6));
			
			Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
			titulo.setNumeroDoDocumento("101010");
			titulo.setNossoNumero("12345678901");
			titulo.setDigitoDoNossoNumero("P");
			
			titulo.setValor(BigDecimal.valueOf(100.23));
			titulo.setDataDoDocumento(new Date());
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(2014, 9, 20);
			titulo.setDataDoVencimento(calendar.getTime());
			
			titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
			
			titulo.setAceite(Aceite.N);
			
			// Dados do boleto
			Boleto boleto = new Boleto(titulo);
			boleto.setLocalPagamento("Pagar preferencialmente no Bradesco");
			boleto.setInstrucaoAoSacado("Evite multas, pague em dias suas contas.");
			
			boleto.setInstrucao1("Após o vencimento, aplicar multa de 2,00% e juros de 1,00% ao mês");
			
			BoletoViewer boletoViewer = new BoletoViewer(boleto);
			File arquivoPdf = boletoViewer.getPdfAsFile("meu-primeiro-boleto.pdf");
			
			mostrarNaTela(arquivoPdf);
		}
		
		private static void mostrarNaTela(File arquivo) {
			Desktop desktop = Desktop.getDesktop();
			
			try {
				desktop.open(arquivo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
