all: nonthread thread-5 thread-10 thread-free


nonthread: nonthread.cpp
	g++ nonthread.cpp -o nonthread.o

thread-5: thread-5.cpp
	g++ thread-5.cpp -o thread-5.o -pthread

thread-10: thread-10.cpp
	g++ thread-10.cpp -o thread-10.o -pthread

thread-free: thread-free.cpp
	g++ thread-free.cpp -o thread-free.o -pthread