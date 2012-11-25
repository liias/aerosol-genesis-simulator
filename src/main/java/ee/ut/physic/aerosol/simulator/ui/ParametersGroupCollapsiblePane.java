package ee.ut.physic.aerosol.simulator.ui;

import ee.ut.physic.aerosol.simulator.domain.simulation.parameter.ParameterDefinition;
import org.jdesktop.swingx.JXCollapsiblePane;

import javax.swing.undo.UndoManager;
import java.awt.*;
import java.util.Collection;

public class ParametersGroupCollapsiblePane extends JXCollapsiblePane {
    private ParametersGroupPane parametersGroupPane;

    public ParametersGroupCollapsiblePane(OrderForm orderForm, Collection<ParameterDefinition> parameterDefinitions, UndoManager undoManager) {
        parametersGroupPane = new ParametersGroupPane(orderForm, parameterDefinitions, undoManager);
        setLayout(new BorderLayout());
        setContentPane(parametersGroupPane);
        setAnimated(false);
    }

    public Collection<ParameterLine> getParameterLines() {
        return parametersGroupPane.getParameterLines();
    }
    public ParameterLine getParameterLineByName(String name) throws NoSuchFieldException {
        return parametersGroupPane.getParameterLineByName(name);
    }
}
