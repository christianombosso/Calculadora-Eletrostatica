# ⚡ Calculadora de Eletrostática em Java

Uma calculadora desenvolvida em Java com interface gráfica (JFrame), voltada para resolver problemas de força elétrica entre cargas pontuais com base na Lei de Coulomb. Ideal para estudantes de física e eletricidade, ela permite realizar diferentes tipos de cálculos com cargas elétricas em microcoulombs (μC), incluindo casos com mais de duas cargas.

Diferente de exemplos básicos com valores fixos, esta calculadora é dinâmica: o usuário pode inserir qualquer valor dentro dos limites definidos, testar cenários e visualizar os resultados de forma intuitiva.

## 📸 Interface da Calculadora

<img src="/interface1.png" alt="Interface da calculadora" width="400"/>

### Exemplo de resultado:

<img src="/resultado1.png" alt="Resultado do cálculo" width="300"/>

### Exemplo de erro:

<img src="/erro1.png" alt="Mensagem de erro" width="300"/>


## ⚙️ Tipos de cálculo disponíveis

- **Força entre Q1 e Q2:**  
  Calcula diretamente a força elétrica entre duas cargas usando a fórmula da Lei de Coulomb.

- **Força resultante (triângulo):**  
  Considera Q1 interagindo com Q2 e Q3, dispostos formando um triângulo com ângulo de 60°, realizando soma vetorial das forças.

- **Força resultante (linha reta):**  
  Considera as três cargas alinhadas numa reta, somando as forças com base em atração e repulsão.

## 🧑‍💻 Como utilizar?

- **Q1, Q2 e Q3:** cargas elétricas em μC (microcoulombs). Valores entre -1.000.000 e 1.000.000, exceto zero.
- **Distância:** distância entre as cargas em metros. Deve ser maior que 0,001 m e menor que 1000 m.
- **Tipo de cálculo:** escolha entre os três disponíveis usando a caixa de seleção.
- **Arquivo externo:** também é possível carregar um arquivo `.txt` com os valores de Q1, Q2, Q3 e distância, **nessa ordem exata**, cada um em uma linha.

> ⚠️ Se o arquivo possuir menos de 4 linhas, o programa exibirá uma mensagem de erro.

## 🆘 Página de ajuda integrada

A calculadora possui um menu de **Ajuda**, que exibe orientações detalhadas sobre:

- Como preencher os campos corretamente
- Limitações dos valores
- Diferença entre os tipos de cálculo disponíveis

Essa ajuda pode ser acessada a qualquer momento pelo menu superior da interface.

## 🚀 Funcionalidades

- ✅ Interface gráfica (Swing)
- ✅ Três tipos de cálculos com forças elétricas
- ✅ Validação dos dados inseridos
- ✅ Suporte a carregamento de arquivos `.txt`
- ✅ Ajuda embutida explicando o funcionamento
- ✅ Tratamento de erros com mensagens amigáveis

## 🖥️ IDE Utilizada

- NetBeans
