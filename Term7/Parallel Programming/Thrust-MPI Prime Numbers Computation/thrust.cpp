#include <iostream>
#include <cmath>
#include <thrust/host_vector.h>
#include <thrust/device_vector.h>
#include <thrust/execution_policy.h>
#include <thrust/copy.h>
#include <thrust/remove.h>
#include <thrust/sequence.h>
#include <sys/time.h>

double currentTimeMillis() // function to get current time in milliseconds
{
    struct timeval time;
    gettimeofday(&time, NULL);
    double s1 = (time.tv_sec) * 1000;
    double s2 = ((double)time.tv_usec / 1000);
    return s1 + s2;
}

struct is_prime_functor
{
    thrust::device_vector<int> primes;

    __host__ __device__
    is_prime_functor(thrust::device_vector<int> &_primes) : primes(_primes) {}

    __host__ __device__ bool operator()(int n)

    {
        int k = 1;
        int quo = n / primes[k];
        int rem = n % primes[k];
        int broken = 0;

        if (rem != 0)
        {
            while (quo > primes[k])
            {
                k += 1;
                quo = n / primes[k];
                rem = n % primes[k];

                if (rem == 0)
                {
                    broken = 1;
                    return true;
                }
            }

            if (broken == 0)
            {
                return false;
            }
        }
        return true;
    }
};

void parallel(int N)

{

    thrust::device_vector<int> prime;
    ;

    int j;
    int k;
    int n;
    int quo, rem;
    int serialN;
    int broken = 0;
    int l;
    int totalCount = 0;

    prime.push_back(2);
    n = 3;
    j = 1;
    prime.push_back(n);
    serialN = sqrt(N);

    for (n = 5; n <= N; n += 2)
    {
        k = 1;
        quo = n / prime[k];
        rem = n % prime[k];

        if (rem != 0)
        {
            while (quo > prime[k])
            {
                k += 1;
                quo = n / prime[k];
                rem = n % prime[k];

                if (rem == 0)
                {
                    broken = 1;
                    break;
                }
            }

            if (broken == 0)
            {
                j += 1;
                prime.push_back(n);

                if (n > serialN)
                {
                    break;
                }
            }
            else
            {
                broken = 0;
            }
        }
    }

    broken = 0;
    l = n + 2;

    thrust::device_vector<int> numbers(N / 2);

    thrust::copy(thrust::device, prime.begin(), prime.end(), numbers.begin());

    thrust::sequence(numbers.begin() + j + 1, numbers.end(), l, 2);
    thrust::remove_if(thrust::device, numbers.begin() + j + 1, numbers.end(), is_prime_functor(numbers));

    /*
    std::cout << "Sorted \n";
    int ind = 0;
    while (numbers[ind] < N)
    {
        std::cout << numbers[ind] << " ";
        ind++;
    }
    */
}

int main()
{
    FILE *fpt; // file pointer to write results to csv file
    fpt = fopen("resultsThrust.csv", "a+");
    fprintf(fpt, "M, T1(ms), T2(ms), T4(ms), T8(ms), S2, S4, S8\n");

    int intervalSize[7] = {1000, 10000, 50000, 100000, 1000000, 10000000, 100000000};
    int threads[4] = {1, 2, 4, 8};
    for (int i = 0; i < 7; i++)
    {
        omp_set_num_threads(1);
        double start = currentTimeMillis();
        parallel(intervalSize[i]);
        double end = currentTimeMillis();
        double time_taken1 = end - start;

        omp_set_num_threads(2);
        double start1 = currentTimeMillis();
        parallel(intervalSize[i]);
        double end1 = currentTimeMillis();
        double time_taken2 = end1 - start1;

        omp_set_num_threads(4);
        double start2 = currentTimeMillis();
        parallel(intervalSize[i]);
        double end2 = currentTimeMillis();
        double time_taken3 = end2 - start2;

        omp_set_num_threads(8);
        double start3 = currentTimeMillis();
        parallel(intervalSize[i]);
        double end3 = currentTimeMillis();
        double time_taken4 = end3 - start3;

        fprintf(fpt, "%d, %f, %f, %f, %f, %f, %f, %f\n", intervalSize[i], time_taken1, time_taken2, time_taken3, time_taken4, time_taken1 / time_taken2, time_taken1 / time_taken3, time_taken1 / time_taken4);
    }
    fclose(fpt);

    return 0;
}
