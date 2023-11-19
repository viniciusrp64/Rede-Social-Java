package teste;

import br.com.redesocial.demo.Mensagem;
import br.com.redesocial.demo.Usuario;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ChatApp extends Application {
    private Usuario usuarioLogado;

    @Override
    public void start(Stage primaryStage) {
        // Criação dos componentes da interface gráfica
        Label labelUsuario = new Label("Usuário:");
        TextField textFieldUsuario = new TextField();
        Label labelSenha = new Label("Senha:");
        PasswordField passwordFieldSenha = new PasswordField();
        Button buttonLogin = new Button("Entrar");

        VBox vBoxLogin = new VBox(10, labelUsuario, textFieldUsuario, labelSenha, passwordFieldSenha, buttonLogin);
        vBoxLogin.setPadding(new Insets(20));
        vBoxLogin.setPrefWidth(300);

        Label labelMensagens = new Label("Mensagens recebidas:");
        ListView<Mensagem> listViewMensagens = new ListView<>();

        VBox vBoxMensagens = new VBox(10, labelMensagens, listViewMensagens);
        vBoxMensagens.setPadding(new Insets(20));
        vBoxMensagens.setPrefWidth(300);

        TextArea textAreaMensagem = new TextArea();
        Button buttonEnviar = new Button("Enviar");

        HBox hBoxEnviarMensagem = new HBox(10, textAreaMensagem, buttonEnviar);
        hBoxEnviarMensagem.setPadding(new Insets(20));
        hBoxEnviarMensagem.setPrefWidth(600);

        BorderPane borderPane = new BorderPane(vBoxMensagens, null, hBoxEnviarMensagem, null, vBoxLogin);

        // Configuração dos componentes da interface gráfica
        buttonLogin.setOnAction(event -> {
            // Validação do login do usuário
            String nomeUsuario = textFieldUsuario.getText();
            String senha = passwordFieldSenha.getText();
            if (validarLogin(nomeUsuario, senha)) {
                usuarioLogado = new Usuario(nomeUsuario);
                borderPane.setLeft(vBoxMensagens);
                borderPane.setRight(hBoxEnviarMensagem);
                borderPane.setBottom(null);
                atualizarListaMensagens();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Usuário ou senha inválidos.");
                alert.showAndWait();
            }
        });

        // Configuração da janela principal
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ChatApp");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    private boolean validarLogin(String nomeUsuario, String senha) {
        // Lógica para validar o login do usuário
     return true;
    }


    private void atualizarListaMensagens() {
        // Lógica para atualizar a lista de mensagens recebidas pelo usuário logado
    }

    public static void main(String[] args) {
        launch(args);
    }
}
