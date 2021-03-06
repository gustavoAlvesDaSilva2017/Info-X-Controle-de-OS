/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;

    // Classe de apoio para o banco de dados
    PreparedStatement pst = null;
    ResultSet rs = null; // resultados das conexões sql

    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void consultar() {
        String sql = "SELECT * FROM tbusuarios WHERE iduser=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTfId.getText());
            rs = pst.executeQuery();

            if (rs.next()) {
                jTfNomeUsuario.setText(rs.getString(2));
                jTfTelefone.setText(rs.getString(3));
                jTfLogin.setText(rs.getString(4));
                jPfSenha.setText(rs.getString(5));
                jCbPerfil.setSelectedItem(rs.getString(6));
            } else {
                jTfId.setText("");
                jTfNomeUsuario.setText("");
                jTfTelefone.setText("");
                jTfLogin.setText("");
                jPfSenha.setText("");
                jCbPerfil.setSelectedIndex(0);
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                jTfId.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void adicionar() {
        String sql = "INSERT INTO tbusuarios (iduser,usuario,fone,login,senha,perfil) VALUE(?, ?, ?, ?, ?, ?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTfId.getText());
            pst.setString(2, jTfNomeUsuario.getText());
            pst.setString(3, jTfTelefone.getText());
            pst.setString(4, jTfLogin.getText());
            pst.setString(5, String.valueOf(jPfSenha.getPassword()));
            pst.setString(6, jCbPerfil.getSelectedItem().toString());

            // validaçao dos campos obrigatórios
            if (jTfId.getText().isEmpty() || jTfNomeUsuario.getText().isEmpty() || jTfLogin.getText().isEmpty() || String.valueOf(jPfSenha.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

                    jTfId.setText("");
                    jTfNomeUsuario.setText("");
                    jTfTelefone.setText("");
                    jTfLogin.setText("");
                    jPfSenha.setText("");
                    jCbPerfil.setSelectedIndex(0);
                    jTfId.requestFocus();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void alterar() {
        String sql = "UPDATE tbusuarios SET usuario=?, fone=?, login=?, senha=?, perfil=? WHERE iduser=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTfNomeUsuario.getText());
            pst.setString(2, jTfTelefone.getText());
            pst.setString(3, jTfLogin.getText());
            pst.setString(4, String.valueOf(jPfSenha.getPassword()));
            pst.setString(5, jCbPerfil.getSelectedItem().toString());
            pst.setString(6, jTfId.getText());

            if (jTfId.getText().isEmpty() || jTfNomeUsuario.getText().isEmpty() || jTfLogin.getText().isEmpty() || String.valueOf(jPfSenha.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos obrigatórios!");
            } else {
                int alterado = pst.executeUpdate();

                if (alterado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do usuário alterado com sucesso!");

                    jTfId.setText("");
                    jTfNomeUsuario.setText("");
                    jTfTelefone.setText("");
                    jTfLogin.setText("");
                    jPfSenha.setText("");
                    jCbPerfil.setSelectedIndex(0);
                    jTfId.requestFocus();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void remover() {
        // confirmação da remoção;
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que desja remover este usuário?","Atenção",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM tbusuarios WHERE iduser=?";
            
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, jTfId.getText());
                
                int excluido = pst.executeUpdate();
                
                if (excluido > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!!");
                    
                    jTfId.setText("");
                    jTfNomeUsuario.setText("");
                    jTfTelefone.setText("");
                    jTfLogin.setText("");
                    jPfSenha.setText("");
                    jCbPerfil.setSelectedIndex(0);
                    jTfId.requestFocus();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTfId = new javax.swing.JTextField();
        jTfNomeUsuario = new javax.swing.JTextField();
        jTfTelefone = new javax.swing.JTextField();
        jPfSenha = new javax.swing.JPasswordField();
        jTfLogin = new javax.swing.JTextField();
        jCbPerfil = new javax.swing.JComboBox<>();
        jBtnAddUser = new javax.swing.JButton();
        jBtnPesqUser = new javax.swing.JButton();
        jBtnEditUser = new javax.swing.JButton();
        jBtnDltUser = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setPreferredSize(new java.awt.Dimension(550, 460));

        jLabel1.setText("* Id");

        jLabel2.setText("* Nome do Usuário");

        jLabel3.setText("Telefone");

        jLabel4.setText("* Login");

        jLabel5.setText("* Senha");

        jLabel6.setText("* Perfi do Usuário");

        jCbPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user" }));

        jBtnAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        jBtnAddUser.setToolTipText("Adicionar");
        jBtnAddUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnAddUser.setPreferredSize(new java.awt.Dimension(80, 80));
        jBtnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAddUserActionPerformed(evt);
            }
        });

        jBtnPesqUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        jBtnPesqUser.setToolTipText("Pesquisar");
        jBtnPesqUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnPesqUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesqUserActionPerformed(evt);
            }
        });

        jBtnEditUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        jBtnEditUser.setToolTipText("Editar");
        jBtnEditUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnEditUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditUserActionPerformed(evt);
            }
        });

        jBtnDltUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        jBtnDltUser.setToolTipText("Excluir");
        jBtnDltUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnDltUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDltUserActionPerformed(evt);
            }
        });

        jLabel7.setText("* Campos obrigatórios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTfNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTfId, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBtnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(jBtnPesqUser)
                                .addGap(43, 43, 43)
                                .addComponent(jBtnEditUser))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jBtnDltUser)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jPfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(43, 43, 43)
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jCbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBtnAddUser, jBtnDltUser, jBtnEditUser, jBtnPesqUser});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jTfNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jTfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jPfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jCbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jBtnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesqUser, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEditUser, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnDltUser, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBtnAddUser, jBtnDltUser, jBtnEditUser, jBtnPesqUser});

        setBounds(0, 0, 550, 460);
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnPesqUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesqUserActionPerformed
        consultar();
    }//GEN-LAST:event_jBtnPesqUserActionPerformed

    private void jBtnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAddUserActionPerformed
        adicionar();
    }//GEN-LAST:event_jBtnAddUserActionPerformed

    private void jBtnEditUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditUserActionPerformed
        alterar();
    }//GEN-LAST:event_jBtnEditUserActionPerformed

    private void jBtnDltUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDltUserActionPerformed
        remover();
    }//GEN-LAST:event_jBtnDltUserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAddUser;
    private javax.swing.JButton jBtnDltUser;
    private javax.swing.JButton jBtnEditUser;
    private javax.swing.JButton jBtnPesqUser;
    private javax.swing.JComboBox<String> jCbPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPasswordField jPfSenha;
    private javax.swing.JTextField jTfId;
    private javax.swing.JTextField jTfLogin;
    private javax.swing.JTextField jTfNomeUsuario;
    private javax.swing.JTextField jTfTelefone;
    // End of variables declaration//GEN-END:variables
}
