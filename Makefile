# GNU Makefile
JAR=/usr/local/jdk1.8.0_131/bin/jar
JAVA=/usr/local/jdk1.8.0_131/bin/java
JAVAC=/usr/local/jdk1.8.0_131/bin/javac

#JAVA=java
#JAVAC=javac

SRC_DIR_KOMPRAO=komprao/src
SRC_DIR_FORNECEDOR=fornecedor/src
SRC_DIR_TRANSPORTADORA=transportadora/src
UTILS=utils

BIN_DIR=bin

# Encontrar todos os .java recursivamente
SOURCES_KOMPRAO=$(shell find $(SRC_DIR_KOMPRAO) -name "*.java")
SOURCES_FORNECEDOR=$(shell find $(SRC_DIR_FORNECEDOR) -name "*.java")
SOURCES_TRANSPORTADORA=$(shell find $(SRC_DIR_TRANSPORTADORA) -name "*.java")
SOURCE_UTILS=$(shell find $(UTILS) -name "*.java")

# Alvo padrão
all: ServerEstoque ServerServidor Caixa

ServerEstoque: ServerEstoque.class
	$(JAVA) -cp $(BIN_DIR) server.ServerEstoque 

ServerServidor: ServerServidor.class
	$(JAVA) -cp $(BIN_DIR) server.ServerServidor

Caixa: Caixa.class
	$(JAVA) -cp $(BIN_DIR) caixa.Caixa

Fornecedor: FornecedorServerPublisher.class
	$(JAVA) -cp  $(BIN_DIR) caixa.FornecedorServerPublisher

Transportadora: RecvQueueCarrier.class
	${JAVA} -cp libs/amqp-client-5.16.0.jar ${BIN_DIR} transportadora.RecvQueueCarrier

# Compilar os .java para .class dentro de bin/
compile:
	mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) $(SOURCE_UTILS)
	javac -d $(BIN_DIR) $(SOURCES_KOMPRAO)
	javac -cp libs/amqp-client-5.16.0.jar  -d $(BIN_DIR) $(SOURCES_FORNECEDOR)
	javac -cp libs/amqp-client-5.16.0.jar  -d ${BIN_DIR} ${SOURCES_TRANSPORTADORA}

# Rodar o programa
estoque: 
	java -cp $(BIN_DIR) server.ServerEstoque 

servidor:
	java -cp $(BIN_DIR) server.ServerServidor

caixa:
	java -cp $(BIN_DIR) caixa.Caixa

fornecedor:
	java -cp $(BIN_DIR) fornecedor.FornecedorServerPublisher

transportadora:
	java -cp ${BIN_DIR} transportadora.RecvQueueCarrier
	
# Limpar os .class
clean:
	rm -rf $(BIN_DIR)
