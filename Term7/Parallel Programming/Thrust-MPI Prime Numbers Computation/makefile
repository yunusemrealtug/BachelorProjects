CC = mpicc
LIBS = -lm

TARGET = a.out
SOURCE = mpi.c

.PHONY: all run clean calc_speedup

all: $(TARGET) run

$(TARGET): $(SOURCE)
	$(CC) -o $@ $< $(LIBS)

run: $(TARGET) calc_speedup

mpirun_opts := 1 2 4 6

calc_speedup: $(TARGET)
	@for np in $(mpirun_opts); do \
		echo "Running with $$np processes"; \
		mpirun -n $$np ./$(TARGET); \
	done
	@echo "Calculating speedup..."
	gcc -o calcSpeedUp calcSpeedUp.c
	./calcSpeedUp

clean:
	rm -f $(TARGET) calcSpeedUp
