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
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos(){
        
        try{
            ArrayList<ProdutosDTO> listaProdutos = new ArrayList<>();
            dbctx = conexao.connectDB();
            st = dbctx.prepareStatement("SELECT * FROM leiloestdsat.produtos WHERE status = 'Vendido'");
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
    
    public boolean venderProduto(int id){
        try{
        dbctx = conexao.connectDB();
        st = dbctx.prepareStatement("SELECT * FROM leiloestdsat.produtos where id = ?");
        st.setInt(1,id);
        rs = st.executeQuery();
        
        if(rs.next()){
        String status = rs.getString("status");
        }
        
        if(!"Vendido".equals(status)){
            st = dbctx.prepareStatement("UPDATE leiloestdsat.produtos SET status = 'Vendido' where id= ? ");
            st.setInt(1, id);
            int linhasAfetadas = st.executeUpdate();
            if(linhasAfetadas > 0){
                JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!");
            }
            return true;
        }
        }
        catch (SQLException e){
        System.out.println("Erro ao conectar: " + e.getMessage());
        }
        
        JOptionPane.showMessageDialog(null, "Venda não realizada!");
        return false;
        
    }      
}

