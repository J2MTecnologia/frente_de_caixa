package br.com.yaw.ssjc.controller;

import javax.swing.JOptionPane;

import br.com.yaw.ssjc.validation.MercadoriaValidator;
import br.com.yaw.ssjc.validation.Validator;
import br.com.yaw.ssjc.action.AbstractAction;
import br.com.yaw.ssjc.action.BooleanExpression;
import br.com.yaw.ssjc.action.ConditionalAction;
import br.com.yaw.ssjc.dao.MercadoriaDAO;
import br.com.yaw.ssjc.event.DeletarMercadoriaEvent;
import br.com.yaw.ssjc.event.IncluirMercadoriaEvent;
import br.com.yaw.ssjc.model.Mercadoria;
import br.com.yaw.ssjc.ui.IncluirMercadoriaFrame;

/**
 * Define a <code>Controller</code> responsável por gerir a tela de inclusão/edição de <code>Mercadoria</code>.
 * 
 * @see br.com.yaw.ssjc.controller.AbstractController
 * 
 * @author YaW Tecnologia
 */
public class IncluirMercadoriaController extends AbstractController {

	private IncluirMercadoriaFrame frame;
	private Validator<Mercadoria> validador = new MercadoriaValidator();
	
	public IncluirMercadoriaController(final ListaMercadoriaController parent) {
		super(parent);
		this.frame = new IncluirMercadoriaFrame();
		
		frame.addWindowListener(this);
		registerAction(frame.getCancelarButton(), new AbstractAction() {
			@Override
			public void action() {
				cleanUp();
			}
		});
		
		registerAction(frame.getSalvarButton(), 
			ConditionalAction.build()
				.addConditional(new BooleanExpression() {
					
					public boolean conditional() {
						Mercadoria m = frame.getMercadoria();
						String msg = validador.validate(m);
						if (!"".equals(msg == null ? "" : msg)) {
							JOptionPane.showMessageDialog(frame, msg, "Validação", JOptionPane.INFORMATION_MESSAGE);
							return false;
						}
						return true;
					}
				})
				.addAction(new AbstractAction() {
					private Mercadoria m;
					
					@Override
					public void action() {
						m = frame.getMercadoria();
						MercadoriaDAO dao =  parent.getMercadoriaDAO();
						dao.save(m);
					}
					
					@Override
					public void posAction() {
						cleanUp();
						fireEvent(new IncluirMercadoriaEvent(m));
						m = null;
					}
				}));
		
		
		registerAction(frame.getExcluirButton(), new AbstractAction() {
			private Mercadoria m;
			
			@Override
			public void action() {
				Integer id = frame.getMercadoriaId();
				if (id != null) {
					m = parent.getMercadoriaDAO().findById(id);
					if (m != null) {
						parent.getMercadoriaDAO().remove(m);
					}
				}
			}
			
			@Override
			public void posAction() {
				cleanUp();
				fireEvent(new DeletarMercadoriaEvent(m));
				m = null;
			}
		});
	}
	
	public void show() {
		frame.setTitle("Incluir Mercadoria");
		frame.setVisible(true);
	}
	
	public void show(Mercadoria m) {
		frame.setMercadoria(m);
		frame.setTitle("Editar Mercadoria");
		frame.setVisible(true);
	}
	
	@Override
	protected void cleanUp() {
		frame.setVisible(false);
		frame.resetForm();
		
		super.cleanUp();
	}
	
}
