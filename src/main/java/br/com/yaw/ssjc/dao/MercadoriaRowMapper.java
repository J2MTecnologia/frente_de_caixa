package br.com.yaw.ssjc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.yaw.ssjc.model.Mercadoria;

/**
 * Implementa um componente <code>RowMapper</code> que resolve o mapeamento <i>de</i> <code>ResultSet</code> <i>para</i> <code>Mercadoria</code>.
 * 
 * <p>Nesse contexto o <code>Mapper</code> acessa os dados em um nível mais baixo e transforma em uma instância de <code>Mercadoria</code>.</p>
 * 
 * @see org.springframework.jdbc.core.RowMapper
 * 
 * @author YaW Tecnologia
 */
public class MercadoriaRowMapper implements RowMapper<Mercadoria>{

	public Mercadoria mapRow(ResultSet rs, int row) throws SQLException {
		MercadoriaExtractor ex = new MercadoriaExtractor();
		return ex.extractData(rs);
	}
	
}
