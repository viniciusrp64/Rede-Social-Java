package br.com.redesocial.demo;

import java.time.LocalDateTime;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Mensagem {
    private int COD_MENSAGEM;
    private String DES_MENSAGEM;
    private LocalDateTime DTA_ENVIO;
    private Usuario COD_REMETENTE;
    private Usuario COD_DESTINATARIO;

    public Mensagem(int id, String DES_MENSAGEM, LocalDateTime DTA_ENVIO, Usuario COD_REMETENTE, Usuario COD_DESTINATARIO) {
        this.COD_MENSAGEM = id;
        this.DES_MENSAGEM = DES_MENSAGEM;
        this.DTA_ENVIO = DTA_ENVIO;
        this.COD_REMETENTE = COD_REMETENTE;
        this.COD_DESTINATARIO = COD_DESTINATARIO;
    }

    public Mensagem(String DES_MENSAGEM, Usuario COD_REMETENTE, Usuario COD_DESTINATARIO) {
        this.DES_MENSAGEM = DES_MENSAGEM;
        this.COD_REMETENTE = COD_REMETENTE;
        this.COD_DESTINATARIO = COD_DESTINATARIO;
    }

    public int getId() {
        return COD_MENSAGEM;
    }

    public void setId(int id) {
        this.COD_MENSAGEM = id;
    }

    public String getDES_MENSAGEM() {
        return DES_MENSAGEM;
    }

    public void setDES_MENSAGEM(String DES_MENSAGEM) {
        this.DES_MENSAGEM = DES_MENSAGEM;
    }

    public LocalDateTime getDTA_ENVIO() {
        return DTA_ENVIO;
    }

    public void setDTA_ENVIO(LocalDateTime DTA_ENVIO) {
        this.DTA_ENVIO = DTA_ENVIO;
    }

    public void setCOD_REMETENTE(Usuario COD_REMETENTE) {
        this.COD_REMETENTE = COD_REMETENTE;
    }

    public void setCOD_DESTINATARIO(Usuario COD_DESTINATARIO) {
        this.COD_DESTINATARIO = COD_DESTINATARIO;
    }

    public Usuario getCOD_REMETENTE() {
        return COD_REMETENTE;
    }

    public Usuario getCOD_DESTINATARIO() {
        return COD_DESTINATARIO;
    }

    // Método para enviar mensagem
    public void enviarMensagem() {
        BancoDeDados bancoDeDados = new BancoDeDados();
        Connection conexao = bancoDeDados.conectar();

        String query = "INSERT INTO mensagem (COD_MENSAGEM, DES_MENSAGEM, DTA_ENVIO, COD_REMETENTE, COD_DESTINATARIO) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, this.COD_MENSAGEM);
            statement.setString(2, DES_MENSAGEM);
            statement.setTimestamp(3, Timestamp.valueOf(this.DTA_ENVIO));
            statement.setInt(4, COD_REMETENTE.getCOD_USUARIO());
            statement.setInt(5, COD_DESTINATARIO.getCOD_USUARIO());
            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas > 0) {
                // Se a operação de envio de mensagem for bem-sucedida, obtemos o ID gerado automaticamente e o definimos como atributo da mensagem
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    this.COD_MENSAGEM = rs.getInt(1);
                }
                System.out.println("Mensagem Enviada!");
            } else {
                System.out.println("Mensagem Não Enviada!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao enviar mensagem: " + e.getMessage());
        } finally {
            bancoDeDados.desconectar(conexao);
        }
    }

    // Método para listar mensagens
    public static List<Mensagem> listarMensagens(Usuario COD_REMETENTE, Usuario COD_DESTINATARIO) {
        BancoDeDados bancoDeDados = new BancoDeDados();
        Connection conexao = bancoDeDados.conectar();

        String query = "SELECT COD_MENSAGEM, DES_MENSAGEM, DTA_ENVIO FROM mensagem WHERE (COD_REMETENTE = ? AND COD_DESTINATARIO = ?) OR (COD_REMETENTE = ? AND COD_DESTINATARIO = ?) ORDER BY DTA_ENVIO ASC";
        try {
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setInt(1, COD_REMETENTE.getCOD_USUARIO());
            statement.setInt(2, COD_DESTINATARIO.getCOD_USUARIO());
            statement.setInt(3, COD_DESTINATARIO.getCOD_USUARIO());
            statement.setInt(4, COD_REMETENTE.getCOD_USUARIO());
            ResultSet resultado = statement.executeQuery();

            List<Mensagem> mensagens = new ArrayList<>();
            while (resultado.next()) {
                int id = resultado.getInt("COD_MENSAGEM");
                String conteudo = resultado.getString("DES_MENSAGEM");
                LocalDateTime dataHora = resultado.getTimestamp("DTA_ENVIO").toLocalDateTime();
                Mensagem mensagem = new Mensagem(id, conteudo, dataHora, COD_REMETENTE, COD_DESTINATARIO);
                mensagens.add(mensagem);
            }
            return mensagens;
        } catch (SQLException e) {
            System.out.println("Erro ao listar mensagens: " + e.getMessage());
            return null;
        } finally {
            bancoDeDados.desconectar(conexao);
        }
    }
}
