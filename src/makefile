default: build

build: build/Player.class build/Score.class build/Ball.class
	
test: build
	java -cp build;classes; Pong

clean:
	del build\*.* /Q

build/Player.class: src/Player.java
	javac src/Player.java -d build --class-path classes

build/Score.class: src/Score.java
	javac src/Score.java -d build --class-path classes

build/Ball.class: src/Ball.java
	javac src/Ball.java -d build --class-path classes