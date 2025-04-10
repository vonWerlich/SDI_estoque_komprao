# GNU Makefile
JAR=/usr/local/jdk1.8.0_131/bin/jar
JAVA=/usr/local/jdk1.8.0_131/bin/java
JAVAC=/usr/local/jdk1.8.0_131/bin/javac

#JAVA=java
#JAVAC=javac

SRC_DIR=src
BIN_DIR=bin

# Encontrar todos os .java recursivamente
SOURCES=$(shell find $(SRC_DIR) -name "*.java")

# Alvo padrão
all: ServerEstoque ServerServidor Caixa

ServerEstoque: ServerEstoque.class
	$(JAVA) -cp $(BIN_DIR) server.ServerEstoque 

ServerServidor: ServerServidor.class
	$(JAVA) -cp $(BIN_DIR) server.ServerServidor

Caixa: Caixa.class
	$(JAVA) -cp $(BIN_DIR) caixa.Caixa

# Compilar os .java para .class dentro de bin/
compile:
	mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) $(SOURCES)

# Rodar o programa
estoque: 
	java -cp $(BIN_DIR) server.ServerEstoque 

servidor:
	java -cp $(BIN_DIR) server.ServerServidor

caixa:
	java -cp $(BIN_DIR) caixa.Caixa
	
# Limpar os .class
clean:
	rm -rf $(BIN_DIR)
