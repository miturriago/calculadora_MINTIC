//Liberías

//java.awt, Contiene todas las clases para crear interfaces de usuario y pintar gráficos e imágenes.
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//java.text, Proporciona clases e interfaces para manejar texto, fechas, números y mensajes de una manera independiente de los lenguajes naturales.
import java.text.NumberFormat;
import java.text.ParseException;
//javax.swing proporciona clases para Java swing API como JButton, JTextField, JTextArea, JRadioButton, JCheckbox, JMenu, JColorChooser, etc.
import javax.swing.*;

public class App extends JFrame {
    // label donde se pintará el resultado.
    private JLabel lblResultado = new JLabel(" ");
    // panel para los botenes principales el cual tendrá 4 filas y 4 columnas
    private JPanel pnlBotones = new JPanel(new GridLayout(4, 4));
    // panel para el botón igual, que tendrá una fila y una columna
    private JPanel pnlIgual = new JPanel(new GridLayout(1, 1));
    // array de botones
    private JButton[] botones = { new JButton("1"), new JButton("2"), new JButton("3"), new JButton("+"),
            new JButton("4"), new JButton("5"), new JButton("6"), new JButton("-"), new JButton("7"), new JButton("8"),
            new JButton("9"), new JButton("*"), new JButton("C"), new JButton("0"), new JButton(","), new JButton("/"),
            new JButton("=") };

    // Tamaño de la ventana
    private Dimension dmVentana = new Dimension(300, 440);

    // **********Variables
    private double resultado = 0;
    private double numero;
    // Asignamos valor a nuestras operaciones
    private static final int NINGUNO = 0;
    private static final int SUMA = 1;
    private static final int RESTA = 2;
    private static final int MULTIPLICACION = 3;
    private static final int DIVISION = 4;
    // Inicializamos el operador como NINGUNO (0)
    private int operador = App.NINGUNO;
    // Punto decimal
    private boolean hayPunto = false;
    // Nuevo número
    private boolean nuevoNumero = true;
    private NumberFormat nf = NumberFormat.getInstance();

    public App() {
        // Dimensión de nuestra pantalla
        Dimension dmPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        // Ubicamos nuestra calculadora en todo el centro de la pantalla
        int x = (dmPantalla.width - dmVentana.width) / 2;
        int y = (dmPantalla.height - dmVentana.height) / 2;
        this.setLocation(x, y);
        this.setSize(dmVentana);
        this.setTitle("Calculadora");

        lblResultado.setBackground(Color.white);
        lblResultado.setOpaque(true);
        lblResultado.setFont(new Font("Arial", Font.PLAIN, 32));

        // Declaramos nuestra función para detectar el clic en los botones.
        PulsaRaton pr = new PulsaRaton();

        // Ubicamos nuestros botones del array (Menos el botón = que está en la última
        // posición)
        for (int i = 0; i < botones.length - 1; i++) {
            pnlBotones.add(botones[i]);
            botones[i].addActionListener(pr);
        }

        // Ubicamos el botón igual
        pnlIgual.add(botones[botones.length - 1]);
        botones[botones.length - 1].addActionListener(pr);

        pnlIgual.setPreferredSize(new Dimension(0, 50));
        this.add(lblResultado, BorderLayout.NORTH);
        this.add(pnlBotones);
        this.add(pnlIgual, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        lblResultado.setText("0,0");
        lblResultado.setHorizontalAlignment(JLabel.RIGHT);

    }

    /**
     * @param args the command line arguments
     */

    // Main
    public static void main(String[] args) {
        new App();
    }

    class PulsaRaton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton origen = (JButton) e.getSource();
            String texto = origen.getText();
            // switch para saber que operación o acción realizar
            switch (texto) {
                case "+":
                    operar(App.SUMA);
                    break;
                case "-":
                    operar(App.RESTA);
                    break;
                case "*":
                    operar(App.MULTIPLICACION);
                    break;
                case "/":
                    operar(App.DIVISION);
                    break;
                case ",":
                    if (!nuevoNumero) {
                        if (!hayPunto) {
                            String rdo = lblResultado.getText();
                            lblResultado.setText(rdo + ",");
                        }
                    } else {
                        lblResultado.setText("0,");
                        nuevoNumero = false;
                    }
                    hayPunto = true;
                    break;
                case "C":
                    lblResultado.setText("0,0");
                    nuevoNumero = true;
                    hayPunto = false;
                    break;
                case "=":
                    if (operador != App.NINGUNO) {
                        String rdo = lblResultado.getText();
                        if (!rdo.isEmpty()) {
                            Number n = null;
                            try {
                                n = (Number) nf.parse(rdo);
                                numero = n.doubleValue();
                            } catch (ParseException ex) {
                                numero = 0;
                            }
                            switch (operador) {
                                case App.SUMA:
                                    resultado += numero;
                                    break;
                                case App.RESTA:
                                    resultado -= numero;
                                    break;
                                case App.MULTIPLICACION:
                                    resultado *= numero;
                                    break;
                                case App.DIVISION:
                                    resultado /= numero;
                                    break;
                                default:
                                    resultado = numero;
                                    break;
                            }
                            operador = App.NINGUNO;
                            lblResultado.setText(nf.format(resultado));
                        }
                    }
                    break;
                default:
                    String rdo = lblResultado.getText();
                    if (nuevoNumero) {
                        lblResultado.setText(texto);
                    } else {
                        lblResultado.setText(rdo + texto);
                    }
                    nuevoNumero = false;
                    break;
            }
        }
    }

    // Metordo para saber que operaciones realizar
    public void operar(int operacion) {
        if (!nuevoNumero) {
            String rdo = lblResultado.getText();
            if (!rdo.isEmpty()) {
                Number n = null;
                try {
                    n = (Number) nf.parse(rdo);
                    numero = n.doubleValue();
                } catch (ParseException ex) {
                    
                }
                switch (operador) {
                    case App.SUMA:
                        resultado += numero;
                        break;
                    case App.RESTA:
                        resultado -= numero;
                        break;
                    case App.MULTIPLICACION:
                        resultado *= numero;
                        break;
                    case App.DIVISION:
                        resultado /= numero;
                        break;
                    default:
                        resultado = numero;
                }
                operador = operacion;
                lblResultado.setText(nf.format(resultado));
                nuevoNumero = true;
                hayPunto = false;
            }
        }
    }

}