To run MPI Code, use the commands

- make
- make run

To run Thrust Code use the commands

g++ -O2 -o saxpy thrust.cpp -fopenmp -DTHRUST_DEVICE_SYSTEM=THRUST_DEVICE_SYSTEM_OMP -lgomp -I /usr/local/cuda-12.3/targets/x86_64-linux/include
./saxpy