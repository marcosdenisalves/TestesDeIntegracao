package br.com.alura.leilao.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.LeilaoBuilder;
import br.com.alura.leilao.util.builder.UsuarioBuilder;

class LeilaoDaoTest {

	private LeilaoDao dao;
	private Leilao leilao;
	private Usuario usuario;
	
	private EntityManager em;
	
	private void criarEntidades() {
		usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@gmail.com")
				.comSenha("12345678")
				.criar();
		
		em.persist(usuario);
		
		leilao = new LeilaoBuilder()
				.comNome("Mochila")
				.comValorInicial("500")
				.comData(LocalDate.now())
				.comUsuario(usuario)
				.criar();
		
		leilao = dao.salvar(leilao);
	}

	@BeforeEach
	public void beforeEach() {
		this.em =  JPAUtil.getEntityManager();
		this.dao = new LeilaoDao(em);
		em.getTransaction().begin();

		criarEntidades();
	}
	
	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}
	
	
	@Test
	void deveriaCadastrarUmLeilao() {
		assertNotNull(dao.buscarPorId(leilao.getId()));
	}
	
	@Test
	void deveriaAtualizarUmLeilao() {
		String novoNome = "Celular";
		BigDecimal novoValor = new BigDecimal("400");
		
		this.leilao.setNome(novoNome);
		this.leilao.setValorInicial(novoValor);
		Leilao leilao = dao.salvar(this.leilao);

		assertEquals(novoNome, leilao.getNome());
		assertEquals(novoValor, leilao.getValorInicial());
	}
}
