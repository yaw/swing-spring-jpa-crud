package br.com.yaw.ssjpac.controller;

import javax.annotation.PostConstruct;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.yaw.ssjpac.action.AbstractAction;
import br.com.yaw.ssjpac.action.BooleanExpression;
import br.com.yaw.ssjpac.action.ConditionalAction;
import br.com.yaw.ssjpac.dao.MercadoriaDAO;
import br.com.yaw.ssjpac.event.DeletarMercadoriaEvent;
import br.com.yaw.ssjpac.event.IncluirMercadoriaEvent;
import br.com.yaw.ssjpac.model.Mercadoria;
import br.com.yaw.ssjpac.ui.IncluirMercadoriaFrame;
import br.com.yaw.ssjpac.validation.Validator;

/**
 * Define a <code>Controller</code> responsável por gerir a tela de
 * inclusão/edição de <code>Mercadoria</code>.
 *
 * <p>
 * <code>IncluirMercadoriaController</code> é mapeada como
 * <code>@Component</code> do Spring. Dessa forma uma instância de
 * <code>IncluirMercadoriaController</code> pode ser criada e gerenciada pelo
 * Spring, favorecendo a Inversão de Controle <i>(IoC)</i> e Injeção de
 * Dependência <i>(DI)</i>.
 * </p>
 *
 * <p>
 * Essa <code>Controller</code> depende de outros componentes da camada
 * <code>Model</code> (DAO) e <code>View</code> (telas). As dependências são
 * resolvidas pelo Spring, através da <strong>Injeção de Dependência</strong> c/
 * a anotação <code>@Autowired</code>.
 * </p>
 *
 * @see br.com.yaw.sjpac.controller.PersistenceController
 *
 * @author YaW Tecnologia
 */
@Component
public class IncluirMercadoriaController extends AbstractController {

    @Autowired
    private IncluirMercadoriaFrame frame;

    @Autowired
    private Validator<Mercadoria> validador;

    @Autowired
    private MercadoriaDAO dao;

    @Autowired
    ListaMercadoriaController parent;
    
    /**
     * Método executado pelo <code>Spring</code>, depois de criar a instância de
     * <code>IncluirMercadoriaController</code>.
     *
     * <p>
     * Faz o registro das ações.</p>
     */
    @PostConstruct
    private void init() {
        setParentController(parent);
        frame.addWindowListener(this);
        registerAction(frame.getCancelarButton(), new AbstractAction() {
            public void action() {
                cleanUp();
            }
        });

        registerAction(frame.getSalvarButton(),
                ConditionalAction.build()
                        .addConditional(new BooleanExpression() {

                            @Override
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

                            @Transactional
                            @Override
                            protected void action() {
                                m = frame.getMercadoria();
                                dao.save(m);
                            }

                            @Override
                            public void posAction() {
                                cleanUp();
                                fireEvent(new IncluirMercadoriaEvent(m));
                                m = null;
                            }
                        }));

        registerAction(frame.getExcluirButton(),
                new AbstractAction() {
            private Mercadoria m;

            @Transactional
            @Override
            protected void action() {
                m = frame.getMercadoria();
                Integer id = frame.getMercadoriaId();
                if (id != null) {
                    dao.deleteById(id);
                }
            }

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
