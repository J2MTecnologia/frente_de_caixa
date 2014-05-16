package br.com.yaw.ssjc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.com.yaw.ssjc.model.Mercadoria;

/**
 * <code>ResultSetExtractor</code> que acessa as informações na API JDBC para transformar em um objeto <code>Mercadoria</code>.
 * 
 * <p>Recupera as seguintes informações do <code>ResultSet</code>: </p>
 * <ul>
 *   <li>id</li>
 *   <li>nome</li>
 *   <li>descricao</li>
 *   <li>quantidade</li>
 *   <li>preco</li>
 * </ul>
 * 
 * @see br.com.yaw.ssjc.dao.MercadoriaRowMapper
 * @author YaW Tecnologia
 */
public class MercadoriaExtractor implements ResultSetExtractor<Mercadoria> {

	public Mercadoria extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		int id = rs.getInt("id");
		String nome = rs.getString("nome");
		String descricao = rs.getString("descricao");
		int qtde = rs.getInt("quantidade");
		double preco = rs.getDouble("preco");
		
		return new Mercadoria(id, nome, descricao, qtde, preco);
	}
	
}
