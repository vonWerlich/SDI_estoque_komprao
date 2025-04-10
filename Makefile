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

# Classe com o método main (ajuste conforme necessário)
MAIN_CLASS=caixa.Caixa

# Alvo padrão
all: compile

# Compilar os .java para .class dentro de bin/
compile:
	mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) $(SOURCES)

# Rodar o programa
run: compile
	java -cp $(BIN_DIR) $(MAIN_CLASS)
	
# Limpar os .class
clean:
	rm -rf $(BIN_DIR)
