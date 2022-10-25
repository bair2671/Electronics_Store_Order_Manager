package app.client.controller.widgets;

import app.server.model.TipProdus;
import javafx.scene.control.CheckBox;

public class TipCheckBox extends CheckBox {
    private TipProdus tip;

    public TipCheckBox(TipProdus tip) {
        super(tip.toString());
        this.tip = tip;
    }

    public TipProdus getTip() {
        return tip;
    }

}