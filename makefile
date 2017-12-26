# super simple makefile
# call it using 'make NAME=name_of_code_file_without_extension'
# (assumes a .java extension)

NAME = "Main"
all:
	# (a bit of a hack to compile everything each time ...)
	javac -cp vecmath.jar drawing/*.java
	@echo "Compiling..."

	
run: all
# windows needs a semicolon
		@echo "Running ..."
		java -cp "vecmath.jar:." drawing.Main


clean:
	rm -rf drawing/*.class