package br.com.alura.leilao.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.UsuarioBuilder;

class UsuarioDaoTest {

	private UsuarioDao dao;
	private Usuario usuario;
	private EntityManager em;
	
	@BeforeEach
	public void beforeEach() {
		this.em =  JPAUtil.getEntityManager();
		this.dao = new UsuarioDao(em);
		em.getTransaction().begin();
		usuario = criarUsuario();
	}
	
	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}
	
	@Test
	void deveriaEncontrarUsuarioCadastrado() {
		assertNotNull(this.dao.buscarPorUsername(usuario.getNome()));
	}
	
	@Test
	void naoDeveriaEncontrarUsuarioNaoCadastrado() {
		assertThrows(NoResultException.class ,
				() -> this.dao.buscarPorUsername("beltrano"));
	}
	
	@Test
	void deveriaRemoverUmUsuario() {
		dao.deletar(usuario);
		
		assertThrows(NoResultException.class, 
				() -> this.dao.buscarPorUsername(usuario.getNome()));
	}
	
	private Usuario criarUsuario() {
		Usuario usuario = new UsuarioBuilder()
			.comNome("fulano")
			.comEmail("fulano@gmail.com")
			.comSenha("12345678")
			.criar();
		
		em.persist(usuario);
		return usuario;
	}
}
