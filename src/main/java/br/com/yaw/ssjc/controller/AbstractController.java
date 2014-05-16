package br.com.yaw.ssjc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.com.yaw.ssjc.action.AbstractAction;
import br.com.yaw.ssjc.event.AbstractEvent;
import br.com.yaw.ssjc.event.AbstractEventListener;

/**
 * Classe abstrata que define uma estrutura para componentes da camada controller do padrão arquitetural MVC.
 * 
 * <p><code>Controller</code> é o componente intermediário entre a apresentação (View) e os componentes de negócio (Serviços + DAO + Model).</p>
 * 
 * <p>Habilita:</p>
 * <ul>
 *   <li>Definição de <code>eventos</code> e <code>ações</code> para os componentes gráficos.</li>
 *   <li>Apresentar mensagens de erros gerados em <code>ações</code>dos componentes gráficos.</li>
 *   <li>Liberar recursos do componente no encerramento da janela.</li>
 * </ul>
 * 
 * @author YaW Tecnologia
 */
public abstract class AbstractController implements ActionListener, WindowListener {

	private static Logger log = Logger.getLogger(AbstractController.class);
	
	private AbstractController parent;
	
	private Map<String, AbstractAction> actions = 
			new HashMap<String, AbstractAction>();
	
	private Map<Class<?>, List<AbstractEventListener<?>>> eventListeners = 
			new HashMap<Class<?>, List<AbstractEventListener<?>>>();
	
	public AbstractController(){
		this(null);
	}
	
	/**
	 * Controller possui um auto-relacionamento, útil em situações aonde uma hierarquia de controladores deve ser respeitada.
	 * @param parent controller <i>pai</i>
	 */
	public AbstractController(AbstractController parent){
		this.parent = parent;
	}

	/**
	 * Registra uma <code>ação</code> a um componente <code>button</code>.
	 * 
	 * @param source
	 * @param action
	 */
	protected void registerAction(AbstractButton source, AbstractAction action) {
		if (source.getActionCommand() == null) {
			throw new RuntimeException("Componente (Button) sem ação definida!");
		}
		log.debug("Registrando action: " + action.getClass().getName() + " para o botão: " + source.getText());
        source.addActionListener(this);
        this.actions.put(source.getActionCommand(), action);
    }
	
	/**
	 * Notifica o <code>evento</code>.
	 * 
	 * @param event tipo do evento
	 */
	@SuppressWarnings("unchecked")
	protected void fireEvent(AbstractEvent<?> event) {
		if (eventListeners.get(event.getClass()) != null) {
            for (AbstractEventListener eventListener : eventListeners.get(event.getClass())) {
                log.debug("Evento: " + event.getClass().getName() + " com listener: " + eventListener.getClass().getName());
                eventListener.handleEvent(event);
            }
        }
		if (parent != null)
			parent.fireEvent(event);
	}
	
	/**
	 * Registra um <code>listener</code> que deve ser acionado de acordo com o tipo do <code>evento</code>.
	 * 
	 * @param eventClass tipo do evento
	 * @param eventListener tratador (listener) do evento
	 */
	protected void registerEventListener(Class<?> eventClass, AbstractEventListener<?> eventListener) {
        log.debug("Registrando listener: " + eventListener + " para o evento: " + eventClass.getName());
        java.util.List<AbstractEventListener<?>> listenersForEvent = eventListeners.get(eventClass);
        if (listenersForEvent == null) {
        	listenersForEvent = new ArrayList<AbstractEventListener<?>>(); 
        }
        listenersForEvent.add(eventListener);
        eventListeners.put(eventClass, listenersForEvent);
    }
	
	public void actionPerformed(ActionEvent actionEvent) {
		try {
			AbstractButton button = (AbstractButton) actionEvent.getSource();
			String actionCommand = button.getActionCommand();
			AbstractAction action = actions.get(actionCommand);

			if (action != null) {
				log.debug("Executando commando: " + actionCommand + " com action: " + action.getClass());
				try {
					action.actionPerformed();
				} catch (Exception ex) {
					handlerException(ex);
				}
			}
		} catch (ClassCastException e) {
			handlerException(new IllegalArgumentException("Action source não é um Abstractbutton: " + actionEvent));
		}
	}
	
	/**
	 * Caso ocorra alguma falha durante a <code>ação</code> apresenta uma mensagem.
	 * 
	 * @param ex
	 */
	protected void handlerException(Exception ex) {
		log.error(ex);
		JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	}
	
	public AbstractController getParentController() {
        return parent;
    }
	
	/**
	 * Método utilizado para liberar recursos carregados pela Controller.
	 */
	protected void cleanUp() {}
	
	public void windowClosing(WindowEvent windowEvent) { 
		cleanUp(); 
	}
	
    public void windowOpened(WindowEvent windowEvent) {}
    public void windowClosed(WindowEvent windowEvent) {}
    public void windowIconified(WindowEvent windowEvent) {}
    public void windowDeiconified(WindowEvent windowEvent) {}
    public void windowActivated(WindowEvent windowEvent) {}
    public void windowDeactivated(WindowEvent windowEvent) {}
    
}
