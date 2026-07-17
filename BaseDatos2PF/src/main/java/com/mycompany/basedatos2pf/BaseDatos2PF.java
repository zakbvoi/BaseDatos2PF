package com.mycompany.basedatos2pf;

import vista.FrmLogin;
import controlador.LoginControlador;

public class BaseDatos2PF {
    public static void main(String[] args) {
        // Instanciamos la vista de inicio
        FrmLogin vistaLogin = new FrmLogin();
        // Le pasamos el mando al controlador
        new LoginControlador(vistaLogin);
        // Mostramos la ventana
        vistaLogin.setVisible(true);
    }
}