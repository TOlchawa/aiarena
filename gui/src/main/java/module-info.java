module com.thm.aiarena.gui {

    requires javafx.fxml;
    requires javafx.controls;
    requires org.controlsfx.controls;
    requires lombok;
    requires com.thm.aiarena.model;
    requires com.thm.aiarena.communication;
    requires java.desktop;


    opens com.thm.aiarena.gui to javafx.fxml, javafx.controls, javafx.graphics, com.thm.aiarena.model, com.thm.aiarena.communication, lombok;
//    opens com.thm.aiarena.gui;
    exports com.thm.aiarena.gui;
}