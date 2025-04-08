# GNU Makefile
JAR=/usr/local/jdk1.8.0_131/bin/jar
#JAVA=/usr/local/jdk1.8.0_131/bin/java
#JAVAC=/usr/local/jdk1.8.0_131/bin/javac

JAVA=java
JAVAC=javac

JFLAGS = -g 
.SUFFIXES: .java .class
.java.class:
	$(JAVAC) $(JFLAGS) $*.java

CLASSES = \
	Caixa.java\
	IEstoque.java\
	IServidor.java\
	ServerEstoque.java\
	ServerServidor.java\

default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm -f *.class 