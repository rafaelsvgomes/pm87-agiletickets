package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
	@Test
	public void deveriaCriarUmaUnicaSessaoQuandoInicioEFimMesmoDia(){
		Espetaculo espetaculo = new Espetaculo();
		espetaculo.setNome("RageAgainst");
		
		LocalDate inicio = new LocalDate(2016,12,14);
		LocalDate fim = inicio; 
		LocalTime horario = new LocalTime(21, 0);
		Periodicidade periodicidade = Periodicidade.DIARIA;
		
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		
		Assert.assertNotNull("Lista de sessões não pode ser nula", sessoes);
		Assert.assertEquals("A lista deve ter apenas 1 item", 1, sessoes.size());
		
		Sessao sessao = sessoes.get(0);
		
		Assert.assertEquals("14/12/16", sessao.getDia());
		Assert.assertEquals("21:00", sessao.getHora());
		Assert.assertEquals(espetaculo, sessao.getEspetaculo());
	}
	
	@Test
	public void deveriaCriarMaisSessoesQuandoInicioMenorQueFimPeriodicidadeDiaria(){
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2016,12,14);
		LocalDate fim = new LocalDate(2016,12,16); 
		LocalTime horario = new LocalTime(21, 0);
		Periodicidade periodicidade = Periodicidade.DIARIA;
		
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		deveInformarListaInvalida(sessoes);
		
		Assert.assertEquals("A lista deve ter 3 sessoes", 3, sessoes.size());
		
		AssertSessao.assertEquals("14/12/16","21:00", espetaculo, sessoes.get(0));
		AssertSessao.assertEquals("15/12/16","21:00", espetaculo, sessoes.get(1));
		AssertSessao.assertEquals("16/12/16","21:00", espetaculo, sessoes.get(2));
	}
	

	private void deveInformarListaInvalida(List<Sessao> sessoes) {
		Assert.assertNotNull("Lista de sessões não pode ser nula", sessoes);
	}
	
	
	 static class AssertSessao {
		public static void assertEquals(String dia, String hora, Espetaculo espetaculo, Sessao sessao) {
			Assert.assertEquals(dia, sessao.getDia());
			Assert.assertEquals(hora, sessao.getHora());
			Assert.assertEquals(espetaculo, sessao.getEspetaculo());			
		}
	 }
	
}


