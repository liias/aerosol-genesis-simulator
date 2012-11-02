package ee.ut.physic.aerosol.simulator.ui;

import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.UndoManager;

public class OrderFormUndoManager extends UndoManager {

    private OrderForm orderForm;

    public OrderFormUndoManager(OrderForm orderForm) {
        super();
        this.orderForm = orderForm;
    }

    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        super.undoableEditHappened(e);
        orderForm.undoableEditHappened();
    }
}
