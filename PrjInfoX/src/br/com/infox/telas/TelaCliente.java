/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;
// a linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 * @author gustavo
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
	initComponents();
	conexao = ModuloConexao.conector();
    }

    private void adicionarCliente() {
	String sql = "INSERT INTO tbclientes (nomecli,endcli,fonecli,emailcli) VALUES(?, ?, ?, ?)";

	try {
	    pst = conexao.prepareStatement(sql);
	    pst.setString(1, jTfNomeCliente.getText());
	    pst.setString(2, jTfEnderecoCliente.getText());
	    pst.setString(3, jTfTelefoneCliente.getText());
	    pst.setString(4, jTfEmailCliente.getText());
	    // validaçao dos campos obrigatórios
	    if (jTfNomeCliente.getText().isEmpty() || jTfTelefoneCliente.getText().isEmpty()) {
		JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos obrigatórios!");
	    } else {
		int adicionado = pst.executeUpdate();

		if (adicionado > 0) {
		    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");

		    jTfNomeCliente.setText("");
		    jTfEnderecoCliente.setText("");
		    jTfTelefoneCliente.setText("");
		    jTfEmailCliente.setText("");
		    jTfNomeCliente.requestFocus();
		    pesquisaClientePeloNomeComFiltro();
		}
	    }

	} catch (Exception e) {
	    JOptionPane.showMessageDialog(null, e);
	}
    }

    // Método para pesquisar clientes pelo nome com filto
    private void pesquisaClientePeloNomeComFiltro() {
	String sql = "SELECT * FROM tbclientes WHERE nomecli LIKE ?";
	try {
	    pst = conexao.prepareStatement(sql);

	    // passando o conteúdo da caixa da pesquisa para a interrogração
	    // atenção ao % - continuação da string sql
	    pst.setString(1, jTfPesquisarCliente.getText() + "%");
	    rs = pst.executeQuery();

	    // a linha abaixo usa a biblioteca r2xml.rar para preencher a tabela
	    jTblClientes.setModel(DbUtils.resultSetToTableModel(rs));
	} catch (Exception e) {
	    JOptionPane.showMessageDialog(null, e);
	}
    }

    // Método para setar os campos do formulário com o conteúdo da tabela
    public void setarCampos() {
	int setar = jTblClientes.getSelectedRow();
	jTfIdCliente.setText(jTblClientes.getModel().getValueAt(setar, 0).toString());
	jTfNomeCliente.setText(jTblClientes.getModel().getValueAt(setar, 1).toString());
	jTfEnderecoCliente.setText(jTblClientes.getModel().getValueAt(setar, 2).toString());
	jTfTelefoneCliente.setText(jTblClientes.getModel().getValueAt(setar, 3).toString());
	jTfEmailCliente.setText(jTblClientes.getModel().getValueAt(setar, 4).toString());

	jBtnAdicionarCliente.setEnabled(false);
        jBtnEditarCliente.setEnabled(true);
        jBtnExcluirCliente.setEnabled(true);
    } // fim setar canpos

    private void alterarCliente() {
	String sql = "UPDATE tbclientes SET nomecli=?, endcli=?, fonecli=?, emailcli=? WHERE idcli=?";

	try {
	    pst = conexao.prepareStatement(sql);
	    pst.setString(1, jTfNomeCliente.getText());
	    pst.setString(2, jTfEnderecoCliente.getText());
	    pst.setString(3, jTfTelefoneCliente.getText());
	    pst.setString(4, jTfEmailCliente.getText());
	    pst.setString(5, jTfIdCliente.getText());
	    if (jTfIdCliente.getText().isEmpty() || jTfNomeCliente.getText().isEmpty() || jTfTelefoneCliente.getText().isEmpty()) {
		JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos obrigatórios!");
	    } else {
		int alterado = pst.executeUpdate();

		if (alterado > 0) {
		    JOptionPane.showMessageDialog(null, "Dados do cliente alterados com sucesso!");
		    
		    jTfNomeCliente.setText("");
		    jTfEnderecoCliente.setText("");
		    jTfTelefoneCliente.setText("");
		    jTfEmailCliente.setText("");
		    jTfPesquisarCliente.setText("");
		    jTfNomeCliente.requestFocus();
		    pesquisaClientePeloNomeComFiltro();
		    jBtnAdicionarCliente.setEnabled(true);
                    jBtnEditarCliente.setEnabled(false);
                    jBtnExcluirCliente.setEnabled(false);
		}
	    }

	} catch (Exception e) {
	    JOptionPane.showMessageDialog(null, e);
	} // fim try - catch
    } // fim altera clientes

    private void removerCliente() {

	String sql = "DELETE FROM tbclientes WHERE idcli=?";
	if (jTfIdCliente.getText().isEmpty()) {
	    JOptionPane.showMessageDialog(null, "É necessário selecionar um cliente da tabela para poder excluí-lo");
	} else {
	    try {
		pst = conexao.prepareStatement(sql);
		pst.setString(1, jTfIdCliente.getText());

		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que desja remover este cliente?", "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (confirma == JOptionPane.YES_OPTION) {
		    int excluido = pst.executeUpdate();

		    if (excluido > 0) {
			JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!!");

			jTfIdCliente.setText("");
			jTfNomeCliente.setText("");
			jTfEnderecoCliente.setText("");
			jTfTelefoneCliente.setText("");
			jTfEmailCliente.setText("");
			jTfNomeCliente.requestFocus();
			pesquisaClientePeloNomeComFiltro();
			jBtnAdicionarCliente.setEnabled(true);
                        jBtnEditarCliente.setEnabled(false);
                        jBtnExcluirCliente.setEnabled(false);
		    }
		}

	    } catch (Exception e) {
		JOptionPane.showMessageDialog(null, e);
	    } // fim try catch
	}

    } // fim if j option pane

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        jTfPesquisarCliente = new javax.swing.JTextField();
        jLblPesquisar = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblClientes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTfNomeCliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTfEnderecoCliente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTfTelefoneCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTfEmailCliente = new javax.swing.JTextField();
        jBtnAdicionarCliente = new javax.swing.JButton();
        jBtnEditarCliente = new javax.swing.JButton();
        jBtnExcluirCliente = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTfIdCliente = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Clientes");
        setPreferredSize(new java.awt.Dimension(550, 460));

        jLabel11.setText("* Campos obrigatórios");

        jTfPesquisarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTfPesquisarClienteKeyReleased(evt);
            }
        });

        jLblPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/search.png"))); // NOI18N
        jLblPesquisar.setToolTipText("Listar Todos");
        jLblPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLblPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLblPesquisarMouseClicked(evt);
            }
        });

        jTblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblClientes);

        jLabel1.setText("* Nome");

        jLabel2.setText("Endereço");

        jLabel3.setText("* Telefone");

        jLabel4.setText(" E-mail");

        jBtnAdicionarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        jBtnAdicionarCliente.setToolTipText("Adicionar");
        jBtnAdicionarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnAdicionarCliente.setPreferredSize(new java.awt.Dimension(80, 80));
        jBtnAdicionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAdicionarClienteActionPerformed(evt);
            }
        });

        jBtnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        jBtnEditarCliente.setToolTipText("Editar");
        jBtnEditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnEditarCliente.setEnabled(false);
        jBtnEditarCliente.setPreferredSize(new java.awt.Dimension(80, 80));
        jBtnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarClienteActionPerformed(evt);
            }
        });

        jBtnExcluirCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        jBtnExcluirCliente.setToolTipText("Excluir");
        jBtnExcluirCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnExcluirCliente.setEnabled(false);
        jBtnExcluirCliente.setPreferredSize(new java.awt.Dimension(80, 80));
        jBtnExcluirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnExcluirClienteActionPerformed(evt);
            }
        });

        jLabel5.setText("Id:");

        jTfIdCliente.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jTfPesquisarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLblPesquisar)
                .addGap(101, 101, 101)
                .addComponent(jLabel11)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTfIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTfEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTfEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTfNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTfTelefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addComponent(jBtnAdicionarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jBtnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jBtnExcluirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 54, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel11))
                    .addComponent(jTfPesquisarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTfIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTfNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTfEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTfTelefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTfEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBtnAdicionarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnEditarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnExcluirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        setBounds(0, 0, 550, 460);
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnAdicionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAdicionarClienteActionPerformed
	adicionarCliente();
    }//GEN-LAST:event_jBtnAdicionarClienteActionPerformed

    private void jTfPesquisarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTfPesquisarClienteKeyReleased
	pesquisaClientePeloNomeComFiltro();
    }//GEN-LAST:event_jTfPesquisarClienteKeyReleased

    private void jTblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblClientesMouseClicked
	setarCampos();
    }//GEN-LAST:event_jTblClientesMouseClicked

    private void jBtnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarClienteActionPerformed
	alterarCliente();
    }//GEN-LAST:event_jBtnEditarClienteActionPerformed

    private void jBtnExcluirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnExcluirClienteActionPerformed
	removerCliente();

    }//GEN-LAST:event_jBtnExcluirClienteActionPerformed

    private void jLblPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLblPesquisarMouseClicked
        pesquisaClientePeloNomeComFiltro();
    }//GEN-LAST:event_jLblPesquisarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAdicionarCliente;
    private javax.swing.JButton jBtnEditarCliente;
    private javax.swing.JButton jBtnExcluirCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLblPesquisar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTblClientes;
    private javax.swing.JTextField jTfEmailCliente;
    private javax.swing.JTextField jTfEnderecoCliente;
    private javax.swing.JTextField jTfIdCliente;
    private javax.swing.JTextField jTfNomeCliente;
    private javax.swing.JTextField jTfPesquisarCliente;
    private javax.swing.JTextField jTfTelefoneCliente;
    // End of variables declaration//GEN-END:variables
}
