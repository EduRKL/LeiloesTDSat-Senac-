/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;


public class ProdutosDAO {
    
    conectaDAO conexao = new conectaDAO();
    Connection dbctx;
    PreparedStatement st;
    ResultSet rs;
    int status;
    
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        try{
            dbctx = conexao.connectDB();
            st = dbctx.prepareStatement("INSERT INTO leiloestdsat.produtos (nome, valor, status) VALUES (?, ?, ?)");
            st.setString(1, produto.getNome());
            st.setInt(2, produto.getValor());
            st.setString(3, produto.getStatus());
            
            status = st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto Cadastrado com Sucesso!");

        } 
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar o produto: " + e.getMessage());
        }
        
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        try{
            ArrayList<ProdutosDTO> listaProdutos = new ArrayList<>();
            dbctx = conexao.connectDB();
            st = dbctx.prepareStatement("SELECT * FROM leiloestdsat.produtos");
            rs = st.executeQuery();
            
            while (rs.next()){
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setValor(rs.getInt("valor"));
            produto.setStatus(rs.getString("status"));
            listagem.add(produto);
            }
            return listagem;
        }
        catch (SQLException e){
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
            }
    }
    
    
    
        
}

