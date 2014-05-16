package br.com.yaw.ssjc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.springframework.context.ApplicationContext;

import br.com.yaw.ssjc.action.AbstractAction;
import br.com.yaw.ssjc.dao.MercadoriaDAO;
import br.com.yaw.ssjc.event.AbstractEventListener;
import br.com.yaw.ssjc.event.AtualizarListarMercadoriaEvent;
import br.com.yaw.ssjc.event.BuscarMercadoriaEvent;
import br.com.yaw.ssjc.event.DeletarMercadoriaEvent;
import br.com.yaw.ssjc.event.IncluirMercadoriaEvent;
import br.com.yaw.ssjc.model.Mercadoria;
import br.com.yaw.ssjc.ui.ListaMercadoriasFrame;
import br.com.yaw.ssjc.ui.SobreFrame;

/**
 * Define a <code>Controller</code> principal do sistema, responsável por gerir a tela com a lista de <code>Mercadoria</code>.
 * 
 * @see br.com.yaw.ssjc.controller.AbstractController
 * 
 * @author YaW Tecnologia
 */
public class ListaMercadoriaController extends AbstractController {

	private ListaMercadoriasFrame frame;
	private SobreFrame sobreFrame;
	
	private IncluirMercadoriaController incluirController;
	private BuscarMercadoriaController buscarController;
	
	private ApplicationContext context;
	
	public ListaMercadoriaController(ApplicationContext context) {
		this.context = context;
		this.frame = new ListaMercadoriasFrame();
		this.frame.addWindowListener(this);
		this.sobreFrame = new SobreFrame();

		this.incluirController = new IncluirMercadoriaController(this);
		this.buscarController = new BuscarMercadoriaController(this);
		
		registerAction(frame.getNewButton(), new AbstractAction() {
			@Override
			public void action() {
				incluirController.show();
			}
		});
		
		registerAction(frame.getRefreshButton(), new AbstractAction() {
			@Override
			public void action() {
				fireEvent(new DeletarMercadoriaEvent(null));
			}
		});
		
		registerAction(frame.getFindButton(), new AbstractAction() {
			@Override
			public void action() {
				buscarController.show();
			}
		});
		
		AbstractAction sobreAction = new AbstractAction() {
			@Override
			protected void action() {
				sobreFrame.setVisible(true);
			}
		};
		registerAction(frame.getMenuSobre(), sobreAction);
		frame.getMenuAjuda().addListener(sobreAction);
		
		frame.getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					Mercadoria m = frame.getTable().getMercadoriaSelected();
					if (m != null) {
						incluirController.show(m);
					}
				}
			}
		});
		
		registerEventListener(IncluirMercadoriaEvent.class, new AbstractEventListener<IncluirMercadoriaEvent>() {
			public void handleEvent(IncluirMercadoriaEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						refreshTable();
					}
				});
			}
		});
		
		registerEventListener(DeletarMercadoriaEvent.class, new AbstractEventListener<DeletarMercadoriaEvent>() {
			public void handleEvent(DeletarMercadoriaEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						refreshTable();
					}
				});
			}
		});
		
		registerEventListener(AtualizarListarMercadoriaEvent.class, new AbstractEventListener<AtualizarListarMercadoriaEvent>() {
			public void handleEvent(AtualizarListarMercadoriaEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						refreshTable();
					}
				});
			}
		});
		
		registerEventListener(BuscarMercadoriaEvent.class, new AbstractEventListener<BuscarMercadoriaEvent>() {
			public void handleEvent(final BuscarMercadoriaEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						List<Mercadoria> list = event.getTarget();
						if (list != null) {
							frame.refreshTable(event.getTarget());
						}
					}
				});
			}
		});
		
		frame.setVisible(true);
		getMercadoriaDAO().init();
		refreshTable();
	}
	
	private void refreshTable() {
		frame.refreshTable(getMercadoriaDAO().getAll());
	}
	
	protected MercadoriaDAO getMercadoriaDAO() {
		MercadoriaDAO dao = (MercadoriaDAO) context.getBean("mercadoriaDAOSpring");
		if (dao == null) {
			throw new RuntimeException("Componente de persistência não foi encontrado!");
		}
		return dao;
	}
	
}
