package br.com.yaw.ssjc.event;

/**
 * Evento deve ser gerado quando for necessário atualizar a tabela de mercadorias.
 * 
 * @author YaW Tecnologia
 */
public class AtualizarListarMercadoriaEvent extends AbstractEvent<Object> {
	
	public AtualizarListarMercadoriaEvent(Object m) {
		super(m);
	}

}
