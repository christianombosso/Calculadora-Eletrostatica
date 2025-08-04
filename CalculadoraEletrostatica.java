import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class CalculadoraEletrostatica extends JFrame implements ActionListener {

    //Criando as variáveis:
    
    JTextField q1Input, q2Input, q3Input, distanciaInput;
    JComboBox<String> tipoCalculo;
    JButton calcularBtn;

    JMenuItem abrirArquivoItem, ajudaItem;

    // Constantes pro cálculo:
    
    private final double K = 8.99e9; // Constante de Coulomb
    private final double Q_MIN = -1000000;
    private final double Q_MAX = 1000000;
    private final double DIST_MIN = 0.001;
    private final double DIST_MAX = 1000; 

    // Construtor:
    
    public CalculadoraEletrostatica() {
        
        // Construindo a janelinha da calculadora:
        
        setTitle("Calculadora de Eletrostática");
        setSize(400, 350);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        addLabel("Q1 (μC):", 30, 30);
        q1Input = addInput(130, 30);
        addLabel("Q2 (μC):", 30, 70);
        q2Input = addInput(130, 70);
        addLabel("Q3 (μC):", 30, 110);
        q3Input = addInput(130, 110);
        addLabel("Distância (m):", 30, 150);
        distanciaInput = addInput(130, 150);
        
        // Opções de cálculo disponíveis na calculadora:

        String[] opcoes = {
            "Força entre Q1 e Q2",
            "Força resultante (triângulo)",
            "Força resultante (linha reta)"
        };
        tipoCalculo = new JComboBox<>(opcoes);
        tipoCalculo.setBounds(30, 190, 320, 25);
        tipoCalculo.addActionListener(this);
        add(tipoCalculo);

        calcularBtn = new JButton("Calcular");
        calcularBtn.setBounds(130, 230, 120, 30);
        calcularBtn.addActionListener(this);
        add(calcularBtn);

        q3Input.setEnabled(false);

        // Criando o menu de abrir arquivos e de ajuda:
        
        JMenuBar menuBar = new JMenuBar();

        JMenu arquivoMenu = new JMenu("Arquivo");
        abrirArquivoItem = new JMenuItem("Abrir");
        abrirArquivoItem.addActionListener(this);
        arquivoMenu.add(abrirArquivoItem);

        JMenu ajudaMenu = new JMenu("Ajuda");
        ajudaItem = new JMenuItem("Sobre");
        ajudaItem.addActionListener(this);
        ajudaMenu.add(ajudaItem);

        menuBar.add(arquivoMenu);
        menuBar.add(ajudaMenu);

        setJMenuBar(menuBar);

        setVisible(true);
    }

    private void addLabel(String texto, int x, int y) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, 100, 25);
        add(label);
    }

    private JTextField addInput(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 150, 25);
        add(field);
        return field;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tipoCalculo) {
            q3Input.setEnabled(tipoCalculo.getSelectedIndex() != 0);
        } else if (e.getSource() == calcularBtn) {
            calcular();
        } else if (e.getSource() == abrirArquivoItem) {
            abrirArquivo();
        } else if (e.getSource() == ajudaItem) {
            mostrarAjuda();
        }
    }
    
    // Função que faz os cálculos:
    
    private void calcular() {
        try {
            double q1 = Double.parseDouble(q1Input.getText());
            double q2 = Double.parseDouble(q2Input.getText());
            double d = Double.parseDouble(distanciaInput.getText());

            if (!validaCarga(q1)) {
                showError(String.format("Q1 deve estar entre %.0f e %.0f μC e diferente de zero.", Q_MIN, Q_MAX));
                return;
            }
            if (!validaCarga(q2)) {
                showError(String.format("Q2 deve estar entre %.0f e %.0f μC e diferente de zero.", Q_MIN, Q_MAX));
                return;
            }
            if (d < DIST_MIN || d > DIST_MAX) {
                showError(String.format("A distância deve ser maior que %.3f m e menor que %.0f m.", DIST_MIN, DIST_MAX));
                return;
            }

            if (tipoCalculo.getSelectedIndex() == 0) {
                
                if (q1 == 0 || q2 == 0) {
                    showError("Q1 e Q2 devem ser diferentes de zero.");
                    return;
                }
            } else {
                if (!q3Input.isEnabled()) {
                    showError("Q3 está desabilitado, selecione uma opção que permita seu uso.");
                    return;
                }
                double q3 = Double.parseDouble(q3Input.getText());
                if (!validaCarga(q3)) {
                    showError(String.format("Q3 deve estar entre %.0f e %.0f μC e diferente de zero.", Q_MIN, Q_MAX));
                    return;
                }
                
                // Conversão de μC para C:
                
                q1 *= 1e-6;
                q2 *= 1e-6;
                q3 *= 1e-6;
                
                if (tipoCalculo.getSelectedIndex() == 1) { // Cálculo do triângulo
                    double f12 = calcularForca(q1, q2, d);
                    double f13 = calcularForca(q1, q3, d);
                    double resultado = Math.sqrt(
                        f12 * f12 + f13 * f13 + 2 * f12 * f13 * Math.cos(Math.toRadians(60))
                    );
                    JOptionPane.showMessageDialog(this, String.format("Resultado: %.4f N", resultado));
                    return;
                } else if (tipoCalculo.getSelectedIndex() == 2) { // Cálculo da linha reta
                    double f12 = calcularForca(q1, q2, d);
                    double f13 = calcularForca(q1, q3, d);
                    double f12dir = (q1 * q2 > 0) ? f12 : -f12;
                    double f13dir = (q1 * q3 > 0) ? f13 : -f13;
                    double resultado = Math.abs(f12dir + f13dir);
                    JOptionPane.showMessageDialog(this, String.format("Resultado: %.4f N", resultado));
                    return;
                }
                return;
            }


            q1 *= 1e-6;
            q2 *= 1e-6;
            double resultado = calcularForca(q1, q2, d);
            JOptionPane.showMessageDialog(this, String.format("Resultado: %.4f N", resultado));

        } catch (NumberFormatException ex) {
            showError("Insira apenas números reais válidos nos campos.");
        }
    }

    // Validando os valores das cargas:
    
    private boolean validaCarga(double q) {
        return q != 0 && q >= Q_MIN && q <= Q_MAX;
    }

    // Calcula a força resultante entre q1 e q2:
    
    private double calcularForca(double q1, double q2, double d) {
        return K * Math.abs(q1 * q2) / (d * d);
    }
    
    // Abre um arquivo e carrega os dados
    
    private void abrirArquivo() {
        JFileChooser chooser = new JFileChooser();
        int retorno = chooser.showOpenDialog(this);
        if (retorno == JFileChooser.APPROVE_OPTION) {
            File arquivo = chooser.getSelectedFile();

            try {
                java.util.List<String> linhas = Files.readAllLines(arquivo.toPath());

                if (linhas.size() < 4) {
                    showError("Arquivo inválido: deve conter pelo menos 4 linhas com os valores Q1, Q2, Q3 e distância.");
                    return;
                }

                q1Input.setText(linhas.get(0).trim());
                q2Input.setText(linhas.get(1).trim());
                q3Input.setText(linhas.get(2).trim());
                distanciaInput.setText(linhas.get(3).trim());

            } catch (IOException ex) {
                showError("Erro ao ler arquivo: " + ex.getMessage());
            }
        }
    }
    
    // Mostra uma janela de como usar a calculadora:

    private void mostrarAjuda() {
        String mensagem = 
            """
            Como usar a Calculadora de Eletrostática:
            
            - Insira os valores de Q1, Q2 e Q3 em microcoulombs (μC), entre -1.000.000 e 1.000.000 (diferentes de zero).
            - Insira a distância em metros (m), maior que 0.001 e menor que 1000.
            - Selecione o tipo de cálculo.
            - Clique em 'Calcular' para ver o resultado.
            
            Limitações dos dados:
            - Cargas (Q1, Q2, Q3): devem ser diferentes de zero e dentro do intervalo permitido para evitar erros.
            - Distância: deve estar entre 0.001 m e 1000 m.
            
            Descrição dos cálculos:
            - Força entre Q1 e Q2: cá1culo direto da força de Coulomb.
            - Força resultante (triângulo): soma vetorial considerando um ângulo de 60º entre forças.
            - Força resultante (linha reta): soma das forças considerando atração e repulsão na mesma linha.
            """;

        JOptionPane.showMessageDialog(this, mensagem, "Ajuda", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        new CalculadoraEletrostatica();
    }
}
