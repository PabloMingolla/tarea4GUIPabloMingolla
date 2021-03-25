/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.String.valueOf;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author pablo
 */
public class PanelPrincipal extends JPanel implements ActionListener {

    // Atributos de la clase (privados)
    private PanelBotones botonera;
    private JTextArea areaTexto;
    private int tipoOperacion;
    private double op1 = 0;
    private String op2;

    // Constructor
    public PanelPrincipal() {
        initComponents();
        tipoOperacion = -1; // No hay operaciones en la calculadora
    }

    // Se inicializan los componentes gráficos y se colocan en el panel
    private void initComponents() {
        // Creamos el panel de botones
        botonera = new PanelBotones();
        // Creamos el área de texto
        areaTexto = new JTextArea(10, 50);
        areaTexto.setEditable(false);
        areaTexto.setBackground(Color.white);

        //Establecemos layout del panel principal
        this.setLayout(new BorderLayout());
        // Colocamos la botonera y el área texto
        this.add(areaTexto, BorderLayout.NORTH);
        this.add(botonera, BorderLayout.SOUTH);

        for (JButton boton : this.botonera.getgrupoBotones()) {
            boton.addActionListener(this);
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // Se obtiene el objeto que desencadena el evento
        Object o = ae.getSource();
        // Si es un botón
        if (o instanceof JButton) {
            switch (((JButton) o).getText()) {
                case "+":
                    //si no tiene un operador registrado, lo cambia al equivalente a la operacion
                    if (tipoOperacion == -1) {
                        tipoOperacion = 0;
                        //guarda en la variable op1 el valor de los numeros introducidos hasta ahora
                        op1 = (Double.parseDouble(areaTexto.getText()));
                        areaTexto.setText("");
                    } else {
                        operacion(op1, Double.parseDouble(areaTexto.getText()));
                        op1 = (Double.parseDouble(areaTexto.getText()));
                        tipoOperacion = 0;
                    }
                    break;
                case "-":
                    if (tipoOperacion == -1) {
                        tipoOperacion = 1;
                        op1 = (Double.parseDouble(areaTexto.getText()));
                        areaTexto.setText("");
                    } else {
                        operacion(op1, (Double.parseDouble(areaTexto.getText())));
                        op1 = (Double.parseDouble(areaTexto.getText()));
                        tipoOperacion = 1;
                    }
                    break;
                case "*":
                    if (tipoOperacion == -1) {
                        tipoOperacion = 2;
                        op1 = (Double.parseDouble(areaTexto.getText()));
                        areaTexto.setText("");
                    } else {
                        operacion(op1, Double.parseDouble(areaTexto.getText()));
                        op1 = (Double.parseDouble(areaTexto.getText()));
                        tipoOperacion = 2;
                    }
                    break;
                case "/":
                    if (tipoOperacion == -1) {
                        tipoOperacion = 3;
                        op1 = (Double.parseDouble(areaTexto.getText()));
                        areaTexto.setText("");
                    } else {
                        operacion(op1, (Integer.parseInt(areaTexto.getText())));
                        op1 = (Double.parseDouble(areaTexto.getText()));
                        tipoOperacion = 3;
                    }
                    break;
                case "C":
                    tipoOperacion = -1;
                    op1 = 0;
                    areaTexto.setText("");
                    break;
                case "=":
                    if (tipoOperacion == 3 && "0".equals(areaTexto.getText())) {
                        tipoOperacion = -1;
                        op1 = 0;
                        areaTexto.setText("");
                        break;
                    }
                    if (tipoOperacion != -1 && areaTexto.getText() != null && !"".equals(areaTexto.getText())) {
                        operacion(op1, (Double.parseDouble(areaTexto.getText())));
                        op1 = (Double.parseDouble(areaTexto.getText()));
                        tipoOperacion = -1;
                    }
                    break;
                default:
                    areaTexto.setText(areaTexto.getText() + ((JButton) o).getText());
                    break;
            }

        }
    }

    private void operacion(double a, double b) {
        switch (tipoOperacion) {
            case 0:
                areaTexto.setText(valueOf(a + b));
                break;
            case 1:
                areaTexto.setText(valueOf(a - b));
                break;
            case 2:
                areaTexto.setText(valueOf(a * b));
                break;
            case 3:
                areaTexto.setText(valueOf(a / b));
                break;
        }
    }
}
