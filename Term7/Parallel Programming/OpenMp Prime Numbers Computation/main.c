#include <stdio.h>
#include <math.h>
#include <omp.h>
#include <sys/time.h>
#include <stdint.h>
#include <stdlib.h>

int compare(const void *a, const void *b) // compare function for sorting
{
    return (*(int *)a - *(int *)b);
}

double currentTimeMillis() // function to get current time in milliseconds
{
    struct timeval time;
    gettimeofday(&time, NULL);
    double s1 = (time.tv_sec) * 1000;
    double s2 = ((double)time.tv_usec / 1000);
    return s1 + s2;
}

void parallelstatic(int chunkSize, int N) // function to find prime numbers using static scheduling
{
    int prime[(N / 2) + 1]; // array cannot have more than (N/2)+1 prime numbers
    int tid;                // thread id
    int j;                  // index of prime array
    int k;                  // index of prime array for checking if a number is prime
    int n;                  // number to be checked
    int quo, rem;           // quotient and remainder
    int serialN;            // square root of N
    int broken = 0;         // flag to check if a number is prime
    int chunk = chunkSize;  // chunk size for static scheduling

    prime[0] = 2;
    n = 3;
    j = 1;
    prime[j] = n;
    serialN = sqrt(N);

    for (n = 5; n <= N; n += 2)
    {
        k = 1;
        quo = n / prime[k];
        rem = n % prime[k]; // finds quotient and remainder firstly
        if (rem != 0)       // if n is divisible by a prime number, rem will be 0, so n is not prime
        {
            while (quo > prime[k]) // if n is greater than prime[k]**2 continue loop else n is prime
            {
                k += 1;
                quo = n / prime[k];
                rem = n % prime[k];
                if (rem == 0) // if divisible by a prime number, n is not prime and loop breaks.
                {
                    broken = 1;
                    break;
                }
            }
            if (broken == 0)
            {
                j += 1;
                prime[j] = n;
                if (n > serialN) // if ne is greater than square root of n break loop.
                {
                    break; // so that can be calculatable in parallel, we can reach all prime numbers as prime[k] to check new numbers are prime.
                }
            }
            else
            {
                broken = 0; // set broken 0 for next number
            }
        }
    }
    int l = n + 2;

#pragma omp parallel for shared(l, prime, chunk, j) private(n, k, quo, rem, tid) schedule(static, chunk)
    // l is the first number to be checked in parallel
    // prime will be the shared between arrays.
    // chunk is the chunk size for static scheduling
    // j is the index of prime array
    // n is the number to be checked, values of n are distributed in chunks
    // k is the index of prime array for checking if a number is prime
    // quo and rem are quotient and remainder
    // tid is the thread id
    for (n = l; n < N + 1; n += 2)
    {
        tid = omp_get_thread_num();
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
#pragma omp critical(myregion) // to prevent overwriting of prime array
                {

                    j += 1;
                    prime[j] = n;
                }
            }
            else
            {
                broken = 0;
            }
        }
    }
    // sorting prime array and printing it
    /*size_t end_index = j + 1;

    qsort(prime, end_index, sizeof(prime[0]), compare);

    for (int k = 0; k < j + 1; k++)
        printf("%d\n", prime[k]);*/
}

void paralleldynamic(int chunkSize, int N)
{
    int prime[(N / 2) + 1];
    int tid;
    int j;
    int k;
    int n;
    int quo, rem;
    int serialN;
    int broken = 0;
    int chunk = chunkSize;

    prime[0] = 2;
    n = 3;
    j = 1;
    prime[j] = n;
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
                prime[j] = n;
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
    int l = n;

#pragma omp parallel for shared(l, prime, chunk, j) private(n, k, quo, rem, tid) schedule(dynamic, chunk)
    for (n = l; n < N + 1; n += 2)
    {
        tid = omp_get_thread_num();
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
#pragma omp critical(myregion)
                {

                    j += 1;
                    prime[j] = n;
                }
            }
            else
            {
                broken = 0;
            }
        }
    }

    /*size_t end_index = j + 1;

    qsort(prime, end_index, sizeof(prime[0]), compare);

    for (int k = 0; k < j + 1; k++)
        printf("%d\n", prime[k]);*/
}

void parallelguided(int chunkSize, int N)
{
    int prime[(N / 2) + 1];
    int tid;
    int j;
    int k;
    int n;
    int quo, rem;
    int serialN;
    int broken = 0;
    int chunk = chunkSize;

    prime[0] = 2;
    n = 3;
    j = 1;
    prime[j] = n;
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
                prime[j] = n;
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
    int l = n;

#pragma omp parallel for shared(l, prime, chunk, j) private(n, k, quo, rem, tid) schedule(guided, chunk)
    for (n = l; n < N + 1; n += 2)
    {
        tid = omp_get_thread_num();
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
#pragma omp critical(myregion)
                {

                    j += 1;
                    prime[j] = n;
                }
            }
            else
            {
                broken = 0;
            }
        }
    }

    /*size_t end_index = j + 1;

    qsort(prime, end_index, sizeof(prime[0]), compare);

    for (int k = 0; k < j + 1; k++)
        printf("%d\n", prime[k]);*/
}

