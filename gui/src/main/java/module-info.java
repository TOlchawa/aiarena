module com.thm.aiarena.gui {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                            
    opens com.thm.aiarena.gui to javafx.fxml;
    exports com.thm.aiarena.gui;
}