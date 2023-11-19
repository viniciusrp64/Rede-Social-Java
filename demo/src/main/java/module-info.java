module br.com.redesocial.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens br.com.redesocial.demo to javafx.fxml;
    exports br.com.redesocial.demo;
}