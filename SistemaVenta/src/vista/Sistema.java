/*
 * 
 */
package vista;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.System.Logger;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.ClienteDAO;
import modelo.Config;
import modelo.Detalle;
import modelo.Eventos;
import modelo.Producto;
import modelo.ProductoDao;
import modelo.Proveedor;
import modelo.ProveedorDao;
import modelo.Venta;
import modelo.VentaDao;
import modelo.login;
import modelo.loginDAO;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import reporte.Excel;
import reporte.Grafico;

public class Sistema extends javax.swing.JFrame {

    Cliente cl = new Cliente();
    ClienteDAO client = new ClienteDAO();
    Proveedor pr = new Proveedor();
    ProveedorDao PrDao = new ProveedorDao();
    Producto pro = new Producto();
    ProductoDao proDao = new ProductoDao();
    Venta v = new Venta();
    VentaDao Vdao = new VentaDao();
    Detalle DetVenta = new Detalle();
    Config conf = new Config();
    Eventos event = new Eventos();
    login user = new login();
    loginDAO userDao = new loginDAO();
    DefaultTableModel modelo = new DefaultTableModel();
    int item;
    double Totalpagar = 0.00;

    public Sistema() {
        initComponents();

    }

    public Sistema(login priv) {
        initComponents();

        setLocationRelativeTo(null);
        setResizable(false);
        txt_idUsuario.setVisible(false);
        txt_idCliente.setVisible(false);
        txt_idProveedor.setVisible(false);
        txt_idProducto.setVisible(false);
        txt_IdVentas.setVisible(false);
        txtIdConfig.setVisible(false);
        ListarConfig();
        
        proDao.ConsultarProveedor(cbx_ProveedorProducto);
        AutoCompleteDecorator.decorate(cbx_ProveedorProducto);
         
        if (priv.getPermiso().equals("Contable")) {
            btnUsuario.setEnabled(false);
            btnProveedor.setEnabled(false);
            btnProducto.setEnabled(false);
            btnConfig.setEnabled(false);
            Label_Vendedor.setText(priv.getNombre());
        } else if (priv.getPermiso().equals("Gestion")) {
            btnUsuario.setEnabled(false);
            btnNuevaVenta.setEnabled(false);
            btnCliente.setEnabled(false);
            btnVentas.setEnabled(false);
            btnConfig.setEnabled(false);
            jTabbedPane1.setSelectedIndex(2);
            Label_Vendedor.setText(priv.getNombre());
        } else {
            Label_Vendedor.setText(priv.getNombre());
        }

    }

