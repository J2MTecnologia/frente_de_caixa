package br.com.yaw.ssjc.event;

import br.com.yaw.ssjc.model.Mercadoria;

/**
 * Evento deve ser gerado durante a inclusão de uma <code>Mercadoria</code>.
 * 
 * <p>Recebe a referência da <code>Mercadoria</code> que foi incluida.</p>
 * 
 * @author YaW Tecnologia
 */
public class IncluirMercadoriaEvent extends AbstractEvent<Mercadoria> {
	
	public IncluirMercadoriaEvent(Mercadoria m) {
		super(m);
	}
}
