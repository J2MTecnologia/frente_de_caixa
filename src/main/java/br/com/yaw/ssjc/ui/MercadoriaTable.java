package br.com.yaw.ssjc.ui;

import java.util.List;

import javax.swing.JTable;

import br.com.yaw.ssjc.model.Mercadoria;

/**
 * <code>JTable</code> com operações customizadas para entidade <code>Mercadoria</code>.
 * 
 * @see br.com.yaw.sjc.ui.MercadoriaTableModel
 * 
 * @author YaW Tecnologia
 */
public class MercadoriaTable extends JTable {

	private MercadoriaTableModel modelo;
	
	public MercadoriaTable() {
		modelo = new MercadoriaTableModel();
		setModel(modelo);
	}
	
	/**
	 * @return <code>Mercadoria</code> selecionada na tabela. Caso a tabela não tenha elementos, retorna <code>null</code>.
	 */
	public Mercadoria getMercadoriaSelected() {
		int i = getSelectedRow();
		if (i < 0) {
			return null;
		}
		return modelo.getMercadoriaAt(i);
	}
	
	/**
	 * Recarrega a tabela de <code>Mercadoria</code> com a lista <code>mercadorias</code>.
	 * @param mercadorias <code>List</code> com os elementos <code>Mercadoria</code> que devem ser exibidos na tabela.
	 */
	public void reload(List<Mercadoria> mercadorias) {
		modelo.reload(mercadorias);
	}
}