    public void ListarCliente() {
        List<Cliente> ListarCl = client.ListarCliente();
        modelo = (DefaultTableModel) TableCliente.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarCl.size(); i++) {
            ob[0] = ListarCl.get(i).getId();
            ob[1] = ListarCl.get(i).getRuc();
            ob[2] = ListarCl.get(i).getNombre();
            ob[3] = ListarCl.get(i).getRazon();
            ob[4] = ListarCl.get(i).getTelefono();
            ob[5] = ListarCl.get(i).getDireccion();
            modelo.addRow(ob);
        }
        TableCliente.setModel(modelo);
    }

    public void ListarProveedor() {
        List<Proveedor> ListarPr = PrDao.ListaProveedor();
        modelo = (DefaultTableModel) TableProveedor.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPr.size(); i++) {
            ob[0] = ListarPr.get(i).getId();
            ob[1] = ListarPr.get(i).getRuc();
            ob[2] = ListarPr.get(i).getNombre();
            ob[3] = ListarPr.get(i).getTelefono();
            ob[4] = ListarPr.get(i).getDireccion();
            ob[5] = ListarPr.get(i).getRazon();
            modelo.addRow(ob);
        }
        TableProveedor.setModel(modelo);
    }

    public void ListarProducto() {
        List<Producto> ListarPro = proDao.ListarProducto();
        modelo = (DefaultTableModel) TableProducto.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getCodigo();
            ob[2] = ListarPro.get(i).getNombre();
            ob[3] = ListarPro.get(i).getProveedor();
            ob[4] = ListarPro.get(i).getStock();
            ob[5] = ListarPro.get(i).getPrecio();
            modelo.addRow(ob);
        }
        TableProducto.setModel(modelo);
    }

    public void ListarVentas() {
        List<Venta> ListarVenta = Vdao.ListarVentas();
        modelo = (DefaultTableModel) TableVentas.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < ListarVenta.size(); i++) {
            ob[0] = ListarVenta.get(i).getId();
            ob[1] = ListarVenta.get(i).getCliente();
            ob[2] = ListarVenta.get(i).getVendedor();
            ob[3] = ListarVenta.get(i).getTotal();
            modelo.addRow(ob);
        }
        TableVentas.setModel(modelo);
    }

    public void ListarUsuario() {
        List<login> ListUser = userDao.ListarUsuario();
        modelo = (DefaultTableModel) TableUsuario.getModel();

        Object[] ob = new Object[8];
        for (int i = 0; i < ListUser.size(); i++) {
            ob[0] = ListUser.get(i).getId();
            ob[1] = ListUser.get(i).getNombre();
            ob[2] = ListUser.get(i).getCorreo();
            ob[3] = ListUser.get(i).getTelefono();
            ob[4] = ListUser.get(i).getUsuario();
            ob[5] = ListUser.get(i).getPass();
            ob[6] = ListUser.get(i).getPermiso();
            ob[7] = ListUser.get(i).getEstatus();
            modelo.addRow(ob);
        }
        TableUsuario.setModel(modelo);
    }

    public void ListarConfig() {
        conf = proDao.BuscarDatos();
        txtIdConfig.setText("" + conf.getId_config());
        txtRucConfig.setText("" + conf.getRuc());
        txtNombreConfig.setText("" + conf.getNombre());
        txtTelefonoConfig.setText("" + conf.getTelefono());
        txtDireccionConfig.setText("" + conf.getDireccion());
        txtRazonConfig.setText("" + conf.getRazon());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel_Logo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnUsuario = new javax.swing.JButton();
        btnNuevaVenta = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnProducto = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        Label_Vendedor = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_CodigoVenta = new javax.swing.JTextField();
        txt_DescripcionVenta = new javax.swing.JTextField();
        txt_CantidadVenta = new javax.swing.JTextField();
        txt_PrecioVenta = new javax.swing.JTextField();
        txt_StockDisponible = new javax.swing.JTextField();
        btn_EliminarVenta = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_RucCliVenta = new javax.swing.JTextField();
        txt_NombreCliVenta = new javax.swing.JTextField();
        btn_GenerarVenta = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        Labe_Total = new javax.swing.JLabel();
        txt_TelefonoCliVenta = new javax.swing.JTextField();
        txt_RaSocialCliVenta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txt_DireccionCliVenta = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_RucCliente = new javax.swing.JTextField();
        txt_NombreCliente = new javax.swing.JTextField();
        txt_TelefonoCliente = new javax.swing.JTextField();
        txt_DireccionCliente = new javax.swing.JTextField();
        txt_RaSocilaCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableCliente = new javax.swing.JTable();
        btn_GuardarCliente = new javax.swing.JButton();
        btn_EditarCliente = new javax.swing.JButton();
        btn_EliminarCLiente = new javax.swing.JButton();
        btn_NuevoCliente = new javax.swing.JButton();
        txt_idCliente = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txt_RucProveedor = new javax.swing.JTextField();
        txt_RaSocialProveedor = new javax.swing.JTextField();
        txt_NombreProveedor = new javax.swing.JTextField();
        txt_TelefonoProveedor = new javax.swing.JTextField();
        txt_DireccionProveedor = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProveedor = new javax.swing.JTable();
        btn_GuardarProveedor = new javax.swing.JButton();
        btn_EditarProveedor = new javax.swing.JButton();
        btn_NuevoProveedor = new javax.swing.JButton();
        btn_EliminarProveedor = new javax.swing.JButton();
        txt_idProveedor = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txt_CodigoProducto = new javax.swing.JTextField();
        txt_DescripcionProducto = new javax.swing.JTextField();
        txt_CantidadProducto = new javax.swing.JTextField();
        txt_PrecioProducto = new javax.swing.JTextField();
        cbx_ProveedorProducto = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableProducto = new javax.swing.JTable();
        btn_GuardarProducto = new javax.swing.JButton();
        btn_EditarProducto = new javax.swing.JButton();
        btn_EliminarProducto = new javax.swing.JButton();
        btn_NuevoProducto = new javax.swing.JButton();
        btn_ExcelProducto = new javax.swing.JButton();
        txt_idProducto = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        txt_IdVentas = new javax.swing.JTextField();
        btn_GraficaCircular = new javax.swing.JButton();
        DateInicio = new com.toedter.calendar.JDateChooser();
        jLabel34 = new javax.swing.JLabel();
        btn_GraficaBarras = new javax.swing.JButton();
        DateFin = new com.toedter.calendar.JDateChooser();
        jLabel41 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtRucConfig = new javax.swing.JTextField();
        txtNombreConfig = new javax.swing.JTextField();
        txtTelefonoConfig = new javax.swing.JTextField();
        txtDireccionConfig = new javax.swing.JTextField();
        txtRazonConfig = new javax.swing.JTextField();
        btnActualizarConfig = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        txtIdConfig = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        txt_NombreUsuario = new javax.swing.JTextField();
        txt_MaiUsuariol = new javax.swing.JTextField();
        txt_TelefonoUsuario = new javax.swing.JTextField();
        txt_UsernameUsuario = new javax.swing.JTextField();
        txt_PasswordUsuario = new javax.swing.JTextField();
        cmb_PermisosUsuario = new javax.swing.JComboBox<>();
        cmb_EstatusUsuario = new javax.swing.JComboBox<>();
        btn_ActualizarUsuario = new javax.swing.JButton();
        btn_EliminarUsuario = new javax.swing.JButton();
        jLabel_etiqueta = new javax.swing.JLabel();
        jLabel_etiqueta1 = new javax.swing.JLabel();
        jLabel_etiqueta2 = new javax.swing.JLabel();
        jLabel_etiqueta3 = new javax.swing.JLabel();
        jLabel_etiqueta4 = new javax.swing.JLabel();
        jLabel_etiqueta5 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        btn_NuevoUsuario = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        TableUsuario = new javax.swing.JTable();
        btn_LimpiarUsuario = new javax.swing.JToggleButton();
        txt_idUsuario = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel_Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_icon.jpg"))); // NOI18N
        getContentPane().add(jLabel_Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 130));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/encabezado.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 830, 180));

        jPanel1.setBackground(new java.awt.Color(0, 51, 204));

        btnUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Clientes.png"))); // NOI18N
        btnUsuario.setText("Usuarios");
        btnUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioActionPerformed(evt);
            }
        });

        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Nventa.png"))); // NOI18N
        btnNuevaVenta.setText("Nueva Venta");
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });

        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Clientes.png"))); // NOI18N
        btnCliente.setText("Cliente");
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });

        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/proveedor.png"))); // NOI18N
        btnProveedor.setText("Proveedor");
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });

        btnProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/producto.png"))); // NOI18N
        btnProducto.setText("Productos");
        btnProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductoActionPerformed(evt);
            }
        });

        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/compras.png"))); // NOI18N
        btnVentas.setText("Ventas");
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/config.png"))); // NOI18N
        btnConfig.setText("Config");
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        Label_Vendedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Label_Vendedor.setForeground(new java.awt.Color(255, 255, 255));
        Label_Vendedor.setText("jLabel34");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnConfig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevaVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Label_Vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(194, Short.MAX_VALUE)
                .addComponent(Label_Vendedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 610));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Codigo");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Descripcion");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Cantidad");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Precio");

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 204));
        jLabel6.setText("Stock Disponible");

        txt_CodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_CodigoVentaKeyPressed(evt);
            }
        });

        txt_DescripcionVenta.setEditable(false);

        txt_CantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_CantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_CantidadVentaKeyTyped(evt);
            }
        });

        txt_PrecioVenta.setEditable(false);

        txt_StockDisponible.setEditable(false);

        btn_EliminarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        btn_EliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarVentaActionPerformed(evt);
            }
        });

        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ));
        jScrollPane1.setViewportView(TableVenta);
        if (TableVenta.getColumnModel().getColumnCount() > 0) {
            TableVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            TableVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            TableVenta.getColumnModel().getColumn(2).setPreferredWidth(30);
            TableVenta.getColumnModel().getColumn(3).setPreferredWidth(30);
            TableVenta.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("DNI/RUC");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Nombre");

        txt_RucCliVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_RucCliVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_RucCliVentaKeyTyped(evt);
            }
        });

        txt_NombreCliVenta.setEditable(false);

        btn_GenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        btn_GenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GenerarVentaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Total:");

        Labe_Total.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Labe_Total.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Labe_Total.setText("------------");

        txt_TelefonoCliVenta.setEditable(false);

        txt_RaSocialCliVenta.setEditable(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Razon Social");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Telefono");

        txt_DireccionCliVenta.setEditable(false);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("Direccion");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addGap(88, 88, 88))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txt_TelefonoCliVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(21, 21, 21)))
                                .addGap(4, 4, 4)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33)
                                    .addComponent(txt_DireccionCliVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(214, 214, 214))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_RucCliVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_NombreCliVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_RaSocialCliVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)))
                        .addComponent(btn_GenerarVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Labe_Total, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_CodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(jLabel2)))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_DescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(txt_CantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(txt_PrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_StockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addComponent(btn_EliminarVenta)
                                .addGap(33, 33, 33))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4)
                                .addComponent(jLabel6))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_CantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_CodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_DescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_PrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_StockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(btn_EliminarVenta, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_RucCliVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_NombreCliVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_RaSocialCliVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jLabel33))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_TelefonoCliVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_DireccionCliVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_GenerarVenta)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(Labe_Total)))))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel2);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("DNI/RUC:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Nombre:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Telefono:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Direccion:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Razon Social:");

        txt_RucCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_RucClienteKeyTyped(evt);
            }
        });

        TableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI/RUC", "NOMBRE", "RAZON SOCIAL", "TELEFONO", "DIRECCION"
            }
        ));
        TableCliente.setFocusable(false);
        TableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableCliente);
        if (TableCliente.getColumnModel().getColumnCount() > 0) {
            TableCliente.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableCliente.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableCliente.getColumnModel().getColumn(3).setPreferredWidth(80);
            TableCliente.getColumnModel().getColumn(4).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        btn_GuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/GuardarTodo.png"))); // NOI18N
        btn_GuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_GuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GuardarClienteActionPerformed(evt);
            }
        });

        btn_EditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Actualizar (2).png"))); // NOI18N
        btn_EditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_EditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditarClienteActionPerformed(evt);
            }
        });

        btn_EliminarCLiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/borrar.png"))); // NOI18N
        btn_EliminarCLiente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_EliminarCLiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarCLienteActionPerformed(evt);
            }
        });

        btn_NuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nuevo.png"))); // NOI18N
        btn_NuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_NuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoClienteActionPerformed(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel37.setText("Registro Cliente");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_DireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt_RaSocilaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_NombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_TelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_RucCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_idCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_EliminarCLiente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btn_GuardarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_EditarCliente)
                            .addComponent(btn_NuevoCliente, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(335, 335, 335)
                .addComponent(jLabel37)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel37)
                .addGap(18, 18, 18)
                .addComponent(txt_idCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txt_RucCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txt_NombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txt_RaSocilaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txt_TelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txt_DireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_GuardarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_EditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_EliminarCLiente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_NuevoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("tab2", jPanel3);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Razon Social:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Direccion:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Telefono:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Nombre:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("DNI/RUC:");

        txt_RucProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_RucProveedorKeyTyped(evt);
            }
        });

        TableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI/RUC", "NOMBRE", "TELEFONO", "DIRECCION", "RAZON SOCIAL"
            }
        ));
        TableProveedor.setFocusable(false);
        TableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableProveedor);
        if (TableProveedor.getColumnModel().getColumnCount() > 0) {
            TableProveedor.getColumnModel().getColumn(0).setPreferredWidth(10);
            TableProveedor.getColumnModel().getColumn(1).setPreferredWidth(40);
            TableProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProveedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableProveedor.getColumnModel().getColumn(4).setPreferredWidth(80);
            TableProveedor.getColumnModel().getColumn(5).setPreferredWidth(70);
        }

        btn_GuardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/GuardarTodo.png"))); // NOI18N
        btn_GuardarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_GuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GuardarProveedorActionPerformed(evt);
            }
        });

        btn_EditarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Actualizar (2).png"))); // NOI18N
        btn_EditarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_EditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditarProveedorActionPerformed(evt);
            }
        });

        btn_NuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nuevo.png"))); // NOI18N
        btn_NuevoProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_NuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoProveedorActionPerformed(evt);
            }
        });

        btn_EliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/borrar.png"))); // NOI18N
        btn_EliminarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_EliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarProveedorActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel38.setText("Registro Proveedor");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txt_idProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 139, Short.MAX_VALUE))
                            .addComponent(txt_DireccionProveedor)
                            .addComponent(txt_TelefonoProveedor)
                            .addComponent(txt_NombreProveedor)
                            .addComponent(txt_RucProveedor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_RaSocialProveedor, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_EliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btn_GuardarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_EditarProveedor)
                            .addComponent(btn_NuevoProveedor, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel38)
                .addGap(251, 251, 251))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_idProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txt_RucProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txt_NombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txt_TelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txt_DireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txt_RaSocialProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_GuardarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_EditarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_EliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_NuevoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jTabbedPane1.addTab("tab3", jPanel4);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Codigo:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Descripcion:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Cantidad:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Precio:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Proveedor:");

        txt_CantidadProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_CantidadProductoKeyTyped(evt);
            }
        });

        txt_PrecioProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_PrecioProductoKeyTyped(evt);
            }
        });

        cbx_ProveedorProducto.setEditable(true);

        TableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO", "DESCRIPCION", "PROVEEDOR", "STOCK", "PRECIO"
            }
        ));
        TableProducto.setFocusable(false);
        TableProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableProducto);
        if (TableProducto.getColumnModel().getColumnCount() > 0) {
            TableProducto.getColumnModel().getColumn(0).setPreferredWidth(10);
            TableProducto.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableProducto.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProducto.getColumnModel().getColumn(3).setPreferredWidth(100);
            TableProducto.getColumnModel().getColumn(4).setPreferredWidth(40);
            TableProducto.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        btn_GuardarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/GuardarTodo.png"))); // NOI18N
        btn_GuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GuardarProductoActionPerformed(evt);
            }
        });

        btn_EditarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Actualizar (2).png"))); // NOI18N
        btn_EditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditarProductoActionPerformed(evt);
            }
        });

        btn_EliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/borrar.png"))); // NOI18N
        btn_EliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarProductoActionPerformed(evt);
            }
        });

        btn_NuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nuevo.png"))); // NOI18N
        btn_NuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoProductoActionPerformed(evt);
            }
        });

        btn_ExcelProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        btn_ExcelProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ExcelProductoActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel39.setText("Registro Producto");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbx_ProveedorProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_PrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_CodigoProducto)
                                .addComponent(txt_DescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_CantidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_idProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btn_ExcelProducto)
                                .addGap(18, 18, 18)
                                .addComponent(btn_GuardarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_NuevoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_EliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_EditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addGap(280, 280, 280))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(txt_idProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txt_CodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txt_DescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txt_CantidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txt_PrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(cbx_ProveedorProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(32, 32, 32)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_ExcelProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_GuardarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_NuevoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_EliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_EditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jTabbedPane1.addTab("tab4", jPanel5);

        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL"
            }
        ));
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(1).setPreferredWidth(100);
            TableVentas.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableVentas.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        btn_GraficaCircular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/torta.png"))); // NOI18N
        btn_GraficaCircular.setText("Ventas por Clientes");
        btn_GraficaCircular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GraficaCircularActionPerformed(evt);
            }
        });

        jLabel34.setText("Seleccionar fecha Inicio:");

        btn_GraficaBarras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/report.png"))); // NOI18N
        btn_GraficaBarras.setText("Cantidad Productos");
        btn_GraficaBarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GraficaBarrasActionPerformed(evt);
            }
        });

        jLabel41.setText("Seleccionar fecha fin:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(txt_IdVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel34))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel41)
                                    .addComponent(DateFin, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_GraficaBarras)
                                    .addComponent(btn_GraficaCircular))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_IdVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel34)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DateFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btn_GraficaCircular, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_GraficaBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", jPanel6);

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("RUC");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("NOMBRE");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("TELEFONO");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("DIRECCION");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("RAZON SOCIAL");

        txtRucConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucConfigKeyTyped(evt);
            }
        });

        btnActualizarConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-actualizar-40.png"))); // NOI18N
        btnActualizarConfig.setText("ACTUALIZAR");
        btnActualizarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfigActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel31.setText("DATOS DE LA EMPRESA");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDireccionConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(txtRucConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(69, 69, 69)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnActualizarConfig)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27))
                                .addGap(69, 69, 69)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(txtRazonConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(jLabel31)))
                .addContainerGap(147, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel31)
                .addGap(17, 17, 17)
                .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRucConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccionConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(btnActualizarConfig)
                .addGap(36, 36, 36))
        );

        jTabbedPane1.addTab("tab6", jPanel7);

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel35.setText("Registro de Usuarios");

        txt_NombreUsuario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_NombreUsuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txt_NombreUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        txt_MaiUsuariol.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_MaiUsuariol.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txt_TelefonoUsuario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_TelefonoUsuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txt_UsernameUsuario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_UsernameUsuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txt_PasswordUsuario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_PasswordUsuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        cmb_PermisosUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Contable", "Gestion", " " }));
        cmb_PermisosUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_PermisosUsuarioActionPerformed(evt);
            }
        });

        cmb_EstatusUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        cmb_EstatusUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_EstatusUsuarioActionPerformed(evt);
            }
        });

        btn_ActualizarUsuario.setForeground(new java.awt.Color(204, 204, 204));
        btn_ActualizarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-actualizar-40.png"))); // NOI18N
        btn_ActualizarUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_ActualizarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ActualizarUsuarioActionPerformed(evt);
            }
        });

        btn_EliminarUsuario.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btn_EliminarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-eliminar-40 (1).png"))); // NOI18N
        btn_EliminarUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_EliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarUsuarioActionPerformed(evt);
            }
        });

        jLabel_etiqueta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_etiqueta.setText("Nombre:");

        jLabel_etiqueta1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_etiqueta1.setText("Email:");

        jLabel_etiqueta2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_etiqueta2.setText("Telefono:");

        jLabel_etiqueta3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_etiqueta3.setText("Permisos de:");

        jLabel_etiqueta4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_etiqueta4.setText("Username:");

        jLabel_etiqueta5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_etiqueta5.setText("Estatus:");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setText("Password:");

        btn_NuevoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btn_NuevoUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_NuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoUsuarioActionPerformed(evt);
            }
        });

        TableUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "EMAIL", "TELEFONO", "USUARIO", "PASSWORD", "PERMISO", "ESTATUS"
            }
        ));
        TableUsuario.setFocusable(false);
        TableUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableUsuarioMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(TableUsuario);
        if (TableUsuario.getColumnModel().getColumnCount() > 0) {
            TableUsuario.getColumnModel().getColumn(0).setPreferredWidth(5);
            TableUsuario.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableUsuario.getColumnModel().getColumn(2).setPreferredWidth(50);
            TableUsuario.getColumnModel().getColumn(3).setPreferredWidth(20);
            TableUsuario.getColumnModel().getColumn(4).setPreferredWidth(40);
            TableUsuario.getColumnModel().getColumn(5).setPreferredWidth(30);
            TableUsuario.getColumnModel().getColumn(6).setPreferredWidth(30);
            TableUsuario.getColumnModel().getColumn(7).setPreferredWidth(20);
        }

        btn_LimpiarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-archivo-40.png"))); // NOI18N
        btn_LimpiarUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_LimpiarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LimpiarUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(254, 254, 254)
                .addComponent(jLabel35)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(btn_NuevoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btn_ActualizarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btn_LimpiarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(btn_EliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel_etiqueta1)
                                            .addComponent(jLabel_etiqueta2)
                                            .addComponent(jLabel_etiqueta4)
                                            .addComponent(jLabel36))
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txt_TelefonoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txt_MaiUsuariol, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txt_PasswordUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                                    .addComponent(txt_UsernameUsuario, javax.swing.GroupLayout.Alignment.TRAILING)))))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel_etiqueta)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_NombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_etiqueta3)
                                    .addComponent(cmb_PermisosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmb_EstatusUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_etiqueta5))
                                .addGap(38, 38, 38)))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(txt_idUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addComponent(txt_idUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_NombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_etiqueta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_MaiUsuariol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_etiqueta1))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_TelefonoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_etiqueta2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_UsernameUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_etiqueta4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_PasswordUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel_etiqueta3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmb_PermisosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel_etiqueta5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmb_EstatusUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 28, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_EliminarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(btn_LimpiarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_ActualizarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(btn_NuevoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        jTabbedPane1.addTab("tab7", jPanel8);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 830, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_GuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GuardarClienteActionPerformed
        if (!"".equals(txt_RucCliente.getText()) || !"".equals(txt_NombreCliente.getText()) || !"".equals(txt_RaSocilaCliente.getText()) || !"".equals(txt_TelefonoCliente.getText()) || !"".equals(txt_DireccionCliente.getText())) {

            cl.setRuc(Integer.parseInt(txt_RucCliente.getText()));
            cl.setNombre(txt_NombreCliente.getText());
            cl.setRazon(txt_RaSocilaCliente.getText());
            cl.setTelefono(Integer.parseInt(txt_TelefonoCliente.getText()));
            cl.setDireccion(txt_DireccionCliente.getText());
            client.RegistrarCliente(cl);

            LimpiarTabla();
            LimpiarCliente();
            ListarCliente();
            JOptionPane.showMessageDialog(null, "Cliente Registrado con exito.");
        } else {
            JOptionPane.showMessageDialog(null, "Debe completar todos los campos.");
        }
    }//GEN-LAST:event_btn_GuardarClienteActionPerformed

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        LimpiarTabla();
        LimpiarCliente();
        ListarCliente();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btnClienteActionPerformed

    private void TableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableClienteMouseClicked
        int fila = TableCliente.rowAtPoint(evt.getPoint());
        txt_idCliente.setText(TableCliente.getValueAt(fila, 0).toString());
        txt_RucCliente.setText(TableCliente.getValueAt(fila, 1).toString());
        txt_NombreCliente.setText(TableCliente.getValueAt(fila, 2).toString());
        txt_RaSocilaCliente.setText(TableCliente.getValueAt(fila, 3).toString());
        txt_TelefonoCliente.setText(TableCliente.getValueAt(fila, 4).toString());
        txt_DireccionCliente.setText(TableCliente.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_TableClienteMouseClicked

    private void btn_EliminarCLienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarCLienteActionPerformed
        if (!"".equals(txt_idCliente.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere eliminar?");
            if (pregunta == 0) {
                int id = Integer.parseInt(txt_idCliente.getText());
                client.EliminarCliente(id);
                LimpiarTabla();
                LimpiarCliente();
                ListarCliente();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Cliente");
        }
    }//GEN-LAST:event_btn_EliminarCLienteActionPerformed

    private void btn_EditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditarClienteActionPerformed
        if ("".equals(txt_idCliente.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un Cliente.");
        } else {
            if (!"".equals(txt_RucCliente.getText()) || !"".equals(txt_NombreCliente.getText()) || !"".equals(txt_RaSocilaCliente.getText()) || !"".equals(txt_TelefonoCliente.getText()) || !"".equals(txt_DireccionCliente.getText())) {
                cl.setRuc(Integer.parseInt(txt_RucCliente.getText()));
                cl.setNombre(txt_NombreCliente.getText());
                cl.setRazon(txt_RaSocilaCliente.getText());
                cl.setTelefono(Integer.parseInt(txt_TelefonoCliente.getText()));
                cl.setDireccion(txt_DireccionCliente.getText());
                cl.setId(Integer.parseInt(txt_idCliente.getText()));
                client.ModificarCliente(cl);
                LimpiarTabla();
                ListarCliente();
                LimpiarCliente();
                JOptionPane.showMessageDialog(null, "Cliente actualizado con exito.");
            }
        }

    }//GEN-LAST:event_btn_EditarClienteActionPerformed

    private void btn_NuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoClienteActionPerformed
        LimpiarCliente();
    }//GEN-LAST:event_btn_NuevoClienteActionPerformed

    private void btn_GuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GuardarProveedorActionPerformed
        if (!"".equals(txt_RucProveedor.getText()) || !"".equals(txt_NombreProveedor.getText()) || !"".equals(txt_TelefonoProveedor.getText()) || !"".equals(txt_DireccionProveedor.getText()) || !"".equals(txt_RaSocialProveedor.getText())) {

            pr.setRuc(Integer.parseInt(txt_RucProveedor.getText()));
            pr.setNombre(txt_NombreProveedor.getText());
            pr.setTelefono(Integer.parseInt(txt_TelefonoProveedor.getText()));
            pr.setDireccion(txt_DireccionProveedor.getText());
            pr.setRazon(txt_RaSocialProveedor.getText());
            PrDao.RegistrarProveedor(pr);
            LimpiarTabla();
            LimpiarProveedor();
            ListarProveedor();

            JOptionPane.showMessageDialog(null, "Proveedor Registrado con exito.");
        } else {
            JOptionPane.showMessageDialog(null, "Los compos estan vacios");
        }
    }//GEN-LAST:event_btn_GuardarProveedorActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        LimpiarTabla();
        LimpiarProveedor();
        ListarProveedor();
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void TableProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProveedorMouseClicked
        int fila = TableProveedor.rowAtPoint(evt.getPoint());
        txt_idProveedor.setText(TableProveedor.getValueAt(fila, 0).toString());
        txt_RucProveedor.setText(TableProveedor.getValueAt(fila, 1).toString());
        txt_NombreProveedor.setText(TableProveedor.getValueAt(fila, 2).toString());
        txt_TelefonoProveedor.setText(TableProveedor.getValueAt(fila, 3).toString());
        txt_DireccionProveedor.setText(TableProveedor.getValueAt(fila, 4).toString());
        txt_RaSocialProveedor.setText(TableProveedor.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_TableProveedorMouseClicked

    private void btn_EliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarProveedorActionPerformed
        if (!"".equals(txt_idProveedor.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere eliminar?");
            if (pregunta == 0) {
                int id = Integer.parseInt(txt_idProveedor.getText());
                PrDao.EliminarProveedor(id);
                LimpiarTabla();
                LimpiarProveedor();
                ListarProveedor();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Proveedor");
        }
    }//GEN-LAST:event_btn_EliminarProveedorActionPerformed

    private void btn_NuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoProveedorActionPerformed
        LimpiarProveedor();
    }//GEN-LAST:event_btn_NuevoProveedorActionPerformed

    private void btn_EditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditarProveedorActionPerformed
        if ("".equals(txt_idProveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un Proveedor.");
        } else {
            if (!"".equals(txt_RucProveedor.getText()) || !"".equals(txt_NombreProveedor.getText()) || !"".equals(txt_TelefonoProveedor.getText()) || !"".equals(txt_DireccionProveedor.getText()) || !"".equals(txt_RaSocialProveedor.getText())) {
                pr.setRuc(Integer.parseInt(txt_RucProveedor.getText()));
                pr.setNombre(txt_NombreProveedor.getText());
                pr.setTelefono(Integer.parseInt(txt_TelefonoProveedor.getText()));
                pr.setDireccion(txt_DireccionProveedor.getText());
                pr.setRazon(txt_RaSocialProveedor.getText());
                pr.setId(Integer.parseInt(txt_idProveedor.getText()));
                PrDao.ModificarProveedor(pr);
                LimpiarTabla();
                ListarProveedor();
                LimpiarProveedor();
                JOptionPane.showMessageDialog(null, "Proveedor actualizado con exito.");
            }
        }
    }//GEN-LAST:event_btn_EditarProveedorActionPerformed

    private void btn_GuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GuardarProductoActionPerformed
        if (!"".equals(txt_CodigoProducto.getText()) || !"".equals(txt_DescripcionProducto.getText()) || !"".equals(txt_CantidadProducto.getText()) || !"".equals(txt_PrecioProducto.getText()) || !"".equals(cbx_ProveedorProducto.getSelectedItem())) {

            pro.setCodigo(txt_CodigoProducto.getText());
            pro.setNombre(txt_DescripcionProducto.getText());
            pro.setStock(Integer.parseInt(txt_CantidadProducto.getText()));
            pro.setPrecio(Double.parseDouble(txt_PrecioProducto.getText()));
            pro.setProveedor(cbx_ProveedorProducto.getSelectedItem().toString());
            proDao.RegistrarProducto(pro);
            LimpiarTabla();
            LimpiarProducto();
            ListarProducto();

            JOptionPane.showMessageDialog(null, "El producto se registro con exito.");
        } else {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.");
        }
    }//GEN-LAST:event_btn_GuardarProductoActionPerformed

    private void btnProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductoActionPerformed
        LimpiarProducto();
        LimpiarTabla();
        ListarProducto();
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_btnProductoActionPerformed

    private void TableProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProductoMouseClicked
        int fila = TableProducto.rowAtPoint(evt.getPoint());
        txt_idProducto.setText(TableProducto.getValueAt(fila, 0).toString());
        txt_CodigoProducto.setText(TableProducto.getValueAt(fila, 1).toString());
        txt_DescripcionProducto.setText(TableProducto.getValueAt(fila, 2).toString());
        cbx_ProveedorProducto.setSelectedItem(TableProducto.getValueAt(fila, 3).toString());
        txt_CantidadProducto.setText(TableProducto.getValueAt(fila, 4).toString());
        txt_PrecioProducto.setText(TableProducto.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_TableProductoMouseClicked

    private void btn_NuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoProductoActionPerformed
        LimpiarProducto();
    }//GEN-LAST:event_btn_NuevoProductoActionPerformed

    private void btn_EliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarProductoActionPerformed
        if (!"".equals(txt_idProducto.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txt_idProducto.getText());
                proDao.EliminarProducto(id);
                LimpiarTabla();
                LimpiarProducto();
                ListarProducto();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Producto");
        }
    }//GEN-LAST:event_btn_EliminarProductoActionPerformed

    private void btn_EditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditarProductoActionPerformed
        if ("".equals(txt_idProducto.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un Producto.");
        } else {
            if (!"".equals(txt_CodigoProducto.getText()) || !"".equals(txt_DescripcionProducto.getText()) || !"".equals(txt_CantidadProducto.getText()) || !"".equals(txt_PrecioProducto.getText())) {
                pro.setCodigo(txt_CodigoProducto.getText());
                pro.setNombre(txt_DescripcionProducto.getText());
                pro.setProveedor(cbx_ProveedorProducto.getSelectedItem().toString());
                pro.setStock(Integer.parseInt(txt_CantidadProducto.getText()));
                pro.setPrecio(Double.parseDouble(txt_PrecioProducto.getText()));
                pro.setId(Integer.parseInt(txt_idProducto.getText()));
                proDao.ModificarProducto(pro);
                LimpiarTabla();
                ListarProducto();
                LimpiarProducto();
                JOptionPane.showMessageDialog(null, "Producto actualizado con exito.");
            }
        }
    }//GEN-LAST:event_btn_EditarProductoActionPerformed

    private void btn_ExcelProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ExcelProductoActionPerformed
        Excel.reportes();
    }//GEN-LAST:event_btn_ExcelProductoActionPerformed

    private void txt_CodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_CodigoVentaKeyPressed
        //Esta funcion trae los datos una ves ingresado el codigo y dar enter
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txt_CodigoVenta.getText())) {
                String cod = txt_CodigoVenta.getText();
                pro = proDao.BuscarPro(cod);
                if (pro.getNombre() != null) {
                    txt_DescripcionVenta.setText("" + pro.getNombre());
                    txt_PrecioVenta.setText("" + pro.getPrecio());
                    txt_StockDisponible.setText("" + pro.getStock());
                    txt_CantidadVenta.requestFocus();
                } else {
                    txt_CodigoVenta.setText("");
                    txt_CodigoVenta.requestFocus();
                    JOptionPane.showMessageDialog(null, "El codigo es incorrecto");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto");
                txt_CodigoVenta.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_CodigoVentaKeyPressed

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        jTabbedPane1.setSelectedIndex(4);
        LimpiarTabla();
        ListarVentas();
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_btnConfigActionPerformed

    private void txt_CantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_CantidadVentaKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txt_CantidadVenta.getText())) {
                String cod = txt_CodigoVenta.getText();
                String desc = txt_DescripcionVenta.getText();
                int cant = Integer.parseInt(txt_CantidadVenta.getText());
                double precio = Double.parseDouble(txt_PrecioVenta.getText());
                double total = cant * precio;
                int stock = Integer.parseInt(txt_StockDisponible.getText());

                if (stock >= cant) {
                    item = item + 1;
                    modelo = (DefaultTableModel) TableVenta.getModel();

                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(cod);
                    lista.add(desc);
                    lista.add(cant);
                    lista.add(precio);
                    lista.add(total);

                    Object[] obj = new Object[5];
                    obj[0] = lista.get(1);
                    obj[1] = lista.get(2);
                    obj[2] = lista.get(3);
                    obj[3] = lista.get(4);
                    obj[4] = lista.get(5);
                    modelo.addRow(obj);
                    TableVenta.setModel(modelo);
                    TotalPagar();
                    LimpiarVenta();
                    txt_CodigoVenta.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese cantidad");
            }
        }
    }//GEN-LAST:event_txt_CantidadVentaKeyPressed

    private void btn_EliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarVentaActionPerformed
        modelo = (DefaultTableModel) TableVenta.getModel();
        modelo.removeRow(TableVenta.getSelectedRow());
        TotalPagar();
        txt_CodigoVenta.requestFocus();
    }//GEN-LAST:event_btn_EliminarVentaActionPerformed

    private void txt_RucCliVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_RucCliVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txt_RucCliVenta.getText())) {
                int ruc = Integer.parseInt(txt_RucCliVenta.getText());
                cl = client.BuscarCliente(ruc);
                if (cl.getNombre() != null) {
                    txt_NombreCliVenta.setText("" + cl.getNombre());
                    txt_RaSocialCliVenta.setText("" + cl.getRazon());
                    txt_TelefonoCliVenta.setText("" + cl.getTelefono());
                    txt_DireccionCliVenta.setText("" + cl.getDireccion());
                } else {
                    txt_RucCliVenta.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese un cliente");
            }
        }
    }//GEN-LAST:event_txt_RucCliVentaKeyPressed

    private void btn_GenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GenerarVentaActionPerformed
        if (TableVenta.getRowCount() > 0) {
            if (!"".equals(txt_NombreCliVenta.getText())) {
                RegistrarVenta();
                RegistrarDetalle();
                ActualizarStock();
                FacturaPdf();
                LimpiarTabla();
                LimpiarVenta();
                LimpiarClienteVenta();
                JOptionPane.showMessageDialog(null, "Venta registrada.");
            } else {
                JOptionPane.showMessageDialog(null, "Debe ingresar un cliente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar un productos");
        }
    }//GEN-LAST:event_btn_GenerarVentaActionPerformed

    private void txt_CantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_CantidadVentaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txt_CantidadVentaKeyTyped

    private void txt_RucCliVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_RucCliVentaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txt_RucCliVentaKeyTyped

    private void txt_RucClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_RucClienteKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txt_RucClienteKeyTyped

    private void txt_RucProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_RucProveedorKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txt_RucProveedorKeyTyped

    private void txt_CantidadProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_CantidadProductoKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txt_CantidadProductoKeyTyped

    private void txtRucConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucConfigKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtRucConfigKeyTyped

    private void txt_PrecioProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PrecioProductoKeyTyped
        event.numberDecimalKeyPress(evt, txt_PrecioProducto);
    }//GEN-LAST:event_txt_PrecioProductoKeyTyped

    private void btnActualizarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfigActionPerformed

        if (!"".equals(txtRucConfig.getText()) || !"".equals(txtNombreConfig.getText()) || !"".equals(txtRazonConfig.getText()) || !"".equals(txtDireccionConfig.getText()) || !"".equals(txtTelefonoConfig.getText())) {
            conf.setRuc(Integer.parseInt(txtRucConfig.getText()));
            conf.setNombre(txtNombreConfig.getText());
            conf.setRazon(txtRazonConfig.getText());
            conf.setRazon(txtDireccionConfig.getText());
            conf.setTelefono(Integer.parseInt(txtTelefonoConfig.getText()));
            conf.setId_config(Integer.parseInt(txtIdConfig.getText()));
            proDao.ModificarDatos(conf);
            ListarConfig();
            JOptionPane.showMessageDialog(null, "Datos modificados con exito.");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnActualizarConfigActionPerformed

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked
        int fila = TableVentas.rowAtPoint(evt.getPoint());
        txt_IdVentas.setText(TableVentas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_TableVentasMouseClicked

    private void btn_ActualizarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ActualizarUsuarioActionPerformed
        if ("".equals(txt_idUsuario.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un Usuario.");
        } else {
            if (!"".equals(txt_NombreUsuario.getText()) || !"".equals(txt_MaiUsuariol.getText()) || !"".equals(txt_TelefonoUsuario.getText())
                    || !"".equals(txt_UsernameUsuario.getText()) || !"".equals(String.valueOf(txt_PasswordUsuario.getText()))) {

                user.setNombre(txt_NombreUsuario.getText());
                user.setCorreo(txt_MaiUsuariol.getText());
                user.setTelefono(txt_TelefonoUsuario.getText());
                user.setUsuario(txt_UsernameUsuario.getText());
                user.setPass(txt_PasswordUsuario.getText());
                user.setPermiso(cmb_PermisosUsuario.getSelectedItem().toString());
                user.setEstatus(cmb_EstatusUsuario.getSelectedItem().toString());
                user.setId(Integer.parseInt(txt_idUsuario.getText()));
                userDao.ActualizarUsuario(user);

                LimpiarTabla();
                LimpiarUsuario();
                ListarUsuario();
                JOptionPane.showMessageDialog(null, "Usuario actualizado con exito.");
            }
        }
    }//GEN-LAST:event_btn_ActualizarUsuarioActionPerformed

    private void btn_EliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarUsuarioActionPerformed
        if (!"".equals(txt_idUsuario.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Seguro que quiere eliminar al usuario?");

            if (pregunta == 0) {
                int id = Integer.parseInt(txt_idUsuario.getText());
                userDao.EliminarUsuario(id);

                LimpiarTabla();
                LimpiarUsuario();
                ListarUsuario();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un usuario");
        }

    }//GEN-LAST:event_btn_EliminarUsuarioActionPerformed

    private void cmb_EstatusUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_EstatusUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_EstatusUsuarioActionPerformed

    private void cmb_PermisosUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_PermisosUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_PermisosUsuarioActionPerformed

    private void btnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioActionPerformed
        jTabbedPane1.setSelectedIndex(6);
        LimpiarTabla();
        LimpiarUsuario();
        ListarUsuario();
    }//GEN-LAST:event_btnUsuarioActionPerformed

    private void btn_NuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoUsuarioActionPerformed
        if (!"".equals(txt_NombreUsuario.getText()) || !"".equals(txt_MaiUsuariol.getText()) || !"".equals(txt_TelefonoUsuario.getText())
                || !"".equals(txt_UsernameUsuario.getText()) || !"".equals(String.valueOf(txt_PasswordUsuario.getText()))) {

            user.setNombre(txt_NombreUsuario.getText());
            user.setCorreo(txt_MaiUsuariol.getText());
            user.setTelefono(txt_TelefonoUsuario.getText());
            user.setUsuario(txt_UsernameUsuario.getText());
            user.setPass(String.valueOf(txt_PasswordUsuario.getText()));
            user.setPermiso(cmb_PermisosUsuario.getSelectedItem().toString());
            user.setEstatus(cmb_EstatusUsuario.getSelectedItem().toString());
            userDao.RegistrarUsuario(user);

            LimpiarUsuario();
            LimpiarTabla();
            ListarUsuario();

            JOptionPane.showMessageDialog(null, "El usuario se registro con exito.");
        } else {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.");
        }
    }//GEN-LAST:event_btn_NuevoUsuarioActionPerformed

    private void btn_LimpiarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LimpiarUsuarioActionPerformed
        LimpiarUsuario();
    }//GEN-LAST:event_btn_LimpiarUsuarioActionPerformed

    private void TableUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableUsuarioMouseClicked
        int fila = TableUsuario.rowAtPoint(evt.getPoint());
        txt_idUsuario.setText(TableUsuario.getValueAt(fila, 0).toString());
        txt_NombreUsuario.setText(TableUsuario.getValueAt(fila, 1).toString());
        txt_MaiUsuariol.setText(TableUsuario.getValueAt(fila, 2).toString());
        txt_TelefonoUsuario.setText(TableUsuario.getValueAt(fila, 3).toString());
        txt_UsernameUsuario.setText(TableUsuario.getValueAt(fila, 4).toString());
        txt_PasswordUsuario.setText(TableUsuario.getValueAt(fila, 5).toString());
        cmb_PermisosUsuario.setSelectedItem(TableUsuario.getValueAt(fila, 6).toString());
        cmb_EstatusUsuario.setSelectedItem(TableUsuario.getValueAt(fila, 7).toString());
    }//GEN-LAST:event_TableUsuarioMouseClicked

    private void btn_GraficaCircularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GraficaCircularActionPerformed
        SimpleDateFormat darFormat = new SimpleDateFormat("yyyy-MM-dd");
        String FechaInicio = darFormat.format(DateInicio.getDate());

        SimpleDateFormat daFormat = new SimpleDateFormat("yyyy-MM-dd");
        String FechaFin = daFormat.format(DateFin.getDate());

        Grafico.Graficar(FechaInicio, FechaFin);
    }//GEN-LAST:event_btn_GraficaCircularActionPerformed

    private void btn_GraficaBarrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GraficaBarrasActionPerformed
        SimpleDateFormat darFormat = new SimpleDateFormat("yyyy-MM-dd");
        String FechaInicio = darFormat.format(DateInicio.getDate());

        SimpleDateFormat daFormat = new SimpleDateFormat("yyyy-MM-dd");
        String FechaFin = daFormat.format(DateFin.getDate());

        Grafico.Grafica_Barras(FechaInicio, FechaFin);
    }//GEN-LAST:event_btn_GraficaBarrasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateFin;
    private com.toedter.calendar.JDateChooser DateInicio;
    private javax.swing.JLabel Labe_Total;
    private javax.swing.JLabel Label_Vendedor;
    private javax.swing.JTable TableCliente;
    private javax.swing.JTable TableProducto;
    private javax.swing.JTable TableProveedor;
    private javax.swing.JTable TableUsuario;
    private javax.swing.JTable TableVenta;
    private javax.swing.JTable TableVentas;
    private javax.swing.JButton btnActualizarConfig;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnProducto;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JButton btnVentas;
    private javax.swing.JButton btn_ActualizarUsuario;
    private javax.swing.JButton btn_EditarCliente;
    private javax.swing.JButton btn_EditarProducto;
    private javax.swing.JButton btn_EditarProveedor;
    private javax.swing.JButton btn_EliminarCLiente;
    private javax.swing.JButton btn_EliminarProducto;
    private javax.swing.JButton btn_EliminarProveedor;
    private javax.swing.JButton btn_EliminarUsuario;
    private javax.swing.JButton btn_EliminarVenta;
    private javax.swing.JButton btn_ExcelProducto;
    private javax.swing.JButton btn_GenerarVenta;
    private javax.swing.JButton btn_GraficaBarras;
    private javax.swing.JButton btn_GraficaCircular;
    private javax.swing.JButton btn_GuardarCliente;
    private javax.swing.JButton btn_GuardarProducto;
    private javax.swing.JButton btn_GuardarProveedor;
    private javax.swing.JToggleButton btn_LimpiarUsuario;
    private javax.swing.JButton btn_NuevoCliente;
    private javax.swing.JButton btn_NuevoProducto;
    private javax.swing.JButton btn_NuevoProveedor;
    private javax.swing.JButton btn_NuevoUsuario;
    private javax.swing.JComboBox<String> cbx_ProveedorProducto;
    private javax.swing.JComboBox<String> cmb_EstatusUsuario;
    private javax.swing.JComboBox<String> cmb_PermisosUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Logo;
    private javax.swing.JLabel jLabel_etiqueta;
    private javax.swing.JLabel jLabel_etiqueta1;
    private javax.swing.JLabel jLabel_etiqueta2;
    private javax.swing.JLabel jLabel_etiqueta3;
    private javax.swing.JLabel jLabel_etiqueta4;
    private javax.swing.JLabel jLabel_etiqueta5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtDireccionConfig;
    private javax.swing.JTextField txtIdConfig;
    private javax.swing.JTextField txtNombreConfig;
    private javax.swing.JTextField txtRazonConfig;
    private javax.swing.JTextField txtRucConfig;
    private javax.swing.JTextField txtTelefonoConfig;
    private javax.swing.JTextField txt_CantidadProducto;
    private javax.swing.JTextField txt_CantidadVenta;
    private javax.swing.JTextField txt_CodigoProducto;
    private javax.swing.JTextField txt_CodigoVenta;
    private javax.swing.JTextField txt_DescripcionProducto;
    private javax.swing.JTextField txt_DescripcionVenta;
    private javax.swing.JTextField txt_DireccionCliVenta;
    private javax.swing.JTextField txt_DireccionCliente;
    private javax.swing.JTextField txt_DireccionProveedor;
    private javax.swing.JTextField txt_IdVentas;
    private javax.swing.JTextField txt_MaiUsuariol;
    private javax.swing.JTextField txt_NombreCliVenta;
    private javax.swing.JTextField txt_NombreCliente;
    private javax.swing.JTextField txt_NombreProveedor;
    private javax.swing.JTextField txt_NombreUsuario;
    private javax.swing.JTextField txt_PasswordUsuario;
    private javax.swing.JTextField txt_PrecioProducto;
    private javax.swing.JTextField txt_PrecioVenta;
    private javax.swing.JTextField txt_RaSocialCliVenta;
    private javax.swing.JTextField txt_RaSocialProveedor;
    private javax.swing.JTextField txt_RaSocilaCliente;
    private javax.swing.JTextField txt_RucCliVenta;
    private javax.swing.JTextField txt_RucCliente;
    private javax.swing.JTextField txt_RucProveedor;
    private javax.swing.JTextField txt_StockDisponible;
    private javax.swing.JTextField txt_TelefonoCliVenta;
    private javax.swing.JTextField txt_TelefonoCliente;
    private javax.swing.JTextField txt_TelefonoProveedor;
    private javax.swing.JTextField txt_TelefonoUsuario;
    private javax.swing.JTextField txt_UsernameUsuario;
    private javax.swing.JTextField txt_idCliente;
    private javax.swing.JTextField txt_idProducto;
    private javax.swing.JTextField txt_idProveedor;
    private javax.swing.JTextField txt_idUsuario;
    // End of variables declaration//GEN-END:variables

    public void LimpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    private void LimpiarCliente() {
        txt_idCliente.setText("");
        txt_RucCliente.setText("");
        txt_NombreCliente.setText("");
        txt_RaSocilaCliente.setText("");
        txt_TelefonoCliente.setText("");
        txt_DireccionCliente.setText("");
    }

    private void LimpiarProveedor() {
        txt_idProveedor.setText("");
        txt_RucProveedor.setText("");
        txt_NombreProveedor.setText("");
        txt_RaSocialProveedor.setText("");
        txt_TelefonoProveedor.setText("");
        txt_DireccionProveedor.setText("");
    }

    private void LimpiarProducto() {
        txt_idProducto.setText("");
        txt_CodigoProducto.setText("");
        txt_DescripcionProducto.setText("");
        txt_CantidadProducto.setText("");
        txt_PrecioProducto.setText("");
        cbx_ProveedorProducto.setSelectedIndex(0);
    }

    private void LimpiarVenta() {
        txt_CodigoVenta.setText("");
        txt_DescripcionVenta.setText("");
        txt_CantidadVenta.setText("");
        txt_PrecioVenta.setText("");
        txt_StockDisponible.setText("");
    }

    private void LimpiarClienteVenta() {
        txt_RucCliVenta.setText("");
        txt_NombreCliVenta.setText("");
        txt_RaSocialCliVenta.setText("");
        txt_TelefonoCliVenta.setText("");
        txt_DireccionCliVenta.setText("");
        Labe_Total.setText("");
    }

    private void LimpiarUsuario() {
        txt_idUsuario.setText("");
        txt_NombreUsuario.setText("");
        txt_MaiUsuariol.setText("");
        txt_TelefonoUsuario.setText("");
        txt_UsernameUsuario.setText("");
        txt_PasswordUsuario.setText("");
        cmb_PermisosUsuario.setSelectedIndex(0);
        cmb_EstatusUsuario.setSelectedIndex(0);
    }

    private void TotalPagar() {
        Totalpagar = 0.00;
        int numFila = TableVenta.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double calcular = Double.parseDouble(String.valueOf(TableVenta.getModel().getValueAt(i, 4)));
            Totalpagar = Totalpagar + calcular;

        }
        Labe_Total.setText(String.format("%.2f", Totalpagar));
    }

    private void RegistrarVenta() {
        String cliente = txt_NombreCliVenta.getText();
        String vendedor = Label_Vendedor.getText();
        double total = Totalpagar;

        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setTotal(total);
        //v.setFecha(FechaActual);
        Vdao.RegistrarVenta(v);
    }

    private void RegistrarDetalle() {
        int id_venta = Vdao.IdVenta();
        for (int i = 0; i < TableVenta.getRowCount(); i++) {

            String codigo = TableVenta.getValueAt(i, 0).toString();
            int cantidad = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(TableVenta.getValueAt(i, 3).toString());

            DetVenta.setCodigo_producto(codigo);
            DetVenta.setCantidad(cantidad);
            DetVenta.setPrecio(precio);
            DetVenta.setId_venta(id_venta);
            Vdao.RegistrarDetalle(DetVenta);
        }
    }

    private void ActualizarStock() {
        for (int i = 0; i < TableVenta.getRowCount(); i++) {

            String codigo = TableVenta.getValueAt(i, 0).toString();
            int cantidad = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());

            pro = proDao.BuscarPro(codigo);
            int StockActual = pro.getStock() - cantidad;
            Vdao.ActualizarStock(StockActual, codigo);

        }
    }

    private void FacturaPdf() {
        try {
            int id = Vdao.IdVenta();
            String ruta = System.getProperty("user.home");
            FileOutputStream archivo;
            File file = new File(ruta + "/Desktop/" + id + ".pdf");
            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("src/img/logo_icon.jpg");  //para agregar la imagen
            img.scaleToFit(1000, 1000);

            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Comprobante: " + id + "\nFecha: " + new SimpleDateFormat("dd-mm-yyyy").format(date) + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);//cantidad de columnas
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            Encabezado.addCell(img); //para agregar la imagen

            String ruc = txtRucConfig.getText();
            String nom = txtNombreConfig.getText();
            String ra = txtRazonConfig.getText();
            String tel = txtTelefonoConfig.getText();
            String dir = txtDireccionConfig.getText();

            Encabezado.addCell("");
            Encabezado.addCell("Ruc: " + ruc + "\nNombre: " + nom + "\nRazon Social: " + ra + "\nTelefono: " + tel + "\nDireccion: " + dir);
            Encabezado.addCell(fecha);
            doc.add(Encabezado);

            //Datos del cliente
            Paragraph cliente = new Paragraph();
            cliente.add(Chunk.NEWLINE);
            cliente.add("Datos del Cliente" + "\n\n");
            doc.add(cliente);

            PdfPTable tablacliente = new PdfPTable(4);//cantidad de columnas
            tablacliente.setWidthPercentage(100);
            tablacliente.getDefaultCell().setBorder(0);
            float[] ColumnaCliente = new float[]{30f, 60f, 30f, 50f};
            tablacliente.setWidths(ColumnaCliente);
            tablacliente.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell cliente1 = new PdfPCell(new Phrase("Ruc", negrita));
            PdfPCell cliente2 = new PdfPCell(new Phrase("Razon Social", negrita));
            PdfPCell cliente3 = new PdfPCell(new Phrase("Telefono", negrita));
            PdfPCell cliente4 = new PdfPCell(new Phrase("Direccion", negrita));

            cliente1.setBorder(0);
            cliente2.setBorder(0);
            cliente3.setBorder(0);
            cliente4.setBorder(0);
            tablacliente.addCell(cliente1);
            tablacliente.addCell(cliente2);
            tablacliente.addCell(cliente3);
            tablacliente.addCell(cliente4);

            tablacliente.addCell(txt_RucCliVenta.getText());
            tablacliente.addCell(txt_RaSocialCliVenta.getText());
            tablacliente.addCell(txt_TelefonoCliVenta.getText());
            tablacliente.addCell(txt_DireccionCliVenta.getText());

            doc.add(tablacliente);

            // detalle productos
            PdfPTable tablaproductos = new PdfPTable(5);//cantidad de columnas
            tablaproductos.setWidthPercentage(100);
            tablaproductos.getDefaultCell().setBorder(0);
            float[] ColumnaProductos = new float[]{30f, 60f, 30f, 30f, 40f};
            tablaproductos.setWidths(ColumnaProductos);
            tablaproductos.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell productos1 = new PdfPCell(new Phrase("\n\n" + "Codigo", negrita));
            PdfPCell productos2 = new PdfPCell(new Phrase("\n\n" + "Descripcion", negrita));
            PdfPCell productos3 = new PdfPCell(new Phrase("\n\n" + "Cantidad", negrita));
            PdfPCell productos4 = new PdfPCell(new Phrase("\n\n" + "Precio", negrita));
            PdfPCell productos5 = new PdfPCell(new Phrase("\n\n" + "Total", negrita));

            productos1.setBorder(0);
            productos2.setBorder(0);
            productos3.setBorder(0);
            productos4.setBorder(0);
            productos5.setBorder(0);

            productos1.setBackgroundColor(BaseColor.DARK_GRAY);
            productos2.setBackgroundColor(BaseColor.DARK_GRAY);
            productos3.setBackgroundColor(BaseColor.DARK_GRAY);
            productos4.setBackgroundColor(BaseColor.DARK_GRAY);
            productos5.setBackgroundColor(BaseColor.DARK_GRAY);

            tablaproductos.addCell(productos1);
            tablaproductos.addCell(productos2);
            tablaproductos.addCell(productos3);
            tablaproductos.addCell(productos4);
            tablaproductos.addCell(productos5);

            for (int i = 0; i < TableVenta.getRowCount(); i++) {
                String codigo = TableVenta.getValueAt(i, 0).toString();
                String descripcion = TableVenta.getValueAt(i, 1).toString();
                String cantidad = TableVenta.getValueAt(i, 2).toString();
                String precio = TableVenta.getValueAt(i, 3).toString();
                String total = TableVenta.getValueAt(i, 4).toString();

                tablaproductos.addCell(codigo);
                tablaproductos.addCell(descripcion);
                tablaproductos.addCell(cantidad);
                tablaproductos.addCell(precio);
                tablaproductos.addCell(total);
            }

            doc.add(tablaproductos);

            // cierre de la factura
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a pagar:" + Totalpagar);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Firma de recepcion: \n\n");
            firma.add("________________________ \n\n");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("Gracias por su preferencia");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }
}
