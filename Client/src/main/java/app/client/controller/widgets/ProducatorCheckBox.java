package app.client.controller.widgets;

import app.server.model.Producator;
import javafx.scene.control.CheckBox;

public class ProducatorCheckBox extends CheckBox {
    private Producator producator;

    public ProducatorCheckBox(Producator tip) {
        super(tip.toString());
        this.producator = tip;
    }

    public Producator getProducator() {
        return producator;
    }

}
