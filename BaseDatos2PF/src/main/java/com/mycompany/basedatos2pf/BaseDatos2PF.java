package com.mycompany.basedatos2pf;

import modelo.Usuario;
import vista.FrmLogin;
import dao.SistemaDAOImpl;
import controlador.LoginControlador;

public class BaseDatos2PF {

    public static void main(String[] args) {
        // 1. Instanciamos el Modelo, la Vista y el DAO
        Usuario modelo = new Usuario();
        FrmLogin vista = new FrmLogin();
        SistemaDAOImpl dao = new SistemaDAOImpl();
        
        // 2. Instanciamos el Controlador y le pasamos las 3 piezas
        LoginControlador controlador = new LoginControlador(modelo, vista, dao);
        
        // 3. Hacemos visible la ventana
        vista.setVisible(true);
    }
}