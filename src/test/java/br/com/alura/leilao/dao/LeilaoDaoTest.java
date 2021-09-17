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

class LeilaoDaoTest {

	private LeilaoDao dao;
	private Leilao leilao;
	private Usuario usuario;
	private EntityManager em;
	
	@BeforeEach
	public void beforeEach() {
		this.em =  JPAUtil.getEntityManager();
		this.dao = new LeilaoDao(em);
		em.getTransaction().begin();

		this.usuario = criarUsuario();
		this.leilao = criarLeilao();
	}
	
	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}
	
	private Leilao criarLeilao() {
		Leilao leilao = new Leilao("Mochila", new BigDecimal("70"), LocalDate.now(), usuario);
		em.persist(leilao);
		return leilao;
	}
	
	private Usuario criarUsuario() {
		Usuario usuario = new Usuario("fulano", "fulano@gmail.com", "12345678");
		em.persist(usuario);
		return usuario;
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