int main()
{
    int intervalSize[4] = {10000, 50000, 100000, 1000000}; // maximum number to be checked
    int chunkSize[5] = {100, 1000, 5000, 10000, 100000};   // chunk sizes

    // int intervalSize[2] = {10, 100}; sample values for testing
    // int chunkSize[2] = {3, 10};
    FILE *fpt; // file pointer to write results to csv file
    fpt = fopen("results.csv", "w+");
    fprintf(fpt, "M, Openmp Loop Scheduling Method, Chunk Size, T1(ms), T2(ns), T4(ms), T8(ms), S2, S4, S8\n");

    double t;
    double time_taken1;
    double time_taken2;
    double time_taken3;
    double time_taken4;
    double time_taken5;
    double time_taken6;
    double time_taken7;
    double time_taken8;
    double time_taken9;
    double time_taken10;
    double time_taken11;
    double time_taken12;
    double s2;
    double s4;
    double s8;

    omp_set_dynamic(0);

    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 5; j++)
        {
            omp_set_num_threads(1);

            t = currentTimeMillis();
            parallelstatic(chunkSize[j], intervalSize[i]);
            t = currentTimeMillis() - t;
            time_taken1 = t;

            omp_set_num_threads(2);

            t = currentTimeMillis();
            parallelstatic(chunkSize[j], intervalSize[i]);
            t = currentTimeMillis() - t;
            time_taken2 = t;

            omp_set_num_threads(4);

            t = currentTimeMillis();
            parallelstatic(chunkSize[j], intervalSize[i]);
            t = currentTimeMillis() - t;
            time_taken3 = t;

            omp_set_num_threads(8);

            t = currentTimeMillis();
            parallelstatic(chunkSize[j], intervalSize[i]);
            t = currentTimeMillis() - t;
            time_taken4 = t;
            s2 = time_taken1 / time_taken2;
            s4 = time_taken1 / time_taken3;
            s8 = time_taken1 / time_taken4;

            fprintf(fpt, "%d, Static, %d, %f, %f, %f, %f, %f, %f, %f\n", intervalSize[i], chunkSize[j], time_taken1, time_taken2, time_taken3, time_taken4, s2, s4, s8);

            omp_set_num_threads(1);

            t = currentTimeMillis();
            paralleldynamic(chunkSize[j], intervalSize[i]);
            t = currentTimeMillis() - t;
            time_taken1 = t;

            omp_set_num_threads(2);

            t = currentTimeMillis();
            paralleldynamic(chunkSize[j], intervalSize[i]);
            t = currentTimeMillis() - t;
            time_taken2 = t;

            omp_set_num_threads(4);

            t = currentTimeMillis();
            paralleldynamic(chunkSize[j], intervalSize[i]);
            t = currentTimeMillis() - t;
            time_taken3 = t;

            omp_set_num_threads(8);

            t = currentTimeMillis();
            paralleldynamic(chunkSize[j], intervalSize[i]);
            t = currentTimeMillis() - t;
            time_taken4 = t;

            s2 = time_taken1 / time_taken2;
            s4 = time_taken1 / time_taken3;
            s8 = time_taken1 / time_taken4;

            fprintf(fpt, "%d, Dynamic, %d, %f, %f, %f, %f, %f, %f, %f,\n", intervalSize[i], chunkSize[j], time_taken1, time_taken2, time_taken3, time_taken4, s2, s4, s8);

            omp_set_num_threads(1);

            t = currentTimeMillis();
            parallelguided(chunkSize[j], intervalSize[i]);
            t = currentTimeMillis() - t;
            time_taken1 = t;

            omp_set_num_threads(2);

            t = currentTimeMillis();
            parallelguided(chunkSize[j], intervalSize[i]);
            t = currentTimeMillis() - t;
            time_taken2 = t;

            omp_set_num_threads(4);

            t = currentTimeMillis();
            parallelguided(chunkSize[j], intervalSize[i]);
            t = currentTimeMillis() - t;
            time_taken3 = t;

            omp_set_num_threads(8);

            t = currentTimeMillis();
            parallelguided(chunkSize[j], intervalSize[i]);
            t = currentTimeMillis() - t;
            time_taken4 = t;

            s2 = time_taken1 / time_taken2;
            s4 = time_taken1 / time_taken3;
            s8 = time_taken1 / time_taken4;

            fprintf(fpt, "%d, Guided, %d, %f, %f, %f, %f, %f, %f, %f,\n", intervalSize[i], chunkSize[j], time_taken1, time_taken2, time_taken3, time_taken4, s2, s4, s8);
        }
    }

    fclose(fpt);

    return 0;
}
