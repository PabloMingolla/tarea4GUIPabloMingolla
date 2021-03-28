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
    private double op1;
    private String op2 = "";

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
                    //si no se ha introducido ningún número, ignora el intento de introducir operaciones
                    if(areaTexto.getText().equals("")){
                        break;
                    }
                    //si no tiene un operador registrado, lo cambia al equivalente a la operacion y llama a op1
                    if (tipoOperacion == -1) {
                        tipoOperacion = 0;
                        setop1(o);
                    } else {
                        //si ya lo tiene, llama a op2, y luego cambia el tipo de operacion y escribe el simbolo
                        //siempre que se encuentre con una division entre 0, se reinicia la calculadora
                        if (entreCero()) {
                            break;
                        }
                        setop2();
                        tipoOperacion = 0;
                        areaTexto.setText(areaTexto.getText() + ((JButton) o).getText());
                    }
                    break;
                case "-":
                    if(areaTexto.getText().equals("")){
                        break;
                    }
                    if (tipoOperacion == -1) {
                        tipoOperacion = 1;
                        setop1(o);
                    } else {
                        if (entreCero()) {
                            break;
                        }
                        setop2();
                        tipoOperacion = 1;
                        areaTexto.setText(areaTexto.getText() + ((JButton) o).getText());
                    }
                    break;
                case "*":
                    if(areaTexto.getText().equals("")){
                        break;
                    }
                    if (tipoOperacion == -1) {
                        tipoOperacion = 2;
                        setop1(o);
                    } else {
                        if (entreCero()) {
                            break;
                        }
                        setop2();
                        tipoOperacion = 2;
                        areaTexto.setText(areaTexto.getText() + ((JButton) o).getText());
                    }
                    break;
                case "/":
                    if(areaTexto.getText().equals("")){
                        break;
                    }
                    if (tipoOperacion == -1) {
                        tipoOperacion = 3;
                        setop1(o);
                    } else {
                        if (entreCero()) {
                            break;
                        }
                        setop2();
                        tipoOperacion = 3;
                        areaTexto.setText(areaTexto.getText() + ((JButton) o).getText());
                    }
                    break;
                case "C":
                    reset();
                    break;
                case "=":
                    if (entreCero()) {
                        break;
                    }
                    //se comprueba que no haya datos erroneos a la hora de calcular
                    if (tipoOperacion != -1 && areaTexto.getText() != null && !areaTexto.getText().equals("")) {
                        setop2();
                        tipoOperacion = -1;
                    }
                    break;
                default:
                    //si se introduce un número, se añade al areaTexto
                    areaTexto.setText(areaTexto.getText() + ((JButton) o).getText());
                    //y si ya se esta trabajando con el segundo número, es decir, ya se ha introducido un operador, se guarda el número en el String op2
                    if (tipoOperacion != -1) {
                        op2 += ((JButton) o).getText();
                    }
                    break;
            }

        }
    }

    //realiza la operacion que toque, cambiando areaTexto al resultado
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

    private void reset() {
        tipoOperacion = -1;
        op1 = 0;
        op2 = "";
        areaTexto.setText("");
    }

    private void setop1(Object o) {
        //guarda en la variable op1 el valor de los numeros introducidos hasta ahora
        op1 = (Double.parseDouble(areaTexto.getText()));
        areaTexto.setText(areaTexto.getText() + ((JButton) o).getText());
    }

    private void setop2() {
        //llama a operacion y sustituye areaTexto por el ressultado
        operacion(op1, (Double.parseDouble(op2)));
        op1 = (Double.parseDouble(areaTexto.getText()));
        //resetea op2
        op2 = "";
    }

    private boolean entreCero() {
        if (tipoOperacion == 3 && "0".equals(op2)) {
            reset();
            return true;
        } else {
            return false;
        }
    }
}
