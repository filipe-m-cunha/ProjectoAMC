# ProjectoAMC
Código Java para o projeto da cadeira de Algoritmos e Modelação Computacional - Algoritmo de aprendizagem em grafos (Chow Liu) com base em Campos de Markov aleatórios para aprendizagem automática.

## Utilização do algoritmo

### 1. Criação do Conjunto de Dados

1.1 Obter filepath do conjunto de dados que se pretende treinar;

1.2 Criar Dataset a partir desse conjunto de dados: 

    1.2.1 Dataset data = new Dataset(filepath);

### 2. Criar e treinar classificador

2.1 Na classe do classificador já está implementada toda a lógica por detrás do algoritmo, pelo que basta escolher um valor para $\delta$ (para datasets grandes, quanto menor melhor - rule of thumb) e, possivelmente, para a origem do grafo, e criar o classificador com base nisso:

    2.1.2 Classifier classificador = new Classifier(data, delta);

    2.1.2 (no caso de se ter uma origem específica em mente) Classifier classificador = new Classifier(data, origem, delta)

### 3. Classificação

3.1 Tendo já o classificador treinado, dado um vetor u que se pretenda classificar, basta correr:

    3.1.1 classifier.classify(u);

3.2 Para obter estatíticas do classificador (accuracy, recall, prevalence threshold, F1 score), correr:

    3.2.1 classifier.getAccuracyBin()
    
    Note-se que esta última função foi criada tendo em mente dados binários, mas o classificador pode ser usado para classificar qualquer outro tipo de dados.
