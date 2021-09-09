package br.com.alura.leilao.dao;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class UsuarioDaoTest {

	private UsuarioDao dao;
	
	@Test
	void testeBuscaDeUsuarioPeloUsername() {
		EntityManager em = JPAUtil.getEntityManager();

		Usuario usuario = new Usuario("fulano", "fulano@gmail.com", "12345678");
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		
		this.dao = new UsuarioDao(em);
		Usuario encontrado = this.dao.buscarPorUsername(usuario.getNome());
		
		assertNotNull(encontrado);
	}
}
